package com.colorful.rss;

import java.io.Serializable;

/**
 * A URL that points to the documentation for the format used in the RSS file.
 * It's probably a pointer to this page. It's for people who might stumble
 * across an RSS file on a Web server 25 years from now and wonder what it is.
 * 
 * @author Bill Brown
 * 
 */
public class Docs implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1840987541596737383L;

	private final String docs;

	Docs(String docs) {
		this.docs = docs;
	}

	public String getDocs() {
		return docs;
	}

}
