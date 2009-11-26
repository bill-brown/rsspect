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
 * <p>The &lt;description> element.</p>
 * <p>From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0 specification</a>...</p>
 * <p>For Channel: Phrase or sentence describing the channel.</p>
 * 
 * <p>For Item: The item synopsis.</p>
 * 
 * @author Bill Brown
 * 
 */
public class Description implements Serializable {

	private static final long serialVersionUID = -3376088317656959708L;

	private final String description;

	Description(String description) {
		// descriptions can be empty
		this.description = (description == null) ? "" : description;
	}

	/**
	 * @return the description (can be the empty string).
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Shows the contents of the &lt;description> element.
	 */
	@Override
	public String toString() {
		return "<description>" + description + "</description>";
	}
}
