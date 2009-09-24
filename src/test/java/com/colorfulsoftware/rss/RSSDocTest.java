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

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/* add the stax-utils dependency in the root pom to run this example.
 import javanet.staxutils.IndentingXMLStreamWriter;
 */

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.colorfulsoftware.rss.Attribute;
import com.colorfulsoftware.rss.Channel;
import com.colorfulsoftware.rss.Cloud;
import com.colorfulsoftware.rss.Comments;
import com.colorfulsoftware.rss.Day;
import com.colorfulsoftware.rss.Description;
import com.colorfulsoftware.rss.Enclosure;
import com.colorfulsoftware.rss.Hour;
import com.colorfulsoftware.rss.Image;
import com.colorfulsoftware.rss.Item;
import com.colorfulsoftware.rss.LastBuildDate;
import com.colorfulsoftware.rss.Link;
import com.colorfulsoftware.rss.ManagingEditor;
import com.colorfulsoftware.rss.Name;
import com.colorfulsoftware.rss.PubDate;
import com.colorfulsoftware.rss.RSS;
import com.colorfulsoftware.rss.RSSDoc;
import com.colorfulsoftware.rss.RSSpectException;
import com.colorfulsoftware.rss.SkipDays;
import com.colorfulsoftware.rss.SkipHours;
import com.colorfulsoftware.rss.Source;
import com.colorfulsoftware.rss.TextInput;
import com.colorfulsoftware.rss.Title;
import com.colorfulsoftware.rss.URL;

public class RSSDocTest {

	// http://nytimes.com feed from 04/24/2009
	private String expectedRSS1 = "<?xml version=\"1.0\"?>"
			+ "<?xml-stylesheet href=\"/css/rss20.xsl\" type=\"text/xsl\"?>"
			+ "<rss xmlns:pheedo=\"http://www.pheedo.com/namespace/pheedo\" xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xmlns:media=\"http://search.yahoo.com/mrss/\" xmlns:atom=\"http://www.w3.org/2005/Atom\" xmlns:nyt=\"http://www.nytimes.com/namespaces/rss/2.0\" version=\"2.0\">"
			+ "<fakeExt xmlns=\"http://www.fake.extension.org/fakeness\" />"
			+ "<channel>"
			+ "<pubDate>Fri, 24 Apr 2009 17:29:13 GMT</pubDate>"
			+ "<category>music</category>"
			+ "<category>news</category>"
			+ "<rating>funky</rating>"
			+ "<managingEditor>Bill Brown</managingEditor>"
			+ "<cloud domain=\"rpc.sys.com\" port=\"80\" path=\"/RPC2\" registerProcedure=\"pingMe\" protocol=\"soap\"/>"
			+ "<docs>http://www.colorfulsoftware.com</docs>"
			+ "<skipDays><day>Monday</day><day>Tuesday</day><day>Wednesday</day></skipDays>"
			+ "<skipHours><hour>0</hour><hour>12</hour><hour>23</hour></skipHours>"
			+ "<ttl>60</ttl>"
			+ "<webMaster>Bill Brown</webMaster>"
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
			+ "<textInput>"
			+ "<title>Submit Data</title>"
			+ "<description>a typical textarea input</description>"
			+ "<name>textArea</name>"
			+ "<link>http://www.earthbeats.net</link>"
			+ "</textInput>"

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
			+ "<source url=\"http://www.tomalak.org/links2.xml\">Tomalak's Realm</source>"
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
			+ "<source url=\"http://www.tomalak.org/links2.xml\">Tomalak's Realm</source>"
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
			+ "<pubDate>Fri, 24 Apr 2009 17:28:46 GMT</pubDate>"
			+ "<source url=\"http://www.tomalak.org/links2.xml\">Tomalak's Realm</source>"
			+ "</item>" + "</channel>" + "</rss>";

	private String expectedRSS2 = "<rss version=\"2.0\"><channel><title>simplest feed</title><link>http://www.outthere.net</link><description>something cool</description></channel></rss>";

	private String expectedRSS3 = "<rss version=\"2.0\"><channel><title>simplest feed</title><link>http://www.outthere.net</link><description>something cool</description><item><title>simplest feed</title><comments>these are comments.  they contain </comments><description><b xmlns=\"http://www.w3.org/1999/xhtml\">html</b></description></item></channel></rss>";

	private String expectedRSS4 = "<rss version=\"2.0\"><channel><title>simplest feed</title><link>http://www.outthere.net</link><description>something cool</description><category domain=\"http://www.colorfulsoftware.com\">Funky</category></channel></rss>";

	private String expectedRSS5 = "<rss version=\"2.0\"><channel><title>simplest feed</title><link>http://www.outthere.net</link><description>something cool</description><item><title>first title</title></item><item><description>first description</description></item></channel></rss>";

	private String expectedRSS6 = "<rss version=\"2.0\" xmlns:atom=\"http://www.w3.org/2005/Atom\"><channel><title>simplest feed</title><link>http://www.outthere.net</link><description>something cool</description><atom:link rel=\"self\" type=\"application/rss+xml\" href=\"http://www.colorfulsoftware.com/news.xml\"/></channel></rss>";

	private String expectedRSS7 = "<rss version=\"2.0\" xmlns:atom=\"http://www.w3.org/2005/Atom\"><channel><title>simplest feed</title><link>http://www.outthere.net</link><description>something cool</description></channel><atom:link rel=\"self\" type=\"application/rss+xml\" href=\"http://www.colorfulsoftware.com/news.xml\"/></rss>";

