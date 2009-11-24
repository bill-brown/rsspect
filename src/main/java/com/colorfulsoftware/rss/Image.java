/**
 * Copyright (C) 2009 William R. Brown <wbrown@colorfulsoftware.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package com.colorfulsoftware.rss;

import java.io.Serializable;

/**
 * <p>The &lt;image> element.</p>
 * <p>From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0 specification</a>...</p>
 * <p>Specifies a GIF, JPEG or PNG image that can be displayed with the channel.
 * More info <a href=
 * "http://cyber.law.harvard.edu/rss/rss.html#ltimagegtSubelementOfLtchannelgt"
 * >here</a>.</p>
 * 
 * <p>&lt;image> is an optional sub-element of &lt;channel>, which contains three
 * required and three optional sub-elements.</p>
 * 
 * <p>&lt;url> is the URL of a GIF, JPEG or PNG image that represents the channel.</p>
 * 
 * <p>&lt;title> describes the image, it's used in the ALT attribute of the HTML
 * &lt;img> tag when the channel is rendered in HTML.</p>
 * 
 * <p>&lt;link> is the URL of the site, when the channel is rendered, the image is
 * a link to the site. (Note, in practice the image &lt;title> and &lt;link>
 * should have the same value as the channel's &lt;title> and &lt;link>.</p>
 * 
 * <p>Optional elements include &lt;width> and &lt;height>, numbers, indicating the
 * width and height of the image in pixels. &lt;description> contains text that
 * is included in the TITLE attribute of the link formed around the image in the
 * HTML rendering.</p>
 * 
 * <p>Maximum value for width is 144, default value is 88.</p>
 * 
 * <p>Maximum value for height is 400, default value is 31.</p>
 * 
 * @author Bill Brown
 * 
 */
public class Image implements Serializable {

	private static final long serialVersionUID = -812074455770644390L;

	/* required sub elements */
	private final URL url;

	private final Title title;

	private final Link link;

	/* optional sub elements */
	private final Width width;

	private final Height height;

	private final Description description;

	Image(URL url, Title title, Link link, Width width, Height height,
			Description description) throws RSSpectException {
		// make sure id is present
		if (url == null) {
			throw new RSSpectException(
					"image elements MUST contain a url element.");
		}
		this.url = new URL(url);

		// make sure title is present
		if (title == null) {
			throw new RSSpectException(
					"image elements MUST contain a title element.");
		}
		this.title = new Title(title.getTitle());

		// make sure updated is present
		if (link == null) {
			throw new RSSpectException(
					"image elements MUST contain a link element.");
		}
		this.link = new Link(link);

		this.width = (width == null) ? null : new Width(width);
		this.height = (height == null) ? null : new Height(height);
		this.description = (description == null) ? null : new Description(
				description.getDescription());
	}

	Image(Image image) {
		this.url = image.getUrl();
		this.title = image.getTitle();
		this.link = image.getLink();
		this.width = image.getWidth();
		this.height = image.getHeight();
		this.description = image.getDescription();
	}

	/**
	 * @return the url element.
	 */
	public URL getUrl() {
		return new URL(url);
	}

	/**
	 * @return the title element.
	 */
	public Title getTitle() {
		return new Title(title.getTitle());
	}

	/**
	 * @return the link element.
	 */
	public Link getLink() {
		return new Link(link);

	}

	/**
	 * @return the width element.
	 */
	public Width getWidth() {
		return (width == null) ? null : new Width(width);
	}

	/**
	 * @return the height element.
	 */
	public Height getHeight() {
		return (height == null) ? null : new Height(height);
	}

	/**
	 * @return the description element.
	 */
	public Description getDescription() {
		return (description == null) ? null : new Description(description
				.getDescription());
	}

	/**
	 * Shows the contents of the &lt;image> element.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("<image>" + url + title + link);

		if (width != null) {
			sb.append(width);
		}

		if (height != null) {
			sb.append(height);
		}

		if (description != null) {
			sb.append(description);
		}

		sb.append("</image>");
		return sb.toString();
	}
}
