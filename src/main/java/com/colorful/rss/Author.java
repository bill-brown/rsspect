package com.colorful.rss;

import java.io.Serializable;

/**
 * Email address of the author of the item. <a href=
 * "http://cyber.law.harvard.edu/rss/rss.html#ltauthorgtSubelementOfLtitemgt"
 * >More</a>.
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

	public Author(String author) {
		this.author = author;
	}

	public String getAuthor() {
		return author;
	}

}
