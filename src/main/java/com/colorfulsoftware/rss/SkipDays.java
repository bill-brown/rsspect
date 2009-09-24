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
import java.util.LinkedList;
import java.util.List;

/**
 * A hint for aggregators telling them which days they can skip. More info <a
 * href
 * ="http://cyber.law.harvard.edu/rss/skipHoursDays.html#skipdays">Ffhere</a>s.
 * 
 * An XML element that contains up to seven <day> sub-elements whose value is
 * Monday, Tuesday, Wednesday, Thursday, Friday, Saturday or Sunday. Aggregators
 * may not read the channel during days listed in the skipDays element.
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
			this.skipDays.add(new Day(day.getDay()));
		}
	}

	public List<Day> getSkipDays() {
		try {
			List<Day> skipDaysCopy = new LinkedList<Day>();
			for (Day day : this.skipDays) {
				skipDaysCopy.add(new Day(day.getDay()));
			}
			return skipDaysCopy;
		} catch (Exception e) {
			// we should never get here.
			return null;
		}
	}

	public Day getSkipDay(String skipDay) throws RSSpectException {
		if (this.skipDays != null) {
			for (Day day : this.skipDays) {
				if (day.getDay() != null && day.getDay().equals(skipDay)) {
					return new Day(skipDay);
				}
			}
		}
		return null;
	}

}
