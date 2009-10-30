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
 * The <a href="http://www.w3.org/PICS/">PICS</a> rating for the channel.
 * 
 * @author Bill Brown
 * 
 */
public class Rating implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1511199969383702784L;

	private final String rating;

	Rating(String rating) {
		this.rating = rating;
	}

	/**
	 * @return the rating.
	 */
	public String getRating() {
		return rating;
	}

	/**
	 * Shows the contents of the <rating> element.
	 */
	@Override
	public String toString() {
		return "<rating>" + rating + "</rating>";
	}
}
