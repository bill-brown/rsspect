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

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * This class is used by the RSSDoc to read an xml file into a Feed bean.
 * 
 * @author Bill Brown
 * 
 */
class RSSReader {

	/**
	 * This method transforms an xml stream into a Feed bean
	 * 
	 * @param reader
	 *            the object containing the rss data
	 * @return the rsspect RSS bean
	 * @throws Exception
	 *             if the stream cannot be parsed.
	 */
	RSS readRSS(XMLStreamReader reader) throws Exception {

		Channel channel = null;
		List<Attribute> attributes = null;
		List<Extension> extensions = null;
		String elementName = null;

		while (reader.hasNext()) {
			switch (reader.next()) {

			case XMLStreamConstants.START_DOCUMENT:
				RSSDoc.encoding = reader.getEncoding();
				RSSDoc.xml_version = reader.getVersion();
				break;

			case XMLStreamConstants.START_ELEMENT:
				elementName = getElementName(reader);
				// call each feed elements read method depending on the name
				if (elementName.equals("rss")) {
					attributes = getAttributes(reader);
				} else if (elementName.equals("channel")) {
					channel = readChannel(reader);
				} else {// extension
					extensions = readExtension(reader, extensions, elementName);
				}
				break;

			case XMLStreamConstants.END_ELEMENT:
				reader.next();
				break;
			case XMLStreamConstants.ATTRIBUTE:
			case XMLStreamConstants.CDATA:
			case XMLStreamConstants.CHARACTERS:
			case XMLStreamConstants.COMMENT:
			case XMLStreamConstants.DTD:
			case XMLStreamConstants.END_DOCUMENT:
			case XMLStreamConstants.ENTITY_DECLARATION:
			case XMLStreamConstants.ENTITY_REFERENCE:
			case XMLStreamConstants.NAMESPACE:
			case XMLStreamConstants.NOTATION_DECLARATION:
			case XMLStreamConstants.PROCESSING_INSTRUCTION:
			case XMLStreamConstants.SPACE:
				break;
			default:
				throw new Exception("unknown event in the xml file = "
						+ reader.getEventType());
			}
		}

		return RSSDoc.buildRSS(channel, attributes, extensions);
	}

	List<Attribute> getAttributes(XMLStreamReader reader) throws Exception {

		List<Attribute> attributes = new LinkedList<Attribute>();

		if (reader.getEventType() == XMLStreamConstants.START_DOCUMENT) {
			reader.next();
		}

		if (reader.getEventType() != XMLStreamConstants.START_ELEMENT) {
			return null;
		}

		int eventSkip = 0;
		for (int i = 0; i < reader.getNamespaceCount(); i++) {
			eventSkip++;
			String attrName = "xmlns";
			if (reader.getNamespacePrefix(i) != null) {
				attrName += ":" + reader.getNamespacePrefix(i);
			}

			attributes.add(RSSDoc.buildAttribute(attrName, reader
					.getNamespaceURI(i)));
		}
		for (int i = 0; i < reader.getAttributeCount(); i++) {
			eventSkip++;
			String attrName = null;
			if (reader.getAttributeName(i).getPrefix() != null
					&& !reader.getAttributeName(i).getPrefix().equals("")) {
				attrName = reader.getAttributeName(i).getPrefix() + ":"
						+ reader.getAttributeName(i).getLocalPart();
			} else {
				attrName = reader.getAttributeName(i).getLocalPart();
			}

			attributes.add(RSSDoc.buildAttribute(attrName, reader
					.getAttributeValue(i)));
		}

		// return null if no attributes were created.
		return (attributes.size() == 0) ? null : attributes;
	}

