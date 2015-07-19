package application;
	
import Util.AutoUpdater;
import Util.FileHandler;
import Util.ParseSetup;
import Util.SettingsHandler;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			// TODO Auto Generated method stub
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// Register parse
		new ParseSetup().initializeParse();
		
		boolean isRegisterd = new FileHandler().registered();
		
		// If the user has not set the UUID yet, ask them to set it!
		if (isRegisterd) {
			new Thread() {
	            @Override
	            public void run() {
	                javafx.application.Application.launch(NotesList.class);
	            }
	        }.start();
	        new AutoUpdater().start();
		} else {
			new Thread() {
	            @Override
	            public void run() {
	                javafx.application.Application.launch(Register.class);
	            }
	        }.start();
	        
	        // Add default settings
	        SettingsHandler sh = new SettingsHandler();
	        sh.set("Auto-sync: 5");
		}
	}
}
