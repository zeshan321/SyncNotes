package application;

import java.net.URL;
import java.util.ResourceBundle;

import Util.Database;
import Util.Note;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class NoteViewController implements Initializable {
	
	@FXML private Label note_title;
	@FXML private TextArea note_body;
	@FXML private AnchorPane note_pane;
	@FXML private Button note_close;

	private Note note;
	
	private double initialX;
	private double initialY;
	
	
	public void setTitle(String title) {
		note_title.setText(title);
	}
	
	public void setBody(String body) {
		note_body.setText(body);
	}
	
	public void setNote(Note note) {
		this.note = note;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		note_pane.setOnMousePressed(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent me) {
	            if (me.getButton() != MouseButton.MIDDLE) {
	                initialX = me.getSceneX();
	                initialY = me.getSceneY();
	            }
	        }
	    });

		note_pane.setOnMouseDragged(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent me) {
	            if (me.getButton() != MouseButton.MIDDLE) {
	            	note_pane.getScene().getWindow().setX(me.getScreenX() - initialX);
	            	note_pane.getScene().getWindow().setY(me.getScreenY() - initialY);
	            	
	            }
	        }
	    });
		
		note_pane.setOnMouseReleased(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent me) {
	        	Database db = new Database();
            	db.update(note, me.getScreenX() - initialX, me.getScreenY() - initialY, "OPEN");  
	        }
		});
	}
	
	@FXML protected void note_close(ActionEvent event) {
		Node source = (Node)  event.getSource(); 
	    Stage stage  = (Stage) source.getScene().getWindow();
	    
	    Database db = new Database();
    	db.update(note, stage.getScene().getX() - initialX, stage.getScene().getY() - initialY, "CLOSED");  
    	
    	stage.close();
	}
}
