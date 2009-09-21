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
package com.colorfulsoftware.rss;

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
