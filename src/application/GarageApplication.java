
package application;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Locale;
import java.util.Optional;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * <h1>GarageApplication</h1>
 * This is the application’s primary class, handling start 
 * and stop of application as well as login and logout of operator account. 
 * This class will run continuously, with and without the presence of 
 * an operator, and will only be shutdown during maintenance.
 */

public class GarageApplication extends Application{
	private CustomerManager customerManager;
	private CustomerListView customerListView;
	private final String operatorpass = "a";
	private int logincounter = 0;
	private boolean login = false;


	/**
	 * The entry point for the Java program.
	 * @param args
	 */
	public static void main(String[] args) {	
		// launch() do the following:
		// - creates an instance of the Main class
		// - calls Main.init()
		// - create and start the javaFX application thread
		// - waits for the javaFX application to finish (close all windows)
		// the javaFX application thread do:
		// - calls Main.start(Stage s)
		// - runs the event handling loop
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		while(login==false){
			login();
		}
		if(Dialogs.confirmDialog("Start options","Launch from previous", "Would you like to launch form a previous file? If you choose not to, a new file will be created.")){
			FileChooser chooser = new FileChooser();
			chooser.setTitle("Choose launch file");
			File file = chooser.showOpenDialog(new Stage());
			open(file);

		} else{
			customerManager = new CustomerManager();
		}
		// set default locale english 
		Locale.setDefault(Locale.ENGLISH);

		customerListView = new CustomerListView(customerManager);
		BorderPane root = new BorderPane();
		root.setTop(new GarageMenu(customerManager, customerListView));
		root.setCenter(customerListView);

		Scene scene = new Scene(root);
		primaryStage.setTitle("Bicycle Garage");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public void login(){
		if(logincounter==3){
			Dialogs.alertError("Log-in attempts exceeded", "Log-in attempts exceeded", "System is shutting down due to exceeded number of log-in attempts");
			System.exit(1);
		}
		Optional<String> pass = Dialogs.logInDialog("Log-in","Log into application", "Please enter your password to log in");

		if (pass.isPresent()) {
			String input = pass.get();
			if(input.equals(operatorpass)){
				login = true;
			}
			else{
				logincounter++;
				Dialogs.alertError("Log-in failed", "Log-in failed", "Incorrect password entered");
				login();
			}

		} else{
			if(Dialogs.confirmDialog("Exit the application", "Exit the application?", "Are you sure you want to exit the application?")) {
				System.exit(1);
			}
			else {
				login();
			}
		}
	}

	@Override
	public void stop(){
		// Here you can place any action to be done when the application is closed, i.e. save phone book to file.
		if(Dialogs.confirmDialog("Save options","Save current setup before termination.", "Would you like to save the current setup to file?")){
			FileChooser chooser = new FileChooser();
			chooser.setTitle("Choose save directory");
			File file = chooser.showSaveDialog(new Stage());
			save(file);
		}
	}
	public void save(File file) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
			out.writeObject(customerManager);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	

	public void open(File file){
		try{
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
			customerManager = (CustomerManager) in.readObject();
			in.close();
		} catch (Exception e){
			e.printStackTrace();
			System.exit(1);
		}
	}

}
