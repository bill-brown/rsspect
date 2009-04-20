package com.colorful.rss;

import java.io.Serializable;

/**
 * Maximum value for width is 144, default value is 88. 
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

	public Width(String width) throws RSSpectException {
		if(width != null) {
			try {
				int localHeight =  Integer.parseInt(width);
				if(localHeight > 400){
					throw new RSSpectException("height cannot be greater than 144px.");
				}
			}catch(NumberFormatException n){
				throw new RSSpectException("invalid number format for width.");
			}
		}
		this.width = width;
	}

	public String getWidth() {
		return width;
	}
}
