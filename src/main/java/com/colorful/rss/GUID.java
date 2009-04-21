package com.colorful.rss;

import java.io.Serializable;

/**
 * A string that uniquely identifies the item. <a href=
 * "http://cyber.law.harvard.edu/rss/rss.html#ltguidgtSubelementOfLtitemgt"
 * >More</a>.
 * 
 * <guid> is an optional sub-element of <item>.
 * 
 * guid stands for globally unique identifier. It's a string that uniquely
 * identifies the item. When present, an aggregator may choose to use this
 * string to determine if an item is new.
 * 
 * <guid>http://some.server.com/weblogItem3207</guid>
 * 
 * There are no rules for the syntax of a guid. Aggregators must view them as a
 * string. It's up to the source of the feed to establish the uniqueness of the
 * string.
 * 
 * If the guid element has an attribute named "isPermaLink" with a value of
 * true, the reader may assume that it is a permalink to the item, that is, a
 * url that can be opened in a Web browser, that points to the full item
 * described by the <item> element. An example:
 * 
 * <guid isPermaLink="true">http://inessential.com/2002/09/01.php#a2</guid>
 * 
 * isPermaLink is optional, its default value is true. If its value is false,
 * the guid may not be assumed to be a url, or a url to anything in particular.
 * 
 * @author Bill Brown
 * 
 */
public class GUID implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1285259651943559185L;

	private final String guid;
	private final Attribute isPermaLink;

	GUID(Attribute isPermaLink, String guid) {
		this.isPermaLink = (isPermaLink == null) ? null : new Attribute(
				isPermaLink.getName(), isPermaLink.getValue());
		this.guid = guid;
	}

	public String getGuid() {
		return guid;
	}

	public Attribute getIsPermaLink() {
		return new Attribute(isPermaLink.getName(), isPermaLink.getValue());
	}
}
