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
 * A string indicating the program used to generate the channel.
 * 
 * @author Bill Brown
 * 
 */
public class Generator implements Serializable {

	private static final long serialVersionUID = -4131939434912584770L;

	private final String generator;

	Generator(String generator) {
		this.generator = generator;
	}

	/**
	 * @return the generator of the feed. 
	 */
	public String getGenerator() {
		return generator;
	}
	
	/**
	 * Shows the contents of the <generator> element.
	 */
	@Override
	public String toString() {
		return "<generator>" + generator + "</generator>";
	}

}
