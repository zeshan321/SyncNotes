package Util;

import java.util.List;

import org.parse4j.ParseException;
import org.parse4j.ParseQuery;
import org.parse4j.callback.FindCallback;

import application.NoteViewController;
import application.NotesList;
import javafx.application.Platform;

public class AutoUpdater extends Thread {

	public static boolean running = true;

	@Override
	public void run() {
		while (running) {
			try {
				final ParseQuery<Note> query = ParseQuery.getQuery("Note");

				query.whereEqualTo("ownerID", new FileHandler().getID());
				query.findInBackground(new FindCallback<Note>() {
					@Override
					public void done(List<Note> arg0, ParseException arg1) {
						for (Note note: arg0) {
							Database db = new Database();
							if (db.contains(note.getID())) {
								db.update(note, 0, 0, false);
							} else {
								db.addNote(note);
							}

							NoteViewController controller = NotesList.stageMap.get(note.getID());
							
							if (controller != null) {
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									controller.setNote(note);
									controller.setTitle(note.getTitle());
									controller.setBody(note.getBody());
								}
							});
							}
						}

					}
				});
				
				sleep(300000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
