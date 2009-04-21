package com.colorful.rss;

import java.io.Serializable;

/**
 * A string indicating the program used to generate the channel.
 * 
 * @author Bill Brown
 * 
 */
public class Generator implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4131939434912584770L;

	private final String generator;

	Generator(String generator) {
		this.generator = generator;
	}

	public String getGenerator() {
		return generator;
	}

}
