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

	TextInput(Title title, Description description, Name name, Link link)
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

		// make sure name is present
		if (name == null) {
			throw new RSSpectException(
					"textInput elements MUST contain a name element.");
		}
		this.name = new Name(name.getName());

		// make sure link is present
		if (link == null) {
			throw new RSSpectException(
					"textInput elements MUST contain a link element.");
		}
		this.link = new Link(link.getLink());
		;
	}

	/**
	 * @return the title object.
	 */
	public Title getTitle() {
		return new Title(title.getTitle());
	}

	/**
	 * @return the description object.
	 */
	public Description getDescription() {
		return new Description(description.getDescription());
	}

	/**
	 * @return the name object.
	 */
	public Name getName() {
		return new Name(name.getName());
	}

	/**
	 * @return the link object.
	 */
	public Link getLink() {
		try {
			return new Link(link.getLink());
		} catch (Exception e) {
			// we should never get here.
			return null;
		}
	}

}
