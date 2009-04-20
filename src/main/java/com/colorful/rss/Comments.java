package com.colorful.rss;

import java.io.Serializable;

/**
 * URL of a page for comments relating to the item. <a href=
 * "http://cyber.law.harvard.edu/rss/rss.html#ltcommentsgtSubelementOfLtitemgt"
 * >More</a>.
 * 
 * @author Bill Brown
 * 
 */
public class Comments implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2013605887767981438L;

	private final String comments;

	public Comments(String comments) {
		this.comments = comments;
	}

	public String getComments() {
		return comments;
	}

}
