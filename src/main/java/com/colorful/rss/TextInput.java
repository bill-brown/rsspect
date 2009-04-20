package com.colorful.rss;

import java.io.Serializable;

/**
 * Specifies a text input box that can be displayed with the channel. More info
 * <a href=
 * "http://cyber.law.harvard.edu/rss/rss.html#lttextinputgtSubelementOfLtchannelgt"
 * >here</a>.
 * 
 * A channel may optionally contain a <textInput> sub-element, which contains
 * four required sub-elements.
 * 
 * <title> -- The label of the Submit button in the text input area.
 * 
 * <description> -- Explains the text input area.
 * 
 * <name> -- The name of the text object in the text input area.
 * 
 * <link> -- The URL of the CGI script that processes text input requests.
 * 
 * The purpose of the <textInput> element is something of a mystery. You can use
 * it to specify a search engine box. Or to allow a reader to provide feedback.
 * Most aggregators ignore it.
 * 
 * @author Bill Brown
 * 
 */
public class TextInput implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1063471668352135813L;

	private final Title title;
	private final Description description;
	private final Name name;
	private final Link link;

	public TextInput(Title title, Description description, Name name, Link link)
			throws RSSpectException {
		// make sure title is present
		if (title == null) {
			throw new RSSpectException(
					"textInput elements MUST contain a title element.");
		}
		this.title = new Title(title.getTitle());

		// make sure description is present
		if (description == null) {
			throw new RSSpectException(
					"textInput elements MUST contain a description element.");
		}
		this.description = new Description(description.getDescription());

		// make sure id is present
		if (name == null) {
			throw new RSSpectException(
					"textInput elements MUST contain a name element.");
		}
		this.name = new Name(name.getName());

		// make sure updated is present
		if (link == null) {
			throw new RSSpectException(
					"textInput elements MUST contain a link element.");
		}
		this.link = new Link(link.getLink());
		;
	}

	public Title getTitle() {
		return new Title(title.getTitle());
	}

	public Description getDescription() {
		return new Description(description.getDescription());
	}

	public Name getName() {
		return new Name(name.getName());
	}

	public Link getLink() {
		return new Link(link.getLink());
	}

}
