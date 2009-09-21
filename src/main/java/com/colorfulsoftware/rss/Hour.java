/**
 * Copyright (C) 2009 William R. Brown <info@colorfulsoftware.com>
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

public class Hour implements Serializable {

	private static final long serialVersionUID = -6736105071042205154L;

	private final String hour;

	Hour(String hour) throws RSSpectException {
		if (hour != null) {
			try {
				int localHour = Integer.parseInt(hour);
				if (localHour > 23 || localHour < 0) {
					throw new RSSpectException(
							"hour elements must be between 0 and 23 inclusive.");
				}
			} catch (NumberFormatException n) {
				throw new RSSpectException("invalid number format for hour.");
			}
		}
		this.hour = hour;
	}

	public String getHour() {
		return hour;
	}
}
