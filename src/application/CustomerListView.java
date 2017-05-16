package application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;


/**
 * <h1>ListView</h1>
 * This class creates and populates the main view 
 * where the list of customers, buttons and selected customer information 
 * is displayed and interacted with. 
 *
 */

public class CustomerListView extends BorderPane {
	private ListView<Customer> listView;
	private ObservableList<Customer> obsList2;
	private CustomerManager customerManager;
	private Button addBicycleButton;
	private Button removeCustomerButton;
	private Button removeBicycleButton;
	private Label numbersLabel;
	private Label numbersLabel1;
	private Label numbersLabel2;

	/** Creates a list view of all customer names and adds buttons for adding/removing customers and bicycles.
	 * @param CustomerManager containing the customers
	 */
	public CustomerListView(CustomerManager customerManager) {	
		this.customerManager = customerManager;

		//TESTTESTTEST Creates an observable wrapper for the customers.
		obsList2 = FXCollections.observableArrayList();
		obsList2.setAll(customerManager.allCustomers());

		// Create a list view to display the names. 
		// The list view is automatically updated when the observable list i updated.
		listView = new ListView<>(obsList2);
		listView.setPrefWidth(600);
		listView.setPrefHeight(300);

		setTop(listView);

		// A label to display names
		numbersLabel = new Label();
		numbersLabel.setMinWidth(200);
		// A label to display phone numbers
		numbersLabel1 = new Label();
		numbersLabel1.setMinWidth(200);
		// A label to display barcodes
		numbersLabel2 = new Label();
		numbersLabel2.setMinWidth(200);

		Button addCustomerButton = new Button("Add customer");
		addCustomerButton.setOnAction(e -> addCustomer());

		addBicycleButton = new Button("Add bicycle");
		addBicycleButton.setOnAction(e -> addBicycle());

		removeCustomerButton = new Button("Remove customer");
		removeCustomerButton.setOnAction(e -> removeCustomer());

		removeBicycleButton = new Button("Remove bicycle");
		removeBicycleButton.setOnAction(e -> removeBicycle());

		HBox buttonBox = new HBox();
		buttonBox.setSpacing(0);
		buttonBox.setPadding(new Insets(10, 10, 10, 10));
		buttonBox.getChildren().addAll(numbersLabel, numbersLabel1, numbersLabel2, addCustomerButton, addBicycleButton, removeCustomerButton, removeBicycleButton);
		setBottom(buttonBox);

		// The method change is called when a row in the list view is selected. 
		listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Customer>() {
			@Override
			public void changed(ObservableValue<? extends Customer> observable, Customer oldValue, Customer newValue) {
				int index = listView.getSelectionModel().getSelectedIndex();

				if (index != -1) {
					Customer customer = obsList2.get(index);
					addBicycleButton.setDisable(false);
					removeBicycleButton.setDisable(false);
					removeCustomerButton.setDisable(false);
					if(Collections.emptySet().equals(customer.getBicycles())){
						removeBicycleButton.setDisable(true);
					}
					numbersLabel.setText("Name: \n" + newValue.getName());
					numbersLabel1.setText("Phone Number: \n" + newValue.getPhoneNr());
					if(newValue.getBicycles().isEmpty()) {
						numbersLabel2.setText("Bicycle barcodes: \n" + "No registred bicycles");
					}
					else {
						numbersLabel2.setText("Bicycle barcodes: \n" + newValue.getBicycles().toString().replaceAll("\\[", "").replaceAll("\\]",""));
					}
				} else {
					numbersLabel.setText("");
					numbersLabel1.setText("");
					numbersLabel2.setText("");
				}

			}
		});
		clearSelection();	
	}

	/**
	 * Clears all selections in the list view and disable all buttons except the
	 * button for adding a name.
	 */
	public void clearSelection() {
		addBicycleButton.setDisable(true);
		removeBicycleButton.setDisable(true);
		removeCustomerButton.setDisable(true);
		numbersLabel.setText("");
		numbersLabel1.setText("");
		numbersLabel2.setText("");
		listView.getSelectionModel().clearSelection();
	}

	/**
	 * Selects row index in the list view if index in [0, number rows).
	 * Otherwise nothing happens.
	 * @param index the index of the row to select
	 */
	public void select(int index) {
		listView.getSelectionModel().clearSelection();
		if (index > -1 && index < obsList2.size()) {
			listView.getSelectionModel().select(index);
		}
	}

	/**
	 * Selects the row containing name. If no row contains the name nothing
	 * happens.
	 * @param name the name to select
	 */
	public void select(Customer customer) {
		int index = obsList2.indexOf(customer);
		select(index);
	}

	/**
	 * Fills the rows in the vie list with the strings in col.
	 * @param col a collection containing strings that will be displayed in the list view
	 */
	public void fillList(Collection<Customer> col) {
		obsList2.setAll(col);
	}

	private void addCustomer() {
		clearSelection();
		String[] labels = {"Name", "Phone number"};
		Optional<String[]> result = Dialogs.twoInputsDialog("Add new customer", 
				"Please enter the name and phone number of the new customer and press Ok.", labels);
		if (result.isPresent()) {
			String[] inputs = result.get();
			if (inputs.length == 2 && inputs[1].matches("\\d+") && inputs[0].matches("^[\\p{L} .'-]+$")) {
				boolean success = customerManager.addCustomer(inputs[0], inputs[1]);
				if(success) {
					obsList2.setAll(customerManager.allCustomers());
					select(customerManager.findCustomerByPhoneNr(inputs[1]));
				} else {
					Dialogs.alert("Failed to add customer", "Failed to add customer", "The system is already full or the entered phone number is already registered to another customer.");
					clearSelection();
				}
			}
			else {
				if(Dialogs.confirmDialog("Failed to add customer", "Invalid inputs", "You have to enter both a name and a valid phone number to add a customer. Would you like to try again?")) {
				addCustomer();
				}
			}
		}
		else {
			if(Dialogs.confirmDialog("Failed to add customer", "Not enough inputs", "You have to enter both a name and a valid phone number to add a customer. Would you like to try again?")) {
				addCustomer();
			}

		}
	}

	private void addBicycle() {
		int index = listView.getSelectionModel().getSelectedIndex();
		if (index != -1) {
			Customer customer = obsList2.get(index);		
			if(Dialogs.confirmDialog("Add new bicycle","You are about to remove a bicycle.", "Are you sure that you want to add a bicycle to " + customer.getName() + "?")) {
				boolean success = customerManager.addBicycle(customer);
				if(success) {
					select(index);
				} else {
					Dialogs.alert("Failed to add bicycle","Failed to add bicycle" , "The garage is either full or the customer already has two registered bicycles.");
				}
			}	
		}	
	}

	//Personen vars namn är valt i listvyn ska tas bort. Innan personen tas bort ska användaren tillfrågas om personen verkligen ska tas bort.
	private void removeCustomer(){
		int index = listView.getSelectionModel().getSelectedIndex();
		if (index != -1) {
			Customer customer = obsList2.get(index);
			if(Dialogs.confirmDialog("Remove customer","Do you really want to remove this customer?","Are you sure you want to remove " + customer.getName() + " from the system?")){
				customerManager.removeCustomer(customer);
			}
			obsList2.setAll(customerManager.allCustomers());
		}
	}

	private void removeBicycle(){
		int index = listView.getSelectionModel().getSelectedIndex();
		if (index != -1) {
			Customer customer = obsList2.get(index);
			Optional<String> number = Dialogs.oneInputDialog("Remove bicycle", "Specify barcode", "Enter the barcode of the bicycle you want to remove from " + customer.getName());
			if (number.isPresent()) {
				String numb = number.get();
				if(customer == customerManager.findCustomerByBarcode(numb)) {
					if(customerManager.removeBicycle(numb)){
						Dialogs.alert("Remove Bicycle", "Success!", "The bicycle was successfully removed!");
					}
					else {		
						if(Dialogs.confirmDialog("An error här står det ssaker.","Error","The entered barcode does not exist in the system. Do you want to remove a different bicycle?")){
							removeBicycle();
						}
					}
				}
				else {
					if(Dialogs.confirmDialog("An error occurred","Error","The entered barcode does not belong to the chosen customer. Do you want to remove a different bicycle?")){
						removeBicycle();
					}
				}
				select(index);
			}
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
} 






