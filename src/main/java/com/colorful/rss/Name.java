package com.colorful.rss;

import java.io.Serializable;

/**
 * The name of the text object in the text input area. 
 * 
 * @author Bill Brown
 *
 */
public class Name implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final String name;

	Name(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
