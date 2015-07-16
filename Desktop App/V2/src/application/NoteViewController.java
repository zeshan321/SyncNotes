package application;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import org.parse4j.ParseException;
import org.parse4j.callback.SaveCallback;

import Util.Database;
import Util.Note;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
	@FXML private Label label_save;
	@FXML private TextArea note_body;
	@FXML private AnchorPane note_pane;
	@FXML private Button note_close;

	private Note note;

	private double initialX;
	private double initialY;
	private double newX;
	private double newY;


	public void setTitle(String title) {
		note_title.setText(title);
	}

	public void setBody(String body) {
		note_body.setText(body);
	}

	public void setNote(Note note) {
		this.note = note;
	}
	
	public Label getTitle() {
		return note_title;
	}
	
	public TextArea getBody() {
		return note_body;
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
					newX = me.getScreenX() - initialX;
					newY = me.getScreenY() - initialY;
					
					note_pane.getScene().getWindow().setX(newX);
					note_pane.getScene().getWindow().setY(newY);

					note_pane.requestFocus();
				}
			}
		});

		note_pane.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				Database db = new Database();
				db.update(note, me.getScreenX() - initialX, me.getScreenY() - initialY, true);  
				
				note_pane.requestFocus();
			}
		});
		
		note_body.focusedProperty().addListener(new ChangeListener<Boolean>()
		{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    {
		        if (!newPropertyValue) {
		        	if (note.getBody().equals(note_body.getText())) {
		        		return;
		        	}
		        	
		        	Platform.runLater(new Runnable() {
						@Override public void run() {
							note.setBody(note_body.getText());
							setStatus("Saving...");

							note.saveInBackground(new SaveCallback() {
								@Override
								public void done(ParseException e) {
									if (e == null) {
										setStatus("Saved!");
									} else {
										setStatus("Failed!");
									}
								}
							});
							
							Database db = new Database();
							db.update(note, newX, newY, true);
						}
					});
		        }
		    }
		});
	}

	@FXML protected void note_close(ActionEvent event) {
		Node source = (Node)  event.getSource(); 
		Stage stage  = (Stage) source.getScene().getWindow();

		Database db = new Database();
		db.update(note, stage.getScene().getX() - initialX, stage.getScene().getY() - initialY, false);  

		note.saveInBackground();

		stage.close();
	}

	private void setStatus(String status) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				label_save.setVisible(true);
				label_save.setText(status);
				
				new Timer().schedule(new TimerTask() {          
				    @Override
				    public void run() {
				    	label_save.setVisible(false);     
				    }
				}, 2000);
			}
		});
	}
}
