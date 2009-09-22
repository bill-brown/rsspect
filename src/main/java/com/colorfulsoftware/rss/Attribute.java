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


public class Attribute implements Serializable {
    
	private static final long serialVersionUID = -3880416791234118400L;
	private final String name;
    private final String value;
    
    //use the factory method in the RSSDoc.
    Attribute(String name, String value){
        this.name = name;
        this.value = value;
    }
    
    /**
     * 
     * @return the name of this attribute
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @return the value of this attribute
     */
    public String getValue() {
        return value;
    }    
    
    @Override
    public boolean equals(Object obj) {
    	if(obj instanceof Attribute){   		
    		Attribute local = (Attribute)obj;
    		if(local.name != null && local.value != null){
    			return local.name.equals(this.name)
    				&& local.value.equals(this.value);
    		}
    	}
    	return false;
    }
}