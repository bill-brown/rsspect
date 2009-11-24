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
 * <p>The &lt;comments> element.</p>
 * <p>From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0 specification</a>...</p>
 * <p>URL of a page for comments relating to the item. <a href=
 * "http://cyber.law.harvard.edu/rss/rss.html#ltcommentsgtSubelementOfLtitemgt"
 * >More</a>.</p>
 * 
 * <p>&lt;comments> is an optional sub-element of &lt;item>.</p>
 * 
 * <p>If present, it is the url of the comments page for the item.</p>
 * 
 * <p>&lt;comments>http://ekzemplo.com/entry/4403/comments&lt;/comments></p>
 * 
 * <p>More about comments <a
 * href="http://cyber.law.harvard.edu/rss/weblogComments.html">here</a>.</p>
 * 
 * @author Bill Brown
 * 
 */
public class Comments implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2013605887767981438L;

	private final String comments;

	Comments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the comments url.
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * Shows the contents of the &lt;comments> element.
	 */
	@Override
	public String toString() {
		return "<comments>" + comments + "</comments>";
	}
}
