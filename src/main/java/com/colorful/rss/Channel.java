package com.colorful.rss;

import java.io.Serializable;
import java.util.List;
import java.util.SortedMap;


public class Channel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6193654133883709775L;
	/* required fields */
	private final Title title; 
	private final Link link; 
	private final Description description; 
	/* optional fields */
	private final Language language;
	private final Copyright copyright;
	private final ManagingEditor managingEditor;
	private final WebMaster webMaster;
	private final PubDate pubDate;
	private final LastBuildDate lastBuildDate;
	private final Category category;
	private final Generator generator;
	private final Docs docs;
	private final Cloud cloud;
	private final TTL ttl;
	private final Image image;
	private final Rating rating;
	private final TextInput textInput;
	private final SkipHours skipHours;
	private final SkipDays skipDays;
	
	
	Channel(Title title
			,Link link
			,Description description
			,Language language
			,Copyright copyright
			,ManagingEditor managingEditor
			,WebMaster webMaster
			,PubDate pubDate
			,LastBuildDate lastBuildDate
			,Category category
			,Generator generator
			,Docs docs
			,Cloud cloud
			,TTL ttl
			,Image image
			,Rating rating
			,TextInput textInput
			,SkipHours skipHours
			,SkipDays skipDays) throws RSSpectException {
		
		//make sure id is present
        if(title == null){
            throw new RSSpectException("channel elements MUST contain a title element.");
        }
        this.title = title;
        
        //make sure title is present
        if(link == null){
            throw new RSSpectException("channel elements MUST contain a link element.");
        }
        this.link = link;
        
        //make sure updated is present
        if(description == null){
            throw new RSSpectException("channel elements MUST contain a description element.");
        }
        this.description = description;
        
	}


	public Title getTitle() {
		return new Title(title.getText());
	}


	public Link getLink() {
		return link;
	}


	public Description getDescription() {
		return description;
	}
	
}
