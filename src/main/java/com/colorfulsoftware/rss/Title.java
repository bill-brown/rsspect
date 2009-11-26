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
 * <p>The &lt;title> element.</p>
 * <p>From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0 specification</a>...</p>
 * <p>For Channel: The name of the channel. It's how people refer to your service.
 * If you have an HTML website that contains the same information as your RSS
 * file, the title of your channel should be the same as the title of your
 * website.</p>
 * 
 * <p>For Item: The title of the item.</p>
 * 
 * @author Bill Brown
 * 
 */
public class Title implements Serializable {

	private static final long serialVersionUID = -8102075738201739128L;

	private final String title;

	Title(String title) {
		this.title = title;
	}

	/**
	 * @return the title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Shows the contents of the &lt;title> element.
	 */
	@Override
	public String toString() {
		return "<title>" + title + "</title>";
	}
}
