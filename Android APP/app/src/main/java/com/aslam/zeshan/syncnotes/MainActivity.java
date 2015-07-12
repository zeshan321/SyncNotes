package com.aslam.zeshan.syncnotes;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.aslam.zeshan.syncnotes.Adapter.NoteListHandler;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;


public class MainActivity extends ActionBarActivity {

    SearchView searchView;
    Context con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        con = this;

        // Update database
        new NotesUpdater(this).runUpdater();

        // List view setup
        new NoteListHandler(this).initialSetup(false);

        // Create note button
        final FloatingActionButton createNote = (FloatingActionButton) findViewById(R.id.createNote);
        createNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(con, NoteActivity.class);
                intent.putExtra("newNote", true);

                con.startActivity(intent);
            }
        });

        // On select
        ListView listView = NoteListHandler.listView;

        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Note note = NoteListHandler.emailsArrayAdapater.getItem(position);

                Intent intent = new Intent(con, NoteActivity.class);
                intent.putExtra("newNote", false);
                intent.putExtra("ID", note.getID());

                con.startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_main, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.setIconified(true);
                NoteListHandler.emailsArrayAdapater.restoreList();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    searchView.setIconified(true);
                    NoteListHandler.emailsArrayAdapater.restoreList();
                    return false;
                }

                NoteListHandler.emailsArrayAdapater.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
        } else {
            super.onBackPressed();
        }
    }
}
