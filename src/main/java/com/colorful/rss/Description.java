package com.colorful.rss;

import java.io.Serializable;

/**
 * For Channel: Phrase or sentence describing the channel.
 * 
 * For Item: The item synopsis.
 * 
 * @author Bill Brown
 * 
 */
public class Description implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3376088317656959708L;

	private final String description;

	Description(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
