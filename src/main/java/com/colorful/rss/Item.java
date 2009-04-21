package com.colorful.rss;

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
				this.categories.add(new Category(category.getCategory(),
						category.getDomain()));
			}
		}

		this.comments = (comments == null) ? null : new Comments(comments
				.getComments());

		this.enclosure = (enclosure == null) ? null : new Enclosure(enclosure
				.getEnclosure(), enclosure.getAttributes());

		this.guid = (guid == null) ? null : new GUID(guid.getGuid(), guid
				.getIsPermaLink());

		this.pubDate = (pubDate == null) ? null : new PubDate(pubDate
				.getDateTime());

		this.source = (source == null) ? null : new Source(source.getSource(),
				source.getUrl());
		
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

	public Description getDescription() {
		return new Description(description.getDescription());
	}

	public Link getLink() {
		try {
			return (link == null) ? null : new Link(link.getLink());
		} catch (Exception e) {
			// we should never get here.
			return null;
		}
	}

	public Author getAuthor() {
		return new Author(author.getAuthor());
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

	public Comments getComments() {
		return new Comments(comments.getComments());
	}

	public Enclosure getEnclosure() {
		try {
			return (enclosure == null) ? null : new Enclosure(enclosure
					.getEnclosure(), enclosure.getAttributes());
		} catch (Exception e) {
			// we should never get here.
			return null;
		}
	}

	public GUID getGuid() {
		return new GUID(guid.getGuid(), guid.getIsPermaLink());
	}

	public PubDate getPubDate() {
		return new PubDate(pubDate.getDateTime());
	}

	public Source getSource() {
		try {
			return (source == null) ? null : new Source(source.getSource(),
					source.getUrl());
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
