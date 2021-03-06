package com.aslam.zeshan.syncnotes;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.io.Serializable;


@ParseClassName("Note")
public class Note extends ParseObject implements Serializable {

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
}
