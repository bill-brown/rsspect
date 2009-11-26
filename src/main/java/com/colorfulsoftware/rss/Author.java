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
 * <p>The &lt;author> element.</p>
 * <p>From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0 specification</a>...</p>
 * <p>Email address of the author of the item. <a href=
 * "http://cyber.law.harvard.edu/rss/rss.html#ltauthorgtSubelementOfLtitemgt"
 * >More</a>.</p>
 * 
 * <p>&lt;author> is an optional sub-element of &lt;item>.</p>
 * 
 * <p>It's the email address of the author of the item. For newspapers and
 * magazines syndicating via RSS, the author is the person who wrote the article
 * that the &lt;item> describes. For collaborative weblogs, the author of the
 * item might be different from the managing editor or webmaster. For a weblog
 * authored by a single individual it would make sense to omit the &lt;author>
 * element.</p>
 * 
 * <p>&lt;author>lawyer@boyer.net (Lawyer Boyer)&lt;/author></p>
 * 
 * @author Bill Brown
 * 
 */
public class Author implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -547859529015538572L;

	private final String author;

	Author(String author) {
		this.author = author;
	}

	/**
	 * @return the author's email address and maybe more text.
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Shows the contents of the &lt;author> element.
	 */
	@Override
	public String toString() {
		return "<author>" + author + "</author>";
	}

}
