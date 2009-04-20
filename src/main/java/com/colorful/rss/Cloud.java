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

	public Cloud(String cloud) {
		this.cloud = cloud;
	}

	public String getCloud() {
		return cloud;
	}

}
