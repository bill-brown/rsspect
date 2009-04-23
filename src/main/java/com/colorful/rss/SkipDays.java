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
package com.colorful.rss;

import java.io.Serializable;

/**
 * A hint for aggregators telling them which days they can skip. More info <a
 * href
 * ="http://cyber.law.harvard.edu/rss/skipHoursDays.html#skipdays">Ffhere</a>s.
 * 
 * @author Bill Brown
 * 
 */
public class SkipDays implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2480844569809695010L;

	private final String skipDays;

	SkipDays(String skipDays) {
		this.skipDays = skipDays;
	}

	public String getSkipDays() {
		return skipDays;
	}

}