	List<Extension> readExtension(XMLStreamReader reader,
			List<Extension> extensions, String elementName) throws Exception {

		if (extensions == null) {
			extensions = new LinkedList<Extension>();
		}

		StringBuffer extText = new StringBuffer();
		List<Attribute> attributes = getAttributes(reader);

		while (reader.hasNext()) {
			boolean breakOut = false;
			String elementNamePrev = null;
			switch (reader.next()) {
			case XMLStreamConstants.START_ELEMENT:
				elementName = getElementName(reader);
				if ((elementNamePrev != null)
						&& (!elementName.equals(elementNamePrev))) {
					List<Extension> subExtn = readExtension(reader, null,
							elementName);
					Extension extn = subExtn.get(0);
					if (extn != null) {
						StringBuffer extnStr = new StringBuffer();
						extnStr.append("<" + extn.getElementName());
						List<Attribute> extnAttrs = extn.getAttributes();
						// add the attributes
						if (extnAttrs != null && extnAttrs.size() > 0) {
							for (Attribute attr : extnAttrs) {
								extnStr.append(" " + attr.getName() + "=\""
										+ attr.getValue() + "\"");
							}
						}
						extnStr.append(">");
						extnStr.append(extn.getContent());
						extnStr.append("</" + extn.getElementName() + ">");
					}
				}
				break;

			case XMLStreamConstants.END_ELEMENT:
				String elementNameEnd = getElementName(reader);
				if (elementNameEnd.equals(elementName)) {
					breakOut = true;
				}
				break;

			default:
				extText = extText.append(reader.getText());
			}
			if (breakOut) {
				break;
			}
		}
		extensions.add(RSSDoc.buildExtension(elementName, attributes, extText
				.toString()));
		return extensions;
	}

	Author readAuthor(XMLStreamReader reader) throws Exception {
		return RSSDoc.buildAuthor(reader.getElementText());
	}

	List<Category> readCategory(XMLStreamReader reader,
			List<Category> categories) throws Exception {

		if (categories == null) {
			categories = new LinkedList<Category>();
		}

		categories.add(RSSDoc.buildCategory(RSSDoc.getAttributeFromGroup(
				getAttributes(reader), "domain"), reader.getElementText()));

		return categories;
	}

	Channel readChannel(XMLStreamReader reader) throws Exception {

		Title title = null;
		Link link = null;
		Description description = null;
		Language language = null;
		Copyright copyright = null;
		ManagingEditor managingEditor = null;
		WebMaster webMaster = null;
		PubDate pubDate = null;
		LastBuildDate lastBuildDate = null;
		List<Category> categories = null;
		Generator generator = null;
		Docs docs = null;
		Cloud cloud = null;
		TTL ttl = null;
		Image image = null;
		Rating rating = null;
		TextInput textInput = null;
		SkipHours skipHours = null;
		SkipDays skipDays = null;
		List<Item> items = null;
		List<Extension> extensions = null;
		String elementName = null;

		while (reader.hasNext()) {
			boolean breakOut = false;
			switch (reader.next()) {

			case XMLStreamConstants.START_ELEMENT:
				elementName = getElementName(reader);
				// call each feed elements read method depending on the name
				if (elementName.equals("title")) {
					title = readTitle(reader);
				} else if (elementName.equals("link")) {
					link = readLink(reader);
				} else if (elementName.equals("description")) {
					description = readDescription(reader);
				} else if (elementName.equals("language")) {
					language = readLanguage(reader);
				} else if (elementName.equals("copyright")) {
					copyright = readCopyright(reader);
				} else if (elementName.equals("managingEditor")) {
					managingEditor = readManagingEditor(reader);
				} else if (elementName.equals("webMaster")) {
					webMaster = readWebMaster(reader);
				} else if (elementName.equals("pubDate")) {
					pubDate = readPubDate(reader);
				} else if (elementName.equals("lastBuildDate")) {
					lastBuildDate = readLastBuildDate(reader);
				} else if (elementName.equals("category")) {
					categories = readCategory(reader, categories);
				} else if (elementName.equals("generator")) {
					generator = readGenerator(reader);
				} else if (elementName.equals("docs")) {
					docs = readDocs(reader);
				} else if (elementName.equals("cloud")) {
					cloud = readCloud(reader);
				} else if (elementName.equals("ttl")) {
					ttl = readTTL(reader);
				} else if (elementName.equals("image")) {
					image = readImage(reader);
				} else if (elementName.equals("rating")) {
					rating = readRating(reader);
				} else if (elementName.equals("textInput")) {
					textInput = readTextInput(reader);
				} else if (elementName.equals("skipHours")) {
					skipHours = readSkipHours(reader);
				} else if (elementName.equals("skipDays")) {
					skipDays = readSkipDays(reader);
				} else if (elementName.equals("item")) {
					items = readItem(reader, items);
				} else {// extension
					extensions = readExtension(reader, extensions, elementName);
				}
				break;

			case XMLStreamConstants.END_ELEMENT:
				elementName = getElementName(reader);
				if (elementName.equals("channel")) {
					breakOut = true;
				}
				break;
			}
			if (breakOut) {
				break;
			}
		}

		return RSSDoc.buildChannel(title, link, description, language,
				copyright, managingEditor, webMaster, pubDate, lastBuildDate,
				categories, generator, docs, cloud, ttl, image, rating,
				textInput, skipHours, skipDays, extensions, items);

	}

