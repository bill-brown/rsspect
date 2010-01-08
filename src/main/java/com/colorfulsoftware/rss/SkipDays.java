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
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * The &lt;skipDays> element.
 * </p>
 * <p>
 * From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0
 * specification</a>...
 * </p>
 * <p>
 * A hint for aggregators telling them which days they can skip. More info <a
 * href
 * ="http://cyber.law.harvard.edu/rss/skipHoursDays.html#skipdays">here</a>.
 * </p>
 * 
 * <p>
 * An XML element that contains up to seven &lt;day> sub-elements whose value is
 * Monday, Tuesday, Wednesday, Thursday, Friday, Saturday or Sunday. Aggregators
 * may not read the channel during days listed in the &lt;skipDays> element.
 * </p>
 * 
 * @author Bill Brown
 * 
 */
public class SkipDays implements Serializable {

	private static final long serialVersionUID = -2480844569809695010L;

	private final List<Day> skipDays;

	SkipDays(List<Day> skipDays) throws RSSpectException {
		if (skipDays == null || skipDays.size() == 0) {
			throw new RSSpectException(
					"skipDays elements should contain at least one <day> sub element.");
		}
		this.skipDays = new LinkedList<Day>();
		for (Day day : skipDays) {
			this.skipDays.add(new Day(day));
		}
	}

	SkipDays(SkipDays skipDays) {
		this.skipDays = skipDays.getSkipDays();
	}

	/**
	 * @return the list of days to skip.
	 */
	public List<Day> getSkipDays() {
		List<Day> skipDaysCopy = new LinkedList<Day>();
		for (Day day : this.skipDays) {
			skipDaysCopy.add(new Day(day));
		}
		return skipDaysCopy;
	}

	/**
	 * @param skipDay
	 *            the day of the week.
	 * @return the day object of null if not found.
	 */
	public Day getSkipDay(String skipDay) {
		for (Day day : this.skipDays) {
			if (day.getDay().equals(skipDay)) {
				return new Day(day);
			}
		}
		return null;
	}

	/**
	 * Shows the contents of the &lt;skipDays> element.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("<skipDays>");
		for (Day day : this.skipDays) {
			sb.append(day);
		}
		sb.append("</skipDays>");
		return sb.toString();
	}
}
