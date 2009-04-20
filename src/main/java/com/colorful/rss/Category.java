package com.colorful.rss;

import java.io.Serializable;

/**
 * Specify one or more categories that the channel belongs to. Follows the same
 * rules as the <item>-level <a href=
 * "http://cyber.law.harvard.edu/rss/rss.html#ltcategorygtSubelementOfLtitemgt"
 * >category</a> element. More <a
 * href="http://cyber.law.harvard.edu/rss/rss.html#syndic8">info</a>.
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

	public Category(String category) {
		this.category = category;
	}

	public String getCategory() {
		return category;
	}

}