	private String expectedRSS8 = "<rss version=\"2.0\" xmlns:media=\"http://www.w3.org/2005/Atom\"><channel><title>simplest feed</title><link>http://www.outthere.net</link><description>something cool</description></channel><media:credit>Khalid Mohammed/Associated<media:subEle>test</media:subEle> Press</media:credit></rss>";
	
	
	private static Calendar theDate;
	static {
		theDate = Calendar.getInstance();
		theDate.clear();
		theDate.set(2009, 0, 1);
	}

	private RSS rss1;
	private RSSDoc rssDoc;

	@Before
	public void setUp() throws Exception {
		rssDoc = new RSSDoc("UTF-8","1.0");
	}

	@After
	public void tearDown() throws Exception {
		new File("target/out1.xml").delete();
		// new File("out2.xml").delete();
	}

	@Test
	public void testGetRSSpectVersion() {
		// fail("Not yet implemented");
	}

	@Test
	public void testWriteRSSDocOutputStreamRSSStringString() {
		try {
			rss1 = rssDoc.readRSSToBean(new java.net.URL(
					"http://feeds.nytimes.com/nyt/rss/HomePage"));
			rssDoc.writeRSSDoc(new FileOutputStream("target/out1.xml"), rss1,
					rssDoc.getEncoding(), rssDoc.getXmlVersion());
			RSS rss2 = rssDoc.readRSSToBean(new File("target/out1.xml"));
			assertNotNull(rss2);
			assertNotNull(rss2.getAttributes());
			assertNotNull(rss2.getChannel());
			assertNull(rss2.getExtensions());
		} catch (Exception e) {
			e.printStackTrace();
			fail("could not write output file with file output stream.");
		}

		try {
			rssDoc.writeRSSDoc(new FileOutputStream("target/out1.xml"), null,
					rssDoc.getEncoding(), rssDoc.getXmlVersion());
			RSS rss2 = rssDoc.readRSSToBean(new File("target/out1.xml"));
			assertNotNull(rss2);
			assertNotNull(rss2.getAttributes());
			assertNotNull(rss2.getChannel());
			assertNull(rss2.getExtensions());
		} catch (Exception e) {
			assertTrue(e instanceof RSSpectException);
			assertEquals(e.getMessage(), "error writing rss feed: null");
		}
	}

	@Test
	public void testWriteRSSDocWriterRSSStringString() {
		// fail("Not yet implemented");
	}

