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
 * <p>
 * The &lt;managingEditor> element.
 * </p>
 * <p>
 * From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0
 * specification</a>...
 * </p>
 * <p>
 * Email address for person responsible for editorial content.
 * </p>
 * 
 * @author Bill Brown
 * 
 */
public class ManagingEditor implements Serializable {

	private static final long serialVersionUID = -6599327589073455199L;

	private final String managingEditor;

	ManagingEditor(String managingEditor) throws RSSpectException {
		if (managingEditor == null || managingEditor.equals("")) {
			throw new RSSpectException("managingEditor SHOULD NOT be blank.");
		}
		this.managingEditor = managingEditor;
	}

	ManagingEditor(ManagingEditor managingEditor) {
		this.managingEditor = managingEditor.managingEditor;
	}

	/**
	 * @return the managing editor.
	 */
	public String getManagingEditor() {
		return managingEditor;
	}

	/**
	 * Shows the contents of the &lt;managingEditor> element.
	 */
	@Override
	public String toString() {
		return "<managingEditor>" + managingEditor + "</managingEditor>";
	}
}
