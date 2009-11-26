/**
 * Copyright (C) 2009 William R. Brown <wbrown@colorfulsoftware.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.colorfulsoftware.rss;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>The &lt;enclosure> element.</p>
 * <p>From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0 specification</a>...</p>
 * <p>Describes a media object that is attached to the item. <a href=
 * "http://cyber.law.harvard.edu/rss/rss.html#ltenclosuregtSubelementOfLtitemgt"
 * >More</a>.</p>
 * 
 * <p>&lt;enclosure> is an optional sub-element of &lt;item>.</p>
 * 
 * <p>It has three required attributes. url says where the enclosure is located,
 * length says how big it is in bytes, and type says what its type is, a
 * standard MIME type.</p>
 * 
 * <p>The url must be an http url.</p>
 * 
 * <p>&lt;enclosure url="http://www.scripting.com/mp3s/weatherReportSuite.mp3"
 * length="12216320" type="audio/mpeg" /></p>
 * 
 * <p>A use-case narrative for this element is <a
 * href="http://www.thetwowayweb.com/payloadsforrss">here</a>.</p>
 * 
 * @author Bill Brown
 * 
 */
public class Enclosure implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7669768690905784080L;

	private final List<Attribute> attributes;
	private final Attribute url;
	private final Attribute length;
	private final Attribute type;

	Enclosure(List<Attribute> attributes) throws RSSpectException {

		if (attributes == null) {
			throw new RSSpectException(
					"enclosure elements MUST contain the url, length and type attributes.  See: http://cyber.law.harvard.edu/rss/rss.html#ltenclosuregtSubelementOfLtitemgt");
		} else {
			this.attributes = new LinkedList<Attribute>();
			for (Attribute attr : attributes) {
				// check for unsupported attribute.
				this.attributes.add(new Attribute(attr));
			}
		}

		if ((this.url = getAttribute("url")) == null) {
			throw new RSSpectException(
					"enclosure elements MUST have a url attribute.");
		}

		if ((this.length = getAttribute("length")) == null) {
			throw new RSSpectException(
					"enclosure elements MUST have a length attribute.");
		}

		if ((this.type = getAttribute("type")) == null) {
			throw new RSSpectException(
					"enclosure elements MUST have a type attribute.");
		}

	}

	Enclosure(Enclosure enclosure) {
		this.attributes = enclosure.getAttributes();
		this.url = enclosure.getUrl();
		this.length = enclosure.getLength();
		this.type = enclosure.getType();
	}

	/**
	 * 
	 * @return the cloud attribute list.
	 */
	public List<Attribute> getAttributes() {

		List<Attribute> attrsCopy = new LinkedList<Attribute>();
		for (Attribute attr : this.attributes) {
			attrsCopy.add(new Attribute(attr));
		}

		return attrsCopy;
	}

	/**
	 * @return the url attribute
	 */
	public Attribute getUrl() {
		return new Attribute(url);

	}

	/**
	 * @return the length attribute.
	 */
	public Attribute getLength() {
		return new Attribute(length);
	}

	/**
	 * @return the type attribute.
	 */
	public Attribute getType() {
		return new Attribute(type);
	}

	/**
	 * @param attrName
	 *            the name of the attribute to get.
	 * @return the Attribute object if attrName matches or null if not found.
	 */
	public Attribute getAttribute(String attrName) {
		for (Attribute attribute : this.attributes) {
			if (attribute.getName().equals(attrName)) {
				return new Attribute(attribute);
			}
		}
		return null;
	}

	/**
	 * Shows the contents of the &lt;enclosure> element.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("<enclosure");
		for (Attribute attribute : attributes) {
			sb.append(attribute);
		}
		sb.append(" />");
		return sb.toString();
	}

}
