package com.aslam.zeshan.syncnotes.Adapter;

import android.app.Activity;
import android.content.Context;
import android.widget.ListView;

import com.aslam.zeshan.syncnotes.Database.NotesDatabase;
import com.aslam.zeshan.syncnotes.NoteObject;
import com.aslam.zeshan.syncnotes.R;

public class NoteListHandler  {

    public static NoteArrayAdapter emailsArrayAdapater;
    public static ListView listView;

    Context con;

    public NoteListHandler(Context con) {
        this.con = con;
    }

    public void initialSetup(boolean type) {
        if (!type) {
            emailsArrayAdapater = new NoteArrayAdapter(con, R.layout.note_list_view);
            listView = (ListView) ((Activity) con).findViewById(R.id.notesList);
        } else {
            emailsArrayAdapater = new NoteArrayAdapter(con, R.layout.note_list_view);
            listView = (ListView) ((Activity) con).findViewById(R.id.notesList);
        }

        NotesDatabase notesDatabase = new NotesDatabase(con);

        for (NoteObject noteObject : notesDatabase.getNotes()) {
            emailsArrayAdapater.add(noteObject);
        }

        listView.setAdapter(emailsArrayAdapater);
    }

    public void add(NoteObject noteObject) {
        emailsArrayAdapater.add(noteObject);

        emailsArrayAdapater.notifyDataSetChanged();
    }

}
