package com.colorful.rss;

import java.io.Serializable;

/**
 * Specifies a text input box that can be displayed with the channel. More info
 * <a href=
 * "http://cyber.law.harvard.edu/rss/rss.html#lttextinputgtSubelementOfLtchannelgt"
 * >here</a>.
 * 
 * @author Bill Brown
 * 
 */
public class TextInput implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1063471668352135813L;

	private final String textInput;

	public TextInput(String textInput) {
		this.textInput = textInput;
	}

	public String getTextInput() {
		return textInput;
	}

}
