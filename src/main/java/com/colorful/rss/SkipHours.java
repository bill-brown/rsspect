package com.colorful.rss;

import java.io.Serializable;

/**
 * A hint for aggregators telling them which hours they can skip. More info <a
 * href
 * ="http://cyber.law.harvard.edu/rss/skipHoursDays.html#skiphours">here</a>.
 * 
 * @author Bill Brown
 * 
 */
public class SkipHours implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7435503230975016475L;

	private final String skipHours;

	SkipHours(String skipHours) {
		this.skipHours = skipHours;
	}

	public String getSkipHours() {
		return skipHours;
	}

}
