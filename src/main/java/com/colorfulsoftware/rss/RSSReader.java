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

	private RSSDoc rss;

	public RSSReader() throws Exception {
		rss = new RSSDoc();
	}

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
			int next = reader.next();
			switch (next) {

			// so far: neither the stax-api or geronimo stax implementations
			// can see this :(
			// case XMLStreamConstants.START_DOCUMENT:
			// rss = new RSSDoc(reader.getEncoding(), reader.getVersion());
			// break;

			case XMLStreamConstants.START_ELEMENT:
				elementName = getElementName(reader);
				// call each feed elements read method depending on the name
				if (elementName.equals("rss")) {
					attributes = getAttributes(reader);
				} else if (elementName.equals("channel")) {
					if (attributes == null) {
						throw new RSSpectException(
								"rss documents must contain the version attribute.");
					}
					channel = readChannel(reader);
				} else {// extension
					if (attributes == null) {
						throw new RSSpectException(
								"rss documents must contain the version attribute.");
					}
					extensions = readExtension(reader, extensions, elementName);
				}
				break;

			case XMLStreamConstants.END_ELEMENT:
				reader.next();
				break;

			default:
				break;
			}
		}

		return rss.buildRSS(channel, attributes, extensions);
	}

	List<Attribute> getAttributes(XMLStreamReader reader) throws Exception {

		List<Attribute> attributes = new LinkedList<Attribute>();

		int eventSkip = 0;
		for (int i = 0; i < reader.getNamespaceCount(); i++) {
			eventSkip++;
			String attrName = "xmlns";
			if (reader.getNamespacePrefix(i) != null) {
				attrName += ":" + reader.getNamespacePrefix(i);
			}

			attributes.add(rss.buildAttribute(attrName, reader
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

			attributes.add(rss.buildAttribute(attrName, reader
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

		StringBuilder extText = new StringBuilder();
		List<Attribute> attributes = getAttributes(reader);

		String elementNameOrig = elementName;
		boolean breakOut = false;
		while (reader.hasNext()) {
			switch (reader.next()) {
			case XMLStreamConstants.START_ELEMENT:
				elementName = getElementName(reader);
				if (!elementName.equals(elementNameOrig)) {
					extText.append(readEncodedHTML(reader, elementName));
				}
				break;

			case XMLStreamConstants.END_ELEMENT:
				String elementNameEnd = getElementName(reader);
				if (elementNameEnd.equals(elementNameOrig)) {
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
		extensions.add(rss.buildExtension(elementName, attributes, extText
				.toString()));
		return extensions;
	}

	Author readAuthor(XMLStreamReader reader) throws Exception {
		return rss.buildAuthor(reader.getElementText());
	}

	List<Category> readCategory(XMLStreamReader reader,
			List<Category> categories) throws Exception {

		if (categories == null) {
			categories = new LinkedList<Category>();
		}

		categories.add(rss.buildCategory(getAttributeFromGroup(
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

		boolean breakOut = false;
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

		return rss.buildChannel(title, link, description, language, copyright,
				managingEditor, webMaster, pubDate, lastBuildDate, categories,
				generator, docs, cloud, ttl, image, rating, textInput,
				skipHours, skipDays, extensions, items);

	}

	Cloud readCloud(XMLStreamReader reader) throws Exception {
		return rss.buildCloud(getAttributes(reader));
	}

	Comments readComments(XMLStreamReader reader) throws Exception {
		return rss.buildComments(reader.getElementText());
	}

	Copyright readCopyright(XMLStreamReader reader) throws Exception {
		return rss.buildCopyright(reader.getElementText());
	}

	Description readDescription(XMLStreamReader reader) throws Exception {
		return rss.buildDescription(readEncodedHTML(reader, "description"));
	}

	Docs readDocs(XMLStreamReader reader) throws Exception {
		return rss.buildDocs(reader.getElementText());
	}

	Enclosure readEnclosure(XMLStreamReader reader) throws Exception {
		return rss.buildEnclosure(getAttributes(reader));
	}

	Generator readGenerator(XMLStreamReader reader) throws Exception {
		return rss.buildGenerator(reader.getElementText());
	}

	GUID readGUID(XMLStreamReader reader) throws Exception {
		return rss.buildGUID(getAttributeFromGroup(getAttributes(reader),
				"isPermaLink"), reader.getElementText());
	}

	Height readHeight(XMLStreamReader reader) throws Exception {
		return rss.buildHeight(reader.getElementText());
	}

	Image readImage(XMLStreamReader reader) throws Exception {

		URL url = null;
		Title title = null;
		Link link = null;
		Width width = null;
		Height height = null;
		Description description = null;
		String elementName = null;

		boolean breakOut = false;
		while (reader.hasNext()) {
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

		return rss.buildImage(url, title, link, width, height, description);

	}

	List<Item> readItem(XMLStreamReader reader, List<Item> items)
			throws Exception {

		if (items == null) {
			items = new LinkedList<Item>();
		}
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

		boolean breakOut = false;
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
					description = rss.buildDescription(readEncodedHTML(reader,
							"description"));
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

		items.add(rss.buildItem(title, link, description, author, categories,
				comments, enclosure, guid, pubDate, source, extensions));

		return items;
	}

	Language readLanguage(XMLStreamReader reader) throws Exception {
		return rss.buildLanguage(reader.getElementText());
	}

	LastBuildDate readLastBuildDate(XMLStreamReader reader) throws Exception {
		return rss.buildLastBuildDate(reader.getElementText());
	}

	Link readLink(XMLStreamReader reader) throws Exception {
		return rss.buildLink(reader.getElementText());
	}

	ManagingEditor readManagingEditor(XMLStreamReader reader) throws Exception {
		return rss.buildManagingEditor(reader.getElementText());
	}

	Name readName(XMLStreamReader reader) throws Exception {
		return rss.buildName(reader.getElementText());
	}

	PubDate readPubDate(XMLStreamReader reader) throws Exception {
		return rss.buildPubDate(reader.getElementText());
	}

	Rating readRating(XMLStreamReader reader) throws Exception {
		return rss.buildRating(reader.getElementText());
	}

	SkipDays readSkipDays(XMLStreamReader reader) throws Exception {

		List<Day> days = null;
		String elementName = null;

		boolean breakOut = false;
		while (reader.hasNext()) {
			switch (reader.next()) {

			case XMLStreamConstants.START_ELEMENT:
				elementName = getElementName(reader);
				if (elementName.equals("day")) {
					days = readDay(reader, days);
				}
				break;

			case XMLStreamConstants.END_ELEMENT:
				elementName = getElementName(reader);
				if (elementName.equals("skipDays")) {
					breakOut = true;
				}
				break;
			}
			if (breakOut) {
				break;
			}
		}
		return rss.buildSkipDays(days);
	}

	List<Day> readDay(XMLStreamReader reader, List<Day> days) throws Exception {
		if (days == null) {
			days = new LinkedList<Day>();
		}
		days.add(rss.buildDay(reader.getElementText()));
		return days;
	}

	SkipHours readSkipHours(XMLStreamReader reader) throws Exception {

		List<Hour> hours = null;
		String elementName = null;
		boolean breakOut = false;

		while (reader.hasNext()) {
			switch (reader.next()) {

			case XMLStreamConstants.START_ELEMENT:
				elementName = getElementName(reader);
				if (elementName.equals("hour")) {
					hours = readHour(reader, hours);
				}
				break;

			case XMLStreamConstants.END_ELEMENT:
				elementName = getElementName(reader);
				if (elementName.equals("skipHours")) {
					breakOut = true;
				}
				break;
			}
			if (breakOut) {
				break;
			}
		}
		return rss.buildSkipHours(hours);
	}

	List<Hour> readHour(XMLStreamReader reader, List<Hour> hours)
			throws Exception {
		if (hours == null) {
			hours = new LinkedList<Hour>();
		}
		hours.add(rss.buildHour(reader.getElementText()));
		return hours;
	}

	Source readSource(XMLStreamReader reader) throws Exception {
		return rss.buildSource(getAttributeFromGroup(getAttributes(reader),
				"url"), reader.getElementText());
	}

	TextInput readTextInput(XMLStreamReader reader) throws Exception {

		Title title = null;
		Description description = null;
		Name name = null;
		Link link = null;
		String elementName = null;
		boolean breakOut = false;

		while (reader.hasNext()) {
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

		return rss.buildTextInput(title, description, name, link);

	}

	Title readTitle(XMLStreamReader reader) throws Exception {
		return rss.buildTitle(reader.getElementText());
	}

	TTL readTTL(XMLStreamReader reader) throws Exception {
		return rss.buildTTL(reader.getElementText());
	}

	URL readURL(XMLStreamReader reader) throws Exception {
		return rss.buildURL(reader.getElementText());
	}

	WebMaster readWebMaster(XMLStreamReader reader) throws Exception {
		return rss.buildWebMaster(reader.getElementText());
	}

	Width readWidth(XMLStreamReader reader) throws Exception {
		return rss.buildWidth(reader.getElementText());
	}

	String readEncodedHTML(XMLStreamReader reader, String parentElement)
			throws XMLStreamException, Exception {
		StringBuilder xhtml = new StringBuilder();
		String elementName = null;
		boolean breakOut = false;
		while (reader.hasNext()) {
			switch (reader.next()) {

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
				if (elementName.equals(parentElement)) {
					breakOut = true;
				} else {
					xhtml.append("</" + elementName + ">");
				}
				break;

			// so far: neither the stax-api or geronimo stax implementations
			// can see this :(
			// case XMLStreamConstants.CDATA:
			// xhtml.append("<![CDATA[" + reader.getText() + "]]>");
			// break;

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

	private String getElementName(XMLStreamReader reader) {
		String elementName = null;
		String prefix = reader.getPrefix();
		if (prefix != null && !prefix.equals("")) {
			elementName = prefix + ":" + reader.getLocalName();
		} else {
			elementName = reader.getLocalName();
		}
		return elementName;
	}

	// checks for and returns the Attribute from the String attribute (argument)
	// in the list of attributes (argument)
	private Attribute getAttributeFromGroup(List<Attribute> attributes,
			String attributeName) {
		if (attributes != null) {
			for (Attribute current : attributes) {
				if (current.getName().equalsIgnoreCase(attributeName)) {
					return new Attribute(current);
				}
			}
		}
		return null;
	}
}