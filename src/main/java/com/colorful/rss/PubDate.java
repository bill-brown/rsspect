package com.colorful.rss;

import java.io.Serializable;
import java.util.Date;

/**
 * For Channel: The publication date for the content in the channel. For
 * example, the New York Times publishes on a daily basis, the publication date
 * flips once every 24 hours. That's when the pubDate of the channel changes.
 * All date-times in RSS conform to the Date and Time Specification of <a
 * href="http://asg.web.cmu.edu/rfc/rfc822.html">RFC 822</a>, with the exception
 * that the year may be expressed with two characters or four characters (four
 * preferred).
 * 
 * For Item: Indicates when the item was published. <a href=
 * "http://cyber.law.harvard.edu/rss/rss.html#ltpubdategtSubelementOfLtitemgt"
 * >More</a>.
 * 
 * @author Bill Brown
 * 
 */
public class PubDate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1876136443511726976L;

	private final RSSDateConstruct pubDate;

	public PubDate(Date pubDate) {
		this.pubDate = new RSSDateConstruct(pubDate);
	}

	/**
	 * 
	 * @return the date timestamp for this element.
	 */
	protected Date getDateTime() {
		return pubDate.getDateTime();
	}

	/**
	 * 
	 * @return the string formated version of the time for example
	 *         2006-04-28T12:50:43.337-05:00
	 */
	public String getText() {
		return pubDate.getText();
	}
}
