
package application;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Locale;
import java.util.Optional;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
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
	private HardwareManager hardwareManager;


	/**
	 * The entry point for the Java program.
	 * @param args
	 */
	public static void main(String[] args) {	
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// set default locale english 
		Locale.setDefault(Locale.ENGLISH);
		
		while(login==false){
			login();
		}
		try{
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("database"));
			customerManager = (CustomerManager) in.readObject();
			customerListView = new CustomerListView(customerManager);
			in.close();
		} catch (Exception e){
			customerManager = new CustomerManager();
			customerListView = new CustomerListView(customerManager);
			customerListView.save();
		} 

		BorderPane root = new BorderPane();
		root.setTop(new GarageMenu(customerManager, customerListView));
		root.setCenter(customerListView);

		Scene scene = new Scene(root);
		primaryStage.setTitle("Bicycle Garage");
		primaryStage.setScene(scene);
		primaryStage.show();
		hardwareManager = new HardwareManager(customerManager.allCustomers());
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
		customerListView.save();
	}
}
