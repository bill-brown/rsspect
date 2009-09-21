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

/**
 * Maximum value for height is 400, default value is 31.
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
		if(height != null) {
			try {
				int localHeight =  Integer.parseInt(height);
				if(localHeight > 400){
					throw new RSSpectException("height cannot be greater than 400px.");
				}
			}catch(NumberFormatException n){
				throw new RSSpectException("invalid number format for height.");
			}
		}
		this.height = height;
	}

	public String getHeight() {
		return height;
	}
}
