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
import java.text.SimpleDateFormat;
import java.util.Date;

class RSSDateConstruct implements Serializable {

	private static final long serialVersionUID = 661002136563928416L;

	private final Date dateTime;

	private final String text;

	/**
	 * 
	 * @param updated
	 *            the date formatted to [RFC3339]
	 * @throws RSSpectException
	 *             If the dateTime format is invalid.
	 */
	RSSDateConstruct(String dateTime) throws RSSpectException {

		if (dateTime == null || dateTime.trim().equals("")) {
			throw new RSSpectException(
					"the date for this element SHOULD NOT be blank.");
		}

		Date local = null;
		SimpleDateFormat sdf = new SimpleDateFormat(
				"EEE, dd MMM yyyy HH:mm:ss z");
		boolean valid = true;
		try {
			local = sdf.parse(dateTime);
		} catch (Exception e1) {
			valid = false;
		}

		if (!valid) {
			try {
				// example: Mon Oct 19 10:52:18 CDT 2009
				// or: Tue Oct 20 02:48:09 GMT+10:00 2009
				sdf = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
				local = sdf.parse(dateTime);
				valid = true;
			} catch (Exception e4) {
				valid = false;
			}
		}

		if (!valid) {
			throw new RSSpectException(
					"error trying to create the date element with string: "
							+ dateTime);
		}

		this.text = sdf.format(this.dateTime = new Date(local.getTime()));
	}

	RSSDateConstruct(Date dateTime) {
		this.dateTime = new Date(dateTime.getTime());
		this.text = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z")
				.format(this.dateTime);
	}

	/**
	 * 
	 * @return the date timestamp for this element.
	 */
	protected Date getDateTime() {
		return new Date(dateTime.getTime());
	}

	/**
	 * 
	 * @return the string formated version of the time for example
	 *         2006-04-28T12:50:43.337-05:00
	 */
	public String getText() {
		return text;
	}

	/**
	 * Shows the contents of the &lt;pubDate> or &lt;lastBuildDate> elements.
	 */
	@Override
	public String toString() {
		return text;
	}
}
