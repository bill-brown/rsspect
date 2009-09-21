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
package com.colorfulsoftware.rss;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Registered <a href="http://www.iana.org/assignments/uri-schemes.html">Uniform
 * Resource Identifer (URI) Schemes</a>
 * 
 * @author Bill Brown
 * 
 */
class URIScheme implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3125832208350819540L;

	private static Map<String, String> uriScheme = new HashMap<String, String>();

	static {
		uriScheme.put("aaa", null);
		uriScheme.put("aaas", null);
		uriScheme.put("acap", null);
		uriScheme.put("cap", null);
		uriScheme.put("cid", null);
		uriScheme.put("crid", null);
		uriScheme.put("data", null);
		uriScheme.put("dav", null);
		uriScheme.put("dict", null);
		uriScheme.put("dns", null);
		uriScheme.put("fax", null);
		uriScheme.put("file", null);
		uriScheme.put("ftp", null);
		uriScheme.put("go", null);
		uriScheme.put("gopher", null);
		uriScheme.put("h323", null);
		uriScheme.put("http", null);
		uriScheme.put("https", null);
		uriScheme.put("iax", null);
		uriScheme.put("icap", null);
		uriScheme.put("im", null);
		uriScheme.put("imap", null);
		uriScheme.put("info", null);
		uriScheme.put("ipp", null);
		uriScheme.put("iris", null);
		uriScheme.put("iris.beep", null);
		uriScheme.put("iris.xpc", null);
		uriScheme.put("iris.xpcs", null);
		uriScheme.put("iris.lwz", null);
		uriScheme.put("ldap", null);
		uriScheme.put("mailto", null);
		uriScheme.put("mid", null);
		uriScheme.put("modem", null);
		uriScheme.put("msrp", null);
		uriScheme.put("msrps", null);
		uriScheme.put("mtqp", null);
		uriScheme.put("mupdate", null);
		uriScheme.put("news", null);
		uriScheme.put("nfs", null);
		uriScheme.put("nntp", null);
		uriScheme.put("opaquelocktoken", null);
		uriScheme.put("pop", null);
		uriScheme.put("pres", null);
		uriScheme.put("rtsp", null);
		uriScheme.put("service", null);
		uriScheme.put("shttp", null);
		uriScheme.put("sieve", null);
		uriScheme.put("sip", null);
		uriScheme.put("sips", null);
		uriScheme.put("snmp", null);
		uriScheme.put("soap.beep", null);
		uriScheme.put("soap.beeps", null);
		uriScheme.put("tag", null);
		uriScheme.put("tel", null);
		uriScheme.put("telnet", null);
		uriScheme.put("tftp", null);
		uriScheme.put("thismessage", null);
		uriScheme.put("tip", null);
		uriScheme.put("tv", null);
		uriScheme.put("urn", null);
		uriScheme.put("vemmi", null);
		uriScheme.put("xmlrpc.beep", null);
		uriScheme.put("xmlrpc.beeps", null);
		uriScheme.put("xmpp", null);
		uriScheme.put("z39.50r", null);
		uriScheme.put("z39.50s", null);
		uriScheme.put("afs", null);
		uriScheme.put("dtn", null);
		uriScheme.put("mailserver", null);
		uriScheme.put("pack", null);
		uriScheme.put("tn3270", null);
		uriScheme.put("prospero", null);
		uriScheme.put("snews", null);
		uriScheme.put("videotex", null);
		uriScheme.put("wais", null);
	}

	public static boolean contains(String scheme) {
		return uriScheme.containsKey(scheme);
	}
}