	Cloud readCloud(XMLStreamReader reader) throws Exception {
		return RSSDoc.buildCloud(getAttributes(reader));
	}

	Comments readComments(XMLStreamReader reader) throws Exception {
		return RSSDoc.buildComments(reader.getElementText());
	}

	Copyright readCopyright(XMLStreamReader reader) throws Exception {
		return RSSDoc.buildCopyright(reader.getElementText());
	}

	Description readDescription(XMLStreamReader reader) throws Exception {
		return RSSDoc.buildDescription(readEncodedHTML(reader, "description"));
	}

	Docs readDocs(XMLStreamReader reader) throws Exception {
		return RSSDoc.buildDocs(reader.getElementText());
	}

	Enclosure readEnclosure(XMLStreamReader reader) throws Exception {
		return RSSDoc.buildEnclosure(getAttributes(reader));
	}

	Generator readGenerator(XMLStreamReader reader) throws Exception {
		return RSSDoc.buildGenerator(reader.getElementText());
	}

	GUID readGUID(XMLStreamReader reader) throws Exception {
		return RSSDoc.buildGUID(RSSDoc.getAttributeFromGroup(
				getAttributes(reader), "isPermaLink"), reader.getElementText());
	}

	Height readHeight(XMLStreamReader reader) throws Exception {
		return RSSDoc.buildHeight(reader.getElementText());
	}

	Image readImage(XMLStreamReader reader) throws Exception {

		URL url = null;
		Title title = null;
		Link link = null;
		Width width = null;
		Height height = null;
		Description description = null;
		String elementName = null;

		while (reader.hasNext()) {
			boolean breakOut = false;
			switch (reader.next()) {

			case XMLStreamConstants.START_ELEMENT:
				elementName = getElementName(reader);
				// call each feed elements read method depending on the name
				if (elementName.equals("url")) {
					url = readURL(reader);
				} else if (elementName.equals("title")) {
					title = readTitle(reader);
				} else if (elementName.equals("link")) {
					link = readLink(reader);
				} else if (elementName.equals("width")) {
					width = readWidth(reader);
				} else if (elementName.equals("height")) {
					height = readHeight(reader);
				} else if (elementName.equals("description")) {
					description = readDescription(reader);
				}
				break;

			case XMLStreamConstants.END_ELEMENT:
				elementName = getElementName(reader);
				if (elementName.equals("image")) {
					breakOut = true;
				}
				break;
			}
			if (breakOut) {
				break;
			}
		}

		return RSSDoc.buildImage(url, title, link, width, height, description);

	}

