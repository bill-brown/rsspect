package com.colorful.rss;

import java.io.Serializable;

/**
 * The <a href="http://www.w3.org/PICS/">PICS</a> rating for the channel.
 * 
 * @author Bill Brown
 * 
 */
public class Rating implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1511199969383702784L;

	private final String rating;

	Rating(String rating) {
		this.rating = rating;
	}

	public String getRating() {
		return rating;
	}

}
