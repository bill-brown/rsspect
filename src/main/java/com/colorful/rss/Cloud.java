package com.colorful.rss;

import java.io.Serializable;

/**
 * Allows processes to register with a cloud to be notified of updates to the
 * channel, implementing a lightweight publish-subscribe protocol for RSS feeds.
 * More info <a href=
 * "http://cyber.law.harvard.edu/rss/rss.html#ltcloudgtSubelementOfLtchannelgt"
 * >here</a>
 * 
 * @author Bill Brown
 * 
 */
public class Cloud implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4431999134564899474L;

	private final String cloud;

	public Cloud(String cloud) {
		this.cloud = cloud;
	}

	public String getCloud() {
		return cloud;
	}

}
