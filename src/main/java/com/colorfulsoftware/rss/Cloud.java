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
import java.util.LinkedList;
import java.util.List;

/**
 * Allows processes to register with a cloud to be notified of updates to the
 * channel, implementing a lightweight publish-subscribe protocol for RSS feeds.
 * More info <a href=
 * "http://cyber.law.harvard.edu/rss/rss.html#ltcloudgtSubelementOfLtchannelgt"
 * >here</a>
 * 
 * <cloud> sub-element of <channel>
 * 
 * <cloud> is an optional sub-element of <channel>.
 * 
 * It specifies a web service that supports the rssCloud interface which can be
 * implemented in HTTP-POST, XML-RPC or SOAP 1.1.
 * 
 * Its purpose is to allow processes to register with a cloud to be notified of
 * updates to the channel, implementing a lightweight publish-subscribe protocol
 * for RSS feeds.
 * 
 * <cloud domain="rpc.sys.com" port="80" path="/RPC2"
 * registerProcedure="myCloud.rssPleaseNotify" protocol="xml-rpc" />
 * 
 * In this example, to request notification on the channel it appears in, you
 * would send an XML-RPC message to rpc.sys.com on port 80, with a path of
 * /RPC2. The procedure to call is myCloud.rssPleaseNotify.
 * 
 * A full explanation of this element and the rssCloud interface is <a
 * href="http://cyber.law.harvard.edu/rss/soapMeetsRss.html#rsscloudInterface"
 * >here</a>.
 * 
 * @author Bill Brown
 * 
 */
public class Cloud implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4431999134564899474L;

	private final List<Attribute> attributes;
	private final Attribute domain;
	private final Attribute port;
	private final Attribute path;
	private final Attribute registerProcedure;
	private final Attribute protocol;

	Cloud(List<Attribute> attributes) throws RSSpectException {
		if (attributes == null) {
			throw new RSSpectException(
					"The cloud element requires attributes:  See \"http://cyber.law.harvard.edu/rss/soapMeetsRss.html#rsscloudInterface\".");
		} else {
			this.attributes = new LinkedList<Attribute>();
			for (Attribute attr : attributes) {
				// check for unsupported attribute.
				this.attributes.add(new Attribute(attr.getName(), attr
						.getValue()));
			}
		}

		if ((this.domain = getAttribute("domain")) == null) {
			throw new RSSpectException(
					"cloud elements MUST have a domain attribute.");
		}

		if ((this.port = getAttribute("port")) == null) {
			throw new RSSpectException(
					"cloud elements MUST have a port attribute.");
		}

		if ((this.path = getAttribute("path")) == null) {
			throw new RSSpectException(
					"cloud elements MUST have a path attribute.");
		}

		if ((this.registerProcedure = getAttribute("registerProcedure")) == null) {
			throw new RSSpectException(
					"cloud elements MUST have a registerProcedure attribute.");
		}

		if ((this.protocol = getAttribute("protocol")) == null) {
			throw new RSSpectException(
					"cloud elements MUST have a protocol attribute.");
		}
		if (!getAttribute("protocol").getValue().equals("xml-rpc")
				&& !getAttribute("protocol").getValue().equals("soap")) {
			throw new RSSpectException(
					"the cloud's protocol attribute must be 'xml-rpc' or 'soap', case-sensitive.");
		}
	}

	Cloud(Cloud cloud) {
		this.attributes = cloud.getAttributes();
		this.domain = cloud.getDomain();
		this.port = cloud.getPort();
		this.path = cloud.getPath();
		this.registerProcedure = cloud.getRegisterProcedure();
		this.protocol = cloud.getProtocol();
	}

	/**
	 * 
	 * @return the cloud attribute list.
	 */
	public List<Attribute> getAttributes() {

		List<Attribute> attrsCopy = new LinkedList<Attribute>();
		if (this.attributes != null) {
			for (Attribute attr : this.attributes) {
				attrsCopy.add(new Attribute(attr));
			}
		}
		return (this.attributes == null) ? null : attrsCopy;
	}

	/**
	 * @return the domain attribute.
	 */
	public Attribute getDomain() {
		return (domain == null) ? null : new Attribute(domain);
	}

	/**
	 * @return the port attribute
	 */
	public Attribute getPort() {
		return (port == null) ? null : new Attribute(port);
	}

	/**
	 * @return the path attribute
	 */
	public Attribute getPath() {
		return (path == null) ? null : new Attribute(path);
	}

	/**
	 * @return the registerProcedure attribute.
	 */
	public Attribute getRegisterProcedure() {
		return (registerProcedure == null) ? null : new Attribute(
				registerProcedure);
	}

	/**
	 * @return the protocol attribute.
	 */
	public Attribute getProtocol() {
		return (protocol == null) ? null : new Attribute(protocol);
	}

	/**
	 * @param attrName
	 *            the name of the attribute to get.
	 * @return the Attribute object if attrName matches or null if not found.
	 */
	public Attribute getAttribute(String attrName) {
		if (this.attributes != null) {
			for (Attribute attribute : this.attributes) {
				if (attribute.getName().equals(attrName)) {
					return new Attribute(attribute);
				}
			}
		}
		return null;
	}

	/**
	 * Shows the contents of the <cloud> element.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("<cloud");
		for (Attribute attribute : attributes) {
			sb.append(attribute);
		}
		sb.append(" />");
		return sb.toString();
	}
}