	List<Item> readItem(XMLStreamReader reader, List<Item> items)
			throws Exception {

		if (items == null) {
			items = new LinkedList<Item>();
		}
		boolean breakOut = false;
		Title title = null;
		Link link = null;
		Description description = null;
		Author author = null;
		List<Category> categories = null;
		Comments comments = null;
		Enclosure enclosure = null;
		GUID guid = null;
		PubDate pubDate = null;
		Source source = null;
		List<Extension> extensions = null;
		String elementName = null;

		while (reader.hasNext()) {
			switch (reader.next()) {

			case XMLStreamConstants.START_ELEMENT:
				elementName = getElementName(reader);
				// call each feed elements read method depending on the name
				if (elementName.equals("title")) {
					title = readTitle(reader);
				} else if (elementName.equals("link")) {
					link = readLink(reader);
				} else if (elementName.equals("description")) {
					description = RSSDoc.buildDescription(readEncodedHTML(
							reader, "description"));
				} else if (elementName.equals("author")) {
					author = readAuthor(reader);
				} else if (elementName.equals("category")) {
					categories = readCategory(reader, categories);
				} else if (elementName.equals("comments")) {
					comments = readComments(reader);
				} else if (elementName.equals("enclosure")) {
					enclosure = readEnclosure(reader);
				} else if (elementName.equals("guid")) {
					guid = readGUID(reader);
				} else if (elementName.equals("pubDate")) {
					pubDate = readPubDate(reader);
				} else if (elementName.equals("source")) {
					source = readSource(reader);
				} else {// extension
					extensions = readExtension(reader, extensions, elementName);
				}
				break;

			case XMLStreamConstants.END_ELEMENT:
				elementName = getElementName(reader);
				if (elementName.equals("item")) {
					breakOut = true;
				}
				break;
			}
			if (breakOut) {
				break;
			}
		}

		items.add(RSSDoc.buildItem(title, link, description, author,
				categories, comments, enclosure, guid, pubDate, source,
				extensions));

		return items;
	}

	Language readLanguage(XMLStreamReader reader) throws Exception {
		return RSSDoc.buildLanguage(reader.getElementText());
	}

	LastBuildDate readLastBuildDate(XMLStreamReader reader) throws Exception {
		String dateText = reader.getElementText();
		try {
			return RSSDoc.buildLastBuildDate(getSimpleDateFormat().parse(
					dateText));
		} catch (Exception e) {
			SimpleDateFormat simpleDateFmt2 = new SimpleDateFormat(
					getSimpleDateFormat().toPattern().substring(0, 19));
			return RSSDoc.buildLastBuildDate(simpleDateFmt2.parse(dateText
					.substring(0, 19)));
		}
	}

	Link readLink(XMLStreamReader reader) throws Exception {
		return RSSDoc.buildLink(reader.getElementText());
	}

	ManagingEditor readManagingEditor(XMLStreamReader reader) throws Exception {
		return RSSDoc.buildManagingEditor(reader.getElementText());
	}

	Name readName(XMLStreamReader reader) throws Exception {
		return RSSDoc.buildName(reader.getElementText());
	}

	SimpleDateFormat getSimpleDateFormat() {
		// example Sun, 19 May 2002 15:21:36 GMT
		return new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
	}

	PubDate readPubDate(XMLStreamReader reader) throws Exception {
		String dateText = reader.getElementText();
		try {
			return RSSDoc.buildPubDate(getSimpleDateFormat().parse(dateText));
		} catch (Exception e) {
			SimpleDateFormat simpleDateFmt2 = new SimpleDateFormat(
					getSimpleDateFormat().toPattern().substring(0, 19));
			return RSSDoc.buildPubDate(simpleDateFmt2.parse(dateText.substring(
					0, 19)));
		}
	}

	Rating readRating(XMLStreamReader reader) throws Exception {
		return RSSDoc.buildRating(reader.getElementText());
	}

	SkipDays readSkipDays(XMLStreamReader reader) throws Exception {
		return RSSDoc.buildSkipDays(reader.getElementText());
	}

