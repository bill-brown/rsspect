package com.colorful.rss;

import java.io.Serializable;

/**
 * URL of a page for comments relating to the item. <a href=
 * "http://cyber.law.harvard.edu/rss/rss.html#ltcommentsgtSubelementOfLtitemgt"
 * >More</a>.
 * 
 * <comments> is an optional sub-element of <item>.
 * 
 * If present, it is the url of the comments page for the item.
 * 
 * <comments>http://ekzemplo.com/entry/4403/comments</comments>
 * 
 * More about comments <a
 * href="http://cyber.law.harvard.edu/rss/weblogComments.html">here</a>.
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

	Comments(String comments) {
		this.comments = comments;
	}

	public String getComments() {
		return comments;
	}

}
