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

/**
 * <p>
 * The &lt;hour> element.
 * </p>
 * <p>
 * From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0
 * specification</a>...
 * </p>
 * <p>
 * This class returns the hour sub element of the &lt;skipHours> element. Valid
 * values are 0 - 23.
 * </p>
 * 
 * @author bill
 * 
 */
public class Hour implements Serializable {

	private static final long serialVersionUID = -6736105071042205154L;

	private final String hour;

	Hour(String hour) throws RSSpectException {

		if (hour == null || hour.equals("")) {
			throw new RSSpectException("hour SHOULD NOT be blank.");
		}

		try {
			int localHour = Integer.parseInt(hour);
			if (localHour > 23 || localHour < 0) {
				throw new RSSpectException(
						"hour elements must be between 0 and 23 inclusive.");
			}
		} catch (NumberFormatException n) {
			throw new RSSpectException("invalid number format for hour.");
		}

		this.hour = hour;
	}

	Hour(Hour hour) {
		this.hour = hour.hour;
	}

	/**
	 * @return the hour.
	 */
	public String getHour() {
		return hour;
	}

	/**
	 * Shows the contents of the &lt;hour> element.
	 */
	@Override
	public String toString() {
		return "<hour>" + hour + "</hour>";
	}
}
