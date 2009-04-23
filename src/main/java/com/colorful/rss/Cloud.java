/**
 * Copyright (C) 2009 William R. Brown <info@colorfulsoftware.com>
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
package com.colorful.rss;

import java.io.Serializable;

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

	private final String cloud;

	Cloud(String cloud) {
		this.cloud = cloud;
	}

	public String getCloud() {
		return cloud;
	}

}
