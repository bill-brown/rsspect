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
			+ "<channel>"
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
			+ "</image>"
			+ "<item>"
			+ "<title>Ford Has Loss of $1.4 Billion in Quarter, but Beats Forecast</title>"
			+ "<link>http://feeds.nytimes.com/click.phdo?i=0155467d396aa780c181f5f7809a803e</link>"
			+ "<pheedo:origLink>http://www.nytimes.com/2009/04/25/business/25ford.html?partner=rss&amp;amp;emc=rss</pheedo:origLink>"
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
			+ "<guid isPermaLink=\"false\">http://thecaucus.blogs.nytimes.com/2009/04/24/congress-reaches-tentative-deal-on-the-budget/index.html</guid>"
			+ "<description>The negotiated plan seems certain to include a procedural maneuver in an effort to avoid filibusters on health care reform.&lt;br clear=&quot;both&quot; style=&quot;clear: both;&quot;/&gt;&lt;br clear=&quot;both&quot; style=&quot;clear: both;&quot;/&gt;&lt;a href=&quot;http://www.pheedo.com/click.phdo?s=89ed26c949918ac94a090111fd880424&amp;p=1&quot;&gt;&lt;img alt=&quot;&quot; style=&quot;border: 0;&quot; border=&quot;0&quot; src=&quot;http://www.pheedo.com/img.phdo?s=89ed26c949918ac94a090111fd880424&amp;p=1&quot;/&gt;&lt;/a&gt;</description>"
			+ "<dc:creator>By CARL HULSE</dc:creator>"
			+ "<pubDate>Fri, 24 Apr 2009 17:28:46 GMT</pubDate>" + "</item>"
			+ "</channel>" + "</rss>";

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
		//new File("out2.xml").delete();
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
			//fail("could not write output file with file output stream.");
		XMLStreamWriter writer = new IndentingXMLStreamWriter(XMLOutputFactory
						.newInstance().createXMLStreamWriter(
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
			assertNull(rss1.getExtensions());

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
			assertNull(rss1.getExtensions());

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
			assertNull(rss1.getExtensions());

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
			assertNull(rss1.getExtensions());

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
		// fail("Not yet implemented");
	}

	@Test
	public void testBuildAttribute() {
		// fail("Not yet implemented");
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
		// fail("Not yet implemented");
	}

	@Test
	public void testBuildCloud() {
		// fail("Not yet implemented");
	}

	@Test
	public void testBuildComments() {
		// fail("Not yet implemented");
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
		// fail("Not yet implemented");
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
		// fail("Not yet implemented");
	}

	@Test
	public void testBuildImage() {
		// fail("Not yet implemented");
	}

	@Test
	public void testBuildItem() {
		// fail("Not yet implemented");
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
		// fail("Not yet implemented");
	}

	@Test
	public void testGetAttributeFromGroup() {
		// fail("Not yet implemented");
	}

}
