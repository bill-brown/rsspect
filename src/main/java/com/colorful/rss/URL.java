package com.colorful.rss;

import java.io.Serializable;

public class URL implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7336240801697282972L;

	private final String url;

	URL(String url) throws RSSpectException {
		if (url != null) {
			url = url.trim();
			url = url.substring(0, url.indexOf(":"));
			if (URIScheme.contains(url)) {
				this.url = url;
			} else {
				throw new RSSpectException(
						"link elements must start with a valid "
								+ "Uniform Resource Identifer (URI) Schemes.  "
								+ "See www.iana.org.");
			}
		} else {
			this.url = url;
		}
	}

	public String getUrl() {
		return url;
	}
}
