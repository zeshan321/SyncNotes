package com.aslam.zeshan.syncnotes.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.aslam.zeshan.syncnotes.Note;
import com.aslam.zeshan.syncnotes.Util.SettingsManager;

import java.util.ArrayList;
import java.util.List;


public class NotesDatabase extends SQLiteOpenHelper {

    public Context con;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Notes";
    private static final String TABLE_CONTACTS = "NotesList";
    private static final String KEY_ID = "ID";
    private static final String KEY_OBJECT = "objectID";
    private static final String KEY_NAME = "title";
    private static final String KEY_EMAIL = "body";

    public NotesDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.con = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " STRING PRIMARY KEY," + KEY_OBJECT + " TEXT," + KEY_NAME + " TEXT," + KEY_EMAIL + " TEXT)";

        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void addNote(String ID, String objectID, String title, String body) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, ID);
        values.put(KEY_OBJECT, objectID);
        values.put(KEY_NAME, title);
        values.put(KEY_EMAIL, body);

        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }

    public void deleteNote(String ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, "ID = ?", new String[]{ID});
        db.close();
    }

    public void update(String ID, String title, String body) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, title);
        values.put(KEY_EMAIL, body);

        db.update(TABLE_CONTACTS, values, "ID=?", new String[]{ID});
        db.close();
    }

    public List<Note> getNotes() {
        List<Note> map = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM " + TABLE_CONTACTS;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor != null && cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    String ID = cursor.getString(cursor.getColumnIndex(KEY_ID));
                    String objectID = cursor.getString(cursor.getColumnIndex(KEY_OBJECT));
                    String title = cursor.getString(cursor.getColumnIndex(KEY_NAME));
                    String body = cursor.getString(cursor.getColumnIndex(KEY_EMAIL));

                    Note note = new Note();
                    note.setID(ID);
                    note.setObjectId(objectID);
                    note.setTitle(title);
                    note.setBody(body);
                    note.setOwner(new SettingsManager(con).getString("ID"));

                    map.add(note);
                    cursor.moveToNext();
                }
            }

            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
            return map;
        }
        return map;

    }

    public Note getNoteByID(String ID) {
        Note note = null;
        try {
            String selectQuery = "SELECT * FROM " + TABLE_CONTACTS;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor != null && cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    String ID1 = cursor.getString(cursor.getColumnIndex(KEY_ID));
                    String objectID = cursor.getString(cursor.getColumnIndex(KEY_OBJECT));
                    String title = cursor.getString(cursor.getColumnIndex(KEY_NAME));
                    String body = cursor.getString(cursor.getColumnIndex(KEY_EMAIL));
                    if (ID.equals(ID1)) {
                        note = new Note();
                        note.setID(ID);
                        note.setObjectId(objectID);
                        note.setTitle(title);
                        note.setBody(body);
                        note.setOwner(new SettingsManager(con).getString("ID"));
                        break;
                    }
                    cursor.moveToNext();
                }
            }

            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return note;

    }

    public int lastID() {
        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToLast();

        int ID = cursor.getInt(cursor.getColumnIndex("ID"));

        cursor.close();
        db.close();
        return ID;
    }

    public boolean contains(String ID) {
        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS + " WHERE  " + KEY_ID + " = ?";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{ID});

        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }

        cursor.close();
        return false;
    }
}
