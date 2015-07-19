package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.parse4j.ParseException;
import org.parse4j.callback.SaveCallback;

import Util.Database;
import Util.FileHandler;
import Util.Location;
import Util.Note;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

public class NoteController implements Initializable {

	@FXML private Label label_status;
	@FXML private Button add_note;
	@FXML private ListView<Note> notes_list;
	
	public static ObservableList<Note> items;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<Note> item = notes_list.getItems();
		for (Note note: new Database().getNoteList()) {
			item.add(note);
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
							grid.add(name, 1, 0);

							Label dt = new Label(note.getID());
							grid.add(dt, 1, 1);            

							setGraphic(grid);
						}
					}
				};

				items = item;
				return cell;
			}
		});

		notes_list.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				try {
					Note note = notes_list.getSelectionModel().getSelectedItem();

					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NoteView.fxml"));
					Parent root1 = (Parent) fxmlLoader.load();

					NoteViewController controller = fxmlLoader.<NoteViewController> getController();
					controller.setNote(note);
					controller.setTitle(note.getTitle());
					controller.setBody(note.getBody());

					Stage stage = new Stage();
					stage.setScene(new Scene(root1));

					stage.initStyle(StageStyle.UNDECORATED);
					stage.setAlwaysOnTop(false);

					// Set previous location
					Location loc = new Database().getLoc(note.getID());
					if (loc.X != 0 || loc.Y != 0) {
						stage.setX(loc.X);
						stage.setY(loc.Y);
					}

					stage.show();

					Database db = new Database();
					db.update(note, loc.X, loc.Y, true);

					NotesList.stageMap.put(note.getID(), controller);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public ListView<Note> getList() {
		return notes_list;
	}
	
	@FXML protected void add_note(ActionEvent event) {
		Note note = new Note();
		note.setID(String.valueOf(UUID.randomUUID()));
		note.setOwner(new FileHandler().getID());
		note.setTitle("");
		note.setBody("");

		note.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				if (e == null) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							setStatus("Created!");
							try {
								FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NoteView.fxml"));
								Parent root1 = (Parent) fxmlLoader.load();

								NoteViewController controller = fxmlLoader.<NoteViewController> getController();
								controller.setNote(note);
								controller.setTitle(note.getTitle());
								controller.setBody(note.getBody());

								Stage stage = new Stage();
								stage.setScene(new Scene(root1));

								stage.initStyle(StageStyle.UNDECORATED);
								stage.setAlwaysOnTop(false);

								// Set previous location
								Location loc = new Location(0,0);

								if (loc.X != 0 || loc.Y != 0) {
									stage.setX(loc.X);
									stage.setY(loc.Y);
								}

								stage.show();

								Database db = new Database();
								db.update(note, loc.X, loc.Y, true);

								NotesList.stageMap.put(note.getID(), controller);
								items.add(note);
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
													grid.add(name, 1, 0);

													Label dt = new Label(note.getID());
													grid.add(dt, 1, 1);            

													setGraphic(grid);
												}
											}
										};

										return cell;
									}
								});
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					});
				} else {
					setStatus("Failed!");
					e.printStackTrace();
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
				
				new Timer().schedule(new TimerTask() {          
				    @Override
				    public void run() {
				    	label_status.setVisible(false);     
				    }
				}, 2000);
			}
		});
	}
}
