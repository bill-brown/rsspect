/**
 * Copyright (C) 2009 William R. Brown <wbrown@colorfulsoftware.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package com.colorfulsoftware.rss;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * A channel may contain any number of <item>s. An item may represent a "story"
 * -- much like a story in a newspaper or magazine; if so its description is a
 * synopsis of the story, and the link points to the full story. An item may
 * also be complete in itself, if so, the description contains the text
 * (entity-encoded HTML is allowed; see examples), and the link and title may be
 * omitted. All elements of an item are optional, however at least one of title
 * or description must be present.
 * 
 * @author Bill Brown
 * 
 */
public class Channel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6193654133883709775L;

	/* required fields */
	private final Title title;

	private final Link link;

	private final Description description;

	/* optional fields */
	private final Language language;

	private final Copyright copyright;

	private final ManagingEditor managingEditor;

	private final WebMaster webMaster;

	private final PubDate pubDate;

	private final LastBuildDate lastBuildDate;

	private final List<Category> categories;

	private final Generator generator;

	private final Docs docs;

	private final Cloud cloud;

	private final TTL ttl;

	private final Image image;

	private final Rating rating;

	private final TextInput textInput;

	private final SkipHours skipHours;

	private final SkipDays skipDays;

	private final List<Item> items;

	private final List<Extension> extensions;

	Channel(Title title, Link link, Description description, Language language,
			Copyright copyright, ManagingEditor managingEditor,
			WebMaster webMaster, PubDate pubDate, LastBuildDate lastBuildDate,
			List<Category> categories, Generator generator, Docs docs,
			Cloud cloud, TTL ttl, Image image, Rating rating,
			TextInput textInput, SkipHours skipHours, SkipDays skipDays,
			List<Extension> extensions, List<Item> items)
			throws RSSpectException {

		// make sure title is present
		if (title == null) {
			throw new RSSpectException(
					"channel elements MUST contain a title element.");
		}
		this.title = new Title(title.getTitle());

		// make sure link is present
		if (link == null) {
			throw new RSSpectException(
					"channel elements MUST contain a link element.");
		}
		this.link = new Link(link.getLink());

		// make sure description is present
		if (description == null) {
			throw new RSSpectException(
					"channel elements MUST contain a description element.");
		}
		this.description = new Description(description.getDescription());

		this.language = (language == null) ? null : new Language(language
				.getLanguage());
		this.copyright = (copyright == null) ? null : new Copyright(copyright
				.getCopyright());
		this.managingEditor = (managingEditor == null) ? null
				: new ManagingEditor(managingEditor.getManagingEditor());
		this.webMaster = (webMaster == null) ? null : new WebMaster(webMaster
				.getWebMaster());
		this.pubDate = (pubDate == null) ? null : new PubDate(pubDate
				.getDateTime());
		this.lastBuildDate = (lastBuildDate == null) ? null
				: new LastBuildDate(lastBuildDate.getDateTime());
		if (categories == null) {
			this.categories = null;
		} else {
			this.categories = new LinkedList<Category>();
			for (Category category : categories) {
				this.categories.add(new Category(category.getDomain(), category
						.getCategory()));
			}
		}
		this.generator = (generator == null) ? null : new Generator(generator
				.getGenerator());
		this.docs = (docs == null) ? null : new Docs(docs.getDocs());
		this.cloud = (cloud == null) ? null : new Cloud(cloud.getAttributes());
		this.ttl = (ttl == null) ? null : new TTL(ttl.getTtl());
		this.image = (image == null) ? null : new Image(image.getUrl(), image
				.getTitle(), image.getLink(), image.getWidth(), image
				.getHeight(), image.getDescription());
		this.rating = (rating == null) ? null : new Rating(rating.getRating());
		this.textInput = (textInput == null) ? null : new TextInput(textInput
				.getTitle(), textInput.getDescription(), textInput.getName(),
				textInput.getLink());
		this.skipHours = (skipHours == null) ? null : new SkipHours(skipHours
				.getSkipHours());
		this.skipDays = (skipDays == null) ? null : new SkipDays(skipDays
				.getSkipDays());

		if (items == null) {
			this.items = null;
		} else {
			this.items = new LinkedList<Item>();
			for (Item item : items) {
				this.items.add(new Item(item.getTitle(), item.getLink(), item
						.getDescription(), item.getAuthor(), item
						.getCategories(), item.getComments(), item
						.getEnclosure(), item.getGuid(), item.getPubDate(),
						item.getSource(), item.getExtensions()));
			}
		}

		if (extensions == null) {
			this.extensions = null;
		} else {
			this.extensions = new LinkedList<Extension>();
			for (Extension extension : extensions) {
				this.extensions.add(new Extension(extension.getElementName(),
						extension.getAttributes(), extension.getContent()));
			}
		}
	}

	/**
	 * @return the title.
	 */
	public Title getTitle() {
		return new Title(title.getTitle());
	}

	/**
	 * @return the link element.
	 */
	public Link getLink() {
		try {
			return new Link(link.getLink());
		} catch (Exception e) {
			// we should never get here.
			return null;
		}
	}

	/**
	 * @return the description element.
	 */
	public Description getDescription() {
		return new Description(description.getDescription());
	}

	/**
	 * @return the language element.
	 */
	public Language getLanguage() {
		return (language == null) ? null : new Language(language.getLanguage());
	}

	/**
	 * @return the copyright element
	 */
	public Copyright getCopyright() {
		return (copyright == null) ? null : new Copyright(copyright
				.getCopyright());
	}

	/**
	 * @return the managing editor element.
	 */
	public ManagingEditor getManagingEditor() {
		return (managingEditor == null) ? null : new ManagingEditor(
				managingEditor.getManagingEditor());
	}

	/**
	 * @return the webmaster element.
	 */
	public WebMaster getWebMaster() {
		return (webMaster == null) ? null : new WebMaster(webMaster
				.getWebMaster());
	}

	/**
	 * @return the pubDate element.
	 */
	public PubDate getPubDate() {
		return (pubDate == null) ? null : new PubDate(pubDate.getDateTime());
	}

	/**
	 * @return the last build date element.
	 */
	public LastBuildDate getLastBuildDate() {
		return (lastBuildDate == null) ? null : new LastBuildDate(lastBuildDate
				.getDateTime());
	}

	/**
	 * @return the list of categories.
	 */
	public List<Category> getCategories() {
		if (categories == null) {
			return null;
		} else {
			List<Category> catsCopy = new LinkedList<Category>();
			for (Category category : this.categories) {
				try {
					catsCopy.add(new Category(category.getDomain(), category
							.getCategory()));
				} catch (Exception e) {
					// we should never get here.
					return null;
				}
			}
			return catsCopy;
		}
	}

	/**
	 * @return the generator element.
	 */
	public Generator getGenerator() {
		return (generator == null) ? null : new Generator(generator
				.getGenerator());
	}

	/**
	 * @return the docs element.
	 */
	public Docs getDocs() {
		return (docs == null) ? null : new Docs(docs.getDocs());
	}

	/**
	 * @return the cloud element.
	 */
	public Cloud getCloud() {
		try {
			return (cloud == null) ? null : new Cloud(cloud.getAttributes());
		} catch (Exception e) {
			// we should never get here.
			return null;
		}
	}

	/**
	 * @return the ttl element.
	 */
	public TTL getTtl() {
		return (ttl == null) ? null : new TTL(ttl.getTtl());
	}

	/**
	 * @return the image element.
	 */
	public Image getImage() {
		try {
			return (image == null) ? null : new Image(image.getUrl(), image
					.getTitle(), image.getLink(), image.getWidth(), image
					.getHeight(), image.getDescription());
		} catch (Exception e) {
			// we should never get here.
			return null;
		}
	}

	/**
	 * @return the rating element.
	 */
	public Rating getRating() {
		return (rating == null) ? null : new Rating(rating.getRating());
	}

	/**
	 * @return the textInput element.
	 */
	public TextInput getTextInput() {
		try {
			return (textInput == null) ? null : new TextInput(textInput
					.getTitle(), textInput.getDescription(), textInput
					.getName(), textInput.getLink());
		} catch (Exception e) {
			// we should never get here.
			return null;
		}
	}

	/**
	 * @return the skipHours element.
	 */
	public SkipHours getSkipHours() {
		try {
			return (skipHours == null) ? null : new SkipHours(skipHours
					.getSkipHours());
		} catch (Exception e) {
			// we should never get here.
			return null;
		}
	}

	/**
	 * @return the skipDays element.
	 */
	public SkipDays getSkipDays() {
		try {
			return (skipDays == null) ? null : new SkipDays(skipDays
					.getSkipDays());
		} catch (Exception e) {
			// we should never get here.
			return null;
		}
	}

	/**
	 * @return the list of items.
	 */
	public List<Item> getItems() {
		if (items == null) {
			return null;
		} else {
			List<Item> itemsCopy = new LinkedList<Item>();
			for (Item item : this.items) {
				try {
					itemsCopy.add(new Item(item.getTitle(), item.getLink(),
							item.getDescription(), item.getAuthor(), item
									.getCategories(), item.getComments(), item
									.getEnclosure(), item.getGuid(), item
									.getPubDate(), item.getSource(), item
									.getExtensions()));
				} catch (Exception e) {
					// we should never get here.
					return null;
				}
			}
			return itemsCopy;
		}
	}

	/**
	 * 
	 * @return the extensions for this entry.
	 */
	public List<Extension> getExtensions() {
		if (extensions == null) {
			return null;
		}
		List<Extension> extsCopy = new LinkedList<Extension>();
		for (Extension extension : this.extensions) {
			try {
				extsCopy.add(new Extension(extension.getElementName(),
						extension.getAttributes(), extension.getContent()));
			} catch (Exception e) {
				// we should never get here.
				return null;
			}
		}
		return extsCopy;
	}

	/**
	 * @param catValue
	 * @return the category name matching this item or null if not found.
	 */
	public Category getCategory(String catValue) {
		if (this.categories != null) {
			for (Category category : this.categories) {
				if (category.getCategory() != null
						&& category.getCategory().equals(catValue)) {
					try {
						return new Category(category.getDomain(), category
								.getCategory());
					} catch (Exception e) {
						// we should never get here.
						return null;
					}
				}
			}
		}
		return null;
	}

	/**
	 * @param titleOrDescription
	 * @return the item with this title or description. returns null if not
	 *         found.
	 * @throws RSSpectException
	 */
	public Item getItem(String titleOrDescription) throws RSSpectException {
		if (this.items != null) {
			for (Item item : this.items) {
				if ((item.getTitle() != null
						&& item.getTitle().getTitle() != null && item
						.getTitle().getTitle().equals(titleOrDescription))
						|| (item.getDescription() != null && item
								.getDescription().getDescription().equals(
										titleOrDescription))) {
					return new Item(item.getTitle(), item.getLink(), item
							.getDescription(), item.getAuthor(), item
							.getCategories(), item.getComments(), item
							.getEnclosure(), item.getGuid(), item.getPubDate(),
							item.getSource(), item.getExtensions());
				}
			}
		}
		return null;
	}

	/**
	 * @param extName
	 * @return the extension matching the element or null if not found.
	 */
	public Extension getExtension(String extName) {
		if (this.extensions != null) {
			for (Extension extension : this.extensions) {
				if (extension.getElementName().equals(extName)) {
					try {
						return new Extension(extension.getElementName(),
								extension.getAttributes(), extension
										.getContent());
					} catch (Exception e) {
						// we should never get here.
						return null;
					}
				}
			}
		}
		return null;
	}
}
