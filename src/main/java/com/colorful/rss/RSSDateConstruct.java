package com.colorful.rss;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    RSSDateConstruct(Date dateTime){
    	
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
    	//example Sun, 19 May 2002 15:21:36 GMT
        return new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z").format(dateTime);
    }

}
