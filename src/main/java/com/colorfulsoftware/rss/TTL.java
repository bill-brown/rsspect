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
 * The &lt;ttl> element.
 * </p>
 * <p>
 * From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0
 * specification</a>...
 * </p>
 * <p>
 * ttl stands for time to live. It's a number of minutes that indicates how long
 * a channel can be cached before refreshing from the source. More info <a href=
 * "http://cyber.law.harvard.edu/rss/rss.html#ltttlgtSubelementOfLtchannelgt"
 * >here</a>.
 * </p>
 * 
 * <p>
 * &lt;ttl> is an optional sub-element of &lt;channel>.
 * </p>
 * 
 * <p>
 * ttl stands for time to live. It's a number of minutes that indicates how long
 * a channel can be cached before refreshing from the source. This makes it
 * possible for RSS sources to be managed by a file-sharing network such as
 * Gnutella.
 * </p>
 * 
 * <p>
 * Example: <ttl>60</ttl>
 * </p>
 * 
 * @author Bill Brown
 * 
 */
public class TTL implements Serializable {

	private static final long serialVersionUID = -6668886988682614060L;

	private final String ttl;

	TTL(String ttl) throws RSSpectException {
		if (ttl == null || ttl.equals("")) {
			throw new RSSpectException("ttl SHOULD NOT be blank.");
		}
		this.ttl = ttl;
	}

	TTL(TTL ttl) {
		this.ttl = ttl.ttl;
	}

	/**
	 * @return the time to live.
	 */
	public String getTtl() {
		return ttl;
	}

	/**
	 * Shows the contents of the &lt;ttl> element.
	 */
	@Override
	public String toString() {
		return "<ttl>" + ttl + "</ttl>";
	}
}
