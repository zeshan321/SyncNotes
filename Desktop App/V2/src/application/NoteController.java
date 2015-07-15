package application;

import java.net.URL;
import java.util.ResourceBundle;

import Util.Database;
import Util.Note;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class NoteController implements Initializable{

	@FXML private Label label_status;
	@FXML private ListView<Note> notes_list;
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Note> items = notes_list.getItems();
        for (Note note: new Database().getNoteList()) {
        	items.add(note);
        }
        notes_list.setCellFactory(new Callback<ListView<Note>, ListCell<Note>>(){
        	 
			@Override
			public ListCell<Note> call(ListView<Note> param) {
				ListCell<Note> cell = new ListCell<Note>(){
					 
					@Override
				    public void updateItem(Note note, boolean empty) {
				        super.updateItem(note, empty);
				        if (empty) {
				            setText(null);
				            setGraphic(null);
				        } else {
				            setText(null);
				 
				            GridPane grid = new GridPane();
				            grid.setHgap(10);
				            grid.setVgap(4);
				            grid.setPadding(new Insets(0, 10, 0, 10));
				      
				            Label name = new Label(note.getTitle());
				            name.getStyleClass().add("cache-list-name");
				            grid.add(name, 1, 0);
				 
				            Label dt = new Label(note.getID());
				            grid.add(dt, 1, 1);            
				            dt.getStyleClass().add("cache-list-dt");
				 
				            setGraphic(grid);
				        }
					}
                };
                
				return cell;
			}
        });
    }
	
	private void setStatus(String status) {
		label_status.setVisible(true);
		
		Platform.runLater(new Runnable() {
	        @Override
	        public void run() {
	        	label_status.setText(status);
	        }
	   });
	}
}
