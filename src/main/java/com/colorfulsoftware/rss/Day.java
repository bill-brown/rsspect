/**
 * Copyright (C) 2009 William R. Brown <wbrown@colorfulsoftware.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package com.colorfulsoftware.rss;

import java.io.Serializable;

/**
 * This class represents the day sub element of the skipDays element.
 * 
 * @author Bill Brown
 * 
 */
public class Day implements Serializable {

	private static final long serialVersionUID = 1428375851718959215L;

	private final String day;

	Day(String day) throws RSSpectException {
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
		this.day = day.getDay();
	}

	/**
	 * @return the day of week; Monday, Tuesday, Wednesday, Thursday, Friday,
	 *         Saturday or Sunday.
	 */
	public String getDay() {
		return day;
	}
	
	/**
	 * Shows the contents of the <day> element.
	 */
	@Override
	public String toString() {
		return "<day>" + day + "</day>";
	}
}
