package com.colorful.rss;

import java.io.Serializable;

/**
 * Email address for person responsible for technical issues relating to
 * channel.
 * 
 * @author Bill Brown
 * 
 */
public class WebMaster implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2326329055869744204L;

	private final String webMaster;

	public WebMaster(String webMaster) {
		this.webMaster = webMaster;
	}

	public String getWebMaster() {
		return webMaster;
	}

}
