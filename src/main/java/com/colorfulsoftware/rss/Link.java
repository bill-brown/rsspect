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
 * For Channel: The URL to the HTML website corresponding to the channel.
 * 
 * For Item: The URL of the item.
 * 
 * @author Bill Brown
 * 
 */
public class Link implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4258812777994100037L;

	private final String link;

	Link(String link) throws RSSpectException {
		if (link != null && link.length() > 0) {
			String linkLocal = link.trim();
			if (linkLocal.length() > 0
					&& new URIScheme().contains(linkLocal.substring(0,
							linkLocal.indexOf(":")))) {
				this.link = link;
			} else {
				throw new RSSpectException(
						"link elements must start with a valid "
								+ "Uniform Resource Identifer (URI) Schemes.  "
								+ "See http://www.iana.org. Yours started with: '"
								+ link + "'");
			}
		} else {
			this.link = link;
		}
	}

	Link(Link link) {
		this.link = link.getLink();
	}

	/**
	 * @return the link information.
	 */
	public String getLink() {
		return this.link;
	}

	/**
	 * Shows the contents of the <link> element.
	 */
	@Override
	public String toString() {
		return "<link>" + link + "</link>";
	}
}
