package com.zeshan.syncnotes;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {

	public Connection Database() {
		Connection c = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:notes.db");
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
		
		return c;
	}

	
}
