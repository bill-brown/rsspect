package com.colorful.rss;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Describes a media object that is attached to the item. <a href=
 * "http://cyber.law.harvard.edu/rss/rss.html#ltenclosuregtSubelementOfLtitemgt"
 * >More</a>.
 * 
 * <enclosure> is an optional sub-element of <item>.
 * 
 * It has three required attributes. url says where the enclosure is located,
 * length says how big it is in bytes, and type says what its type is, a
 * standard MIME type.
 * 
 * The url must be an http url.
 * 
 * <enclosure url="http://www.scripting.com/mp3s/weatherReportSuite.mp3"
 * length="12216320" type="audio/mpeg" />
 * 
 * A use-case narrative for this element is <a
 * href="http://www.thetwowayweb.com/payloadsforrss">here</a>.
 * 
 * @author Bill Brown
 * 
 */
public class Enclosure implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7669768690905784080L;

	private final String enclosure;

	private final List<Attribute> attributes;

	Enclosure(List<Attribute> attributes, String enclosure)
			throws RSSpectException {

		if (attributes == null) {
			throw new RSSpectException(
					"enclosure elements MUST contain the url, length and type attributes.");

		} else {

			boolean hasURL = false;
			boolean hasLength = false;
			boolean hasType = false;
			for (Attribute attr : attributes) {
				// check for url attribute
				if (attr.getName().equals("url")) {
					hasURL = true;
				}
				// check for length attribute
				if (attr.getName().equals("length")) {
					hasLength = true;
				}
				// check for type attribute
				if (attr.getName().equals("type")) {
					hasType = true;
				}
			}

			if (!hasURL) {
				throw new RSSpectException(
						"enclosure elements MUST contain the url attribute.");
			}

			if (!hasLength) {
				throw new RSSpectException(
						"enclosure elements MUST contain the length attribute.");
			}

			if (!hasType) {
				throw new RSSpectException(
						"enclosure elements MUST contain the type attribute.");
			}

			this.attributes = new LinkedList<Attribute>();
			for (Attribute attr : attributes) {
				this.attributes.add(new Attribute(attr.getName(), attr
						.getValue()));
			}
		}

		this.enclosure = enclosure;
	}

	public String getEnclosure() {
		return enclosure;
	}

	/**
	 * 
	 * @return the attributes for this element.
	 */
	public List<Attribute> getAttributes() {
		if (attributes == null) {
			return null;
		} else {
			List<Attribute> attrsCopy = new LinkedList<Attribute>();
			for (Attribute attr : this.attributes) {
				attrsCopy.add(new Attribute(attr.getName(), attr.getValue()));
			}
			return attrsCopy;
		}
	}

}
