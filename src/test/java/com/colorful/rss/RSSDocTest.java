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

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javanet.staxutils.IndentingXMLStreamWriter;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RSSDocTest {

	// http://nytimes.com feed from 04/24/2009
	private String expectedRSS1 = "<?xml version=\"1.0\"?>"
			+ "<?xml-stylesheet href=\"/css/rss20.xsl\" type=\"text/xsl\"?>"
			+ "<rss xmlns:pheedo=\"http://www.pheedo.com/namespace/pheedo\" xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xmlns:media=\"http://search.yahoo.com/mrss/\" xmlns:atom=\"http://www.w3.org/2005/Atom\" xmlns:nyt=\"http://www.nytimes.com/namespaces/rss/2.0\" version=\"2.0\">"
			+ "<fakeExt xmlns=\"http://www.fake.extension.org/fakeness\" />"
			+ "<channel>"
			+ "<category>music</category>"
			+ "<category>news</category>"
			+ "<cloud domain=\"rpc.sys.com\" port=\"80\" path=\"/RPC2\" registerProcedure=\"pingMe\" protocol=\"soap\"/>"
			+ "<docs>http://www.colorfulsoftware.com</docs>"
			+ "<title>NYT &gt; Home Page</title>"
			+ "<link>http://www.nytimes.com/pages/index.html?partner=rss</link>"
			+ "<atom:link rel=\"self\" type=\"application/rss+xml\" href=\"http://www.nytimes.com/services/xml/rss/nyt/HomePage.xml\"/>"
			+ "<description/>"
			+ "<language>en-us</language>"
			+ "<copyright>Copyright 2009 The New York Times Company</copyright>"
			+ "<lastBuildDate>Fri, 24 Apr 2009 17:29:13 GMT</lastBuildDate>"
			+ "<image>"
			+ "<title>NYT &gt; Home Page</title>"
			+ "<url>http://graphics.nytimes.com/images/section/NytSectionHeader.gif</url>"
			+ "<link>http://www.nytimes.com/pages/index.html?partner=rss</link>"
			+ "<height>100</height>"
			+ "<width>144</width>"
			+ "<description>this is the coolest image</description>"
			+ "</image>"

			+ "<item>"
			+ "<title>Ford Has Loss of $1.4 Billion in Quarter, but Beats Forecast</title>"
			+ "<link>http://feeds.nytimes.com/click.phdo?i=0155467d396aa780c181f5f7809a803e</link>"
			+ "<pheedo:origLink>http://www.nytimes.com/2009/04/25/business/25ford.html?partner=rss&amp;amp;emc=rss</pheedo:origLink>"
			+ "<author>Someone important</author>"
			+ "<enclosure url=\"http://www.scripting.com/mp3s/weatherReportSuite.mp3\" length=\"12216320\" type=\"audio/flac\" />"
			+ "<guid isPermaLink=\"false\">http://www.nytimes.com/2009/04/25/business/25ford.html</guid>"
			+ "<description>Despite the loss, Ford said that it was using up less cash than before and that it had $21.3 billion in cash as of March 31.&lt;br clear=&quot;both&quot; style=&quot;clear: both;&quot;/&gt;&lt;br clear=&quot;both&quot; style=&quot;clear: both;&quot;/&gt;&lt;a href=&quot;http://www.pheedo.com/click.phdo?s=0155467d396aa780c181f5f7809a803e&amp;p=1&quot;&gt;&lt;img alt=&quot;&quot; style=&quot;border: 0;&quot; border=&quot;0&quot; src=&quot;http://www.pheedo.com/img.phdo?s=0155467d396aa780c181f5f7809a803e&amp;p=1&quot;/&gt;&lt;/a&gt;</description>"
			+ "<dc:creator>By NICK BUNKLEY</dc:creator>"
			+ "<pubDate>Fri, 24 Apr 2009 16:56:20 GMT</pubDate>"
			+ "<category domain=\"http://www.nytimes.com/namespaces/keywords/nyt_org_all\">Ford Motor Co|F|NYSE</category>"
			+ "<category domain=\"http://www.nytimes.com/namespaces/keywords/des\">Subprime Mortgage Crisis</category>"
			+ "<category domain=\"http://www.nytimes.com/namespaces/keywords/des\">Company Reports</category>"
			+ "</item>"

			+ "<item>"
			+ "<title>At Least 60 More Are Killed in New Attacks in Baghdad</title>"
			+ "<link>http://feeds.nytimes.com/click.phdo?i=fc9008de1b57c65c3ed32c7c74613c9a</link>"
			+ "<pheedo:origLink>http://www.nytimes.com/2009/04/25/world/middleeast/25iraq.html?partner=rss&amp;amp;emc=rss</pheedo:origLink>"
			+ "<author>Someone important</author>"
			+ "<enclosure url=\"http://www.scripting.com/mp3s/weatherReportSuite.mp3?addSomeSpice=yes&amp;does=thisWork\" length=\"12216320\" type=\"audio/flac\" />"
			+ "<guid isPermaLink=\"false\">http://www.nytimes.com/2009/04/25/world/middleeast/25iraq.html</guid>"
			+ "<media:content url=\"http://graphics8.nytimes.com/images/2009/04/24/world/25iraq.ms.75.jpg\" medium=\"image\" height=\"75\" width=\"75\"/>"
			+ "<media:description>A man injured in a suicide bombing at the Kazimiyah hospital in Baghdad on Friday.</media:description>"
			+ "<media:credit>Khalid Mohammed/Associated Press</media:credit>"
			+ "<description>The attacks outside the gates of the holiest Shiite site in Baghdad on Friday came a day after the deadliest day in Iraq in more than a year.&lt;br clear=&quot;both&quot; style=&quot;clear: both;&quot;/&gt;&lt;br clear=&quot;both&quot; style=&quot;clear: both;&quot;/&gt;&lt;a href=&quot;http://www.pheedo.com/click.phdo?s=fc9008de1b57c65c3ed32c7c74613c9a&amp;p=1&quot;&gt;&lt;img alt=&quot;&quot; style=&quot;border: 0;&quot; border=&quot;0&quot; src=&quot;http://www.pheedo.com/img.phdo?s=fc9008de1b57c65c3ed32c7c74613c9a&amp;p=1&quot;/&gt;&lt;/a&gt;</description>"
			+ "<dc:creator>By STEVEN LEE MYERS and TIMOTHY WILLIAMS</dc:creator>"
			+ "<pubDate>Fri, 24 Apr 2009 16:50:22 GMT</pubDate>"
			+ "<category domain=\"http://www.nytimes.com/namespaces/keywords/nyt_geo\">Iraq</category>"
			+ "<category domain=\"http://www.nytimes.com/namespaces/keywords/des\">Iraq War (2003- )</category>"
			+ "<category domain=\"http://www.nytimes.com/namespaces/keywords/des\">Bombs and Explosives</category>"
			+ "<category domain=\"http://www.nytimes.com/namespaces/keywords/des\">Terrorism</category>"
			+ "</item>"

			+ "<item>"
			+ "<title>The Caucus: Congress Reaches a Tentative Deal on the Budget</title>"
			+ "<link>http://feeds.nytimes.com/click.phdo?i=89ed26c949918ac94a090111fd880424</link>"
			+ "<pheedo:origLink>http://thecaucus.blogs.nytimes.com/2009/04/24/congress-reaches-tentative-deal-on-the-budget/index.html?partner=rss&amp;amp;emc=rss</pheedo:origLink>"
			+ "<author>Someone important</author>"
			+ "<enclosure url=\"http://www.scripting.com/mp3s/weatherReportSuite.mp3?addSomeSpice=yes&amp;does=thisWork\" length=\"12216320\" type=\"audio/flac\" />"
			+ "<guid isPermaLink=\"false\">http://thecaucus.blogs.nytimes.com/2009/04/24/congress-reaches-tentative-deal-on-the-budget/index.html</guid>"
			+ "<description>The negotiated plan seems certain to include a procedural maneuver in an effort to avoid filibusters on health care reform.&lt;br clear=&quot;both&quot; style=&quot;clear: both;&quot;/&gt;&lt;br clear=&quot;both&quot; style=&quot;clear: both;&quot;/&gt;&lt;a href=&quot;http://www.pheedo.com/click.phdo?s=89ed26c949918ac94a090111fd880424&amp;p=1&quot;&gt;&lt;img alt=&quot;&quot; style=&quot;border: 0;&quot; border=&quot;0&quot; src=&quot;http://www.pheedo.com/img.phdo?s=89ed26c949918ac94a090111fd880424&amp;p=1&quot;/&gt;&lt;/a&gt;</description>"
			+ "<dc:creator>By CARL HULSE</dc:creator>"
			+ "<pubDate>Fri, 24 Apr 2009 17:28:46 GMT</pubDate>" + "</item>"
			+ "</channel>" + "</rss>";

	private String expectedRSS2 = "<rss version=\"2.0\"><channel><title>simplest feed</title><link>http://www.outthere.net</link><description>something cool</description></channel></rss>";

	private String expectedRSS3 = "<rss version=\"2.0\"><channel><title>simplest feed</title><link>http://www.outthere.net</link><description>something cool</description><item><title>simplest feed</title><comments>these are comments.  they contain </comments><description><b xmlns=\"http://www.w3.org/1999/xhtml\">html</b></description></item></channel></rss>";

	private static Calendar theDate;
	static {
		theDate = Calendar.getInstance();
		theDate.clear();
		theDate.set(2009, 0, 1);
	}

	private RSS rss1;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		new File("out1.xml").delete();
		// new File("out2.xml").delete();
	}

	@Test
	public void testGetRSSpectVersion() {
		// fail("Not yet implemented");
	}

	@Test
	public void testWriteRSSDocOutputStreamRSSStringString() {
		try {
			rss1 = RSSDoc.readRSSToBean(new java.net.URL(
					"http://feeds.nytimes.com/nyt/rss/HomePage"));
			RSSDoc.writeRSSDoc(new FileOutputStream("out1.xml"), rss1,
					RSSDoc.encoding, RSSDoc.xml_version);
			RSS rss2 = RSSDoc.readRSSToBean(new File("out1.xml"));
			assertNotNull(rss2);
			assertNotNull(rss2.getAttributes());
			assertNotNull(rss2.getChannel());
			assertNull(rss2.getExtensions());
		} catch (Exception e) {
			e.printStackTrace();
			fail("could not write output file with file output stream.");
		}
	}

	@Test
	public void testWriteRSSDocWriterRSSStringString() {
		// fail("Not yet implemented");
	}

	@Test
	public void testWriteRSSDocXMLStreamWriterRSSStringString() {
		try {
			rss1 = RSSDoc.readRSSToBean(new java.net.URL(
					"http://feeds.nytimes.com/nyt/rss/HomePage"));
			// fail("could not write output file with file output stream.");
			XMLStreamWriter writer = new IndentingXMLStreamWriter(
					XMLOutputFactory.newInstance().createXMLStreamWriter(
							new FileOutputStream("out2.xml"), RSSDoc.encoding));
			RSSDoc.writeRSSDoc(writer, rss1, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			fail("could not write output file with file output stream.");
		}
	}

	@Test
	public void testReadRSSToStringRSSString() {

	}

	@Test
	public void testReadRSSToStringRSS() {
		try {
			rss1 = RSSDoc.readRSSToBean(expectedRSS1);
			String rss1Str = RSSDoc.readRSSToString(rss1);
			assertNotNull(rss1Str);
			rss1 = RSSDoc.readRSSToBean(rss1Str);
			assertNotNull(rss1);
			assertNotNull(rss1.getAttributes());
			assertNotNull(rss1.getChannel());
			assertNotNull(rss1.getExtensions());

		} catch (Exception e) {
			e.printStackTrace();
			fail("should be working. " + e.getLocalizedMessage());
		}
	}

	@Test
	public void testReadRSSToBeanString() {
		try {
			rss1 = RSSDoc.readRSSToBean(expectedRSS1);
			assertNotNull(rss1);
			assertNotNull(rss1.getAttributes());
			assertNotNull(rss1.getChannel());
			assertNotNull(rss1.getExtensions());

		} catch (Exception e) {
			e.printStackTrace();
			fail("should be working. " + e.getLocalizedMessage());
		}
	}

	@Test
	public void testReadRSSToBeanFile() {
		try {
			rss1 = RSSDoc.readRSSToBean(new File(
					"src/test/resources/nyTimes.rss.xml"));
			assertNotNull(rss1);
			assertNotNull(rss1.getAttributes());
			assertNotNull(rss1.getChannel());

		} catch (Exception e) {
			e.printStackTrace();
			fail("should be working. " + e.getLocalizedMessage());
		}
	}

	@Test
	public void testReadRSSToBeanURL() {
		try {
			rss1 = RSSDoc.readRSSToBean(new java.net.URL(
					"http://feeds.nytimes.com/nyt/rss/HomePage"));
			assertNotNull(rss1);
			assertNotNull(rss1.getAttributes());
			assertNotNull(rss1.getChannel());

		} catch (Exception e) {
			e.printStackTrace();
			fail("should be working. " + e.getLocalizedMessage());
		}
	}

	@Test
	public void testReadRSSToBeanInputStream() {
		try {
			rss1 = RSSDoc.readRSSToBean(new java.net.URL(
					"http://feeds.nytimes.com/nyt/rss/HomePage").openStream());
			assertNotNull(rss1);
			assertNotNull(rss1.getAttributes());
			assertNotNull(rss1.getChannel());
			assertNull(rss1.getExtensions());

		} catch (Exception e) {
			e.printStackTrace();
			fail("should be working. " + e.getLocalizedMessage());
		}
	}

	@Test
	public void testBuildRSS() {
		try {
			rss1 = RSSDoc.readRSSToBean(expectedRSS2);
			assertNotNull(rss1);
			assertNotNull(rss1.getChannel());

		} catch (Exception e) {
			e.printStackTrace();
			fail("should be working. " + e.getLocalizedMessage());
		}
	}

	@Test
	public void testBuildAttribute() {
		try {
			rss1 = RSSDoc.readRSSToBean(expectedRSS1);
			String rss1Str = RSSDoc.readRSSToString(rss1);
			assertNotNull(rss1Str);
			rss1 = RSSDoc.readRSSToBean(rss1Str);

			assertNotNull(rss1);
			assertNotNull(rss1.getExtensions());

			for (Attribute attr : rss1.getAttributes()) {
				assertNotNull(attr);
				assertNotNull(attr.getName());
				assertNotNull(attr.getValue());

				if (attr.getName().equals("xmlns:pheedo")) {
					assertTrue(attr.equals(RSSDoc.buildAttribute(
							"xmlns:pheedo",
							"http://www.pheedo.com/namespace/pheedo")));
				}

				if (attr.getName().equals("xmlns:dc")) {
					assertTrue(attr.equals(RSSDoc.buildAttribute("xmlns:dc",
							"http://purl.org/dc/elements/1.1/")));
				}

				assertFalse(attr.equals(RSSDoc.buildAttribute("xmlns:pheedo",
						"http://www.pheedo.com/namespace/bobo")));

				assertFalse(attr.equals(RSSDoc.buildAttribute("dude", null)));

				assertFalse(attr
						.equals("xmlns:pheedo=\"http://www.pheedo.com/namespace/bobo\""));
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("should be working. " + e.getLocalizedMessage());
		}
	}

	@Test
	public void testBuildAuthor() {
		// fail("Not yet implemented");
	}

	@Test
	public void testBuildCategory() {
		// fail("Not yet implemented");
	}

	@Test
	public void testBuildChannel() {
		try {
			rss1 = RSSDoc.readRSSToBean(expectedRSS2);
			String rss1Str = RSSDoc.readRSSToString(rss1);
			assertNotNull(rss1Str);
			rss1 = RSSDoc.readRSSToBean(rss1Str);

			try {
				RSSDoc.buildChannel(null, null, null, null, null, null, null,
						null, null, null, null, null, null, null, null, null,
						null, null, null, null, null);
			} catch (RSSpectException r) {
				assertEquals(r.getMessage(),
						"channel elements MUST contain a title element.");
			}

			try {
				RSSDoc.buildChannel(RSSDoc.buildTitle("this is a title"), null,
						null, null, null, null, null, null, null, null, null,
						null, null, null, null, null, null, null, null, null,
						null);
			} catch (RSSpectException r) {
				assertEquals(r.getMessage(),
						"channel elements MUST contain a link element.");
			}

			try {
				RSSDoc.buildChannel(RSSDoc.buildTitle("this is a title"),
						RSSDoc.buildLink("http://www.minoritydirectory.net"),
						null, null, null, null, null, null, null, null, null,
						null, null, null, null, null, null, null, null, null,
						null);
			} catch (RSSpectException r) {
				assertEquals(r.getMessage(),
						"channel elements MUST contain a description element.");
			}

			try {
				assertNotNull(RSSDoc.buildChannel(RSSDoc
						.buildTitle("this is a title"), RSSDoc
						.buildLink("http://www.minoritydirectory.net"), RSSDoc
						.buildDescription("this is a description"), null, null,
						null, null, null, null, null, null, null, null, null,
						null, null, null, null, null, null, null));
			} catch (RSSpectException r) {
				fail("this shouldn't happen");
			}

		} catch (Exception e) {
			e.printStackTrace();
			fail("should be working. " + e.getLocalizedMessage());
		}
	}

	@Test
	public void testBuildCloud() {
		try {
			rss1 = RSSDoc.readRSSToBean(expectedRSS1);
			assertNotNull(rss1);
			assertNotNull(rss1.getChannel());
			Cloud cloud = rss1.getChannel().getCloud();
			assertNotNull(cloud);
			assertNotNull(cloud.getAttributes());
			assertNotNull(cloud.getDomain());
			assertNotNull(cloud.getPath());
			assertNotNull(cloud.getPort());
			assertNotNull(cloud.getProtocol());
			assertNotNull(cloud.getRegisterProcedure());
			for (Attribute attr : cloud.getAttributes()) {
				assertNotNull(attr);
				assertNotNull(attr.getName());
				assertNotNull(attr.getValue());
			}

			try {
				RSSDoc.buildCloud(null);
			} catch (RSSpectException r) {
				assertEquals(
						r.getMessage(),
						"The cloud element requires attributes:  See \"http://cyber.law.harvard.edu/rss/soapMeetsRss.html#rsscloudInterface\".");
			}

			List<Attribute> attrs = new LinkedList<Attribute>();
			try {
				RSSDoc.buildCloud(attrs);
			} catch (RSSpectException r) {
				assertEquals(r.getMessage(),
						"cloud elements MUST have a domain attribute.");
			}
			attrs.add(RSSDoc.buildAttribute("domain", "domain"));

			try {
				RSSDoc.buildCloud(attrs);
			} catch (RSSpectException r) {
				assertEquals(r.getMessage(),
						"cloud elements MUST have a port attribute.");
			}
			attrs.add(RSSDoc.buildAttribute("port", "port"));

			try {
				RSSDoc.buildCloud(attrs);
			} catch (RSSpectException r) {
				assertEquals(r.getMessage(),
						"cloud elements MUST have a path attribute.");
			}
			attrs.add(RSSDoc.buildAttribute("path", "path"));

			try {
				RSSDoc.buildCloud(attrs);
			} catch (RSSpectException r) {
				assertEquals(r.getMessage(),
						"cloud elements MUST have a registerProcedure attribute.");
			}
			attrs.add(RSSDoc.buildAttribute("registerProcedure",
					"registerProcedure"));

			try {
				RSSDoc.buildCloud(attrs);
			} catch (RSSpectException r) {
				assertEquals(r.getMessage(),
						"cloud elements MUST have a protocol attribute.");
			}
			attrs.add(RSSDoc.buildAttribute("protocol", "protocol"));

			try {
				RSSDoc.buildCloud(attrs);
			} catch (RSSpectException r) {
				assertEquals(r.getMessage(),
						"the cloud's protocol attribute must be 'xml-rpc' or 'soap', case-sensitive.");
			}

			attrs = new LinkedList<Attribute>();
			attrs.add(RSSDoc.buildAttribute("domain", "domain"));
			attrs.add(RSSDoc.buildAttribute("port", "port"));
			attrs.add(RSSDoc.buildAttribute("path", "path"));
			attrs.add(RSSDoc.buildAttribute("registerProcedure",
					"registerProcedure"));
			attrs.add(RSSDoc.buildAttribute("protocol", "soap"));
			assertNotNull(RSSDoc.buildCloud(attrs));

		} catch (Exception e) {
			e.printStackTrace();
			fail("should be working. " + e.getLocalizedMessage());
		}
	}

	@Test
	public void testBuildComments() {
		try {
			rss1 = RSSDoc.readRSSToBean(expectedRSS3);
			assertNotNull(rss1);
			assertNotNull(rss1.getChannel());

			List<Item> items = rss1.getChannel().getItems();
			assertNotNull(items);
			for (Item item : items) {
				Comments comments = item.getComments();
				assertNotNull(comments);
				assertNotNull(comments.getComments());
			}

		} catch (Exception e) {
			e.printStackTrace();
			fail("should be working. " + e.getLocalizedMessage());
		}
	}

	@Test
	public void testBuildCopyright() {
		// fail("Not yet implemented");
	}

	@Test
	public void testBuildDescription() {
		// fail("Not yet implemented");
	}

	@Test
	public void testBuildDocs() {
		// fail("Not yet implemented");
	}

	@Test
	public void testBuildEnclosure() {
		try {
			rss1 = RSSDoc.readRSSToBean(expectedRSS1);
			assertNotNull(rss1);
			assertNotNull(rss1.getChannel());
			List<Item> items = rss1.getChannel().getItems();
			assertNotNull(items);
			assertTrue(items.size() == 3);
			for (Item item : items) {

				assertNotNull(item.getEnclosure());
				Enclosure enclosure = item.getEnclosure();
				assertNotNull(enclosure.getAttributes());
				assertNotNull(enclosure.getUrl());
				assertNotNull(enclosure.getLength());
				assertNotNull(enclosure.getType());

				for (Attribute attr : enclosure.getAttributes()) {
					assertNotNull(attr);
					assertNotNull(attr.getName());
					assertNotNull(attr.getValue());
				}
			}

			try {
				RSSDoc.buildEnclosure(null);
			} catch (RSSpectException r) {
				assertEquals(
						r.getMessage(),
						"enclosure elements MUST contain the url, length and type attributes.  See: http://cyber.law.harvard.edu/rss/rss.html#ltenclosuregtSubelementOfLtitemgt");
			}

			List<Attribute> attrs = new LinkedList<Attribute>();
			try {
				RSSDoc.buildEnclosure(attrs);
			} catch (RSSpectException r) {
				assertEquals(r.getMessage(),
						"enclusure elements MUST have a url attribute.");
			}
			attrs
					.add(RSSDoc.buildAttribute("url",
							"http://www.earthbeats.net"));

			try {
				RSSDoc.buildEnclosure(attrs);
			} catch (RSSpectException r) {
				assertEquals(r.getMessage(),
						"enclusure elements MUST have a length attribute.");
			}
			attrs.add(RSSDoc.buildAttribute("length", "1234567"));

			try {
				RSSDoc.buildEnclosure(attrs);
			} catch (RSSpectException r) {
				assertEquals(r.getMessage(),
						"enclusure elements MUST have a type attribute.");
			}
			attrs.add(RSSDoc.buildAttribute("type", "media/flac"));

			assertNotNull(RSSDoc.buildEnclosure(attrs));

		} catch (Exception e) {
			e.printStackTrace();
			fail("should be working. " + e.getLocalizedMessage());
		}
	}

	@Test
	public void testBuildExtension() {
		// fail("Not yet implemented");
	}

	@Test
	public void testBuildGenerator() {
		// fail("Not yet implemented");
	}

	@Test
	public void testBuildGUID() {
		// fail("Not yet implemented");
	}

	@Test
	public void testBuildHeight() {
		try {
			rss1 = RSSDoc.readRSSToBean(expectedRSS1);
			assertNotNull(rss1);
			assertNotNull(rss1.getChannel());
			assertNotNull(rss1.getChannel().getImage());
			assertNotNull(rss1.getChannel().getImage().getHeight());
			assertNotNull(rss1.getChannel().getImage().getHeight().getHeight());
			assertEquals(rss1.getChannel().getImage().getHeight().getHeight(),
					"100");
			try {
				RSSDoc.buildHeight("401");
			} catch (RSSpectException r) {
				assertEquals(r.getMessage(),
						"height cannot be greater than 400px.");
			}

			try {
				RSSDoc.buildHeight("abc");
			} catch (RSSpectException r) {
				assertEquals(r.getMessage(),
						"invalid number format for height.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			fail("should be working. " + e.getLocalizedMessage());
		}
	}

	@Test
	public void testBuildImage() {
		URL url = null;
		Title title = null;
		Link link = null;
		try {
			RSSDoc.buildImage(url, title, link, null, null, null);
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(),
					"image elements MUST contain a url element.");
		}

		try {
			url = RSSDoc.buildURL("http://www.earthbeats.net");
			RSSDoc.buildImage(url, title, link, null, null, null);
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(),
					"image elements MUST contain a title element.");
		}

		try {
			title = RSSDoc.buildTitle("this is a title");
			RSSDoc.buildImage(url, title, link, null, null, null);
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(),
					"image elements MUST contain a link element.");
		}
		try {
			link = RSSDoc.buildLink("http://www.earthbeats.net");
			Image image = RSSDoc.buildImage(url, title, link, null, null, null);
			assertNotNull(image);
			assertEquals(image.getUrl().getUrl(), "http://www.earthbeats.net");
			assertEquals(image.getTitle().getTitle(), "this is a title");
			assertEquals(image.getLink().getLink(), "http://www.earthbeats.net");
		} catch (RSSpectException r) {
			fail("should not fail here.");
		}
	}

	@Test
	public void testBuildItem() {
		try {
			rss1 = RSSDoc.readRSSToBean(expectedRSS1);
			assertNotNull(rss1);
			assertNotNull(rss1.getChannel());
			List<Item> items = rss1.getChannel().getItems();
			assertNotNull(items);
			for (Item item : items) {
				assertNotNull(item.getTitle());
				assertNotNull(item.getDescription());
				assertNotNull(item.getAuthor());
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("should be working. " + e.getLocalizedMessage());
		}
	}

	@Test
	public void testBuildLanguage() {
		// fail("Not yet implemented");
	}

	@Test
	public void testBuildLastBuildDate() {
		// fail("Not yet implemented");
	}

	@Test
	public void testBuildLink() {
		// fail("Not yet implemented");
	}

	@Test
	public void testBuildManagingEditor() {
		// fail("Not yet implemented");
	}

	@Test
	public void testBuildName() {
		// fail("Not yet implemented");
	}

	@Test
	public void testBuildPubDate() {
		// fail("Not yet implemented");
	}

	@Test
	public void testBuildRating() {
		// fail("Not yet implemented");
	}

	@Test
	public void testBuildSkipDays() {
		// fail("Not yet implemented");
	}

	@Test
	public void testBuildSkipHours() {
		// fail("Not yet implemented");
	}

	@Test
	public void testBuildSource() {
		// fail("Not yet implemented");
	}

	@Test
	public void testBuildTextInput() {
		// fail("Not yet implemented");
	}

	@Test
	public void testBuildTitle() {
		// fail("Not yet implemented");
	}

	@Test
	public void testBuildTTL() {
		// fail("Not yet implemented");
	}

	@Test
	public void testBuildURL() {
		// fail("Not yet implemented");
	}

	@Test
	public void testBuildWebMaster() {
		// fail("Not yet implemented");
	}

	@Test
	public void testBuildWidth() {
		try {
			rss1 = RSSDoc.readRSSToBean(expectedRSS1);
			assertNotNull(rss1);
			assertNotNull(rss1.getChannel());
			assertNotNull(rss1.getChannel().getImage());
			assertNotNull(rss1.getChannel().getImage().getWidth());
			assertNotNull(rss1.getChannel().getImage().getWidth().getWidth());
			assertEquals(rss1.getChannel().getImage().getWidth().getWidth(),
					"144");
			try {
				RSSDoc.buildWidth("145");
			} catch (RSSpectException r) {
				assertEquals(r.getMessage(),
						"width cannot be greater than 144px.");
			}

			try {
				RSSDoc.buildWidth("abc");
			} catch (RSSpectException r) {
				assertEquals(r.getMessage(), "invalid number format for width.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			fail("should be working. " + e.getLocalizedMessage());
		}
	}

	@Test
	public void testGetAttributeFromGroup() {
		// fail("Not yet implemented");
	}

}
