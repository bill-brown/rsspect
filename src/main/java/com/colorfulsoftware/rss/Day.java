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
 * The &lt;day> element.
 * </p>
 * <p>
 * From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0
 * specification</a>...
 * </p>
 * <p>
 * This class represents the day sub element of the &lt;skipDays> element.
 * </p>
 * 
 * @author Bill Brown
 * 
 */
public class Day implements Serializable {

	private static final long serialVersionUID = 1428375851718959215L;

	private final String day;

	Day(String day) throws RSSpectException {
		if (day == null || day.equals("")) {
			throw new RSSpectException("day SHOULD NOT be blank.");
		}
		this.day = day;
		if (!this.day.equals("Monday") && !this.day.equals("Tuesday")
				&& !this.day.equals("Wednesday")
				&& !this.day.equals("Thursday") && !this.day.equals("Friday")
				&& !this.day.equals("Saturday") && !this.day.equals("Sunday")) {
			throw new RSSpectException(
					"day elements must have a value of Monday, Tuesday, Wednesday, Thursday, Friday, Saturday or Sunday.");
		}

	}

	Day(Day day) {
		this.day = day.day;
	}

	/**
	 * @return the day of week; Monday, Tuesday, Wednesday, Thursday, Friday,
	 *         Saturday or Sunday.
	 */
	public String getDay() {
		return day;
	}

	/**
	 * Shows the contents of the &lt;day> element.
	 */
	@Override
	public String toString() {
		return "<day>" + day + "</day>";
	}
}
