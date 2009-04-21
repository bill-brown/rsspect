package com.colorful.rss;

import java.io.Serializable;

/**
 * Copyright notice for content in the channel.
 * 
 * @author Bill Brown
 *
 */
public class Copyright implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 26097518613338635L;

	private final String copyright;
	
	Copyright(String copyright) {
		this.copyright = copyright;
	}

	public String getCopyright() {
		return copyright;
	}

}
