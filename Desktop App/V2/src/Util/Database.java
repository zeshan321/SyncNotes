package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Database {
	
    String TABLE_CONTACTS = "NotesList";
    String KEY_ID = "ID";
    String KEY_OBJECT = "objectID";
    String KEY_NAME = "title";
    String KEY_EMAIL = "body";
    String KEY_LOCX = "locX";
    String KEY_LOCY = "locY";
    String KEY_STATUS = "status";

	public Database() {
		Connection c = null;
		Statement statement = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:notes.db");
			
			statement = c.createStatement();
			
			String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS + "("
	                + KEY_ID + " STRING PRIMARY KEY, objectID " + KEY_OBJECT + ", " + KEY_NAME + " TEXT," + KEY_EMAIL + " TEXT," + KEY_LOCX + " DOUBLE," + KEY_LOCY + " DOUBLE, " + KEY_STATUS + " BOOLEAN)";
			
			statement.execute(CREATE_CONTACTS_TABLE);
			statement.close();
			c.close();
		} catch ( SQLException | ClassNotFoundException e ) {
			e.printStackTrace();
		}
	}
	
	public void addNote(Note note) {
		Connection c = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:notes.db");
			
			PreparedStatement statement = c.prepareStatement(
		            "INSERT INTO " + TABLE_CONTACTS + " (ID,objectID,title,body,locX,locY,status) VALUES(?,?,?,?,?,?,?)");
			
		        statement.setString(1, note.getID());
		        statement.setString(2, note.getObjectId());
		        statement.setString(3, note.getTitle());
		        statement.setString(4, note.getBody());
		        statement.setDouble(5, 0);
		        statement.setDouble(6, 0);
		        statement.setBoolean(7, false);
		        statement.execute();

		        c.close();
		} catch ( SQLException | ClassNotFoundException e ) {
			e.printStackTrace();
		}
	}

	public Note getNote(String ID) {
		Connection c = null;
		Statement statement = null;
		Note note = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:notes.db");
			
			statement = c.createStatement();
			
			ResultSet rs = statement.executeQuery( "SELECT * FROM " + TABLE_CONTACTS);
		      while ( rs.next() ) {
		    	  if (rs.getString("ID").equals(ID)) {
		    		  note = new Note();
		    		  note.setID(ID);
		    		  note.setObjectId(rs.getString("objectID"));
		    		  note.setTitle(rs.getString("title"));
		    		  note.setBody(rs.getString("body"));
		    		  break;
		    	  }
		      }
			
			statement.close();
			rs.close();
			c.close();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		
		return note;
	}
	
	public List<Note> getNoteList() {
		Connection c = null;
		Statement statement = null;
		List<Note> list = new ArrayList<Note>();
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:notes.db");
			
			statement = c.createStatement();
			
			ResultSet rs = statement.executeQuery( "SELECT * FROM " + TABLE_CONTACTS);
		      while ( rs.next() ) {
		    		  Note note = new Note();
		    		  note.setID(rs.getString("ID"));
		    		  note.setObjectId(rs.getString("objectID"));
		    		  note.setTitle(rs.getString("title"));
		    		  note.setBody(rs.getString("body"));
		    		  note.setStatus(rs.getBoolean("status"));
		    		  
		    		  list.add(note);
		      }
			
			statement.close();
			rs.close();
			c.close();
		} catch ( SQLException | ClassNotFoundException e ) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public boolean contains(String ID) {
		Connection c = null;
		Statement statement = null;
		boolean exists = false;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:notes.db");
			
			statement = c.createStatement();
			
			ResultSet rs = statement.executeQuery( "SELECT * FROM " + TABLE_CONTACTS);
		      while ( rs.next() ) {
		    		  if (rs.getString("ID").equals(ID)) {
		    			  exists = true;
		    		  }
		      }
			
			statement.close();
			rs.close();
			c.close();
		} catch ( SQLException | ClassNotFoundException e ) {
			e.printStackTrace();
		}
		
		return exists;
	}
	
	public void update(Note note, double x, double y, boolean status) {
		Connection c = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:notes.db");
			
			PreparedStatement statement = c.prepareStatement(
		            "UPDATE " + TABLE_CONTACTS + " SET objectID = ?, title = ?, body = ?, locX = ?,  locY = ?, status = ? WHERE ID = ?;");
			
				statement.setString(1, note.getObjectId());
		        statement.setString(2, note.getTitle());
		        statement.setString(3, note.getBody());
		        statement.setDouble(4, x);
		        statement.setDouble(5, y);
		        statement.setBoolean(6, status);
		        statement.setString(7, note.getID());
		        statement.executeUpdate();

		        c.close();
		} catch ( SQLException | ClassNotFoundException e ) {
			e.printStackTrace();
		}
	}
	
	public Location getLoc(String ID) {
		Location loc = null;
		Connection c = null;
		Statement statement = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:notes.db");
			
			statement = c.createStatement();
			
			ResultSet rs = statement.executeQuery( "SELECT * FROM " + TABLE_CONTACTS);
		      while ( rs.next() ) {
		    	  if (rs.getString("ID").equals(ID)) {
		    		  loc = new Location(rs.getDouble("locX"), rs.getDouble("locY"));
		    		  break;
		    	  }
		      }
			
			statement.close();
			rs.close();
			c.close();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		
		return loc;
	}
}