package com.colorful.rss;

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

	public ManagingEditor(String managingEditor) {
		this.managingEditor = managingEditor;
	}

	public String getManagingEditor() {
		return managingEditor;
	}

}
