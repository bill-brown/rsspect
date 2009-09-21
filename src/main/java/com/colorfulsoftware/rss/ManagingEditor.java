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
 * Email address for person responsible for editorial content.
 * 
 * @author Bill Brown
 * 
 */
public class ManagingEditor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6599327589073455199L;

	private final String managingEditor;

	ManagingEditor(String managingEditor) {
		this.managingEditor = managingEditor;
	}

	public String getManagingEditor() {
		return managingEditor;
	}

}
