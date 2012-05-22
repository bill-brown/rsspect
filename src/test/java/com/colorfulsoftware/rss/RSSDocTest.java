/**
 * Copyright 2011 Bill Brown
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.colorfulsoftware.rss;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URLClassLoader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

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

/**
 * The class test the rss library's reading and writing capablity.
 * 
 * @author Bill Brown
 * 
 */
public class RSSDocTest implements Serializable {

	private static final long serialVersionUID = 4215775931435710474L;

	// http://nytimes.com feed from 04/24/2009
	private String expectedRSS1 = "<?xml version=\"1.0\"?>"
			+ "<?xml-stylesheet href=\"/css/rss20.xsl\" type=\"text/xsl\"?>"
			+ "<rss xmlns:pheedo=\"http://www.pheedo.com/namespace/pheedo\" xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xmlns:media=\"http://search.yahoo.com/mrss/\" xmlns:atom=\"http://www.w3.org/2005/Atom\" xmlns:nyt=\"http://www.nytimes.com/namespaces/rss/2.0\" version=\"2.0\">"
			+ "<fakeExt xmlns=\"http://www.fake.extension.org/fakeness\" />"
			+ "<fakeExt xmlns=\"http://www.fake.extension.org/fakeness\">fakecontent</fakeExt>"
			+ "<div xmlns=\"http://www.w3.org/1999/xhtml\">fakecontent<p> hello I am a piece of html sitting inside an rss feed &amp; this is legal.<hr /></p></div>"
			+ "<div xmlns=\"http://www.w3.org/1999/xhtml\">A marked up <br /> rights.This is <span style=\"color:blue;\">blue text :). <hr id=\"unique\" class=\"phat\" /> <a href=\"http://maps.google.com?q=something&amp;b=somethingElse\">a fake map link</a></span>. </div>"
			+ "<div xmlns=\"http://www.w3.org/1999/xhtml\">/mean> Example Feed <p><a href=\"brokenULR\">two start elements in a row</a></p></div>"
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
			+ "<description><![CDATA[test a cdata section]]></description>"
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
			+ "<dc:creator>By STEVEN LEE MYERS and TIMOTHY WILLIAMS<dc:subelement><dc:another testattr=\"testval\" /> this is a sub extension <dc:another>with another sub element</dc:another> good bye. </dc:subelement></dc:creator>"
			+ "<atom:title atom:type=\"html\">&lt;div&gt;test &amp;mdash; title&lt;/div&gt;</atom:title>"
			+ "<atom:rights atom:type=\"xhtml\"><div xmlns=\"http://www.w3.org/1999/xhtml\">test &mdash; title <p><hr />lets test <span class=\"bold\">>bold</span>a sub extension. <hr /></p></div></atom:rights>"
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
			+ "<comments>these are test comments.</comments>"
			+ "<pubDate>Fri, 24 Apr 2009 17:28:46 GMT</pubDate>"
			+ "<source url=\"http://www.tomalak.org/links2.xml\">Tomalak's Realm</source>"
			+ "</item>" + "</channel>" + "</rss>";

	private String expectedRSS2 = "<rss version=\"2.0\"><channel><title>simplest feed</title><link>http://www.outthere.net</link><description>something  &amp;mdash; cool</description></channel></rss>";

	private String expectedRSS3 = "<rss version=\"2.0\"><channel><title>simplest feed</title><link>http://www.outthere.net</link><description>something cool</description><item><title>simplest feed</title><comments>these are comments.  they contain </comments><description><b xmlns=\"http://www.w3.org/1999/xhtml\">html</b></description></item></channel></rss>";

	private String expectedRSS4 = "<rss version=\"2.0\"><channel><title>simplest feed</title><link>http://www.outthere.net</link><description>something cool</description><category domain=\"http://www.colorfulsoftware.com\">Funky</category></channel></rss>";

	private String expectedRSS5 = "<rss version=\"2.0\"><channel><title>simplest feed</title><link>http://www.outthere.net</link><description>something cool</description><item><title>first title</title></item><item><description>first description</description></item></channel></rss>";

	private String expectedRSS6 = "<rss version=\"2.0\" xmlns:atom=\"http://www.w3.org/2005/Atom\"><channel><title>simplest feed</title><link>http://www.outthere.net</link><description>something cool</description><atom:link rel=\"self\" type=\"application/rss+xml\" href=\"http://www.colorfulsoftware.com/news.xml\"/></channel></rss>";

	private String expectedRSS7 = "<rss version=\"2.0\" xmlns:atom=\"http://www.w3.org/2005/Atom\"><channel><title>simplest feed</title><link>http://www.outthere.net</link><description>something cool</description></channel><atom:link rel=\"self\" type=\"application/rss+xml\" href=\"http://www.colorfulsoftware.com/news.xml\"/></rss>";

	private String expectedRSS8 = "<rss version=\"2.0\" xmlns:media=\"http://www.w3.org/2005/Atom\"><channel><title>simplest feed</title><link>http://www.outthere.net</link><description>something cool</description></channel><media:credit>Khalid Mohammed/Associated<media:subEle>test</media:subEle> Press</media:credit></rss>";

	private String expectedRSS9 = "<channel><title>simplest feed</title><link>http://www.outthere.net</link><description>something cool</description></channel>";

