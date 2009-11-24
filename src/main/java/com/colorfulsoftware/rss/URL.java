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
 * <p>The &lt;url> element.</p>
 * <p>From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0 specification</a>...</p>
 * <p>This class models a &lt;url> element in an rss feed.</p>
 * 
 * @author Bill Brown
 * 
 */
public class URL implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7336240801697282972L;

	private final String url;

	URL(String url) throws RSSpectException {
		if (url != null && url.length() > 0) {
			String urlLocal = url.trim();
			if (urlLocal.length() > 0
					&& new URIScheme().contains(urlLocal.substring(0, urlLocal
							.indexOf(":")))) {
				this.url = url;
			} else {
				throw new RSSpectException(
						"link elements must start with a valid "
								+ "Uniform Resource Identifer (URI) Schemes.  "
								+ "See http://www.iana.org. Yours started with: '"
								+ url + "'");
			}
		} else {
			this.url = url;
		}
	}

	URL(URL url) {
		this.url = url.getUrl();
	}

	/**
	 * @return the url.
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Shows the contents of the &lt;url> element.
	 */
	@Override
	public String toString() {
		return "<url>" + url + "</url>";
	}
}
