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
 * <p>The &lt;width> element.</p>
 * <p>From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0 specification</a>...</p>
 * <p>Maximum value for width is 144, default value is 88.</p>
 * 
 * @author Bill Brown
 * 
 */
public class Width implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8591451698889716382L;

	private final String width;

	Width(String width) throws RSSpectException {
		if (width != null) {
			try {
				int localWidth = Integer.parseInt(width);
				if (localWidth > 144) {
					throw new RSSpectException(
							"width cannot be greater than 144px.");
				}
			} catch (NumberFormatException n) {
				throw new RSSpectException("invalid number format for width.");
			}
		}
		this.width = width;
	}

	Width(Width width) {
		this.width = width.getWidth();
	}

	/**
	 * @return the width.
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * Shows the contents of the &lt;width> element.
	 */
	@Override
	public String toString() {
		return "<width>" + width + "</width>";
	}
}
