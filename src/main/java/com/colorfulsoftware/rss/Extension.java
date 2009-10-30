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
 * This class can be used to add extended namespace elements.
 * 
 * @author Bill Brown
 * 
 */
public class Extension implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7382104018423864548L;
	private final String elementName;
	private final List<Attribute> attributes;
	private final String content;

	Extension(String elementName, List<Attribute> attributes, String content)
			throws RSSpectException {

		this.elementName = elementName;

		this.content = content;

		if (attributes == null) {
			this.attributes = null;
		} else {
			this.attributes = new LinkedList<Attribute>();
			for (Attribute attr : attributes) {
				this.attributes.add(new Attribute(attr));
			}
		}

		if (elementName == null
				|| (elementName.indexOf(":") == -1 && getAttribute("xmlns") == null)
				|| elementName.indexOf(":") == 0) {

			throw new RSSpectException("Extension element '" + elementName
					+ "' is missing a namespace prefix or namespace declaration.");
		}
	}

	Extension(Extension extension) {
		this.elementName = extension.getElementName();
		this.attributes = extension.getAttributes();
		this.content = extension.getContent();
	}

	/**
	 * 
	 * @return the attribute list.
	 */
	public List<Attribute> getAttributes() {

		List<Attribute> attrsCopy = new LinkedList<Attribute>();
		if (this.attributes != null) {
			for (Attribute attr : this.attributes) {
				attrsCopy.add(new Attribute(attr));
			}
		}
		return (this.attributes == null) ? null : attrsCopy;
	}

	/**
	 * @return the content.
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @return the extension element name. eg "atom:link" or "someExtension"
	 */
	public String getElementName() {
		return elementName;
	}

	/**
	 * @param attrName
	 *            the name of the attribute to get.
	 * @return the Attribute object if attrName matches or null if not found.
	 */
	public Attribute getAttribute(String attrName) {
		if (this.attributes != null) {
			for (Attribute attribute : this.attributes) {
				if (attribute.getName().equals(attrName)) {
					return new Attribute(attribute);
				}
			}
		}
		return null;
	}

	/**
	 * Shows the contents of the extension element.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("<" + elementName);
		if (attributes != null) {
			for (Attribute attribute : attributes) {
				sb.append(attribute);
			}
		}

		if (content == null || content.equals("")) {
			sb.append(" />");
		} else {
			sb.append(" >" + content + "</" + elementName + ">");
		}

		return sb.toString();
	}
}
