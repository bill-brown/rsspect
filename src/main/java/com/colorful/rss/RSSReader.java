package com.colorful.rss;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import javax.xml.stream.XMLStreamConstants;
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

	List<Category> readCategory(XMLStreamReader reader,
			List<Category> categories) throws Exception {

		if (categories == null) {
			categories = new LinkedList<Category>();
		}

		categories.add(RSSDoc
				.buildCategory(RSSDoc.getAttributeFromGroup(getAttributes(
						reader, null), "domain"), reader.getElementText()));

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
					categories = readCategory(reader, categories);
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
		return RSSDoc.buildSource(RSSDoc.getAttributeFromGroup(getAttributes(
				reader, null), "url"), reader.getElementText());
	}

	TextInput readTextInput(XMLStreamReader reader) throws Exception {

		Title title = null;
		Description description = null;
		Name name = null;
		Link link = null;

		while (reader.hasNext()) {
			switch (reader.next()) {

			case XMLStreamConstants.START_ELEMENT:

				// call each feed elements read method depending on the name
				if (reader.getLocalName().equals("title")) {
					title = readTitle(reader);
				} else if (reader.getLocalName().equals("description")) {
					description = readDescription(reader);
				} else if (reader.getLocalName().equals("name")) {
					name = readName(reader);
				} else if (reader.getLocalName().equals("link")) {
					link = readLink(reader);
				}
				break;

			case XMLStreamConstants.END_ELEMENT:
				reader.next();
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
}