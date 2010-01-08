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

/**
 * <p>
 * This class contains the attribute definition for elements.
 * </p>
 * 
 * @author Bill Brown
 * 
 */
public class Attribute implements Serializable {

	private static final long serialVersionUID = -3880416791234118400L;
	private final String name;
	private final String value;

	// use the factory method in the RSSDoc.
	Attribute(String name, String value) throws RSSpectException {
		if (name == null || name.equals("")) {
			throw new RSSpectException("Attribute names SHOULD NOT be blank.");
		}
		this.name = name;
		this.value = (value == null) ? "" : value;
	}

	// copy constructor
	Attribute(Attribute attribute) {
		this.name = attribute.name;
		this.value = attribute.value;
	}

	/**
	 * 
	 * @return the name of this attribute
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return the value of this attribute
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @return true if the attribute name and value are equal.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Attribute) {
			Attribute local = (Attribute) obj;
			return local.name.equals(this.name)
					&& local.value.equals(this.value);
		}
		return false;
	}

	/**
	 * Shows the contents of the element's attribute in the form of '
	 * attrName="attrValue"'.
	 */
	@Override
	public String toString() {
		return " " + name + "=\"" + value + "\"";
	}
}
