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
 * <p>The &lt;element> element.</p>
 * <p>From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0 specification</a>...</p>
 * <p>Maximum value for height is 400, default value is 31.</p>
 * 
 * @author Bill Brown
 * 
 */
public class Height implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 828264834229377611L;

	private final String height;

	Height(String height) throws RSSpectException {
		if (height != null) {
			try {
				int localHeight = Integer.parseInt(height);
				if (localHeight > 400) {
					throw new RSSpectException(
							"height cannot be greater than 400px.");
				}
			} catch (NumberFormatException n) {
				throw new RSSpectException("invalid number format for height.");
			}
		}
		this.height = height;
	}

	Height(Height height) {
		this.height = height.getHeight();
	}

	/**
	 * @return the height.
	 */
	public String getHeight() {
		return height;
	}

	/**
	 * Shows the contents of the &lt;height> element.
	 */
	@Override
	public String toString() {
		return "<height>" + height + "</height>";
	}
}
