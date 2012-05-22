/**
 * Copyright 2011 Bill Brown
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.colorfulsoftware.rss;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class RSSDateConstruct implements Serializable {

	private static final long serialVersionUID = 661002136563928416L;

	private final Date dateTime;

	private final String text;

	/**
	 * 
	 * @param updated
	 *            the date formatted to [RFC822]
	 * @throws RSSpectException
	 *             If the dateTime format is invalid.
	 */
	RSSDateConstruct(String dateTime) throws RSSpectException {

		if (dateTime == null || dateTime.trim().equals("")) {
			throw new RSSpectException(
					"the date for this element SHOULD NOT be blank.");
		}

		Date local = null;
		List<SimpleDateFormat> formats = new ArrayList<SimpleDateFormat>();
		// for the rfc 822 spec, day of week and seconds are optional.
		// for the rss spec, year can be 2 or 4 digits with preference for 4
		formats.add(new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z"));
		formats.add(new SimpleDateFormat("dd MMM yyyy HH:mm:ss Z"));
		formats.add(new SimpleDateFormat("EEE, dd MMM yyyy HH:mm Z"));
		formats.add(new SimpleDateFormat("dd MMM yyyy HH:mm Z"));
		formats.add(new SimpleDateFormat("EEE, dd MMM yy HH:mm:ss Z"));
		formats.add(new SimpleDateFormat("dd MMM yy HH:mm:ss Z"));
		formats.add(new SimpleDateFormat("EEE, dd MMM yy HH:mm Z"));
		formats.add(new SimpleDateFormat("dd MMM yy HH:mm Z"));
		// for convenience add the default format for
		// Calendar.getInstance().getTime().toString()
		formats.add(new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy"));

		boolean valid = false;
		SimpleDateFormat sdf = null;
		for (SimpleDateFormat fmt : formats) {
			try {
				local = fmt.parse(dateTime);
				valid = true;
				sdf = fmt;
				break;
			} catch (Exception e) {
				System.out.println("error parsing date: "
						+ e.getLocalizedMessage());
			}
		}

		if (!valid) {
			throw new RSSpectException(
					"Error trying to parse a date in RFC 822 format for: '"
							+ dateTime + "'");
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

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof RSS)) {
			return false;
		}
		return text.equals(((RSSDateConstruct) obj).text);
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}
}
