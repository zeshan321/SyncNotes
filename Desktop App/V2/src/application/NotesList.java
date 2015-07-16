package application;

import java.io.IOException;
import java.util.HashMap;

import Util.Database;
import Util.Location;
import Util.Note;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class NotesList extends Application {
	
	private Stage primaryStage;
    private AnchorPane rootLayout;
    
    public static HashMap<String, NoteViewController> stageMap = new HashMap<>();

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Notes");
		
        initRootLayout();
        loadNotes();
    }

	public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Register.class.getResource("NotesList.fxml"));
            rootLayout = (AnchorPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
				public void handle(WindowEvent we) {
                	//AutoUpdater.running = false;
                }
            });       
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	private void loadNotes() {
		for (Note note: new Database().getNoteList()) {
			if (note.getStatus()) {
        	try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NoteView.fxml"));
				Parent root1 = (Parent) fxmlLoader.load();

				NoteViewController controller = fxmlLoader.<NoteViewController> getController();
				controller.setNote(note);
				controller.setTitle(note.getTitle());
				controller.setBody(note.getBody());

				Stage stage = new Stage();
				stage.setScene(new Scene(root1));

				stage.initStyle(StageStyle.TRANSPARENT);

				// Set previous location
				Location loc = new Database().getLoc(note.getID());
				if (loc.X != 0 || loc.Y != 0) {
					stage.setX(loc.X);
					stage.setY(loc.Y);
				}
				

				stage.show();

				stageMap.put(note.getID(), controller);
			} catch (IOException e) {
				e.printStackTrace();
			}
			}
        }
	}
	
	 /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
