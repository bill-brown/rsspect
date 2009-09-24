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
		this.url = new URL(url.getUrl());

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
		this.link = new Link(link.getLink());

		this.width = (width == null) ? null : new Width(width.getWidth());
		this.height = (height == null) ? null : new Height(height.getHeight());
		this.description = (description == null) ? null : new Description(
				description.getDescription());
	}

	public URL getUrl() {
		try {
			return new URL(url.getUrl());
		} catch (Exception e) {
			// we should never get here.
			return null;
		}
	}

	public Title getTitle() {
		return new Title(title.getTitle());
	}

	public Link getLink() {
		try {
			return new Link(link.getLink());
		} catch (Exception e) {
			// we should never get here.
			return null;
		}
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
		return (description == null) ? null : new Description(description
				.getDescription());
	}
}
