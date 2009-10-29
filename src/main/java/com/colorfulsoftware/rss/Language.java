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
 * The language the channel is written in. This allows aggregators to group all
 * Italian language sites, for example, on a single page. A list of allowable
 * values for this element, as provided by Netscape, is <a
 * href="http://cyber.law.harvard.edu/rss/languages.html">here</a>. You may also
 * use <a
 * href="http://www.w3.org/TR/REC-html40/struct/dirlang.html#langcodes">values
 * defined</a> by the W3C.
 * 
 * @author Bill Brown
 * 
 */
public class Language implements Serializable {

	private static final long serialVersionUID = -8639326685256827986L;

	private final String language;

	Language(String language) {
		this.language = language;
	}

	/**
	 * @return the language.
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Shows the contents of the <language> element.
	 */
	@Override
	public String toString() {
		return "<language>" + language + "</language>";
	}
}
