package com.colorful.rss;

import java.io.Serializable;

/**
 * For Channel: The URL to the HTML website corresponding to the channel.
 * 
 * For Item: The URL of the item.
 * 
 * @author Bill Brown
 * 
 */
public class Link implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4258812777994100037L;

	private final String link;

	public Link(String link) {
		this.link = link;
	}

	public String getLink() {
		return link;
	}

}
