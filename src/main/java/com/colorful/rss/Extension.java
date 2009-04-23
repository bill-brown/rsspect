package com.colorful.rss;

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

	// use the factory method in the FeedDoc.
	Extension(String elementName, List<Attribute> attributes, String content) {

		this.elementName = elementName;
		this.content = content;

		if (attributes == null) {
			this.attributes = null;
		} else {
			this.attributes = new LinkedList<Attribute>();
			for (Attribute attr : attributes) {
				this.attributes.add(new Attribute(attr.getName(), attr
						.getValue()));
			}
		}
	}

	/**
	 * 
	 * @return the attributes for this element.
	 */
	public List<Attribute> getAttributes() {
		if (attributes == null) {
			return null;
		}
		List<Attribute> attrsCopy = new LinkedList<Attribute>();
		for (Attribute attr : this.attributes) {
			attrsCopy.add(new Attribute(attr.getName(), attr.getValue()));
		}
		return attrsCopy;
	}

	public String getContent() {
		return content;
	}

	public String getElementName() {
		return elementName;
	}
}
