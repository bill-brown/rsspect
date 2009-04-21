package com.colorful.rss;

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
 * @author billbrown
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
			List<Item> items, List<Extension> extensions)
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
				this.categories.add(new Category(category.getCategory(),
						category.getDomain()));
			}
		}
		this.generator = (generator == null) ? null : new Generator(generator
				.getGenerator());
		this.docs = (docs == null) ? null : new Docs(docs.getDocs());
		this.cloud = (cloud == null) ? null : new Cloud(cloud.getCloud());
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

	public Title getTitle() {
		return new Title(title.getTitle());
	}

	public Link getLink() {
		try {
			return (link == null) ? null : new Link(link.getLink());
		} catch (Exception e) {
			// we should never get here.
			return null;
		}
	}

	public Description getDescription() {
		return new Description(description.getDescription());
	}

	public Language getLanguage() {
		return new Language(language.getLanguage());
	}

	public Copyright getCopyright() {
		return new Copyright(copyright.getCopyright());
	}

	public ManagingEditor getManagingEditor() {
		return new ManagingEditor(managingEditor.getManagingEditor());
	}

	public WebMaster getWebMaster() {
		return new WebMaster(webMaster.getWebMaster());
	}

	public PubDate getPubDate() {
		return new PubDate(pubDate.getDateTime());
	}

	public LastBuildDate getLastBuildDate() {
		return new LastBuildDate(lastBuildDate.getDateTime());
	}

	public List<Category> getCategories() {
		if (categories == null) {
			return null;
		} else {
			List<Category> catsCopy = new LinkedList<Category>();
			for (Category category : this.categories) {
				catsCopy.add(new Category(category.getCategory(), category
						.getDomain()));
			}
			return catsCopy;
		}
	}

	public Generator getGenerator() {
		return new Generator(generator.getGenerator());
	}

	public Docs getDocs() {
		return new Docs(docs.getDocs());
	}

	public Cloud getCloud() {
		return new Cloud(cloud.getCloud());
	}

	public TTL getTtl() {
		return new TTL(ttl.getTtl());
	}

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

	public Rating getRating() {
		return new Rating(rating.getRating());
	}

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

	public SkipHours getSkipHours() {
		return new SkipHours(skipHours.getSkipHours());
	}

	public SkipDays getSkipDays() {
		return new SkipDays(skipDays.getSkipDays());
	}

	public List<Item> getItems() {
		try {
			if (items == null) {
				return null;
			} else {
				List<Item> itemsCopy = new LinkedList<Item>();
				for (Item item : this.items) {
					itemsCopy.add(new Item(item.getTitle(), item.getLink(),
							item.getDescription(), item.getAuthor(), item
									.getCategories(), item.getComments(), item
									.getEnclosure(), item.getGuid(), item
									.getPubDate(), item.getSource(), item
									.getExtensions()));
				}
				return itemsCopy;
			}
		} catch (Exception e) {
			// we should never get here.
			return null;
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
			extsCopy.add(new Extension(extension.getElementName(), extension
					.getAttributes(), extension.getContent()));
		}
		return extsCopy;
	}
}
