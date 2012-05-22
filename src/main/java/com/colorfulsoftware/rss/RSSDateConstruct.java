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
				"EEE, dd MMM yyyy HH:mm:ss Z");
		SimpleDateFormat sdf2 = new SimpleDateFormat(
				"dd MMM yyyy HH:mm:ss Z");
		SimpleDateFormat sdf3 = new SimpleDateFormat(
                                "EEE, dd MMM yyyy HH:mm Z");
		SimpleDateFormat sdf4 = new SimpleDateFormat(
                                "dd MMM yyyy HH:mm Z");
		SimpleDateFormat[] rfc822 = new 
SimpleDateFormat[]{sdf,sdf2,sdf3,sdf4};

		boolean valid = false;
		for(SimpleDateFormat fmt: rfc822){
		try {
			local = fmt.parse(dateTime);
			valid = true;
			break;
		} catch (Exception e1) {
			e1.printStackTrace();
			//System.out.println("e.getMessage());
		}
		}

		//*		
		//if (!valid) {	
		//	try {
				// example: Mon Oct 19 10:52:18 CDT 2009
				// or: Tue Oct 20 02:48:09 GMT+10:00 2009
		//		sdf = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
		//		local = sdf.parse(dateTime);
		//		valid = true;
		//	} catch (Exception e4) {
		//		valid = false;
		//	}
		//}
		//*/

		if (!valid) {
			throw new RSSpectException(
					"error trying to parse rfc822 date: '"
							+ dateTime+"'");
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
