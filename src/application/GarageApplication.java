
package application;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Locale;
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
 * 
 * @version 1.0
 * @author Group 9
 */

public class GarageApplication extends Application{
	private CustomerManager customerManager;
	private CustomerListView customerListView;
	private final String operatorpass = "1Qazwsx*";
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
	
	/**
	 * The start function that handles operator login, opens/creates a new database file 
	 * and sets the primary stage.
	 * @param primaryStage The primaryStage of the application
	 */
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
			customerManager.setSize(customerManager.getBikesInGarage().size());
			hardwareManager = new HardwareManager(customerManager.allCustomers());
			customerListView = new CustomerListView(customerManager, hardwareManager);
			
			in.close();
		} catch (Exception e){
			customerManager = new CustomerManager();
			hardwareManager = new HardwareManager(customerManager.allCustomers());
			customerListView = new CustomerListView(customerManager, hardwareManager);
			customerListView.save();
		} 

		BorderPane root = new BorderPane();
		root.setTop(new GarageMenu(customerManager, customerListView));
		root.setCenter(customerListView);

		Scene scene = new Scene(root);
		primaryStage.setTitle("Bicycle Garage");
		primaryStage.setX(0);
		primaryStage.setY(280);
		primaryStage.setMaxWidth(1100);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	private void login(){
		if(logincounter==3){
			Dialogs.alertError("Log-in attempts exceeded", "Log-in attempts exceeded", "System is shutting down due to exceeded number of log-in attempts");
			System.exit(1);
		}
		String pass = Dialogs.logInDialog("Log-in","Log into application", "Please enter your password to log in");
		if (pass!=null&&!pass.isEmpty()) {
			if(pass.equals(operatorpass)){
				login = true;
			}
			else{
				logincounter++;
				Dialogs.alertError("Log-in failed", "Log-in failed", "Incorrect password entered");
				login();
			}
		} 
		else{
			if(Dialogs.confirmDialog("Exit the application", "Exit the application?", "Are you sure you want to exit the application?")) {
				stop();
			}
			else {
				login();
			}
		}
	}
	
	/**
	 * The exit point for the Java program. Leaves an alert about the consequences of quitting the program.
	 */
	@Override
	public void stop(){
		Dialogs.alert("System shutdown", "The system is shutting down", "Warning: The bicycle garage won't function without running this application. The application shall only be shutdown during maintenance hours.");
		if(login==true){
			customerListView.save();
		}
		System.exit(1);
	}
}
