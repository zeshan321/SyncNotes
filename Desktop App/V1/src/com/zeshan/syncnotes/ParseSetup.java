package com.zeshan.syncnotes;

import org.parse4j.Parse;
import org.parse4j.util.ParseRegistry;

public class ParseSetup {

	public void initializeParse() {
		ParseRegistry.registerSubclass(Note.class);
		Parse.initialize("iqnAuoS1KihbbhQBvfjlu0wFlVvPSTukN8J8ncpz", "uaZc6oGzjaFOnoUfqxqIsp6UnanOlBOfyHRmqPac");
	}
}
