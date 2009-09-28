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
 * A channel may contain any number of <item>s. An item may represent a "story"
 * -- much like a story in a newspaper or magazine; if so its description is a
 * synopsis of the story, and the link points to the full story. An item may
 * also be complete in itself, if so, the description contains the text
 * (entity-encoded HTML is allowed; see <a
 * href="http://cyber.law.harvard.edu/rss/encodingDescriptions.html"
 * >examples</a>), and the link and title may be omitted. All elements of an
 * item are optional, however at least one of title or description must be
 * present.
 * 
 * @author Bill Brown
 * 
 */
public class Item implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6856122030764773780L;
	/* one of the two of these is required */
	private final Title title;

	private final Description description;

	/* optional elements */
	private final Link link;

	private final Author author;

	private final List<Category> categories;

	private final Comments comments;

	private final Enclosure enclosure;

	private final GUID guid;

	private final PubDate pubDate;

	private final Source source;

	private final List<Extension> extensions;

	Item(Title title, Link link, Description description, Author author,
			List<Category> categories, Comments comments, Enclosure enclosure,
			GUID guid, PubDate pubDate, Source source,
			List<Extension> extensions) throws RSSpectException {

		// make sure title or description is present
		if (title == null && description == null) {
			throw new RSSpectException(
					"item elements MUST contain either a title or description element.");
		}
		this.title = (title == null) ? null : new Title(title.getTitle());

		this.link = (link == null) ? null : new Link(link.getLink());

		this.description = (description == null) ? null : new Description(
				description.getDescription());

		this.author = (author == null) ? null : new Author(author.getAuthor());

		if (categories == null) {
			this.categories = null;
		} else {
			this.categories = new LinkedList<Category>();
			for (Category category : categories) {
				this.categories.add(new Category(category.getDomain(), category
						.getCategory()));
			}
		}

		this.comments = (comments == null) ? null : new Comments(comments
				.getComments());

		this.enclosure = (enclosure == null) ? null : new Enclosure(enclosure
				.getAttributes());

		this.guid = (guid == null) ? null : new GUID(guid.getIsPermaLink(),
				guid.getGuid());

		this.pubDate = (pubDate == null) ? null : new PubDate(pubDate
				.getDateTime());

		this.source = (source == null) ? null : new Source(source.getUrl(),
				source.getSource());

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
	 * @return the title object.
	 */
	public Title getTitle() {
		return (title == null) ? null : new Title(title.getTitle());
	}

	/**
	 * @return the description object.
	 */
	public Description getDescription() {
		return (description == null) ? null : new Description(description
				.getDescription());
	}

	/**
	 * @return the link object.
	 */
	public Link getLink() {
		try {
			return (link == null) ? null : new Link(link.getLink());
		} catch (Exception e) {
			// we should never get here.
			return null;
		}
	}

	/**
	 * @return the author object.
	 */
	public Author getAuthor() {
		return (author == null) ? null : new Author(author.getAuthor());
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
	 * @return the comments object.
	 */
	public Comments getComments() {
		return (comments == null) ? null : new Comments(comments.getComments());
	}

	/**
	 * @return the enclosure object.
	 */
	public Enclosure getEnclosure() {
		try {
			return (enclosure == null) ? null : new Enclosure(enclosure
					.getAttributes());
		} catch (Exception e) {
			// we should never get here.
			return null;
		}
	}

	/**
	 * @return the guid object.
	 */
	public GUID getGuid() {
		return (guid == null) ? null : new GUID(guid.getIsPermaLink(), guid
				.getGuid());
	}

	/**
	 * @return the published date object.
	 */
	public PubDate getPubDate() {
		return (pubDate == null) ? null : new PubDate(pubDate.getDateTime());
	}

	/**
	 * @return the source object.
	 */
	public Source getSource() {
		try {
			return (source == null) ? null : new Source(source.getUrl(), source
					.getSource());
		} catch (Exception e) {
			// we should never get here.
			return null;
		}
	}

	/**
	 * 
	 * @return the list of extensions for this entry.
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
	 * @param catValue the value for the category.
	 * @return the category object if found otherwise null.
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
	 * @param extName
	 *            the element name of the extension. eg. "atom:link" or
	 *            "someExtension"
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
