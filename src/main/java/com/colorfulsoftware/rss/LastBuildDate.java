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
 * The &lt;lastBuildDate> element.
 * </p>
 * <p>
 * From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0
 * specification</a>...
 * </p>
 * <p>
 * The last time the content of the channel changed.
 * </p>
 * 
 * @author Bill Brown
 * 
 */
public class LastBuildDate implements Serializable {

	private static final long serialVersionUID = -8692371191911347659L;

	private final RSSDateConstruct lastBuildDate;

	LastBuildDate(String lastBuildDate) throws RSSpectException {
		this.lastBuildDate = new RSSDateConstruct(lastBuildDate);
	}

	LastBuildDate(Date lastBuildDate) {
		this.lastBuildDate = new RSSDateConstruct(lastBuildDate);
	}

	/**
	 * 
	 * @return the date timestamp for this element.
	 */
	protected Date getDateTime() {
		return lastBuildDate.getDateTime();
	}

	/**
	 * 
	 * @return the string formated version of the time for example
	 *         2006-04-28T12:50:43.337-05:00
	 */
	public String getText() {
		return lastBuildDate.getText();
	}

	/**
	 * Shows the contents of the &lt;lastBuildDate> element.
	 */
	@Override
	public String toString() {
		return "<lastBuildDate>" + lastBuildDate + "</lastBuildDate>";
	}
}
