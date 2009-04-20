package com.colorful.rss;

import java.io.Serializable;

/**
 * ttl stands for time to live. It's a number of minutes that indicates how long
 * a channel can be cached before refreshing from the source. More info <a href=
 * "http://cyber.law.harvard.edu/rss/rss.html#ltttlgtSubelementOfLtchannelgt"
 * >here</a>.
 * 
 * <ttl> is an optional sub-element of <channel>.
 * 
 * ttl stands for time to live. It's a number of minutes that indicates how long
 * a channel can be cached before refreshing from the source. This makes it
 * possible for RSS sources to be managed by a file-sharing network such as
 * Gnutella.
 * 
 * Example: <ttl>60</ttl>
 * 
 * @author Bill Brown
 * 
 */
public class TTL implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6668886988682614060L;

	private final String ttl;

	public TTL(String ttl) {
		this.ttl = ttl;
	}

	public String getTtl() {
		return ttl;
	}

}