	@Test
	public void testWriteRSSDocXMLStreamWriterRSSStringString() {
		/*
		 * add the stax-utils dependency in the root pom to run this example.
		 * try { rss1 = rssDoc.readRSSToBean(new java.net.URL(
		 * "http://feeds.nytimes.com/nyt/rss/HomePage")); XMLStreamWriter writer
		 * = new IndentingXMLStreamWriter(
		 * XMLOutputFactory.newInstance().createXMLStreamWriter( new
		 * FileOutputStream("target/out2.xml"), rssDoc.encoding));
		 * rssDoc.writeRSSDoc(writer, rss1, null, null); } catch (Exception e) {
		 * e.printStackTrace();
		 * fail("could not write output file with file output stream."); }
		 */

		try {
			rss1 = rssDoc.readRSSToBean(new java.net.URL(
					"http://feeds.nytimes.com/nyt/rss/HomePage"));
			XMLStreamWriter writer = XMLOutputFactory.newInstance()
					.createXMLStreamWriter(
							new FileOutputStream("target/out2.xml"),
							rssDoc.getEncoding());
			rssDoc.writeRSSDoc(writer, rss1, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			fail("could not write output file with file output stream.");
		}
		
		try {
			rss1 = rssDoc.readRSSToBean(new java.net.URL(
					"http://feeds.nytimes.com/nyt/rss/HomePage"));
			XMLStreamWriter writer = XMLOutputFactory.newInstance()
					.createXMLStreamWriter(
							new FileOutputStream("target/out2.xml"),
							rssDoc.getEncoding());
			rssDoc.writeRSSDoc(writer, null, null, null);
			fail("we should fail before this.");
		} catch (Exception e) {
			assertTrue(e instanceof RSSpectException);
			assertEquals(e.getMessage(), "error writing rss feed: null");
			
		}

		try {
			rss1 = rssDoc.readRSSToBean(new java.net.URL(
					"http://feeds.nytimes.com/nyt/rss/HomePage"));
			rssDoc.writeRSSDoc(new File("target/out2.xml"), rss1, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			fail("could not write output file with file output stream.");
		}

		try {
			rssDoc.writeRSSDoc(new File("target/out2.xml"), null, null, null);
		} catch (Exception e) {
			assertTrue(e instanceof RSSpectException);
			assertEquals(e.getMessage(), "error writing rss feed: null");
		}

		/*
		 * add the stax-utils dependency in the root pom to run this example.
		 * try { XMLStreamWriter writer = new IndentingXMLStreamWriter(
		 * XMLOutputFactory.newInstance().createXMLStreamWriter( new
		 * FileOutputStream("target/out2.xml"), rssDoc.encoding));
		 * rssDoc.writeRSSDoc(writer, null, null, null); } catch (Exception e) {
		 * assertTrue(e instanceof RSSpectException);
		 * assertEquals(e.getMessage(), "error writing rss feed: null"); }
		 */
	}

	@Test
	public void testReadRSSToStringRSSString() {

	}

	@Test
	public void testReadRSSToStringRSS() {
		try {
			rss1 = rssDoc.readRSSToBean(expectedRSS1);
			String rss1Str = rssDoc.readRSSToString(rss1);
			assertNotNull(rss1Str);
			rss1 = rssDoc.readRSSToBean(rss1Str);
			assertNotNull(rss1);
			assertNotNull(rss1.getAttributes());
			assertNotNull(rss1.getChannel());
			assertNotNull(rss1.getExtensions());

		} catch (Exception e) {
			e.printStackTrace();
			fail("should be working. " + e.getLocalizedMessage());
		}

		try {
			rss1 = rssDoc.readRSSToBean(expectedRSS1);
			String rss1Str = rssDoc.readRSSToString(rss1,
					"javanet.staxutils.IndentingXMLStreamWriter");
			rss1 = rssDoc.readRSSToBean(rss1Str);
			assertNotNull(rss1);
			assertNotNull(rss1.getAttributes());
			assertNotNull(rss1.getChannel());
			assertNotNull(rss1.getExtensions());

		} catch (Exception e) {
			e.printStackTrace();
			fail("should be working. " + e.getLocalizedMessage());
		}

		try {
			rss1 = rssDoc.readRSSToBean(expectedRSS1);
			String rss1Str = rssDoc.readRSSToString(rss1, "Bunky");
			rss1 = rssDoc.readRSSToBean(rss1Str);
			assertNotNull(rss1);
			assertNotNull(rss1.getAttributes());
			assertNotNull(rss1.getChannel());
			assertNotNull(rss1.getExtensions());

		} catch (Exception e) {
			fail("should not get here." + e.getLocalizedMessage());
		}

		try {
			rss1 = rssDoc.readRSSToBean(expectedRSS1);
			String rss1Str = rssDoc.readRSSToString(null,
					"javanet.staxutils.IndentingXMLStreamWriter");
			rss1 = rssDoc.readRSSToBean(rss1Str);
			assertNotNull(rss1);
			assertNotNull(rss1.getAttributes());
			assertNotNull(rss1.getChannel());
			assertNotNull(rss1.getExtensions());

		} catch (Exception e) {
			assertTrue(e instanceof RSSpectException);
			System.out.println("message = '" + e.getMessage() + "'");
			assertEquals(e.getMessage(), "error reading rss feed: null");
		}
	}

	@Test
	public void testReadRSSToBeanString() {
		try {
			rss1 = rssDoc.readRSSToBean(expectedRSS1);
			assertNotNull(rss1);
			assertNotNull(rss1.getAttributes());
			assertNotNull(rss1.getChannel());
			assertNotNull(rss1.getExtensions());
			Channel channel = rss1.getChannel();
			assertNotNull(channel.getCategories());
			assertNotNull(channel.getRating());
			assertNotNull(channel.getManagingEditor());
			assertNotNull(channel.getCloud());
			assertNotNull(channel.getSkipDays());
			assertNotNull(channel.getSkipHours());
			assertNotNull(channel.getTtl());
			assertNotNull(channel.getWebMaster());

		} catch (Exception e) {
			e.printStackTrace();
			fail("should be working. " + e.getLocalizedMessage());
		}

		try {
			rss1 = rssDoc.readRSSToBean("");
			fail("should not get here.");
		} catch (RSSpectException e) {
			System.out.println("message = '" + e.getMessage() + "'");
			assertEquals(e.getMessage(),
					"error reading rss feed: ParseError at [row,col]:[1,1]\n"
							+ "Message: Premature end of file.");
		}
	}

	@Test
	public void testReadRSSToBeanFile() {
		try {
			rss1 = rssDoc.readRSSToBean(new File(
					"src/test/resources/nyTimes.rss.xml"));
			assertNotNull(rss1);
			assertNotNull(rss1.getAttributes());
			assertNotNull(rss1.getChannel());

		} catch (Exception e) {
			e.printStackTrace();
			fail("should be working. " + e.getLocalizedMessage());
		}

		try {
			rss1 = rssDoc
					.readRSSToBean(new File("src/test/resources/out1.xml"));
			fail("should not get here.");
		} catch (RSSpectException e) {
			System.out.println("message = '" + e.getMessage() + "'");
			assertEquals(e.getMessage(),
					"error reading rss feed: ParseError at [row,col]:[1,1]\n"
							+ "Message: Premature end of file.");
		}
	}

	@Test
	public void testReadRSSToBeanURL() {
		try {
			rss1 = rssDoc.readRSSToBean(new java.net.URL(
					"http://feeds.nytimes.com/nyt/rss/HomePage"));
			assertNotNull(rss1);
			assertNotNull(rss1.getAttributes());
			assertNotNull(rss1.getChannel());

		} catch (Exception e) {
			e.printStackTrace();
			fail("should be working. " + e.getLocalizedMessage());
		}

		try {
			rss1 = rssDoc.readRSSToBean(new java.net.URL(
					"http://www.earthbeats.net/drops.xml"));
			fail("should not get here.");
		} catch (Exception e) {
			assertTrue(e instanceof RSSpectException);
			System.out.println("message = '" + e.getMessage() + "'");
			assertEquals(e.getMessage(),
					"error reading rss feed: rss elements MUST contain a channel element.");
		}

		try {
			rss1 = rssDoc.readRSSToBean(new java.net.URL(
					"http://www.someunknownnonworkingurl.nogood"));
			fail("should not get here.");
		} catch (Exception e) {
			assertTrue(e instanceof RSSpectException);
			System.out.println("message = '" + e.getMessage() + "'");
			/*
			 * for some reason hosts behind Comcast (and maybe other) ISPs choke
			 * on this we would like to test for unknown host here because they
			 * return an error document instead of unknown host. :(
			 * assertEquals(e.getMessage(),
			 * "error reading rss feed: java.net.UnknownHostException: www.someunknownnonworkingurl.nogood: www.someunknownnonworkingurl.nogood"
			 * );
			 */
			assertTrue(e.getMessage().startsWith("error reading rss feed: "));

		}
	}

	@Test
	public void testReadRSSToBeanInputStream() {
		try {
			rss1 = rssDoc.readRSSToBean(new java.net.URL(
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
			rss1 = rssDoc.readRSSToBean(expectedRSS2);
			assertNotNull(rss1);
			assertNotNull(rss1.getChannel());

		} catch (Exception e) {
			e.printStackTrace();
			fail("should be working. " + e.getLocalizedMessage());
		}

		try {
			RSS rss = rssDoc.buildRSS(null, null, null);
			assertNotNull(rss);
			fail("we should have thrown an exception above.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(),
					"rss elements MUST contain a channel element.");
		}

		try {
			Channel channel = rssDoc.buildChannel(rssDoc
					.buildTitle("this is a title"), rssDoc
					.buildLink("http://www.minoritydirectory.net"), rssDoc
					.buildDescription("this is a description"), null, null,
					null, null, null, null, null, null, null, null, null, null,
					null, null, null, null, null, null);
			RSS rss = rssDoc.buildRSS(channel, null, null);
			assertNotNull(rss);
			fail("we should have thrown an exception above.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(),
					"RSS elements must contain a version attribute.");
		}

		try {
			rss1 = rssDoc.readRSSToBean(expectedRSS7);
			assertNotNull(rss1);
			assertNotNull(rss1.getChannel());
			assertNotNull(rss1.getAttribute("version"));
			assertNull(rss1.getAttribute("bunk"));
			Extension extOne = rss1.getExtension("atom:link");
			assertNotNull(extOne);
			assertNotNull(extOne.getAttribute("type"));
			assertEquals(extOne.getAttribute("type").getValue(),
					"application/rss+xml");
			assertNull(extOne.getAttribute("Bunky"));
			assertNull(rss1.getExtension("quePasa?"));
		} catch (RSSpectException r) {
			fail("should not get here.");
		}
	}

	@Test
	public void testBuildAttribute() {
		try {
			rss1 = rssDoc.readRSSToBean(expectedRSS1);
			String rss1Str = rssDoc.readRSSToString(rss1);
			assertNotNull(rss1Str);
			rss1 = rssDoc.readRSSToBean(rss1Str);

			assertNotNull(rss1);
			assertNotNull(rss1.getExtensions());

			for (Attribute attr : rss1.getAttributes()) {
				assertNotNull(attr);
				assertNotNull(attr.getName());
				assertNotNull(attr.getValue());

				if (attr.getName().equals("xmlns:pheedo")) {
					assertTrue(attr.equals(rssDoc.buildAttribute(
							"xmlns:pheedo",
							"http://www.pheedo.com/namespace/pheedo")));
				}

				if (attr.getName().equals("xmlns:dc")) {
					assertTrue(attr.equals(rssDoc.buildAttribute("xmlns:dc",
							"http://purl.org/dc/elements/1.1/")));
				}

				assertFalse(attr.equals(rssDoc.buildAttribute("xmlns:pheedo",
						"http://www.pheedo.com/namespace/bobo")));

				assertFalse(attr.equals(rssDoc.buildAttribute("dude", null)));

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
		try {
			Category cat = rssDoc.buildCategory(null, "");
			assertNotNull(cat);
			assertEquals(cat.getCategory(), "");
			assertEquals(cat.getDomain(), null);
			cat = rssDoc.buildCategory(null, null);
			fail("should not fail here.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(),
					"Category elements SHOULD contain text data.  Empty strings are allowed.");
		}
	}

	@Test
	public void testBuildChannel() {
		try {
			rss1 = rssDoc.readRSSToBean(expectedRSS2);
			String rss1Str = rssDoc.readRSSToString(rss1);
			assertNotNull(rss1Str);
			rss1 = rssDoc.readRSSToBean(rss1Str);

			try {
				rssDoc.buildChannel(null, null, null, null, null, null, null,
						null, null, null, null, null, null, null, null, null,
						null, null, null, null, null);
				fail("we should have thrown an exception above.");
			} catch (RSSpectException r) {
				assertEquals(r.getMessage(),
						"channel elements MUST contain a title element.");
			}

			try {
				rssDoc.buildChannel(rssDoc.buildTitle("this is a title"), null,
						null, null, null, null, null, null, null, null, null,
						null, null, null, null, null, null, null, null, null,
						null);
				fail("we should have thrown an exception above.");
			} catch (RSSpectException r) {
				assertEquals(r.getMessage(),
						"channel elements MUST contain a link element.");
			}

			try {
				rssDoc.buildChannel(rssDoc.buildTitle("this is a title"),
						rssDoc.buildLink("http://www.minoritydirectory.net"),
						null, null, null, null, null, null, null, null, null,
						null, null, null, null, null, null, null, null, null,
						null);
				fail("we should have thrown an exception above.");
			} catch (RSSpectException r) {
				assertEquals(r.getMessage(),
						"channel elements MUST contain a description element.");
			}

			try {
				assertNotNull(rssDoc.buildChannel(rssDoc
						.buildTitle("this is a title"), rssDoc
						.buildLink("http://www.minoritydirectory.net"), rssDoc
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

		try {
			rss1 = rssDoc.readRSSToBean(expectedRSS4);
			assertNotNull(rss1);
			assertNotNull(rss1.getChannel());
			assertNotNull(rss1.getChannel().getCategory("Funky"));
			assertNull(rss1.getChannel().getCategory("Bunky"));
		} catch (RSSpectException r) {
			System.out.println("error: " + r.getMessage());
			fail("should just work.");
		}
	}

	@Test
	public void testBuildCloud() {
		try {
			rss1 = rssDoc.readRSSToBean(expectedRSS1);
			assertNotNull(rss1);
			assertNotNull(rss1.getChannel());
			Cloud cloud = rss1.getChannel().getCloud();
			assertNotNull(cloud);
			assertNotNull(cloud.getAttributes());
			assertNotNull(cloud.getAttribute("port"));
			assertNull(cloud.getAttribute("bunk"));
			Attribute attribute = cloud.getAttribute("port");
			assertEquals(attribute.getValue(), "80");
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
				rssDoc.buildCloud(null);
				fail("we should have thrown an exception above.");
			} catch (RSSpectException r) {
				assertEquals(
						r.getMessage(),
						"The cloud element requires attributes:  See \"http://cyber.law.harvard.edu/rss/soapMeetsRss.html#rsscloudInterface\".");
			}

			List<Attribute> attrs = new LinkedList<Attribute>();
			try {
				rssDoc.buildCloud(attrs);
				fail("we should have thrown an exception above.");
			} catch (RSSpectException r) {
				assertEquals(r.getMessage(),
						"cloud elements MUST have a domain attribute.");
			}
			attrs.add(rssDoc.buildAttribute("domain", "domain"));

			try {
				rssDoc.buildCloud(attrs);
				fail("we should have thrown an exception above.");
			} catch (RSSpectException r) {
				assertEquals(r.getMessage(),
						"cloud elements MUST have a port attribute.");
			}
			attrs.add(rssDoc.buildAttribute("port", "port"));

			try {
				rssDoc.buildCloud(attrs);
				fail("we should have thrown an exception above.");
			} catch (RSSpectException r) {
				assertEquals(r.getMessage(),
						"cloud elements MUST have a path attribute.");
			}
			attrs.add(rssDoc.buildAttribute("path", "path"));

			try {
				rssDoc.buildCloud(attrs);
				fail("we should have thrown an exception above.");
			} catch (RSSpectException r) {
				assertEquals(r.getMessage(),
						"cloud elements MUST have a registerProcedure attribute.");
			}
			attrs.add(rssDoc.buildAttribute("registerProcedure",
					"registerProcedure"));

			try {
				rssDoc.buildCloud(attrs);
				fail("we should have thrown an exception above.");
			} catch (RSSpectException r) {
				assertEquals(r.getMessage(),
						"cloud elements MUST have a protocol attribute.");
			}
			attrs.add(rssDoc.buildAttribute("protocol", "protocol"));

			try {
				rssDoc.buildCloud(attrs);
				fail("we should have thrown an exception above.");
			} catch (RSSpectException r) {
				assertEquals(r.getMessage(),
						"the cloud's protocol attribute must be 'xml-rpc' or 'soap', case-sensitive.");
			}

			attrs = new LinkedList<Attribute>();
			attrs.add(rssDoc.buildAttribute("domain", "domain"));
			attrs.add(rssDoc.buildAttribute("port", "port"));
			attrs.add(rssDoc.buildAttribute("path", "path"));
			attrs.add(rssDoc.buildAttribute("registerProcedure",
					"registerProcedure"));
			attrs.add(rssDoc.buildAttribute("protocol", "soap"));
			assertNotNull(rssDoc.buildCloud(attrs));

		} catch (Exception e) {
			e.printStackTrace();
			fail("should be working. " + e.getLocalizedMessage());
		}
	}

	@Test
	public void testBuildComments() {
		try {
			rss1 = rssDoc.readRSSToBean(expectedRSS3);
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
			rss1 = rssDoc.readRSSToBean(expectedRSS1);
			assertNotNull(rss1);
			assertNotNull(rss1.getChannel());
			List<Item> items = rss1.getChannel().getItems();
			assertNotNull(items);
			assertTrue(items.size() == 3);
			for (Item item : items) {

				assertNotNull(item.getEnclosure());
				Enclosure enclosure = item.getEnclosure();
				assertNotNull(enclosure.getAttribute("type"));
				assertNull(enclosure.getAttribute("bunk"));
				Attribute attribute = enclosure.getAttribute("type");
				assertEquals(attribute.getValue(), "audio/flac");
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
				rssDoc.buildEnclosure(null);
				fail("we should have thrown an exception above.");
			} catch (RSSpectException r) {
				assertEquals(
						r.getMessage(),
						"enclosure elements MUST contain the url, length and type attributes.  See: http://cyber.law.harvard.edu/rss/rss.html#ltenclosuregtSubelementOfLtitemgt");
			}

			List<Attribute> attrs = new LinkedList<Attribute>();
			try {
				rssDoc.buildEnclosure(attrs);
				fail("we should have thrown an exception above.");
			} catch (RSSpectException r) {
				assertEquals(r.getMessage(),
						"enclusure elements MUST have a url attribute.");
			}
			attrs
					.add(rssDoc.buildAttribute("url",
							"http://www.earthbeats.net"));

			try {
				rssDoc.buildEnclosure(attrs);
				fail("we should have thrown an exception above.");
			} catch (RSSpectException r) {
				assertEquals(r.getMessage(),
						"enclusure elements MUST have a length attribute.");
			}
			attrs.add(rssDoc.buildAttribute("length", "1234567"));

			try {
				rssDoc.buildEnclosure(attrs);
				fail("we should have thrown an exception above.");
			} catch (RSSpectException r) {
				assertEquals(r.getMessage(),
						"enclusure elements MUST have a type attribute.");
			}
			attrs.add(rssDoc.buildAttribute("type", "media/flac"));

			assertNotNull(rssDoc.buildEnclosure(attrs));

		} catch (Exception e) {
			e.printStackTrace();
			fail("should be working. " + e.getLocalizedMessage());
		}
	}

	@Test
	public void testBuildExtension() {
		try {
			rss1 = rssDoc.readRSSToBean(expectedRSS6);
			assertNotNull(rss1);
			assertNotNull(rss1.getChannel());
			Extension extOne = rss1.getChannel().getExtension("atom:link");
			assertNotNull(extOne);
			assertNotNull(extOne.getAttribute("type"));
			assertEquals(extOne.getAttribute("type").getValue(),
					"application/rss+xml");
			assertNull(extOne.getAttribute("Bunky"));
			assertNull(rss1.getChannel().getExtension("Bunky"));
			rssDoc.buildExtension(null, null, "Bunky");
			fail("should not get here.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(),
					"channel elements SHOULD contain a title element.");
		}
		
		try {
			//for testing extention element sub elements. 
			rss1 = rssDoc.readRSSToBean(expectedRSS8);
			assertNotNull(rss1);
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(),
					"channel elements SHOULD contain a title element.");
		}
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
			rss1 = rssDoc.readRSSToBean(expectedRSS1);
			assertNotNull(rss1);
			assertNotNull(rss1.getChannel());
			assertNotNull(rss1.getChannel().getImage());
			assertNotNull(rss1.getChannel().getImage().getHeight());
			assertNotNull(rss1.getChannel().getImage().getHeight().getHeight());
			assertEquals(rss1.getChannel().getImage().getHeight().getHeight(),
					"100");
			try {
				rssDoc.buildHeight("401");
				fail("we should have thrown an exception above.");
			} catch (RSSpectException r) {
				assertEquals(r.getMessage(),
						"height cannot be greater than 400px.");
			}

			try {
				rssDoc.buildHeight("abc");
				fail("we should have thrown an exception above.");
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
			rssDoc.buildImage(url, title, link, null, null, null);
			fail("we should have thrown an exception above.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(),
					"image elements MUST contain a url element.");
		}

		try {
			url = rssDoc.buildURL("http://www.earthbeats.net");
			rssDoc.buildImage(url, title, link, null, null, null);
			fail("we should have thrown an exception above.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(),
					"image elements MUST contain a title element.");
		}

		try {
			title = rssDoc.buildTitle("this is a title");
			rssDoc.buildImage(url, title, link, null, null, null);
			fail("we should have thrown an exception above.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(),
					"image elements MUST contain a link element.");
		}
		try {
			link = rssDoc.buildLink("http://www.earthbeats.net");
			Image image = rssDoc.buildImage(url, title, link, null, null, null);
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
			rss1 = rssDoc.readRSSToBean(expectedRSS1);
			assertNotNull(rss1);
			assertNotNull(rss1.getChannel());
			List<Item> items = rss1.getChannel().getItems();
			assertNotNull(items);
			for (Item item : items) {
				assertNotNull(item.getTitle());
				assertNotNull(item.getDescription());
				assertNotNull(item.getAuthor());
				assertNotNull(item.getSource());
				Extension extOne = item.getExtension("media:content");
				assertNull(item.getExtension("thingy:majig"));
				if (extOne != null) {
					assertNotNull(extOne.getAttribute("url"));
					assertNull(extOne.getAttribute("Bunky"));
				}
				Category cat = item.getCategory("Subprime Mortgage Crisis");
				Category cat2 = item.getCategory("");
				Category cat3 = item.getCategory(null);
				assertNull(cat2);
				assertNull(cat3);
				if (cat != null) {
					assertNotNull(cat.getDomain());
					assertEquals(cat.getCategory(), "Subprime Mortgage Crisis");
					assertEquals(cat.getDomain().getValue(),
							"http://www.nytimes.com/namespaces/keywords/des");
				}
			}

			try {
				Item item = rssDoc.buildItem(null, null, rssDoc
						.buildDescription("something cool"), null, null, null,
						null, null, null, null, null);
				assertNotNull(item.getDescription());
				assertNull(item.getTitle());
			} catch (RSSpectException r) {
				fail("should not fail here.");
			}

			try {
				Item item = rssDoc.buildItem(rssDoc.buildTitle("try me"), null,
						null, null, null, null, null, null, null, null, null);
				assertNotNull(item.getTitle());
				assertNull(item.getDescription());
			} catch (RSSpectException r) {
				fail("should not fail here.");
			}

			try {
				rssDoc.buildItem(null, null, null, null, null, null, null,
						null, null, null, null);
				fail("we should have thrown an exception above.");
			} catch (RSSpectException r) {
				assertEquals(r.getMessage(),
						"item elements MUST contain either a title or description element.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			fail("should be working. " + e.getLocalizedMessage());
		}

		try {
			rss1 = rssDoc.readRSSToBean(expectedRSS5);
			assertNotNull(rss1);
			assertNotNull(rss1.getChannel());
			Item itmTitle = rss1.getChannel().getItem("first title");
			assertNotNull(itmTitle);
			Item itmDesc = rss1.getChannel().getItem("first description");
			assertNotNull(itmDesc);
			assertNull(rss1.getChannel().getItem("Bunky"));
		} catch (Exception r) {
			System.out.println("error: " + r.getMessage());
			fail("should just work.");
		}
	}

	@Test
	public void testBuildLanguage() {
		// fail("Not yet implemented");
	}

	@Test
	public void testBuildLastBuildDate() {
		LastBuildDate lastBuildDate = rssDoc.buildLastBuildDate(null);
		assertNotNull(lastBuildDate);
		assertNull(lastBuildDate.getDateTime());
		assertNull(lastBuildDate.getText());
	}

	@Test
	public void testBuildLink() {
		try {
			Link link = rssDoc.buildLink(null);
			assertNotNull(link);
			assertNull(link.getLink());
		} catch (RSSpectException r) {
			fail("should not fail here.");
		}

		try {
			Link link = rssDoc.buildLink("");
			assertNotNull(link);
			assertNotNull(link.getLink());
			assertTrue(link.getLink().length() == 0);
		} catch (RSSpectException r) {
			fail("should not fail here.");
		}

		try {
			Link link = rssDoc.buildLink(" ");
			assertNotNull(link);
			fail("we should have thrown an exception above.");
		} catch (RSSpectException r) {
			assertEquals(
					r.getMessage(),
					"link elements must start with a valid "
							+ "Uniform Resource Identifer (URI) Schemes.  "
							+ "See http://www.iana.org. Yours started with: ' '");
		}

		try {
			Link link = rssDoc.buildLink("abcScheme://testMe");
			assertNotNull(link);
			fail("we should have thrown an exception above.");
		} catch (RSSpectException r) {
			assertEquals(
					r.getMessage(),
					"link elements must start with a valid "
							+ "Uniform Resource Identifer (URI) Schemes.  "
							+ "See http://www.iana.org. Yours started with: 'abcScheme://testMe'");
		}
	}

	@Test
	public void testBuildManagingEditor() {
		try {
			rss1 = rssDoc.readRSSToBean(expectedRSS1);
			assertNotNull(rss1);
			assertNotNull(rss1.getChannel());
			ManagingEditor me = rss1.getChannel().getManagingEditor();
			assertNotNull(me);
			assertNotNull(me.getManagingEditor());
			assertEquals(me.getManagingEditor(), "Bill Brown");

		} catch (Exception e) {
			fail("should not fail here.");
		}
	}

	@Test
	public void testBuildName() {
		// fail("Not yet implemented");
	}

	@Test
	public void testBuildPubDate() {
		PubDate pubDate = rssDoc.buildPubDate(null);
		assertNotNull(pubDate);
		assertNull(pubDate.getDateTime());
		assertNull(pubDate.getText());
	}

	@Test
	public void testBuildRating() {
		// fail("Not yet implemented");
	}

	@Test
	public void testBuildSkipDays() {
		try {
			SkipDays skipDays = rssDoc.buildSkipDays(null);
			assertNotNull(skipDays);
			fail("we should have thrown an exception above.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(),
					"skipDays elements should contain at least one <day> sub element.");
		}

		try {
			SkipDays skipDays = rssDoc.buildSkipDays(new LinkedList<Day>());
			assertNotNull(skipDays);
			fail("we should have thrown an exception above.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(),
					"skipDays elements should contain at least one <day> sub element.");
		}

		try {
			List<Day> days = new LinkedList<Day>();
			days.add(rssDoc.buildDay("yes"));
			SkipDays skipDays = rssDoc.buildSkipDays(days);
			assertNotNull(skipDays);
			fail("we should have thrown an exception above.");
		} catch (RSSpectException r) {
			assertEquals(
					r.getMessage(),
					"day elements must have a value of Monday, Tuesday, Wednesday, Thursday, Friday, Saturday or Sunday.");
		}

		try {
			List<Day> days = new LinkedList<Day>();
			days.add(rssDoc.buildDay("Thursday"));
			days.add(rssDoc.buildDay("Friday"));
			days.add(rssDoc.buildDay("Saturday"));
			days.add(rssDoc.buildDay("Sunday"));
			SkipDays skipDays = rssDoc.buildSkipDays(days);
			assertNotNull(skipDays);
			assertNotNull(skipDays.getSkipDays());
		} catch (Exception e) {
			fail("should not fail here.");
		}
	}

	@Test
	public void testBuildSkipHours() {
		try {
			SkipHours skipHours = rssDoc.buildSkipHours(null);
			assertNotNull(skipHours);
			fail("we should have thrown an exception above.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(),
					"skipHours elements should contain at least one <hour> sub element.");
		}

		try {
			SkipHours skipHours = rssDoc.buildSkipHours(new LinkedList<Hour>());
			assertNotNull(skipHours);
			fail("we should have thrown an exception above.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(),
					"skipHours elements should contain at least one <hour> sub element.");
		}

		try {
			List<Hour> hours = new LinkedList<Hour>();
			hours.add(rssDoc.buildHour("24"));
			SkipHours skipHours = rssDoc.buildSkipHours(hours);
			assertNotNull(skipHours);
			fail("we should have thrown an exception above.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(),
					"hour elements must be between 0 and 23 inclusive.");
		}

		try {
			List<Hour> hours = new LinkedList<Hour>();
			hours.add(rssDoc.buildHour("cat"));
			SkipHours skipHours = rssDoc.buildSkipHours(hours);
			assertNotNull(skipHours);
			fail("we should have thrown an exception above.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(), "invalid number format for hour.");
		}

		try {
			List<Hour> hours = new LinkedList<Hour>();
			hours.add(rssDoc.buildHour("23"));
			SkipHours skipHours = rssDoc.buildSkipHours(hours);
			assertNotNull(skipHours);
			assertNotNull(skipHours.getSkipHours());
		} catch (Exception e) {
			fail("should not fail here.");
		}
	}

	@Test
	public void testBuildSource() {
		try {
			Source source = rssDoc.buildSource(null, "somewhere cool");
			assertNotNull(source);
			fail("we should have thrown an exception above.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(),
					"source elements MUST contain a url attribute.");
		}

		try {
			Source source = rssDoc.buildSource(rssDoc.buildAttribute("cat",
					"dog"), "somewhere cool");
			assertNotNull(source);
			fail("we should have thrown an exception above.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(),
					"source elements MUST contain a url attribute.");
		}

		try {
			Source source = rssDoc.buildSource(rssDoc.buildAttribute("url",
					"http://www.earthbeats.net"), "somewhere cool");
			assertNotNull(source);
			assertNotNull(source.getUrl());
			assertNotNull(source.getUrl().getName());
			assertEquals(source.getUrl().getName(), "url");
			assertNotNull(source.getUrl().getValue());
			assertEquals(source.getUrl().getValue(),
					"http://www.earthbeats.net");
			assertNotNull(source.getSource());
			assertEquals(source.getSource(), "somewhere cool");
		} catch (Exception e) {
			fail("should not fail here.");
		}
	}

	@Test
	public void testBuildTextInput() {
		Title title = null;
		Description description = null;
		Name name = null;
		Link link = null;
		try {
			rssDoc.buildTextInput(title, description, name, link);
			fail("we should have thrown an exception above.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(),
					"textInput elements MUST contain a title element.");
		}

		try {
			title = rssDoc.buildTitle("Submit");
			rssDoc.buildTextInput(title, description, name, link);
			fail("we should have thrown an exception above.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(),
					"textInput elements MUST contain a description element.");
		}

		try {
			description = rssDoc.buildDescription("regular textarea");
			rssDoc.buildTextInput(title, description, name, link);
			fail("we should have thrown an exception above.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(),
					"textInput elements MUST contain a name element.");
		}

		try {
			name = rssDoc.buildName("textArea");
			rssDoc.buildTextInput(title, description, name, link);
			fail("we should have thrown an exception above.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(),
					"textInput elements MUST contain a link element.");
		}

		try {
			link = rssDoc.buildLink("http://www.earthbeats.net");
			TextInput textInput = rssDoc.buildTextInput(title, description,
					name, link);
			assertNotNull(textInput);
			assertEquals(textInput.getTitle().getTitle(), "Submit");
			assertEquals(textInput.getDescription().getDescription(),
					"regular textarea");
			assertEquals(textInput.getName().getName(), "textArea");
			assertEquals(textInput.getLink().getLink(),
					"http://www.earthbeats.net");
		} catch (RSSpectException r) {
			fail("should not fail here.");
		}
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
		try {
			URL url = rssDoc.buildURL(null);
			assertNotNull(url);
			assertNull(url.getUrl());
		} catch (RSSpectException r) {
			fail("should not fail here.");
		}

		try {
			URL url = rssDoc.buildURL("");
			assertNotNull(url);
			assertNotNull(url.getUrl());
			assertTrue(url.getUrl().length() == 0);
		} catch (RSSpectException r) {
			fail("should not fail here.");
		}

		try {
			URL url = rssDoc.buildURL(" ");
			assertNotNull(url);
			fail("we should have thrown an exception above.");
		} catch (RSSpectException r) {
			assertEquals(
					r.getMessage(),
					"link elements must start with a valid "
							+ "Uniform Resource Identifer (URI) Schemes.  "
							+ "See http://www.iana.org. Yours started with: ' '");
		}

		try {
			URL url = rssDoc.buildURL("abcScheme://testMe");
			assertNotNull(url);
			fail("we should have thrown an exception above.");
		} catch (RSSpectException r) {
			assertEquals(
					r.getMessage(),
					"link elements must start with a valid "
							+ "Uniform Resource Identifer (URI) Schemes.  "
							+ "See http://www.iana.org. Yours started with: 'abcScheme://testMe'");
		}
	}

	@Test
	public void testBuildWebMaster() {
		// fail("Not yet implemented");
	}

	@Test
	public void testBuildWidth() {
		try {
			rss1 = rssDoc.readRSSToBean(expectedRSS1);
			assertNotNull(rss1);
			assertNotNull(rss1.getChannel());
			assertNotNull(rss1.getChannel().getImage());
			assertNotNull(rss1.getChannel().getImage().getWidth());
			assertNotNull(rss1.getChannel().getImage().getWidth().getWidth());
			assertEquals(rss1.getChannel().getImage().getWidth().getWidth(),
					"144");
			try {
				rssDoc.buildWidth("145");
				fail("we should have thrown an exception above.");
			} catch (RSSpectException r) {
				assertEquals(r.getMessage(),
						"width cannot be greater than 144px.");
			}

			try {
				rssDoc.buildWidth("abc");
				fail("we should have thrown an exception above.");
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