	// bad lastBuildDate
	private String expectedRSS10 = "<rss version=\"2.0\"><channel><lastBuildDate>abcdefghijabcdefghij</lastBuildDate><title>simplest feed</title><link>http://www.outthere.net</link><description>something cool</description></channel></rss>";

	private String badRSS = "<rss><channel><title>simplest feed</title><link>http://www.outthere.net</link><description>something cool</description></channel></rss>";

	private static Calendar theDate;
	static {
		theDate = Calendar.getInstance();
		theDate.clear();
		theDate.set(2009, 0, 1);
	}

	private RSS rss1;
	private RSSDoc rssDoc;

	/**
	 * @throws Exception
	 *             if there is an issue setting up the data.
	 */
	@Before
	public void setUp() throws Exception {
		rssDoc = new RSSDoc();
		rssDoc.setEncoding("ISO-8859-1");

		// add the indenting stream writer jar to the classpath
		// http://forums.sun.com/thread.jspa?threadID=300557&start=0&tstart=0
		URLClassLoader sysloader = (URLClassLoader) ClassLoader
				.getSystemClassLoader();
		Class<?> sysclass = URLClassLoader.class;
		try {
			Method method = sysclass.getDeclaredMethod("addURL",
					java.net.URL.class);
			method.setAccessible(true);
			method.invoke(sysloader, new Object[] { new java.net.URL(
					"http://ftpna2.bea.com/pub/downloads/jsr173.jar") });
			method.invoke(
					sysloader,
					new Object[] { new java.net.URL(
							"http://repo2.maven.org/maven2/net/java/dev/stax-utils/stax-utils/20060502/stax-utils-20060502.jar") });
		} catch (Throwable t) {
			t.printStackTrace();
			throw new IOException(
					"Error, could not add URL to system classloader");
		}// end try catch

	}

	/**
	 * @throws Exception
	 *             if there is an issue cleaning up the data.
	 */
	@After
	public void tearDown() throws Exception {
		new File("target/out1.xml").delete();
		// new File("out2.xml").delete();
	}

