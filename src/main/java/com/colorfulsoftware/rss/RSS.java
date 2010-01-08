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
 * <p>
 * The &lt;rss> element.
 * </p>
 * <p>
 * From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0
 * specification</a>...
 * </p>
 * <p>
 * At the top level, a RSS document is a &lt;rss> element, with a mandatory
 * attribute called version, that specifies the version of RSS that the document
 * conforms to. If it conforms to this specification, the version attribute must
 * be 2.0.
 * </p>
 * 
 * <p>
 * Subordinate to the &lt;rss> element is a single &lt;channel> element, which
 * contains information about the channel (metadata) and its contents. RSS
 * originated in 1999, and has strived to be a simple, easy to understand
 * format, with relatively modest goals. After it became a popular format,
 * developers wanted to extend it using modules defined in namespaces, as
 * specified by the W3C.
 * </p>
 * 
 * <p>
 * RSS 2.0 adds that capability, following a simple rule. A RSS feed may contain
 * elements not described on this page, only if those elements are defined in a
 * namespace.
 * </p>
 * 
 * <p>
 * The elements defined in this document are not themselves members of a
 * namespace, so that RSS 2.0 can remain compatible with previous versions in
 * the following sense - - a version 0.91 or 0.92 file is also a valid 2.0 file.
 * If the elements of RSS 2.0 were in a namespace, this constraint would break,
 * a version 0.9x file would not be a valid 2.0 file.
 * </p>
 * 
 * <p>
 * The version="2.0" attribute of the &lt;rss> element is automatically provided
 * in this implementation.
 * </p>
 * 
 * @author Bill Brown
 * 
 */
public class RSS implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2952021194132587749L;

	private final Channel channel;
	private final List<Attribute> attributes;
	private final List<Extension> extensions;

	RSS(Channel channel, List<Attribute> attributes, List<Extension> extensions)
			throws RSSpectException {
		if (channel == null) {
			throw new RSSpectException(
					"rss elements MUST contain a channel element.");
		}

		this.channel = new Channel(channel);

		if (attributes == null) {
			throw new RSSpectException(
					"RSS elements must contain a version attribute.");
		} else {
			this.attributes = new LinkedList<Attribute>();

			for (Attribute attr : attributes) {
				this.attributes.add(new Attribute(attr));
			}
		}

		if (extensions == null) {
			this.extensions = null;
		} else {
			this.extensions = new LinkedList<Extension>();
			for (Extension extension : extensions) {
				this.extensions.add(new Extension(extension));
			}
		}
	}

	/**
	 * @return the channel object.
	 */
	public Channel getChannel() {
		return new Channel(channel);
	}

	/**
	 * 
	 * @return the attribute list.
	 */
	public List<Attribute> getAttributes() {

		List<Attribute> attrsCopy = new LinkedList<Attribute>();
		for (Attribute attr : this.attributes) {
			attrsCopy.add(new Attribute(attr));

		}
		return attrsCopy;
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
			extsCopy.add(new Extension(extension));
		}
		return extsCopy;
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
	 * @param extName
	 *            the element name of the extension. eg. "atom:link" or
	 *            "someExtension"
	 * @return the extension matching the element or null if not found.
	 */
	public Extension getExtension(String extName) {
		if (this.extensions != null) {
			for (Extension extension : this.extensions) {
				if (extension.getElementName().equals(extName)) {
					return new Extension(extension);
				}
			}
		}
		return null;
	}

	/**
	 * Shows the contents of the &lt;rss> element.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("<rss");
		for (Attribute attribute : attributes) {
			sb.append(attribute);
		}
		// close the parent element
		sb.append(">");

		sb.append(channel);

		if (extensions != null) {
			for (Extension extension : extensions) {
				sb.append(extension);
			}
		}

		sb.append("</rss>");
		return sb.toString();
	}
}
