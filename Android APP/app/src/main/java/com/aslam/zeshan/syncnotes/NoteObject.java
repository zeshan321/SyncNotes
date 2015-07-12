package com.aslam.zeshan.syncnotes;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("NoteObject")
public class NoteObject extends ParseObject {

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

    public int getID() {
        return getInt("ID");
    }

    public void setID(int value) {
        put("ID", value);
    }
}
