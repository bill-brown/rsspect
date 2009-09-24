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
 * ttl stands for time to live. It's a number of minutes that indicates how long
 * a channel can be cached before refreshing from the source. More info <a href=
 * "http://cyber.law.harvard.edu/rss/rss.html#ltttlgtSubelementOfLtchannelgt"
 * >here</a>.
 * 
 * <ttl> is an optional sub-element of <channel>.
 * 
 * ttl stands for time to live. It's a number of minutes that indicates how long
 * a channel can be cached before refreshing from the source. This makes it
 * possible for RSS sources to be managed by a file-sharing network such as
 * Gnutella.
 * 
 * Example: <ttl>60</ttl>
 * 
 * @author Bill Brown
 * 
 */
public class TTL implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6668886988682614060L;

	private final String ttl;

	TTL(String ttl) {
		this.ttl = ttl;
	}

	public String getTtl() {
		return ttl;
	}

}
