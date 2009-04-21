package com.colorful.rss;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * At the top level, a RSS document is a <rss> element, with a mandatory
 * attribute called version, that specifies the version of RSS that the document
 * conforms to. If it conforms to this specification, the version attribute must
 * be 2.0.
 * 
 * Subordinate to the <rss> element is a single <channel> element, which
 * contains information about the channel (metadata) and its contents. RSS
 * originated in 1999, and has strived to be a simple, easy to understand
 * format, with relatively modest goals. After it became a popular format,
 * developers wanted to extend it using modules defined in namespaces, as
 * specified by the W3C.
 * 
 * RSS 2.0 adds that capability, following a simple rule. A RSS feed may contain
 * elements not described on this page, only if those elements are defined in a
 * namespace.
 * 
 * The elements defined in this document are not themselves members of a
 * namespace, so that RSS 2.0 can remain compatible with previous versions in
 * the following sense -- a version 0.91 or 0.92 file is also a valid 2.0 file.
 * If the elements of RSS 2.0 were in a namespace, this constraint would break,
 * a version 0.9x file would not be a valid 2.0 file.
 * 
 * The version="2.0" attribute of the <rss> element is automatically provided in
 * this implementation.
 * 
 * @author Bill Brown
 * 
 */
public class RSS implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2952021194132587749L;

	private final Channel channel;
	private final List<Attribute> attributes;
	private final List<Extension> extensions;

	RSS(Channel channel, List<Attribute> attributes, List<Extension> extensions)
			throws RSSpectException {
		if (channel == null) {
			throw new RSSpectException(
					"rss elements MUST contain a channel element.");
		}
		try {
			this.channel = new Channel(channel.getTitle(), channel.getLink(),
					channel.getDescription(), channel.getLanguage(), channel
							.getCopyright(), channel.getManagingEditor(),
					channel.getWebMaster(), channel.getPubDate(), channel
							.getLastBuildDate(), channel.getCategories(),
					channel.getGenerator(), channel.getDocs(), channel
							.getCloud(), channel.getTtl(), channel.getImage(),
					channel.getRating(), channel.getTextInput(), channel
							.getSkipHours(), channel.getSkipDays(), channel
							.getItems(), channel.getExtensions());
		} catch (RSSpectException e) {
			throw e;
		}

		if (attributes == null) {
			this.attributes = null;
		} else {
			this.attributes = new LinkedList<Attribute>();
			// always add the version attribute first.
			this.attributes.add(new Attribute("version", "2.0"));
			for (Attribute attr : attributes) {
				this.attributes.add(new Attribute(attr.getName(), attr
						.getValue()));
			}
		}

		if (extensions == null) {
			this.extensions = null;
		} else {
			this.extensions = new LinkedList<Extension>();
			for (Extension extension : extensions) {
				this.extensions.add(new Extension(extension.getElementName(),
						extension.getAttributes(), extension.getContent()));
			}
		}
	}

	public Channel getChannel() {
		try {
			return (channel == null) ? null : new Channel(channel.getTitle(),
					channel.getLink(), channel.getDescription(), channel
							.getLanguage(), channel.getCopyright(), channel
							.getManagingEditor(), channel.getWebMaster(),
					channel.getPubDate(), channel.getLastBuildDate(), channel
							.getCategories(), channel.getGenerator(), channel
							.getDocs(), channel.getCloud(), channel.getTtl(),
					channel.getImage(), channel.getRating(), channel
							.getTextInput(), channel.getSkipHours(), channel
							.getSkipDays(), channel.getItems(), channel
							.getExtensions());
		} catch (Exception e) {
			// we should never get here.
			return null;
		}
	}

	/**
	 * 
	 * @return the attributes for this element.
	 */
	public List<Attribute> getAttributes() {
		if (attributes == null) {
			return null;
		} else {
			List<Attribute> attrsCopy = new LinkedList<Attribute>();
			for (Attribute attr : this.attributes) {
				attrsCopy.add(new Attribute(attr.getName(), attr.getValue()));
			}
			return attrsCopy;
		}
	}

	/**
	 * 
	 * @return the extensions for this entry.
	 */
	public List<Extension> getExtensions() {
		if (extensions == null) {
			return null;
		}
		List<Extension> extsCopy = new LinkedList<Extension>();
		for (Extension extension : this.extensions) {
			extsCopy.add(new Extension(extension.getElementName(), extension
					.getAttributes(), extension.getContent()));
		}
		return extsCopy;
	}
}
