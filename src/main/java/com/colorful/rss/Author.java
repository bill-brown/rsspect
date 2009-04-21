package com.colorful.rss;

import java.io.Serializable;

/**
 * Email address of the author of the item. <a href=
 * "http://cyber.law.harvard.edu/rss/rss.html#ltauthorgtSubelementOfLtitemgt"
 * >More</a>.
 * 
 * <author> is an optional sub-element of <item>.
 * 
 * It's the email address of the author of the item. For newspapers and
 * magazines syndicating via RSS, the author is the person who wrote the article
 * that the <item> describes. For collaborative weblogs, the author of the item
 * might be different from the managing editor or webmaster. For a weblog
 * authored by a single individual it would make sense to omit the <author>
 * element.
 * 
 * <author>lawyer@boyer.net (Lawyer Boyer)</author>
 * 
 * @author Bill Brown
 * 
 */
public class Author implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -547859529015538572L;

	private final String author;

	Author(String author) {
		this.author = author;
	}

	public String getAuthor() {
		return author;
	}

}
