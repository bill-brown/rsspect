package com.colorful.rss;

import java.io.Serializable;

public class URL implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7336240801697282972L;

	private final String url;

	public URL(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
}
