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
 * The &lt;category> element.
 * </p>
 * <p>
 * From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0
 * specification</a>...
 * </p>
 * <p>
 * FOR channels: Specify one or more categories that the channel belongs to.
 * Follows the same rules as the &lt;item>-level <a href=
 * "http://cyber.law.harvard.edu/rss/rss.html#ltcategorygtSubelementOfLtitemgt"
 * >category</a> element. More <a
 * href="http://cyber.law.harvard.edu/rss/rss.html#syndic8">info</a>.
 * </p>
 * 
 * <p>
 * FOR items: Includes the item in one or more categories. <a href=
 * "http://cyber.law.harvard.edu/rss/rss.html#ltcategorygtSubelementOfLtitemgt"
 * >More</a>.
 * 
 * <p>
 * &lt;category> is an optional sub-element of &lt;item>.
 * </p>
 * 
 * <p>
 * It has one optional attribute, domain, a string that identifies a
 * categorization taxonomy.
 * </p>
 * 
 * <p>
 * The value of the element is a forward-slash-separated string that identifies
 * a hierarchic location in the indicated taxonomy. Processors may establish
 * conventions for the interpretation of categories. Two examples are provided
 * below:
 * </p>
 * 
 * <p>
 * &lt;category>Grateful Dead&lt;/category>
 * </p>
 * 
 * <p>
 * &lt;category domain="http://www.fool.com/cusips">MSFT&lt;/category>
 * </p>
 * 
 * <p>
 * You may include as many category elements as you need to, for different
 * domains, and to have an item cross-referenced in different parts of the same
 * domain.
 * </p>
 * 
 * @author Bill Brown
 * 
 */
public class Category implements Serializable {

	private static final long serialVersionUID = 3499943299756566396L;

	private final String category;

	private final Attribute domain;

	Category(Attribute domain, String category) throws RSSpectException {
		this.domain = (domain == null) ? null : new Attribute(domain);
		// spec doesn't require it but category should be present
		if (category == null) {
			throw new RSSpectException(
					"Category elements SHOULD contain text data.  Empty strings are allowed.");
		}
		this.category = category;
	}

	Category(Category category) {
		this.domain = category.getDomain();
		this.category = category.category;
	}

	/**
	 * @return the category.
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @return the domain url for the category.
	 */
	public Attribute getDomain() {
		return (domain == null) ? null : new Attribute(domain);

	}

	/**
	 * Shows the contents of the &lt;category> element.
	 */
	@Override
	public String toString() {
		return "<category" + ((domain == null) ? ">" : domain + " >")
				+ category + "</category>";
	}

}