	SkipHours readSkipHours(XMLStreamReader reader) throws Exception {
		return RSSDoc.buildSkipHours(reader.getElementText());
	}

	Source readSource(XMLStreamReader reader) throws Exception {
		return RSSDoc.buildSource(RSSDoc.getAttributeFromGroup(
				getAttributes(reader), "url"), reader.getElementText());
	}

	TextInput readTextInput(XMLStreamReader reader) throws Exception {

		Title title = null;
		Description description = null;
		Name name = null;
		Link link = null;
		String elementName = null;

		while (reader.hasNext()) {
			boolean breakOut = false;
			switch (reader.next()) {

			case XMLStreamConstants.START_ELEMENT:
				elementName = getElementName(reader);
				// call each feed elements read method depending on the name
				if (elementName.equals("title")) {
					title = readTitle(reader);
				} else if (elementName.equals("description")) {
					description = readDescription(reader);
				} else if (elementName.equals("name")) {
					name = readName(reader);
				} else if (elementName.equals("link")) {
					link = readLink(reader);
				}
				break;

			case XMLStreamConstants.END_ELEMENT:
				elementName = getElementName(reader);
				if (elementName.equals("textInput")) {
					breakOut = true;
				}
				break;
			}
			if (breakOut) {
				break;
			}
		}

		return RSSDoc.buildTextInput(title, description, name, link);

	}

	Title readTitle(XMLStreamReader reader) throws Exception {
		return RSSDoc.buildTitle(reader.getElementText());
	}

	TTL readTTL(XMLStreamReader reader) throws Exception {
		return RSSDoc.buildTTL(reader.getElementText());
	}

	URL readURL(XMLStreamReader reader) throws Exception {
		return RSSDoc.buildURL(reader.getElementText());
	}

	WebMaster readWebMaster(XMLStreamReader reader) throws Exception {
		return RSSDoc.buildWebMaster(reader.getElementText());
	}

	Width readWidth(XMLStreamReader reader) throws Exception {
		return RSSDoc.buildWidth(reader.getElementText());
	}

	String readEncodedHTML(XMLStreamReader reader, String parentElement)
			throws XMLStreamException, Exception {
		StringBuffer xhtml = new StringBuffer();
		String elementName = null;

		while (reader.hasNext()) {
			boolean breakOut = false;
			int next = reader.next();

			switch (next) {

			case XMLStreamConstants.START_ELEMENT:
				elementName = getElementName(reader);
				xhtml.append("<" + elementName);
				List<Attribute> attributes = getAttributes(reader);
				// add the attributes
				if (attributes != null && attributes.size() > 0) {
					for (Attribute attr : attributes) {
						xhtml.append(" " + attr.getName() + "=\""
								+ attr.getValue() + "\"");
					}
				}
				xhtml.append(">");
				break;

			case XMLStreamConstants.END_ELEMENT:
				elementName = getElementName(reader);
				if (elementName.equals(parentElement)
						&& namespaceURI.equals("")) {
					breakOut = true;
				} else {
					xhtml.append("</" + elementName + ">");
				}
				break;

			case XMLStreamConstants.CDATA:
				xhtml.append("<![CDATA[" + reader.getText() + "]]>");
				break;

			default:
				// escape the necessary characters.
				String escapedTxt = reader.getText().replaceAll("&", "&amp;")
						.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
				xhtml.append(escapedTxt);
			}
			if (breakOut) {
				break;
			}
		}
		return xhtml.toString();
	}

	// set the current namespace to the "empty namespace".
	private String namespaceURI = "";

	private String getElementName(XMLStreamReader reader) {
		String elementName = null;
		String prefix = reader.getPrefix();
		if (prefix != null && !prefix.equals("")) {
			elementName = prefix + ":" + reader.getLocalName();
		} else {
			elementName = reader.getLocalName();
		}
		// set the current namespace prefix:
		namespaceURI = (reader.getNamespaceURI() == null) ? "" : reader
				.getNamespaceURI();
		return elementName;
	}
}