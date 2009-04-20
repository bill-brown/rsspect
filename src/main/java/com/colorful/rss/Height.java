package com.colorful.rss;

import java.io.Serializable;

public class Height implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 828264834229377611L;

	private final String height;

	public Height(String height) throws RSSpectException {
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
