package application;

	import java.util.Collections;
	import java.util.Optional;
	import javafx.application.Platform;
	import javafx.scene.control.Menu;
	import javafx.scene.control.MenuBar;
	import javafx.scene.control.MenuItem;
	import java.util.Map;
	import java.util.Set;
	import java.util.TreeSet;

	public class GarageMenu extends MenuBar {
		private CustomerManager customerManager;
		private CustomerListView customerListView;
		
		/** Creates the menu for the phone book application.
		 * @param phoneBook the phone book with names and numbers
		 * @param nameListView handles the list view for the names
		 */
		public GarageMenu(CustomerManager customerManager, CustomerListView customerListView) {
			this.customerManager = customerManager;
			this.customerListView = customerListView;

			final Menu menuGarage = new Menu("Bicycle Garage");
			final MenuItem menuQuit = new MenuItem("Quit");
			menuQuit.setOnAction(e -> Platform.exit());
			menuGarage.getItems().addAll(menuQuit);
		
			final Menu menuFind = new Menu("Search");
			
			final MenuItem menuShowAll = new MenuItem("Show all");
			menuShowAll.setOnAction(e -> showAll());
			menuFind.getItems().addAll(menuShowAll);
			
			final MenuItem menuByName = new MenuItem("by name");
			menuByName.setOnAction(e -> byName());
			menuFind.getItems().addAll(menuByName);
			
			final MenuItem menuByBarcode = new MenuItem("by barcode");
			menuByBarcode.setOnAction(e -> byBarcode());
			menuFind.getItems().addAll(menuByBarcode);
			
			final MenuItem menuPhoneNumber = new MenuItem("by phonenumber");
			menuPhoneNumber.setOnAction(e -> byPhoneNumber());
			menuFind.getItems().addAll(menuPhoneNumber);
			
			final Menu menuMissingPayment = new Menu("List missing payments");
			menuMissingPayment.setOnAction(e -> showAll());

		    getMenus().addAll(menuGarage, menuFind, menuMissingPayment);
		    //setUseSystemMenuBar(true);  // if you want operating system rendered menus, uncomment this line
		}

		
		private void showAll() {
			customerListView.fillList(customerManager.allCustomers());
			customerListView.clearSelection();
		}
		
		private void byName() {
			Optional<String> name = Dialogs.oneInputDialog("Customer search by name", "Search customer by name", "Input the name of the customer you're looking for:");
			if (name.isPresent()) {
				CharSequence n = name.get();
				Set<Customer> customers = new TreeSet<Customer>();
				for(Customer customer : customerManager.allCustomers()){
					if(customer.getName().contains(n)){
						customers.add(customer);
					}
				}
				if(Collections.emptySet().equals(customers)){
					if(Dialogs.confirmDialog("An error occured","No customer by the name of " + n + " found.","Would you like to search for another name instead?")){
						byName();
					}
				} else{
					customerListView.fillList(customers);
					if(customers.size()<2){
						customerListView.select(0);
					}
				}	
			}
		}
		
		private void byPhoneNumber() {
			Optional<String> number = Dialogs.oneInputDialog("Customer search by phone number", "Search customer by phone number.", "Input the phone number of the customer you're looking for:");
			if (number.isPresent()) {
				String n = number.get();
				if(Collections.emptySet().equals(customerManager.findCustomerByPhoneNr(n))){
					if(Dialogs.confirmDialog("An error occured","No customer with phone number " + n + " found.","Would you like to search for another phone number instead?")){
						byPhoneNumber();
					}
				} else{
					customerListView.fillList(customerManager.findCustomerByPhoneNr(n));
				}
			}
		}
		
		private void byBarcode() {
			Optional<String> barcode = Dialogs.oneInputDialog("Customer search by bicycle barcode", "Search customer by bicycle barcode.", "Input the bicycle barcode to find its corresponding owner:");
			if (barcode.isPresent()) {
				String b = barcode.get();
				if(Collections.emptySet().equals(customerManager.findCustomerByBarcode(b))){
					if(Dialogs.confirmDialog("An error occured","No customer with barcode " + b + " found.","Would you like to search for another barcode instead?")){
						byBarcode();
					}
				} else{
					customerListView.fillList(customerManager.findCustomerByBarcode(b));
				}
			}
		}
		
	}
