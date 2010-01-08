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
 * The &lt;name> element.
 * </p>
 * <p>
 * From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0
 * specification</a>...
 * </p>
 * <p>
 * The name of the text object in the text input area.
 * </p>
 * 
 * @author Bill Brown
 * 
 */
public class Name implements Serializable {

	private static final long serialVersionUID = 7870202584874783195L;

	private final String name;

	Name(String name) throws RSSpectException {
		if (name == null || name.equals("")) {
			throw new RSSpectException("name SHOULD NOT be blank.");
		}
		this.name = name;
	}

	Name(Name name) {
		this.name = name.name;
	}

	/**
	 * @return the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Shows the contents of the &lt;name> element.
	 */
	@Override
	public String toString() {
		return "<name>" + name + "</name>";
	}
}
