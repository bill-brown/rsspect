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

/**
 * <p>
 * This class wraps <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0
 * specification</a> errors. It also wraps errors that compromise the quality of
 * the RSSpect built feed.
 * </p>
 * 
 * @author Bill Brown.
 * 
 */
public class RSSpectException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2226200267828833784L;

	/**
	 * @param message
	 *            the message for why the rss feed is not valid.
	 */
	public RSSpectException(String message) {
		super(message);
	}

}
