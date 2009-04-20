package com.colorful.rss;

import java.io.Serializable;

/**
 * A hint for aggregators telling them which days they can skip. More info <a
 * href
 * ="http://cyber.law.harvard.edu/rss/skipHoursDays.html#skipdays">Ffhere</a>s.
 * 
 * @author Bill Brown
 * 
 */
public class SkipDays implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2480844569809695010L;

	private final String skipDays;

	public SkipDays(String skipDays) {
		this.skipDays = skipDays;
	}

	public String getSkipDays() {
		return skipDays;
	}

}
