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
 * Email address of the author of the item. <a href=
 * "http://cyber.law.harvard.edu/rss/rss.html#ltauthorgtSubelementOfLtitemgt"
 * >More</a>.
 * 
 * <author> is an optional sub-element of <item>.
 * 
 * It's the email address of the author of the item. For newspapers and
 * magazines syndicating via RSS, the author is the person who wrote the article
 * that the <item> describes. For collaborative weblogs, the author of the item
 * might be different from the managing editor or webmaster. For a weblog
 * authored by a single individual it would make sense to omit the <author>
 * element.
 * 
 * <author>lawyer@boyer.net (Lawyer Boyer)</author>
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
	 * Shows the contents of the <author> element.
	 */
	@Override
	public String toString() {
		return "<author>" + author + "</author>";
	}

}
