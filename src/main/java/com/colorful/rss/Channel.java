package com.colorful.rss;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

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

	Channel(Title title, Link link, Description description, Language language,
			Copyright copyright, ManagingEditor managingEditor,
			WebMaster webMaster, PubDate pubDate, LastBuildDate lastBuildDate,
			List<Category> categories, Generator generator, Docs docs,
			Cloud cloud, TTL ttl, Image image, Rating rating,
			TextInput textInput, SkipHours skipHours, SkipDays skipDays)
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
	}

	public Title getTitle() {
		return new Title(title.getTitle());
	}

	public Link getLink() {
		return new Link(link.getLink());
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
}
