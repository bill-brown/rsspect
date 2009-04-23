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
package com.colorful.rss;

import java.io.Serializable;

/**
 * FOR channels: Specify one or more categories that the channel belongs to.
 * Follows the same rules as the <item>-level <a href=
 * "http://cyber.law.harvard.edu/rss/rss.html#ltcategorygtSubelementOfLtitemgt"
 * >category</a> element. More <a
 * href="http://cyber.law.harvard.edu/rss/rss.html#syndic8">info</a>.
 * 
 * FOR items: Includes the item in one or more categories. <a href=
 * "http://cyber.law.harvard.edu/rss/rss.html#ltcategorygtSubelementOfLtitemgt"
 * >More</a>.
 * 
 * <category> is an optional sub-element of <item>.
 * 
 * It has one optional attribute, domain, a string that identifies a
 * categorization taxonomy.
 * 
 * The value of the element is a forward-slash-separated string that identifies
 * a hierarchic location in the indicated taxonomy. Processors may establish
 * conventions for the interpretation of categories. Two examples are provided
 * below:
 * 
 * <category>Grateful Dead</category>
 * 
 * <category domain="http://www.fool.com/cusips">MSFT</category>
 * 
 * You may include as many category elements as you need to, for different
 * domains, and to have an item cross-referenced in different parts of the same
 * domain.
 * 
 * @author Bill Brown
 * 
 */
public class Category implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3499943299756566396L;

	private final String category;

	private final Attribute domain;

	Category(Attribute domain, String category) {
		this.domain = (domain == null) ? null : new Attribute(domain.getName(),
				domain.getValue());
		this.category = category;
	}

	public String getCategory() {
		return category;
	}

	public Attribute getDomain() {
		return new Attribute(domain.getName(), domain.getValue());
	}

}
