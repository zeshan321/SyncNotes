package com.aslam.zeshan.syncnotes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import com.aslam.zeshan.syncnotes.Adapter.NoteListHandler;
import com.aslam.zeshan.syncnotes.Database.NotesDatabase;
import com.aslam.zeshan.syncnotes.Util.SettingsManager;
import com.parse.ParseException;
import com.parse.SaveCallback;

import java.util.UUID;

public class NoteActivity extends ActionBarActivity {

    Note note = new Note();
    Context con;

    String ID = null;

    TextView title;
    TextView body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        con = this;
        title = (TextView) findViewById(R.id.noteTitle);
        body = (TextView) findViewById(R.id.noteBody);

        Intent intent = getIntent();
        if (!intent.getBooleanExtra("newNote", true)) {
            ID = intent.getStringExtra("ID");
            note = new NotesDatabase(con).getNoteByID(ID);

            title.setText(note.getTitle());
            body.setText(note.getBody());
        }

        if (ID == null) {
            ID = String.valueOf(UUID.randomUUID());
        }


        // Show back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                saveData();
                System.out.println("SAVING 1");

                Intent intent = new Intent(con, MainActivity.class);
                con.startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause() {
        saveData();

        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void saveData() {
        // Save to parse
        note.setID(ID);
        note.setOwner(new SettingsManager(con).getString("ID"));
        note.setTitle(title.getText().toString());
        note.setBody(body.getText().toString());

        note.saveEventually();

        note.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    // Save to client database
                    NotesDatabase notesDatabase = new NotesDatabase(con);
                    if (notesDatabase.contains(ID))

                    {
                        notesDatabase.update(ID, title.getText().toString(), body.getText().toString());

                        Note updateNote = NoteListHandler.emailsArrayAdapater.getByID(ID);
                        updateNote.setTitle(title.getText().toString());
                        updateNote.setBody(body.getText().toString());
                    } else

                    {
                        notesDatabase.addNote(ID, note.getObjectId(), title.getText().toString(), body.getText().toString());

                        NoteListHandler noteListHandler = new NoteListHandler(con);
                        noteListHandler.add(note);
                    }

                    NoteListHandler.emailsArrayAdapater.notifyDataSetChanged();
                }
            }
        });
    }
}
