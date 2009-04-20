package com.colorful.rss;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

class RSSDateConstruct implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 661002136563928416L;
	
	private final Date dateTime;
    
    /**
     * 
     * @param updated the date formatted to [RFC3339]
     */
    public RSSDateConstruct(Date dateTime){
    	
    	if(dateTime == null){
    		this.dateTime = null;
    	}else{
    		this.dateTime = new Date(dateTime.getTime());
    	}
    }
    
    /**
     * 
     * @return the date timestamp for this element.
     */
    protected Date getDateTime(){
    	return (dateTime == null)? null: new Date(dateTime.getTime());
    }
    
    /**
     * 
     * @return the string formated version of the time
     * 	for example 2006-04-28T12:50:43.337-05:00
     */
    public String getText() {
    	if(dateTime == null){
    		return null;
    	}
    	//example 2006-04-28T12:50:43.337-05:00
    	final String timeZoneOffset;
    	TimeZone timeZone = TimeZone.getDefault();
        int hours = (((timeZone.getRawOffset()/1000)/60)/60);
        if(hours >= 0){
            timeZoneOffset = TimeZone.getTimeZone("GMT"+"+"+hours).getID().substring(3);
        }else{
            timeZoneOffset = TimeZone.getTimeZone("GMT"+"-"+Math.abs(hours)).getID().substring(3);
        }
        return new SimpleDateFormat("yyyy-MM-dd\'T\'HH:mm:ss.SS\'"+timeZoneOffset+"\'").format(dateTime);
    }

}