	/**
	 * test the feed doc constructors.
	 */
	@Test
	public void testFeedDocTest() {
		try {
			RSSDoc rss2 = null;
			List<RSSDoc.ProcessingInstruction> insts = new LinkedList<RSSDoc.ProcessingInstruction>();
			insts.add(new RSSDoc().new ProcessingInstruction("xml-stylesheet",
					"href=\"http://www.blogger.com/styles/atom.css\" type=\"text/css\""));
			rss2 = new RSSDoc(insts);
			rss2.setEncoding("ISO-8859-1");
			System.out
					.println("pi before: " + rss2.getProcessingInstructions());
			assertNotNull(rss2);
			String output = rss2
					.readRSSToString(
							rss2.readRSSToBean(new java.net.URL(
									"http://omsa-uchicago.blogspot.com/feeds/posts/default?alt=rss")),
							null);
			assertTrue(output
					.indexOf("<?xml-stylesheet href=\"http://www.blogger.com/styles/atom.css\" type=\"text/css\"?>") != -1);
			// needed for running on windows
			rss2.setEncoding("ISO-8859-1");
			assertNotNull(rss2.readRSSToBean(output));
			try {
				rss2.readRSSToBean(badRSS);
				fail("should not get here.");
			} catch (Exception e) {
				assertTrue(e instanceof RSSpectException);
				assertEquals(e.getMessage(),
						"rss documents must contain the version attribute.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			fail("should not get here.");
		}
	}

	/**
	 * tests writing an rss feed.
	 */
	@Test
	public void testWriteRSSDocOutputStreamRSSStringString() {
		try {
			rss1 = rssDoc.readRSSToBean(new java.net.URL(
					"http://feeds.nytimes.com/nyt/rss/HomePage"));
			rssDoc.writeRSSDoc(new FileOutputStream("target/out1.xml"), rss1,
					rssDoc.getEncoding(), rssDoc.getXmlVersion());
			RSS rss2 = rssDoc.readRSSToBean(new File("target/out1.xml"));
			assertNotNull(rss2);
			assertNotNull(rssDoc.getLibVersion());
			assertNotNull(rss2.getAttributes());
			assertNotNull(rss2.getChannel());
			assertNotNull(rss2.getChannel().toString());
			assertNull(rss2.getExtensions());
		} catch (Exception e) {
			e.printStackTrace();
			fail("could not write output file with file output stream.");
		}

		try {
			rssDoc.writeRSSDoc(new FileOutputStream("target/out1.xml"), null,
					rssDoc.getEncoding(), rssDoc.getXmlVersion());
			fail("should not get here.");
		} catch (Exception e) {
			assertTrue(e instanceof RSSpectException);
			assertEquals(e.getMessage(), "The rss feed object cannot be null.");
		}

		try {
			rssDoc.readRSSToBean("http://www.google.com/fakepage");
			fail("should not get here.");
		} catch (Exception e) {
			assertTrue(e instanceof Exception);
		}
	}

	/**
	 * tests writing an rss feed from an xml stream.
	 */
	@Test
	public void testWriteRSSDocXMLStreamWriterRSSStringString() {

		try {
			// load the stax util classes
			URLClassLoader sysloader = (URLClassLoader) ClassLoader
					.getSystemClassLoader();
			Class<?> classToLoad = sysloader
					.loadClass("javanet.staxutils.IndentingXMLStreamWriter");
			Constructor<?> indentingXMLStreamWriter = classToLoad
					.getDeclaredConstructor(XMLStreamWriter.class);
			XMLStreamWriter writer = (XMLStreamWriter) indentingXMLStreamWriter
					.newInstance(XMLOutputFactory.newInstance()
							.createXMLStreamWriter(
									new FileOutputStream("target/out2.xml"),
									rssDoc.getEncoding()));
			rss1 = rssDoc.readRSSToBean(new java.net.URL(
					"http://feeds.nytimes.com/nyt/rss/HomePage"));
			rssDoc.writeRSSDoc(writer, rss1, null, null);

			rssDoc.writeRSSDoc(writer, null, null, null);
			fail("should not get here.");
		} catch (Exception e) {
			assertTrue(e instanceof RSSpectException);
			assertEquals(e.getMessage(), "The rss feed object cannot be null.");
		}

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
			assertEquals(e.getMessage(), "The rss feed object cannot be null.");
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
			assertEquals(e.getMessage(), "The rss feed object cannot be null.");
		}
	}

	/**
	 * tests reading an rss feed.
	 */
	@Test
	public void testReadRSSToStringRSS() {
		try {
			rss1 = rssDoc.readRSSToBean(expectedRSS1);
			String rss1Str = rss1.toString();
			BufferedWriter fout = new BufferedWriter(new FileWriter(
					"target/rssDoc.xml"));
			fout.write(rss1Str);
			fout.flush();
			fout.close();
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
			rssDoc.readRSSToString(null,
					"javanet.staxutils.IndentingXMLStreamWriter");
			fail("should not get here.");
		} catch (Exception e) {
			assertTrue(e instanceof RSSpectException);
			assertEquals(e.getMessage(), "The rss feed object cannot be null.");

		}

	}

	/**
	 * tests reeding an rss feed to a string.
	 */
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
		} catch (Exception e) {
			assertEquals(e.getMessage(), "ParseError at [row,col]:[1,1]\n"
					+ "Message: Premature end of file.");
		}
	}

	/**
	 * tests reading an rss feed from a file.
	 */
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
			rssDoc.readRSSToBean(new File(""));
			fail("should not get here");
		} catch (Exception e) {
			assertTrue(e instanceof Exception);
		}

		try {
			rssDoc.readRSSToBean(new File("src/test/resources/brokeRSS.xml"));
			fail("should not get here");
		} catch (Exception e) {
			assertTrue(e instanceof Exception);
		}

		try {
			rssDoc.readRSSToBean(new FileInputStream(
					"src/test/resources/brokeRSS.xml"));
			fail("should not get here");
		} catch (Exception e) {
			assertTrue(e instanceof Exception);
		}

		try {
			rssDoc.readRSSToBean(new FileInputStream(
					"src/test/resources/brokeRSS2.xml"));
			fail("should not get here");
		} catch (Exception e) {
			assertTrue(e instanceof Exception);
		}
	}

	/**
	 * tests reading an rss feed from a url.
	 */
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
			e.printStackTrace();
			assertTrue(e instanceof RSSpectException);
			assertEquals(
					e.getMessage(),
					"Extension element 'id' is missing a namespace prefix or namespace declaration.");
		}

		try {
			rss1 = rssDoc.readRSSToBean(expectedRSS9);
			fail("should not get here.");
		} catch (Exception e) {
			assertTrue(e instanceof RSSpectException);
			assertEquals(e.getMessage(),
					"rss documents must contain the version attribute.");
		}

		try {
			rss1 = rssDoc.readRSSToBean(new java.net.URL(
					"http://www.someunknownnonworkingurl.nogood"));
			fail("should not get here.");
		} catch (Exception e) {
			assertTrue(e instanceof java.net.UnknownHostException
					|| e instanceof Exception);
			/*
			 * for some reason hosts behind Comcast (and maybe other) ISPs choke
			 * on this we would like to test for unknown host here because they
			 * return an error document instead of unknown host. :(
			 * assertEquals(e.getMessage(),
			 * "error reading rss feed: java.net.UnknownHostException: www.someunknownnonworkingurl.nogood: www.someunknownnonworkingurl.nogood"
			 * );
			 * 
			 * 
			 * assertTrue(e.getMessage
			 * ().startsWith("www.someunknownnonworkingurl.nogood"));
			 */
		}
	}

	/**
	 * tests reading an rss feed from an input stream.
	 */
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

	/**
	 * tests building an rss feed.
	 */
	@Test
	public void testBuildRSS() {
		try {
			rss1 = rssDoc.readRSSToBean(expectedRSS1);
			assertNotNull(rss1);
			assertNotNull(rss1.toString());
			assertNotNull(rss1.getChannel());
			assertNotNull(rss1.getChannel().toString());
			rssDoc.writeRSSDoc(new File("target/out3.xml"), rss1,
					rssDoc.getEncoding(), rssDoc.getXmlVersion());
		} catch (Exception e) {
			e.printStackTrace();
			fail("should be working. " + e.getLocalizedMessage());
		}

		try {
			rss1 = rssDoc.readRSSToBean(expectedRSS2);
			assertNotNull(rss1);
			assertNotNull(rss1.getChannel());

		} catch (Exception e) {
			e.printStackTrace();
			fail("should be working. " + e.getLocalizedMessage());
		}

		try {
			rss1 = rssDoc.readRSSToBean(expectedRSS10);
			fail("should not get here");
		} catch (Exception e) {
			assertTrue(e instanceof RSSpectException);
			assertTrue(e.getMessage().startsWith(
					"Error trying to parse a date in RFC 822 format for: "));
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
			Channel channel = rssDoc.buildChannel(
					rssDoc.buildTitle("this is a title"),
					rssDoc.buildLink("http://www.minoritydirectory.net"),
					rssDoc.buildDescription("this is a description"), null,
					null, null, null, null, null, null, null, null, null, null,
					null, null, null, null, null, null, null);
			RSS rss = rssDoc.buildRSS(channel, null, null);
			assertNotNull(rss);
			fail("we should have thrown an exception above.");
		} catch (Exception r) {
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
		} catch (Exception r) {
			fail("should not get here.");
		}

		try {
			rssDoc.buildAttribute(null, "null");
			fail("should not get here");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(), "Attribute names SHOULD NOT be blank.");
		}
	}

	/**
	 * tests building an rss attribute.
	 */
	@Test
	public void testBuildAttribute() {
		try {
			rss1 = rssDoc.readRSSToBean(expectedRSS1);
			String rss1Str = rss1.toString();
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

		try {
			rssDoc.buildAttribute(null, null);
			fail("should not get here.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(), "Attribute names SHOULD NOT be blank.");
		}

		try {
			rssDoc.buildAttribute("", null);
			fail("should not get here.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(), "Attribute names SHOULD NOT be blank.");
		}

		try {
			Attribute attr = rssDoc.buildAttribute("yep", null);
			assertNotNull(attr);
		} catch (RSSpectException r) {
			fail("should not get here.");
		}

		try {
			Attribute attr = rssDoc.buildAttribute("yep", "");
			assertNotNull(attr);
			Attribute attr2 = rssDoc.buildAttribute("yep", "");
			assertTrue(attr.equals(attr2));
			attr2 = rssDoc.buildAttribute("yep", null);
			assertTrue(attr.equals(attr2));
		} catch (RSSpectException r) {
			fail("should not get here.");
		}
	}

	/**
	 * tests building an author
	 */
	@Test
	public void testBuildAuthor() {
		try {
			rssDoc.buildAuthor(null);
			fail("should not get here.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(), "Author names SHOULD NOT be blank.");
		}

		try {
			rssDoc.buildAuthor("");
			fail("should not get here.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(), "Author names SHOULD NOT be blank.");
		}

		try {
			Author author = rssDoc.buildAuthor("someone");
			assertNotNull(author);
			assertEquals(author.getAuthor(), "someone");
		} catch (RSSpectException r) {
			fail("should not get here.");
		}

	}

	/**
	 * tests building an copyright
	 */
	@Test
	public void testBuildCopyright() {
		try {
			rssDoc.buildCopyright(null);
			fail("should not get here.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(), "copyright SHOULD NOT be blank.");
		}

		try {
			rssDoc.buildCopyright("");
			fail("should not get here.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(), "copyright SHOULD NOT be blank.");
		}

		try {
			Copyright copyright = rssDoc.buildCopyright("GPL man.");
			assertNotNull(copyright);
			assertEquals(copyright.getCopyright(), "GPL man.");
		} catch (RSSpectException r) {
			fail("should not get here.");
		}

	}

	/**
	 * tests building a day element
	 */
	@Test
	public void testBuildDay() {
		try {
			rssDoc.buildDay(null);
			fail("should not get here.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(), "day SHOULD NOT be blank.");
		}

		try {
			rssDoc.buildDay("");
			fail("should not get here.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(), "day SHOULD NOT be blank.");
		}

		try {
			Day day = rssDoc.buildDay("Thursday");
			assertNotNull(day);
			assertEquals(day.getDay(), "Thursday");
		} catch (RSSpectException r) {
			fail("should not get here.");
		}

	}

	/**
	 * tests building a hour element
	 */
	@Test
	public void testBuildHour() {
		try {
			rssDoc.buildHour(null);
			fail("should not get here.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(), "hour SHOULD NOT be blank.");
		}

		try {
			rssDoc.buildHour("");
			fail("should not get here.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(), "hour SHOULD NOT be blank.");
		}

		try {
			Hour hour = rssDoc.buildHour("22");
			assertNotNull(hour);
			assertEquals(hour.getHour(), "22");
		} catch (RSSpectException r) {
			fail("should not get here.");
		}
	}

	/**
	 * tests building a ttl element
	 */
	@Test
	public void testBuildTTL() {
		try {
			rssDoc.buildTTL(null);
			fail("should not get here.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(), "ttl SHOULD NOT be blank.");
		}

		try {
			rssDoc.buildTTL("");
			fail("should not get here.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(), "ttl SHOULD NOT be blank.");
		}

		try {
			TTL ttl = rssDoc.buildTTL("10000");
			assertNotNull(ttl);
			assertEquals(ttl.getTtl(), "10000");
		} catch (RSSpectException r) {
			fail("should not get here.");
		}
	}

	/**
	 * tests building a title element
	 */
	@Test
	public void testBuildTitle() {
		try {
			rssDoc.buildTitle(null);
			fail("should not get here.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(), "title SHOULD NOT be blank.");
		}

		try {
			rssDoc.buildTitle("");
			fail("should not get here.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(), "title SHOULD NOT be blank.");
		}

		try {
			Title title = rssDoc.buildTitle("the title");
			assertNotNull(title);
			assertEquals(title.getTitle(), "the title");
		} catch (RSSpectException r) {
			fail("should not get here.");
		}
	}

	/**
	 * tests building a webMaster element
	 */
	@Test
	public void testBuildWebMaster() {
		try {
			rssDoc.buildWebMaster(null);
			fail("should not get here.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(), "webMaster SHOULD NOT be blank.");
		}

		try {
			rssDoc.buildWebMaster("");
			fail("should not get here.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(), "webMaster SHOULD NOT be blank.");
		}

		try {
			WebMaster webMaster = rssDoc.buildWebMaster("dude man");
			assertNotNull(webMaster);
			assertEquals(webMaster.getWebMaster(), "dude man");
		} catch (RSSpectException r) {
			fail("should not get here.");
		}
	}

	/**
	 * tests building a name element
	 */
	@Test
	public void testBuildName() {
		try {
			rssDoc.buildName(null);
			fail("should not get here.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(), "name SHOULD NOT be blank.");
		}

		try {
			rssDoc.buildName("");
			fail("should not get here.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(), "name SHOULD NOT be blank.");
		}

		try {
			Name name = rssDoc.buildName("someone special");
			assertNotNull(name);
			assertEquals(name.getName(), "someone special");
		} catch (RSSpectException r) {
			fail("should not get here.");
		}
	}

	/**
	 * tests building a rating element
	 */
	@Test
	public void testBuildRating() {
		try {
			rssDoc.buildRating(null);
			fail("should not get here.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(), "rating SHOULD NOT be blank.");
		}

		try {
			rssDoc.buildRating("");
			fail("should not get here.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(), "rating SHOULD NOT be blank.");
		}

		try {
			Rating rating = rssDoc
					.buildRating("suds 0.5 density 0 color/hue 1");
			assertNotNull(rating);
			assertEquals(rating.getRating(), "suds 0.5 density 0 color/hue 1");
		} catch (RSSpectException r) {
			fail("should not get here.");
		}
	}

	/**
	 * tests building a language element
	 */
	@Test
	public void testBuildLanguage() {
		try {
			rssDoc.buildLanguage(null);
			fail("should not get here.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(), "language SHOULD NOT be blank.");
		}

		try {
			rssDoc.buildLanguage("");
			fail("should not get here.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(), "language SHOULD NOT be blank.");
		}

		try {
			Language language = rssDoc.buildLanguage("en-US");
			assertNotNull(language);
			assertEquals(language.getLanguage(), "en-US");
		} catch (RSSpectException r) {
			fail("should not get here.");
		}
	}

	/**
	 * tests building a docs element
	 */
	@Test
	public void testBuildDocs() {
		try {
			rssDoc.buildDocs(null);
			fail("should not get here.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(), "docs SHOULD NOT be blank.");
		}

		try {
			rssDoc.buildDocs("");
			fail("should not get here.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(), "docs SHOULD NOT be blank.");
		}

		try {
			Docs docs = rssDoc
					.buildDocs("http://www.colorfulsoftware.com/projects/rsspect/api");
			assertNotNull(docs);
			assertEquals(docs.getDocs(),
					"http://www.colorfulsoftware.com/projects/rsspect/api");
		} catch (RSSpectException r) {
			fail("should not get here.");
		}

	}

	/**
	 * tests building a guid element
	 */
	@Test
	public void testBuildGUID() {
		try {
			rssDoc.buildGUID(rssDoc.buildAttribute("isPermaLink", "true"), null);
			fail("should not get here.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(), "guid SHOULD NOT be blank.");
		}

		try {
			rssDoc.buildGUID(rssDoc.buildAttribute("isPermaLink", "true"), "");
			fail("should not get here.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(), "guid SHOULD NOT be blank.");
		}

		try {
			GUID guid = rssDoc.buildGUID(null,
					"http://www.colorfulsoftware.com/projects/rsspect/api");
			assertNotNull(guid);
			assertEquals(guid.getGuid(),
					"http://www.colorfulsoftware.com/projects/rsspect/api");
			assertNull(guid.getIsPermaLink());
			assertNotNull(guid.toString());
		} catch (RSSpectException r) {
			fail("should not get here.");
		}

		try {
			GUID guid = rssDoc.buildGUID(
					rssDoc.buildAttribute("isPermaLink", "true"),
					"http://www.colorfulsoftware.com/projects/rsspect/api");
			assertNotNull(guid);
			assertEquals(guid.getGuid(),
					"http://www.colorfulsoftware.com/projects/rsspect/api");
		} catch (RSSpectException r) {
			fail("should not get here.");
		}

	}

	/**
	 * tests building a generator element
	 */
	@Test
	public void testBuildGenerator() {
		try {
			rssDoc.buildGenerator(null);
			fail("should not get here.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(), "generator SHOULD NOT be blank.");
		}

		try {
			rssDoc.buildGenerator("");
			fail("should not get here.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(), "generator SHOULD NOT be blank.");
		}

		try {
			Generator generator = rssDoc
					.buildGenerator("http://www.colorfulsoftware.com/projects/rsspect/api");
			assertNotNull(generator);
			assertEquals(generator.getGenerator(),
					"http://www.colorfulsoftware.com/projects/rsspect/api");
		} catch (RSSpectException r) {
			fail("should not get here.");
		}

	}

	/**
	 * tests building a category.
	 */
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

	/**
	 * tests building a channel
	 */
	@Test
	public void testBuildChannel() {
		try {
			rss1 = rssDoc.readRSSToBean(expectedRSS2);
			String rss1Str = rss1.toString();
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
				assertNotNull(rssDoc.buildChannel(
						rssDoc.buildTitle("this is a title"),
						rssDoc.buildLink("http://www.minoritydirectory.net"),
						rssDoc.buildDescription("this is a description"), null,
						null, null, null, null, null, null, null, null, null,
						null, null, null, null, null, null, null, null));
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
		} catch (Exception r) {
			fail("should just work.");
		}
	}

	/**
	 * tests building a cloud.
	 */
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

			attrs = new LinkedList<Attribute>();
			attrs.add(rssDoc.buildAttribute("domain", "domain"));
			attrs.add(rssDoc.buildAttribute("port", "port"));
			attrs.add(rssDoc.buildAttribute("path", "path"));
			attrs.add(rssDoc.buildAttribute("registerProcedure",
					"registerProcedure"));
			attrs.add(rssDoc.buildAttribute("protocol", "xml-rpc"));
			assertNotNull(rssDoc.buildCloud(attrs));

		} catch (Exception e) {
			e.printStackTrace();
			fail("should be working. " + e.getLocalizedMessage());
		}
	}

	/**
	 * tests building comments.
	 */
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

		try {
			rssDoc.buildComments(null);
			fail("should not get here.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(), "comments SHOULD NOT be blank.");
		}

		try {
			rssDoc.buildComments("");
			fail("should not get here.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(), "comments SHOULD NOT be blank.");
		}

		try {
			Comments comments = rssDoc.buildComments("these are comments");
			assertNotNull(comments);
			assertEquals(comments.getComments(), "these are comments");
		} catch (RSSpectException r) {
			fail("should not get here.");
		}
	}

	/**
	 * tests building an enclosure.
	 */
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
						"enclosure elements MUST have a url attribute.");
			}
			attrs.add(rssDoc.buildAttribute("url", "http://www.earthbeats.net"));

			try {
				rssDoc.buildEnclosure(attrs);
				fail("we should have thrown an exception above.");
			} catch (RSSpectException r) {
				assertEquals(r.getMessage(),
						"enclosure elements MUST have a length attribute.");
			}
			attrs.add(rssDoc.buildAttribute("length", "1234567"));

			try {
				rssDoc.buildEnclosure(attrs);
				fail("we should have thrown an exception above.");
			} catch (RSSpectException r) {
				assertEquals(r.getMessage(),
						"enclosure elements MUST have a type attribute.");
			}
			attrs.add(rssDoc.buildAttribute("type", "media/flac"));

			assertNotNull(rssDoc.buildEnclosure(attrs));

		} catch (Exception e) {
			e.printStackTrace();
			fail("should be working. " + e.getLocalizedMessage());
		}
	}

	/**
	 * tests building an extension.
	 */
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
			assertEquals(extOne.getAttribute("type").toString(),
					" type=\"application/rss+xml\"");
			assertNull(extOne.getAttribute("Bunky"));
			assertNull(rss1.getChannel().getExtension("Bunky"));
			rssDoc.buildExtension(null, null, "Bunky");
			fail("should not get here.");
		} catch (Exception r) {
			assertTrue(r instanceof RSSpectException);
			assertEquals(
					r.getMessage(),
					"Extension element '"
							+ null
							+ "' is missing a namespace prefix or namespace declaration.");
		}

		try {
			// for testing extension element sub elements.
			rss1 = rssDoc.readRSSToBean(expectedRSS8);
			assertNotNull(rss1);
		} catch (Exception r) {
			fail("should not get here.");
		}

		try {
			rss1 = rssDoc.readRSSToBean(expectedRSS2);
			Channel channel = rss1.getChannel();
			List<Extension> extns = channel.getExtensions();
			if (extns == null) {
				extns = new LinkedList<Extension>();
			}
			// test a bound extension prefix.
			List<Attribute> attrs = new LinkedList<Attribute>();
			attrs.add(rssDoc.buildAttribute("xmlns:test", "http://12345.com"));
			extns.add(rssDoc.buildExtension("test:ext", attrs,
					"I am a bound extension element"));
			channel = rssDoc.buildChannel(channel.getTitle(),
					channel.getLink(), channel.getDescription(),
					channel.getLanguage(), channel.getCopyright(),
					channel.getManagingEditor(), channel.getWebMaster(),
					channel.getPubDate(), channel.getLastBuildDate(),
					channel.getCategories(), channel.getGenerator(),
					channel.getDocs(), channel.getCloud(), channel.getTtl(),
					channel.getImage(), channel.getRating(),
					channel.getTextInput(), channel.getSkipHours(),
					channel.getSkipDays(), extns, channel.getItems());
			assertNotNull(rssDoc.buildRSS(channel, rss1.getAttributes(),
					rss1.getExtensions()));

			// test an unbound extension prefix at the channel level.
			extns = new LinkedList<Extension>();
			extns.add(rssDoc.buildExtension("test:ext", null,
					"I am an unbound extension element"));
			channel = rssDoc.buildChannel(channel.getTitle(),
					channel.getLink(), channel.getDescription(),
					channel.getLanguage(), channel.getCopyright(),
					channel.getManagingEditor(), channel.getWebMaster(),
					channel.getPubDate(), channel.getLastBuildDate(),
					channel.getCategories(), channel.getGenerator(),
					channel.getDocs(), channel.getCloud(), channel.getTtl(),
					channel.getImage(), channel.getRating(),
					channel.getTextInput(), channel.getSkipHours(),
					channel.getSkipDays(), extns, channel.getItems());
			rssDoc.buildRSS(channel, rss1.getAttributes(), rss1.getExtensions());
			fail("should not get here");
		} catch (Exception e) {
			assertTrue(e instanceof RSSpectException);
			assertTrue(e
					.getMessage()
					.equals("the following extension prefix(es) ( test ) are not bound to a namespace declaration. See http://www.w3.org/TR/1999/REC-xml-names-19990114/#ns-decl."));
		}

		try {
			// test an unbound extension prefix at the rss level.
			rss1 = rssDoc.readRSSToBean(expectedRSS2);
			List<Extension> extns = rss1.getExtensions();
			if (extns == null) {
				extns = new LinkedList<Extension>();
			}

			extns.add(rssDoc.buildExtension("test:ext", null,
					"I am an unbound extension element"));
			rssDoc.buildRSS(rss1.getChannel(), rss1.getAttributes(), extns);
			fail("should not get here");

		} catch (Exception e) {
			assertTrue(e instanceof RSSpectException);
			assertTrue(e
					.getMessage()
					.equals("the following extension prefix(es) ( test ) are not bound to a namespace declaration. See http://www.w3.org/TR/1999/REC-xml-names-19990114/#ns-decl."));
		}
	}

	/**
	 * tests building a height object.
	 */
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

		try {
			rssDoc.buildHeight(null);
			fail("should not get here.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(), "height SHOULD NOT be blank.");
		}

		try {
			rssDoc.buildHeight("");
			fail("should not get here.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(), "height SHOULD NOT be blank.");
		}

		try {
			Height height = rssDoc.buildHeight("400");
			assertNotNull(height);
			assertEquals(height.getHeight(), "400");
		} catch (RSSpectException r) {
			fail("should not get here.");
		}
	}

	/**
	 * tests builidng an image object.
	 */
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

	/**
	 * tests building an item object.
	 */
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
				assertNotNull(item.getAuthor().toString());
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
					assertEquals(
							cat.toString(),
							"<category domain=\"http://www.nytimes.com/namespaces/keywords/des\" >Subprime Mortgage Crisis</category>");
				}
			}

			try {
				Item item = rssDoc.buildItem(null, null,
						rssDoc.buildDescription("something cool"), null, null,
						null, null, null, null, null, null);
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
			fail("should just work.");
		}
	}

	/**
	 * tests building the last build date.
	 */
	@Test
	public void testBuildLastBuildDate() {
		try {
			rssDoc.buildLastBuildDate(null);
			fail("should not fail here.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(),
					"the date for this element SHOULD NOT be blank.");
		}

		try {
			LastBuildDate lastBuildDate = rssDoc.buildLastBuildDate(Calendar
					.getInstance().getTime().toString());
			assertNotNull(lastBuildDate);
			assertNotNull(lastBuildDate.getDateTime());
			assertNotNull(lastBuildDate.getText());

			SimpleDateFormat sdf = new SimpleDateFormat(
					"EEE, dd MMM yyyy HH:mm:ss Z");
			assertNotNull(rssDoc.buildLastBuildDate(sdf.format(Calendar
					.getInstance().getTime())));
			sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss Z");
			assertNotNull(rssDoc.buildLastBuildDate(sdf.format(Calendar
					.getInstance().getTime())));
			sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm Z");
			assertNotNull(rssDoc.buildLastBuildDate(sdf.format(Calendar
					.getInstance().getTime())));
			sdf = new SimpleDateFormat("dd MMM yyyy HH:mm Z");
			assertNotNull(rssDoc.buildLastBuildDate(sdf.format(Calendar
					.getInstance().getTime())));
			sdf = new SimpleDateFormat("EEE, dd MMM yy HH:mm:ss Z");
			assertNotNull(rssDoc.buildLastBuildDate(sdf.format(Calendar
					.getInstance().getTime())));
			sdf = new SimpleDateFormat("dd MMM yy HH:mm:ss Z");
			assertNotNull(rssDoc.buildLastBuildDate(sdf.format(Calendar
					.getInstance().getTime())));
			sdf = new SimpleDateFormat("EEE, dd MMM yy HH:mm Z");
			assertNotNull(rssDoc.buildLastBuildDate(sdf.format(Calendar
					.getInstance().getTime())));
			sdf = new SimpleDateFormat("dd MMM yy HH:mm Z");
			assertNotNull(rssDoc.buildLastBuildDate(sdf.format(Calendar
					.getInstance().getTime())));

		} catch (RSSpectException r) {
			r.printStackTrace();
			fail("should not fail here.");
		}
	}

	/**
	 * tests building a link element.
	 */
	@Test
	public void testBuildLink() {
		try {
			rssDoc.buildLink(null);
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(), "link SHOULD NOT be blank.");
		}

		try {
			rssDoc.buildLink("");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(), "link SHOULD NOT be blank.");
		}

		try {
			Link link = rssDoc.buildLink(" ");
			assertNotNull(link);
			fail("should not fail here.");
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
			fail("should not fail here.");
		} catch (RSSpectException r) {
			assertEquals(
					r.getMessage(),
					"link elements must start with a valid "
							+ "Uniform Resource Identifer (URI) Schemes.  "
							+ "See http://www.iana.org. Yours started with: 'abcScheme://testMe'");
		}
	}

	/**
	 * tests building a managing editor.
	 */
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

		try {
			rssDoc.buildManagingEditor(null);
			fail("should not get here.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(), "managingEditor SHOULD NOT be blank.");
		}

		try {
			rssDoc.buildManagingEditor("");
			fail("should not get here.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(), "managingEditor SHOULD NOT be blank.");
		}

		try {
			ManagingEditor managingEditor = rssDoc.buildManagingEditor("you");
			assertNotNull(managingEditor);
			assertEquals(managingEditor.getManagingEditor(), "you");
		} catch (RSSpectException r) {
			fail("should not get here.");
		}

	}

	/**
	 * tests building a published date.
	 */
	@Test
	public void testBuildPubDate() {
		try {
			rssDoc.buildPubDate(null);
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(),
					"the date for this element SHOULD NOT be blank.");
		}

		try {
			PubDate pubDate = rssDoc.buildPubDate(Calendar.getInstance()
					.getTime().toString());
			assertNotNull(pubDate);
			assertNotNull(pubDate.getDateTime());
			assertNotNull(pubDate.getText());
		} catch (RSSpectException r) {
			r.printStackTrace();
			fail("should not fail here.");
		}
	}

	/**
	 * tests building a skip days object.
	 */
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

		try {
			rss1 = rssDoc.readRSSToBean(expectedRSS1);
			SkipDays sd = rss1.getChannel().getSkipDays();
			assertNotNull(sd);
			assertNotNull(sd.getSkipDay("Monday"));
			assertEquals(sd.getSkipDay("Monday").getDay(), "Monday");
			assertNull(sd.getSkipDay("Bunky"));
		} catch (Exception e) {
			fail("should not fail here.");
		}
	}

	/**
	 * tests building a skip hours object.
	 */
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
			hours.add(rssDoc.buildHour("-2"));
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

		try {
			rss1 = rssDoc.readRSSToBean(expectedRSS1);
			SkipHours sh = rss1.getChannel().getSkipHours();
			assertNotNull(sh);
			assertNotNull(sh.getSkipHour("12"));
			assertEquals(sh.getSkipHour("12").getHour(), "12");
			assertNull(sh.getSkipHour("100"));
		} catch (Exception e) {
			fail("should not fail here.");
		}
	}

	/**
	 * tests building a source element.
	 */
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
			Source source = rssDoc.buildSource(
					rssDoc.buildAttribute("cat", "dog"), "somewhere cool");
			assertNotNull(source);
			fail("we should have thrown an exception above.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(),
					"source elements MUST contain a url attribute.");
		}

		try {
			Source source = rssDoc.buildSource(
					rssDoc.buildAttribute("url", "http://www.earthbeats.net"),
					"somewhere cool");
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

		try {
			rssDoc.buildSource(rssDoc.buildAttribute("url",
					"http://www.colorfulsoftware.com"), null);
			fail("should not get here.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(), "source SHOULD NOT be blank.");
		}

		try {
			rssDoc.buildSource(rssDoc.buildAttribute("url",
					"http://www.colorfulsoftware.com"), "");
			fail("should not get here.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(), "source SHOULD NOT be blank.");
		}

		try {
			Source source = rssDoc.buildSource(rssDoc.buildAttribute("url",
					"http://www.colorfulsoftware.com"), "Colorful Software");
			assertNotNull(source);
			assertEquals(source.getSource(), "Colorful Software");
		} catch (RSSpectException r) {
			fail("should not get here.");
		}
	}

	/**
	 * tests building a text input element.
	 */
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
			assertNotNull(rssDoc.buildDescription(null));
		} catch (RSSpectException r) {
			fail("should not get here.");
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

	/**
	 * tests building a url.
	 */
	@Test
	public void testBuildURL() {
		try {
			rssDoc.buildURL(null);
			fail("we should have thrown an exception above.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(), "url SHOULD NOT be blank.");
		}

		try {
			rssDoc.buildURL("");
			fail("we should have thrown an exception above.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(), "url SHOULD NOT be blank.");
		}

		try {
			rssDoc.buildURL(" ");
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

	/**
	 * tests building a width element.
	 */
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

		try {
			rssDoc.buildWidth(null);
			fail("should not get here.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(), "width SHOULD NOT be blank.");
		}

		try {
			rssDoc.buildWidth("");
			fail("should not get here.");
		} catch (RSSpectException r) {
			assertEquals(r.getMessage(), "width SHOULD NOT be blank.");
		}

		try {
			Width width = rssDoc.buildWidth("144");
			assertNotNull(width);
			assertEquals(width.getWidth(), "144");
		} catch (RSSpectException r) {
			fail("should not get here.");
		}
	}
}
