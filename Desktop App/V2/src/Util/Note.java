package Util;


import java.io.Serializable;

import org.parse4j.ParseClassName;
import org.parse4j.ParseObject;


@ParseClassName("Note")
public class Note extends ParseObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public boolean status;

	public String getTitle() {
        return getString("title");
    }

    public void setTitle(String value) {
        put("title", value);
    }

    public String getBody() {
        return getString("body");
    }

    public void setBody(String value) {
        put("body", value);
    }

    public String getID() {
        return getString("ID");
    }

    public void setID(String value) {
        put("ID", value);
    }

    public String getOwner() {
        return getString("ownerID");
    }

    public void setOwner(String value) {
        put("ownerID", value);
    }
    
    public boolean getStatus() {
    	return status;
    }
    
    public void setStatus(boolean status) {
    	this.status = status;
    }
}