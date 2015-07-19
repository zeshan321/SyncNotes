package com.aslam.zeshan.syncnotes;

import android.content.Context;

import com.aslam.zeshan.syncnotes.Adapter.NoteListHandler;
import com.aslam.zeshan.syncnotes.Database.NotesDatabase;
import com.aslam.zeshan.syncnotes.Util.SettingsManager;
import com.parse.FindCallback;
import com.parse.ParseQuery;

import java.util.List;

public class NotesUpdater {

    Context con;

    public NotesUpdater(Context con) {
        this.con = con;
    }

    public void runUpdater() {
        final NotesDatabase notesDatabase = new NotesDatabase(con);
        final ParseQuery<Note> query = ParseQuery.getQuery("Note");

        query.whereEqualTo("ownerID", new SettingsManager(con).getString("ID"));
        query.findInBackground(new FindCallback<Note>() {
            public void done(List<Note> parseObjects, com.parse.ParseException e) {
                if (e == null) {
                    for (Note note : parseObjects) {
                        if (note != null) {
                            String ID = note.getID();
                            String objectID = note.getObjectId();
                            String title = note.getTitle();
                            String body = note.getBody();

                            if (notesDatabase.contains(ID)) {

                                notesDatabase.update(ID, title, body);

                                Note note1 = NoteListHandler.emailsArrayAdapater.getByID(ID);
                                if (note1 != null) {
                                    note1.setTitle(title);
                                    note1.setBody(body);
                                }
                            } else {
                                NoteListHandler noteListHandler = new NoteListHandler(con);
                                noteListHandler.add(note);

                                notesDatabase.addNote(ID, objectID, title, body);
                            }
                        }
                    }

                    // Update ListView
                    NoteListHandler.emailsArrayAdapater.notifyDataSetChanged();
                }
            }
        });
    }
}
