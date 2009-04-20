package com.colorful.rss;

import java.io.Serializable;

/**
 * The RSS channel that the item came from. <a href=
 * "http://cyber.law.harvard.edu/rss/rss.html#ltsourcegtSubelementOfLtitemgt"
 * >More</a>.
 * 
 * <source> is an optional sub-element of <item>.
 * 
 * Its value is the name of the RSS channel that the item came from, derived
 * from its <title>. It has one required attribute, url, which links to the
 * XMLization of the source.
 * 
 * <source url="http://www.tomalak.org/links2.xml">Tomalak's Realm</source>
 * 
 * The purpose of this element is to propagate credit for links, to publicize
 * the sources of news items. It can be used in the Post command of an
 * aggregator. It should be generated automatically when forwarding an item from
 * an aggregator to a weblog authoring tool.
 * 
 * @author Bill Brown
 * 
 */
public class Source implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4585022053173517802L;

	private final String source;
	private final Attribute url;

	public Source(String source, Attribute url) throws RSSpectException {
		// make sure the url attribute is present
		if (url == null) {
			throw new RSSpectException(
					"source elements MUST contain a url attribute.");
		}
		this.url = new Attribute(url.getName(), url.getValue());

		this.source = source;
	}

	public Attribute getUrl() {
		return new Attribute(url.getName(), url.getValue());
	}

	public String getSource() {
		return source;
	}

}
