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
 * A URL that points to the documentation for the format used in the RSS file.
 * It's probably a pointer to this page. It's for people who might stumble
 * across an RSS file on a Web server 25 years from now and wonder what it is.
 * 
 * @author Bill Brown
 * 
 */
public class Docs implements Serializable {

	private static final long serialVersionUID = 1840987541596737383L;

	private final String docs;

	Docs(String docs) {
		this.docs = docs;
	}

	/**
	 * @return the documentation information for the rss format in url form.
	 */
	public String getDocs() {
		return docs;
	}

	/**
	 * Shows the contents of the <docs> element.
	 */
	@Override
	public String toString() {
		return "<docs>" + docs + "</docs>";
	}

}
