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
			this.skipHours.add(new Hour(hour));
		}
	}

	SkipHours(SkipHours skipHours) {
		this.skipHours = skipHours.getSkipHours();
	}

	/**
	 * @return the list of hours to skip.
	 */
	public List<Hour> getSkipHours() {
		List<Hour> skipHoursCopy = new LinkedList<Hour>();
		for (Hour hour : this.skipHours) {
			skipHoursCopy.add(new Hour(hour));
		}
		return skipHoursCopy;
	}

	/**
	 * @param skipHour
	 *            the hour of the day.
	 * @return the hour object of null if not found.
	 */
	public Hour getSkipHour(String skipHour) {
		for (Hour hour : this.skipHours) {
			if (hour.getHour().equals(skipHour)) {
				return new Hour(hour);
			}
		}
		return null;
	}
}
