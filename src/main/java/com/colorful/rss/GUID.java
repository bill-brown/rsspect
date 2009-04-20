package com.colorful.rss;

import java.io.Serializable;

/**
 * A string that uniquely identifies the item. <a href=
 * "http://cyber.law.harvard.edu/rss/rss.html#ltguidgtSubelementOfLtitemgt"
 * >More</a>.
 * 
 * @author Bill Brown
 * 
 */
public class GUID implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1285259651943559185L;

	private final String guid;

	public GUID(String guid) {
		this.guid = guid;
	}

	public String getGuid() {
		return guid;
	}

}
