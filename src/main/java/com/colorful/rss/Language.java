package com.colorful.rss;

import java.io.Serializable;

/**
 * The language the channel is written in. This allows aggregators to group all
 * Italian language sites, for example, on a single page. A list of allowable
 * values for this element, as provided by Netscape, is <a
 * href="http://cyber.law.harvard.edu/rss/languages.html">here</a>. You may also
 * use <a
 * href="http://www.w3.org/TR/REC-html40/struct/dirlang.html#langcodes">values
 * defined</a> by the W3C.
 * 
 * @author Bill Brown
 * 
 */
public class Language implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8639326685256827986L;

	private final String language;

	public Language(String language) {
		this.language = language;
	}

	public String getLanguage() {
		return language;
	}

}
