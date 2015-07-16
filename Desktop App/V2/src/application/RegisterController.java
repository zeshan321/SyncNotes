package application;

import java.util.List;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.parse4j.callback.FindCallback;

import Util.Database;
import Util.FileHandler;
import Util.Note;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {
	
	@FXML private Button button_start;
	@FXML private TextField textfield_uuid;
	@FXML private Label label_status;
	
	@FXML protected void button_start(ActionEvent event) {
		Node source = (Node)  event.getSource(); 
	    Stage stage  = (Stage) source.getScene().getWindow();
		
		setStatus("Loading...");
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("_Installation");
		
		query.whereEqualTo("installationId", textfield_uuid.getText());
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> arg0, ParseException arg1) {
				if (arg0 == null) {
					setStatus("Error: UUID not found!");
				} else {
					setStatus("Success! Working...");
					
					// Save UUID
					new FileHandler().add(textfield_uuid.getText());
					
					// Load note data
					final ParseQuery<Note> query = ParseQuery.getQuery("Note");

			        query.whereEqualTo("ownerID", textfield_uuid.getText());
			        query.findInBackground(new FindCallback<Note>() {
						@Override
						public void done(List<Note> arg0, ParseException arg1) {
							for (Note note: arg0) {
								
								Database database = new Database();
								if (database.contains(note.getID())) {
									database.update(note, 0, 0, "closed");
								} else {
									database.addNote(note);
								}
							}
							
							Platform.runLater(new Runnable() {
					            public void run() {             
					                try {
					                	stage.close();
										new NotesList().start(new Stage());
									} catch (Exception e) {
										e.printStackTrace();
									}
					            }
					         });
						}
			        });
				}
			}
		});
	}
	
	private void setStatus(String status) {		
		Platform.runLater(new Runnable() {
	        @Override
	        public void run() {
	    		label_status.setVisible(true);
	        	label_status.setText(status);
	        }
	   });
	}
}
