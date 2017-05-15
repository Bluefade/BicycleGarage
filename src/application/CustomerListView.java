package application;

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
		private ListView<String> listView;
		private ObservableList<String> obsList;
		private CustomerManager customerManager;
		private Button addBicycleButton;
		private Button removeCustomerButton;
		private Button removeBicycleButton;
		private Label numbersLabel;
		
		/** Creates a list view of all customer names and adds buttons for adding/removing customers and bicycles.
		 * @param CustomerManager containing the customers
		 */
		public CustomerListView(CustomerManager customerManager) {	
			this.customerManager = customerManager;
			
			// Create an observable wrapper for the names.
			obsList = FXCollections.observableArrayList();
			obsList.setAll(customerManager.allNames());
			
			// Create a list view to display the names. 
			// The list view is automatically updated when the observable list i updated.
			listView = new ListView<>(obsList);
			listView.setPrefWidth(400);
			listView.setPrefHeight(200);

			setTop(listView);
			
			// A label to display phone numbers
			numbersLabel = new Label();
			numbersLabel.setMinWidth(340);
			
			Button addCustomerButton = new Button("Add customer");
			addCustomerButton.setOnAction(e -> addCustomer());
			
			addBicycleButton = new Button("Add bicycle");
			addBicycleButton.setOnAction(e -> addBicycle());
			
			removeCustomerButton = new Button("Remove customer");
			removeCustomerButton.setOnAction(e -> removeCustomer());
			
			removeBicycleButton = new Button("Remove bicycle");
			removeBicycleButton.setOnAction(e -> removeBicycle());
			
			HBox buttonBox = new HBox();
			buttonBox.setSpacing(5);
			buttonBox.setPadding(new Insets(10, 10, 10, 10));
			buttonBox.getChildren().addAll(numbersLabel, addCustomerButton, addBicycleButton, removeCustomerButton, removeBicycleButton);
			setBottom(buttonBox);

			// The method change is called when a row in the list view is selected. 
			listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					int index = listView.getSelectionModel().getSelectedIndex();
					
					if (index != -1) {
						String name = obsList.get(index);
						addBicycleButton.setDisable(false);
						removeBicycleButton.setDisable(false);
						removeCustomerButton.setDisable(false);
						if(Collections.emptySet().equals(customerManager.findBarcodesByName(name))){
							removeBicycleButton.setDisable(true);
						}
						numbersLabel.setText(newValue + " " + customerManager.findBarcodesByName(newValue));
					} else {
						numbersLabel.setText("");
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
			listView.getSelectionModel().clearSelection();
		}
		
		/**
		 * Selects row index in the list view if index in [0, number rows).
		 * Otherwise nothing happens.
		 * @param index the index of the row to select
		 */
		public void select(int index) {
			listView.getSelectionModel().clearSelection();
			if (index > -1 && index < obsList.size()) {
				listView.getSelectionModel().select(index);
			}
		}

		/**
		 * Selects the row containing name. If no row contains the name nothing
		 * happens.
		 * @param name the name to select
		 */
		public void select(String name) {
			int index = obsList.indexOf(name);
			select(index);
		}
		
		/**
		 * Fills the rows in the vie list with the strings in col.
		 * @param col a collection containing strings that will be displayed in the list view
		 */
		public void fillList(Collection<String> col) {
			obsList.setAll(col);
		}
		
		private void addCustomer() {
			clearSelection();
			String[] labels = {"Name", "Phone number"};
			Optional<String[]> result = Dialogs.twoInputsDialog("Add new customer", 
					"Please enter the name and phone number of the new customer and press Ok.", labels);
			if (result.isPresent()) {
				String[] inputs = result.get();
				if (inputs.length == 2) {
				boolean success = customerManager.put(inputs[0], new Customer());
				if(success) {
						obsList.setAll(customerManager.allNames());
						select(inputs[0]);
					} else {
						Dialogs.alert("Add", null, "Failed to add phone number.");
						select(inputs[0]);
					}
				}
			}	
		}
		
		private void addBicycle() {
			int index = listView.getSelectionModel().getSelectedIndex();
			if (index != -1) {
				String name = obsList.get(index);
				Optional<String> result = Dialogs.oneInputDialog("Add a new bicycle to " + name, "Enter the number to add", "Number" );
				if (result.isPresent()) {
					String input = result.get();
					boolean success = customerManager.put(name, input);
					if(success) {
						select(index);
					} else {
						Dialogs.alert("Add", null, "Failed to add phone number.");
					}
				}	
			}	
		}
		
		//Personen vars namn är valt i listvyn ska tas bort. Innan personen tas bort ska användaren tillfrågas om personen verkligen ska tas bort.
		private void removeCustomer(){
			int index = listView.getSelectionModel().getSelectedIndex();
			if (index != -1) {
				String name = obsList.get(index);
				if(Dialogs.confirmDialog("Borttagning av kontakt","Vill du verkligen radera kontakten?","Är du säker på att du vill radera " + name + " från telefonkatalogen?")){
					phoneBook.remove(name);
				}
				obsList.setAll(phoneBook.names());
			}
		}
		
		private void removeBicycle(){
			int index = listView.getSelectionModel().getSelectedIndex();
			if (index != -1) {
				String name = obsList.get(index);
				Optional<String> number = Dialogs.oneInputDialog("Borttagning av nummer", "Val av nummer", "Mata in numret som ska raderas");
				if (number.isPresent()) {
					String numb = number.get();
					if(!phoneBook.removeNumber(name, numb)){
						if(Dialogs.confirmDialog("Ett fel uppstod","Det valda numret kunde inte hittas hos kontakten.","Vill du ta bort ett annat nummer?")){
							removeNumber();
						}
					}
					select(index);
				}
			}
		}
		
	}

	
}
