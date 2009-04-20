package com.colorful.rss;

import java.io.Serializable;

/**
 * Specifies a GIF, JPEG or PNG image that can be displayed with the channel.
 * More info <a href=
 * "http://cyber.law.harvard.edu/rss/rss.html#ltimagegtSubelementOfLtchannelgt"
 * >here</a>.
 * 
 * <image> is an optional sub-element of <channel>, which contains three
 * required and three optional sub-elements.
 * 
 * <url> is the URL of a GIF, JPEG or PNG image that represents the channel.
 * 
 * <title> describes the image, it's used in the ALT attribute of the HTML <img>
 * tag when the channel is rendered in HTML.
 * 
 * <link> is the URL of the site, when the channel is rendered, the image is a
 * link to the site. (Note, in practice the image <title> and <link> should have
 * the same value as the channel's <title> and <link>.
 * 
 * Optional elements include <width> and <height>, numbers, indicating the width
 * and height of the image in pixels. <description> contains text that is
 * included in the TITLE attribute of the link formed around the image in the
 * HTML rendering.
 * 
 * Maximum value for width is 144, default value is 88.
 * 
 * Maximum value for height is 400, default value is 31.
 * 
 * @author Bill Brown
 * 
 */
public class Image implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -812074455770644390L;

	private final URL url;

	private final Title title;

	private final Link link;

	private final Width width;

	private final Height height;

	private final Description description;

	public Image(URL url, Title title, Link link, Width width, Height height,
			Description description) throws RSSpectException {
		// make sure id is present
		if (url == null) {
			throw new RSSpectException(
					"image elements MUST contain a url element.");
		}
		this.url = url;

		// make sure title is present
		if (title == null) {
			throw new RSSpectException(
					"channel elements MUST contain a title element.");
		}
		this.title = title;

		// make sure updated is present
		if (link == null) {
			throw new RSSpectException(
					"channel elements MUST contain a link element.");
		}
		this.link = link;

		this.width = (width == null) ? null : new Width(width.getWidth());
		this.height = (height == null) ? null : new Height(height.getHeight());
		this.description = (description == null) ? null : new Description(
				description.getDescription());
	}

	public URL getUrl() {
		return new URL(url.getUrl());
	}

	public Title getTitle() {
		return new Title(title.getTitle());
	}

	public Link getLink() {
		return new Link(link.getLink());
	}

	public Width getWidth() {
		try {
			return (width == null) ? null : new Width(width.getWidth());
		} catch (Exception e) {
			// we should never get here.
			return null;
		}
	}

	public Height getHeight() {
		try {
			return (height == null) ? null : new Height(height.getHeight());
		} catch (Exception e) {
			// we should never get here.
			return null;
		}
	}

	public Description getDescription() {
		return new Description(description.getDescription());
	}
}
