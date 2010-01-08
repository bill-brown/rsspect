/**
 * Copyright (C) 2009 William R. Brown <wbrown@colorfulsoftware.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.colorfulsoftware.rss;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * The &lt;pubDate> element.
 * </p>
 * <p>
 * From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0
 * specification</a>...
 * </p>
 * <p>
 * For Channel: The publication date for the content in the channel. For
 * example, the New York Times publishes on a daily basis, the publication date
 * flips once every 24 hours. That's when the pubDate of the channel changes.
 * All date-times in RSS conform to the Date and Time Specification of <a
 * href="http://asg.web.cmu.edu/rfc/rfc822.html">RFC 822</a>, with the exception
 * that the year may be expressed with two characters or four characters (four
 * preferred).
 * </p>
 * 
 * <p>
 * For Item: Indicates when the item was published. <a href=
 * "http://cyber.law.harvard.edu/rss/rss.html#ltpubdategtSubelementOfLtitemgt"
 * >More</a>.
 * </p>
 * 
 * <p>
 * &lt;pubDate> is an optional sub-element of &lt;item>.
 * </p>
 * 
 * <p>
 * Its value is a <a href="http://asg.web.cmu.edu/rfc/rfc822.html">date</a>,
 * indicating when the item was published. If it's a date in the future,
 * aggregators may choose to not display the item until that date.
 * </p>
 * 
 * <p>
 * &lt;pubDate>Sun, 19 May 2002 15:21:36 GMT&lt;/pubDate>
 * </p>
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

	PubDate(String pubDate) throws RSSpectException {
		this.pubDate = new RSSDateConstruct(pubDate);
	}

	PubDate(Date pubDate) {
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

	/**
	 * Shows the contents of the &lt;pubDate> element.
	 */
	@Override
	public String toString() {
		return "<pubDate>" + pubDate + "</pubDate>";
	}
}
