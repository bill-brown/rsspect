package com.colorful.rss;

import java.io.Serializable;
import java.util.Date;

/**
 * The last time the content of the channel changed.
 * 
 * @author Bill Brown
 *
 */
public class LastBuildDate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8692371191911347659L;

private final RSSDateConstruct lastBuildDate;
	
	public LastBuildDate(Date lastBuildDate) {
		this.lastBuildDate = new RSSDateConstruct(lastBuildDate);
	}

	/**
     * 
     * @return the date timestamp for this element.
     */
    protected Date getDateTime(){
    	return lastBuildDate.getDateTime();
    }
    
    /**
     * 
     * @return the string formated version of the time
     * 	for example 2006-04-28T12:50:43.337-05:00
     */
    public String getText() {
    	return lastBuildDate.getText();
    }
}
