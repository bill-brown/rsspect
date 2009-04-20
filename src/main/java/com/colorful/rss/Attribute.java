
package com.colorful.rss;


public class Attribute {
    
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
