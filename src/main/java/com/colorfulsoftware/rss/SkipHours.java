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
 * A hint for aggregators telling them which hours they can skip. More info <a
 * href
 * ="http://cyber.law.harvard.edu/rss/skipHoursDays.html#skiphours">here</a>.
 * 
 * skipHours
 * 
 * An XML element that contains up to 24 <hour> sub-elements whose value is a
 * number between 0 and 23, representing a time in GMT, when aggregators, if
 * they support the feature, may not read the channel on hours listed in the
 * skipHours element.
 * 
 * 
 * The hour beginning at midnight is hour zero.
 * 
 * @author Bill Brown
 * 
 */
public class SkipHours implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7435503230975016475L;

	private final List<Hour> skipHours;

	SkipHours(List<Hour> skipHours) throws RSSpectException {
		if (skipHours == null || skipHours.size() == 0) {
			throw new RSSpectException(
					"skipHours elements should contain at least one <hour> sub element.");
		}
		this.skipHours = new LinkedList<Hour>();
		for (Hour hour : skipHours) {
			this.skipHours.add(new Hour(hour.getHour()));
		}
	}

	public List<Hour> getSkipHours() {
		try {
			List<Hour> skipHoursCopy = new LinkedList<Hour>();
			for (Hour hour : this.skipHours) {
				skipHoursCopy.add(new Hour(hour.getHour()));
			}
			return skipHoursCopy;
		} catch (Exception e) {
			// we should never get here.
			return null;
		}
	}

	public Hour getSkipHour(String skipHour) throws RSSpectException {
		// skipDays should not be null from constructor.
		for (Hour hour : this.skipHours) {
			if (hour.getHour().equals(skipHour)) {
				return new Hour(skipHour);
			}
		}
		return null;
	}
}
