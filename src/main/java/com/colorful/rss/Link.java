package com.colorful.rss;

import java.io.Serializable;

/**
 * For Channel: The URL to the HTML website corresponding to the channel.
 * 
 * For Item: The URL of the item.
 * 
 * @author Bill Brown
 * 
 */
public class Link implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4258812777994100037L;

	private final String link;

	Link(String link) throws RSSpectException {
		if (link != null) {
			link = link.trim();
			link = link.substring(0, link.indexOf(":"));
			if (URIScheme.contains(link)) {
				this.link = link;
			} else {
				throw new RSSpectException(
						"link elements must start with a valid "
								+ "Uniform Resource Identifer (URI) Schemes.  "
								+ "See www.iana.org.");
			}
		} else {
			this.link = link;
		}
	}

	public String getLink() {
		return link;
	}

}
