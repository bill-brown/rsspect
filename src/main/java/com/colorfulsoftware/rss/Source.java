/**
 * Copyright (C) 2009 William R. Brown <info@colorfulsoftware.com>
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
 * The RSS channel that the item came from. <a href=
 * "http://cyber.law.harvard.edu/rss/rss.html#ltsourcegtSubelementOfLtitemgt"
 * >More</a>.
 * 
 * <source> is an optional sub-element of <item>.
 * 
 * Its value is the name of the RSS channel that the item came from, derived
 * from its <title>. It has one required attribute, url, which links to the
 * XMLization of the source.
 * 
 * <source url="http://www.tomalak.org/links2.xml">Tomalak's Realm</source>
 * 
 * The purpose of this element is to propagate credit for links, to publicize
 * the sources of news items. It can be used in the Post command of an
 * aggregator. It should be generated automatically when forwarding an item from
 * an aggregator to a weblog authoring tool.
 * 
 * @author Bill Brown
 * 
 */
public class Source implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4585022053173517802L;

	private final String source;
	private final Attribute url;

	Source(Attribute url, String source) throws RSSpectException {
		// make sure the url attribute is present
		if (url == null || !url.getName().equals("url")) {
			throw new RSSpectException(
					"source elements MUST contain a url attribute.");
		}
		this.url = new Attribute(url.getName(), url.getValue());

		this.source = source;
	}

	public Attribute getUrl() {
		return new Attribute(url.getName(), url.getValue());
	}

	public String getSource() {
		return source;
	}

}
