/*
Atomsphere - an atom feed library.
Copyright (C) 2006 William R. Brown.

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
/* Change History:
 *  2006-11-14 wbrown - added javadoc documentation.
 *  2008-03-11 wbrown - fix bug for atomXHTMLTextConstruct to wrap contents in xhtml:div element.
 *  2008-04-17 wbrown - add check for start document in readEntry
 */
package com.colorful.rss;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.TimeZone;
import java.util.TreeMap;

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
	 *            the object containing the atom data
	 * @return the rsspect RSS bean
	 * @throws Exception
	 *             if the stream cannot be parsed.
	 */
	RSS readFeed(XMLStreamReader reader) throws Exception {

		Channel channel = null;
		List<Attribute> attributes = null;
		List<Extension> extensions = null;

		while (reader.hasNext()) {
			switch (reader.next()) {

			case XMLStreamConstants.START_DOCUMENT:
				RSSDoc.encoding = reader.getEncoding();
				RSSDoc.xml_version = reader.getVersion();
				break;

			case XMLStreamConstants.START_ELEMENT:
				// call each feed elements read method depending on the name
				if (reader.getLocalName().equals("rss")) {
					attributes = getAttributes(reader, attributes);
				} else if (reader.getLocalName().equals("channel")) {
					channel = readChannel(reader);
				} else {// extension
					extensions = readExtension(reader, extensions);
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

	List<Attribute> getAttributes(XMLStreamReader reader,
			List<Attribute> attributes) throws Exception {
		if (attributes == null) {
			attributes = new LinkedList<Attribute>();
		}

		// skip the start document section if we are passed a document fragment.
		if (reader.getEventType() == XMLStreamConstants.START_DOCUMENT) {
			reader.next();
		}

		int eventSkip = 0;
		for (int i = 0; i < reader.getNamespaceCount(); i++) {
			eventSkip++;
			String attrName = "xmlns";
			if (reader.getNamespacePrefix(i) != null) {
				attrName += ":" + reader.getNamespacePrefix(i);
			}
			if (attributes == null) {
				attributes = new LinkedList<Attribute>();
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
			if (attributes == null) {
				attributes = new LinkedList<Attribute>();
			}
			attributes.add(RSSDoc.buildAttribute(attrName, reader
					.getAttributeValue(i)));
		}

		return attributes;
	}

	List<Extension> readExtension(XMLStreamReader reader,
			List<Extension> extensions) throws Exception {

		if (extensions == null) {
			extensions = new LinkedList<Extension>();
		}

		String elementName = null;
		String prefix = reader.getPrefix();
		if (prefix != null && !prefix.equals("")) {
			elementName = prefix + ":" + reader.getLocalName();
		} else {
			elementName = reader.getLocalName();
		}

		extensions.add(RSSDoc.buildExtension(elementName, getAttributes(reader,
				null), reader.getElementText()));
		return extensions;
	}

	Author readAuthor(XMLStreamReader reader) throws Exception {
		return RSSDoc.buildAuthor(reader.getElementText());
	}

	Category readCategory(XMLStreamReader reader) throws Exception {
		return RSSDoc.buildCategory(RSSDoc.getAttributeFromGroup(getAttributes(
				reader, null), "domain"), reader.getElementText());
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

		while (reader.hasNext()) {
			switch (reader.next()) {

			case XMLStreamConstants.START_ELEMENT:

				// call each feed elements read method depending on the name
				if (reader.getLocalName().equals("title")) {
					title = readTitle(reader);
				} else if (reader.getLocalName().equals("link")) {
					link = readLink(reader);
				} else if (reader.getLocalName().equals("description")) {
					description = readDescription(reader);
				} else if (reader.getLocalName().equals("language")) {
					language = readLanguage(reader);
				} else if (reader.getLocalName().equals("copyright")) {
					copyright = readCopyright(reader);
				} else if (reader.getLocalName().equals("managingEditor")) {
					managingEditor = readManagingEditor(reader);
				} else if (reader.getLocalName().equals("webMaster")) {
					webMaster = readWebMaster(reader);
				} else if (reader.getLocalName().equals("pubDate")) {
					pubDate = readPubDate(reader);
				} else if (reader.getLocalName().equals("lastBuildDate")) {
					lastBuildDate = readLastBuildDate(reader);
				} else if (reader.getLocalName().equals("category")) {
					categories = readCategories(reader, categories);
				} else if (reader.getLocalName().equals("generator")) {
					generator = readGenerator(reader);
				} else if (reader.getLocalName().equals("docs")) {
					docs = readDocs(reader);
				} else if (reader.getLocalName().equals("cloud")) {
					cloud = readCloud(reader);
				} else if (reader.getLocalName().equals("ttl")) {
					ttl = readTTL(reader);
				} else if (reader.getLocalName().equals("image")) {
					image = readImage(reader);
				} else if (reader.getLocalName().equals("rating")) {
					rating = readRating(reader);
				} else if (reader.getLocalName().equals("textInput")) {
					textInput = readTextInput(reader);
				} else if (reader.getLocalName().equals("skipHours")) {
					skipHours = readSkipHours(reader);
				} else if (reader.getLocalName().equals("skipDays")) {
					skipDays = readSkipDays(reader);
				} else if (reader.getLocalName().equals("item")) {
					items = readItem(reader, items);
				} else {// extension
					extensions = readExtension(reader, extensions);
				}
				break;

			case XMLStreamConstants.END_ELEMENT:
				reader.next();
				break;
			}
		}

		return RSSDoc.buildChannel(title, link, description, language,
				copyright, managingEditor, webMaster, pubDate, lastBuildDate,
				categories, generator, docs, cloud, ttl, image, rating,
				textInput, skipHours, skipDays, items, extensions);

	}

	Cloud readCloud(XMLStreamReader reader) throws Exception {
		return RSSDoc.buildCloud(reader.getElementText());
	}

	Comments readComments(XMLStreamReader reader) throws Exception {
		return RSSDoc.buildComments(reader.getElementText());
	}

	Copyright readCopyright(XMLStreamReader reader) throws Exception {
		return RSSDoc.buildCopyright(reader.getElementText());
	}

	Description readDescription(XMLStreamReader reader) throws Exception {
		return RSSDoc.buildDescription(reader.getElementText());
	}

	Docs readDocs(XMLStreamReader reader) throws Exception {
		return RSSDoc.buildDocs(reader.getElementText());
	}

	Enclosure readEnclosure(XMLStreamReader reader) throws Exception {
		return RSSDoc.buildEnclosure(getAttributes(reader, null), reader
				.getElementText());
	}

	Generator readGenerator(XMLStreamReader reader) throws Exception {
		return RSSDoc.buildGenerator(reader.getElementText());
	}

	GUID readGUID(XMLStreamReader reader) throws Exception {
		return RSSDoc.buildGUID(RSSDoc.getAttributeFromGroup(getAttributes(
				reader, null), "isPermaLink"), reader.getElementText());
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

		while (reader.hasNext()) {
			switch (reader.next()) {

			case XMLStreamConstants.START_ELEMENT:

				// call each feed elements read method depending on the name
				if (reader.getLocalName().equals("url")) {
					url = readURL(reader);
				} else if (reader.getLocalName().equals("title")) {
					title = readTitle(reader);
				} else if (reader.getLocalName().equals("link")) {
					link = readLink(reader);
				} else if (reader.getLocalName().equals("width")) {
					width = readWidth(reader);
				} else if (reader.getLocalName().equals("height")) {
					height = readHeight(reader);
				} else if (reader.getLocalName().equals("description")) {
					description = readDescription(reader);
				}
				break;

			case XMLStreamConstants.END_ELEMENT:
				reader.next();
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
		List<Extension> extensions;

		while (reader.hasNext()) {
			switch (reader.next()) {

			case XMLStreamConstants.START_ELEMENT:

				// call each feed elements read method depending on the name
				if (reader.getLocalName().equals("title")) {
					title = readTitle(reader);
				} else if (reader.getLocalName().equals("link")) {
					link = readLink(reader);
				} else if (reader.getLocalName().equals("description")) {
					description = readDescription(reader);
				} else if (reader.getLocalName().equals("author")) {
					author = readAuthor(reader);
				} else if (reader.getLocalName().equals("category")) {
					categories = readCategory(reader, categories);
				} else if (reader.getLocalName().equals("comments")) {
					comments = readComments(reader);
				} else if (reader.getLocalName().equals("enclosure")) {
					enclosure = readEnclosure(reader);
				} else if (reader.getLocalName().equals("guid")) {
					guid = readGUID(reader);
				} else if (reader.getLocalName().equals("pubDate")) {
					pubDate = readPubDate(reader);
				} else if (reader.getLocalName().equals("source")) {
					source = readSource(reader);
				} else {// extension
					extensions = readExtension(reader, extensions);
				}
				break;

			case XMLStreamConstants.END_ELEMENT:
				if (reader.getLocalName().equals("item")) {
					breakOut = true;
				} else {
					reader.next();
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

	
	
	SortedMap<String, Entry> readEntry(XMLStreamReader reader,
			SortedMap<String, Entry> entries) throws Exception {

		if (entries == null) {
			entries = new TreeMap<String, Entry>();
		}
		boolean breakOut = false;

		Id id = null;
		Title title = null;
		Updated updated = null;
		Rights rights = null;
		Content content = null;
		List<Attribute> attributes = null;
		List<Author> authors = null;
		List<Category> categories = null;
		List<Contributor> contributors = null;
		List<Link> links = null;
		List<Extension> extensions = null;
		Published published = null;
		Summary summary = null;
		Source source = null;

		attributes = getAttributes(reader, attributes);

		while (reader.hasNext()) {
			switch (reader.next()) {

			case XMLStreamConstants.START_DOCUMENT:
				RSSDoc.encoding = reader.getEncoding();
				RSSDoc.xml_version = reader.getVersion();
				break;

			case XMLStreamConstants.START_ELEMENT:

				// call each feed elements read method depending on the name
				if (reader.getLocalName().equals("id")) {
					id = readId(reader);
				} else if (reader.getLocalName().equals("author")) {
					authors = readAuthor(reader, authors);
				} else if (reader.getLocalName().equals("category")) {
					categories = readCategory(reader, categories);
				} else if (reader.getLocalName().equals("contributor")) {
					contributors = readContributor(reader, contributors);
				} else if (reader.getLocalName().equals("content")) {
					content = readContent(reader);
				} else if (reader.getLocalName().equals("link")) {
					links = readLink(reader, links);
				} else if (reader.getLocalName().equals("published")) {
					published = readPublished(reader);
				} else if (reader.getLocalName().equals("rights")) {
					rights = readRights(reader);
				} else if (reader.getLocalName().equals("source")) {
					source = readSource(reader);
				} else if (reader.getLocalName().equals("summary")) {
					summary = readSummary(reader);
				} else if (reader.getLocalName().equals("title")) {
					title = readTitle(reader);
				} else if (reader.getLocalName().equals("updated")) {
					updated = readUpdated(reader);
				} else {// extension
					extensions = readExtension(reader, extensions);
				}
				break;

			case XMLStreamConstants.END_ELEMENT:
				if (reader.getLocalName().equals("entry")) {
					breakOut = true;
				} else {
					reader.next();
				}
				break;
			}
			if (breakOut) {
				break;
			}
		}

		entries.put(updated.getText(), RSSDoc.buildEntry(id, title, updated,
				rights, content, authors, categories, contributors, links,
				attributes, extensions, published, summary, source));

		return entries;
	}

	boolean containsXHTML(List<Attribute> attributes) {
		if (attributes != null) {
			Iterator<Attribute> attrsItr = attributes.iterator();
			// look for the xhtml type.
			while (attrsItr.hasNext()) {
				Attribute attribute = (Attribute) attrsItr.next();
				if (attribute.getName().equals("type")
						&& attribute.getValue().equals("xhtml")) {
					return true;
				}
			}
		}
		return false;
	}

	Source readSource(XMLStreamReader reader) throws Exception {
		boolean breakOut = false;

		List<Attribute> attributes = null;
		List<Author> authors = null;
		List<Category> categories = null;
		List<Contributor> contributors = null;
		List<Link> links = null;
		List<Extension> extensions = null;
		Generator generator = null;
		Icon icon = null;
		Id id = null;
		Logo logo = null;
		Rights rights = null;
		Subtitle subtitle = null;
		Title title = null;
		Updated updated = null;

		attributes = getAttributes(reader, attributes);

		while (reader.hasNext()) {
			switch (reader.next()) {

			case XMLStreamConstants.START_ELEMENT:
				// call each feed elements read method depending on the name
				if (reader.getLocalName().equals("author")) {
					authors = readAuthor(reader, authors);
				} else if (reader.getLocalName().equals("category")) {
					categories = readCategory(reader, categories);
				} else if (reader.getLocalName().equals("contributor")) {
					contributors = readContributor(reader, contributors);
				} else if (reader.getLocalName().equals("generator")) {
					generator = readGenerator(reader);
				} else if (reader.getLocalName().equals("icon")) {
					icon = readIcon(reader);
				} else if (reader.getLocalName().equals("id")) {
					id = readId(reader);
				} else if (reader.getLocalName().equals("link")) {
					links = readLink(reader, links);
				} else if (reader.getLocalName().equals("logo")) {
					logo = readLogo(reader);
				} else if (reader.getLocalName().equals("rights")) {
					rights = readRights(reader);
				} else if (reader.getLocalName().equals("subtitle")) {
					subtitle = readSubtitle(reader);
				} else if (reader.getLocalName().equals("title")) {
					title = readTitle(reader);
				} else if (reader.getLocalName().equals("updated")) {
					updated = readUpdated(reader);
				} else {// extension
					extensions = readExtension(reader, extensions);
				}
				break;

			case XMLStreamConstants.END_ELEMENT:
				if (reader.getLocalName().equals("source")) {
					breakOut = true;
				} else {
					reader.next();
				}
				break;
			}
			if (breakOut) {
				break;
			}
		}
		return RSSDoc.buildSource(id, title, updated, rights, authors,
				categories, contributors, links, attributes, extensions,
				generator, subtitle, icon, logo);
	}

	SimpleDateFormat getSimpleDateFormat() {
		//example Sun, 19 May 2002 15:21:36 GMT
        return new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
	}

	PubDate readPubDate(XMLStreamReader reader) throws Exception {
		String dateText = reader.getElementText();
		try {
			return RSSDoc.buildPubDate(getSimpleDateFormat().parse(dateText));
		} catch (Exception e) {
			SimpleDateFormat simpleDateFmt2 = new SimpleDateFormat(
					getSimpleDateFormat().toPattern().substring(0, 19));
			return RSSDoc.buildPubDate(simpleDateFmt2.parse(dateText
					.substring(0, 19)));
		}
		
	}

	Content readContent(XMLStreamReader reader) throws Exception {
		List<Attribute> attributes = getAttributes(reader, null);
		// if the content is XHTML, we need to skip the contents of the div.
		String content = null;
		if (containsXHTML(attributes)) {
			content = readXHTML(reader);
		} else {
			content = reader.getElementText();
		}
		return RSSDoc.buildContent(content, attributes);
	}

	Updated readUpdated(XMLStreamReader reader) throws Exception {
		List<Attribute> attributes = getAttributes(reader, null);
		String dateText = reader.getElementText();
		try {
			return RSSDoc.buildUpdated(getSimpleDateFormat().parse(dateText),
					attributes);
		} catch (Exception e) {
			SimpleDateFormat simpleDateFmt2 = new SimpleDateFormat(
					getSimpleDateFormat().toPattern().substring(0, 19));
			return RSSDoc.buildUpdated(simpleDateFmt2.parse(dateText.substring(
					0, 19)), attributes);
		}
	}

	Title readTitle(XMLStreamReader reader) throws Exception {
		List<Attribute> attributes = getAttributes(reader, null);
		// if the content is XHTML, we need to read in the contents of the div.
		String title = null;
		if (containsXHTML(attributes)) {
			title = readXHTML(reader);
		} else {
			title = reader.getElementText();
		}
		return RSSDoc.buildTitle(title, attributes);
	}

	String readXHTML(XMLStreamReader reader) throws XMLStreamException,
			Exception {
		StringBuffer xhtml = new StringBuffer();
		while (reader.hasNext()) {
			boolean breakOut = false;
			switch (reader.next()) {
			case XMLStreamConstants.START_ELEMENT:
				if (reader.getLocalName().equals("div")) {
					// for now just ignore the attributes
					getAttributes(reader, null);
				} else {
					if (reader.getPrefix() != null
							&& !reader.getPrefix().equals("")) {
						xhtml.append("<" + reader.getPrefix() + ":"
								+ reader.getLocalName());
					} else {
						xhtml.append("<" + reader.getLocalName());
					}
					List<Attribute> attributes = getAttributes(reader, null);
					// add the attributes
					if (attributes != null && attributes.size() > 0) {
						Iterator<Attribute> attrItr = attributes.iterator();
						while (attrItr.hasNext()) {
							Attribute attr = (Attribute) attrItr.next();
							xhtml.append(" " + attr.getName() + "="
									+ attr.getValue());
						}
						xhtml.append(" ");
					}
					xhtml.append(">");

				}
				break;
			case XMLStreamConstants.END_ELEMENT:
				if (reader.getLocalName().equals("div")) {
					breakOut = true;
				} else {
					if (reader.getPrefix() != null
							&& !reader.getPrefix().equals("")) {
						xhtml.append("</" + reader.getPrefix() + ":"
								+ reader.getLocalName() + ">");
					} else {
						xhtml.append("</" + reader.getLocalName() + ">");
					}
				}
				break;
			default:
				xhtml.append(reader.getText());
			}
			if (breakOut) {
				// clear past the end enclosing div.
				reader.next();
				break;
			}
		}
		return xhtml.toString().replaceAll("<br></br>", "<br />").replaceAll(
				"<hr></hr>", "<hr />");
	}

	Subtitle readSubtitle(XMLStreamReader reader) throws Exception {
		List<Attribute> attributes = getAttributes(reader, null);
		// if the content is XHTML, we need to read in the contents of the div.
		String subtitle = null;
		if (containsXHTML(attributes)) {
			subtitle = readXHTML(reader);
		} else {
			subtitle = reader.getElementText();
		}
		return RSSDoc.buildSubtitle(subtitle, attributes);
	}

	Rights readRights(XMLStreamReader reader) throws Exception {
		List<Attribute> attributes = getAttributes(reader, null);
		// if the content is XHTML, we need to read in the contents of the div.
		String rights = null;
		if (containsXHTML(attributes)) {
			rights = readXHTML(reader);
		} else {
			rights = reader.getElementText();
		}
		return RSSDoc.buildRights(rights, attributes);
	}

	Logo readLogo(XMLStreamReader reader) throws Exception {
		return RSSDoc.buildLogo(getAttributes(reader, null), reader
				.getElementText());
	}

	List<Link> readLink(XMLStreamReader reader, List<Link> links)
			throws Exception {
		if (links == null) {
			links = new LinkedList<Link>();
		}
		links.add(RSSDoc.buildLink(getAttributes(reader, null), reader
				.getElementText()));
		return links;
	}

	Id readId(XMLStreamReader reader) throws Exception {
		return RSSDoc.buildId(getAttributes(reader, null), reader
				.getElementText());
	}

	Icon readIcon(XMLStreamReader reader) throws Exception {
		return RSSDoc.buildIcon(getAttributes(reader, null), reader
				.getElementText());
	}

	List<Contributor> readContributor(XMLStreamReader reader,
			List<Contributor> contributors) throws Exception {

		if (contributors == null) {
			contributors = new LinkedList<Contributor>();
		}

		AtomPersonConstruct person = readAtomPersonConstruct(reader,
				"contributor");
		contributors.add(RSSDoc.buildContributor(person.getName(), person
				.getUri(), person.getEmail(), person.getAttributes(), person
				.getExtensions()));

		return contributors;
	}

	List<Category> readCategory(XMLStreamReader reader,
			List<Category> categories) throws Exception {
		if (categories == null) {
			categories = new LinkedList<Category>();
		}
		categories.add(RSSDoc.buildCategory(getAttributes(reader, null), reader
				.getElementText()));
		return categories;
	}

	List<Author> readAuthor(XMLStreamReader reader, List<Author> authors)
			throws Exception {
		if (authors == null) {
			authors = new LinkedList<Author>();
		}
		AtomPersonConstruct person = readAtomPersonConstruct(reader, "author");
		authors.add(RSSDoc.buildAuthor(person.getName(), person.getUri(),
				person.getEmail(), person.getAttributes(), person
						.getExtensions()));
		return authors;
	}

	AtomPersonConstruct readAtomPersonConstruct(XMLStreamReader reader,
			String personType) throws Exception {
		boolean breakOut = false;
		final List<Attribute> attributes = getAttributes(reader, null);
		Name name = null;
		URI uri = null;
		Email email = null;
		List<Extension> extensions = null;
		while (reader.hasNext()) {
			switch (reader.next()) {
			case XMLStreamConstants.START_ELEMENT:

				if (reader.getLocalName().equals("name")) {
					name = RSSDoc.buildName(reader.getElementText());
				} else if (reader.getLocalName().equals("uri")) {
					uri = RSSDoc.buildURI(reader.getElementText());
				} else if (reader.getLocalName().equals("email")) {
					email = RSSDoc.buildEmail(reader.getElementText());
				} else {
					if (extensions == null) {
						extensions = new LinkedList<Extension>();
					}
					extensions = readExtension(reader, extensions);
				}
				break;

			case XMLStreamConstants.END_ELEMENT:
				if (reader.getLocalName().equals(personType)) {
					breakOut = true;
				} else {
					reader.next();
				}
				break;
			}
			if (breakOut) {
				break;
			}

		}
		return RSSDoc.buildAtomPersonConstruct(name, uri, email, attributes,
				extensions);
	}
}