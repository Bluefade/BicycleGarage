package application;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
		
		final MenuItem menuCheckedInBicycles = new MenuItem("by checked in bicycles");
		menuPhoneNumber.setOnAction(e -> byBicyclesInGarage());
		menuFind.getItems().addAll(menuCheckedInBicycles);

		final Menu menuListMissingPayment = new Menu("List missing payments");

		final MenuItem menuMissingPayment = new MenuItem("List missing payments");
		menuMissingPayment.setOnAction(e -> byMissingPayment());
		menuListMissingPayment.getItems().addAll(menuMissingPayment);

		getMenus().addAll(menuGarage, menuFind, menuListMissingPayment);
		//setUseSystemMenuBar(true);  // if you want operating system rendered menus, uncomment this line
	}


	private void showAll() {
		customerListView.fillList(customerManager.allCustomers());
		customerListView.clearSelection();
	}

	private void byName() {
		Optional<String> name = Dialogs.oneInputDialog("Customer search by name", "Search customer by name", "Input the name of the customer you're looking for");
		if (name.isPresent()) {
			CharSequence n = name.get();
			Set<Customer> customers = new TreeSet<Customer>();
			for(Customer customer : customerManager.allCustomers()){
				if(customer.getName().contains(n)){
					customers.add(customer);
				}
			}
			if(Collections.emptySet().equals(customers)){
				if(Dialogs.confirmDialog("No customer found","No customer found","No customer by the name of '" + n + "' found. Would you like to search for another name instead?")){
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
		Optional<String> number = Dialogs.oneInputDialog("Customer search by phone number", "Search customer by phone number.", "Input the phone number of the customer you're looking for");
		if (number.isPresent()) {
			String n = number.get();
			if(null == customerManager.findCustomerByPhoneNr(n)){
				if(Dialogs.confirmDialog("Customer not found","Customer not found","No customer with phone number '" + n + "' found. Would you like to search for another phone number instead?")){
					byPhoneNumber();
				}
			} 
			else{
				Set<Customer> cm = new HashSet<Customer>();
				cm.add(customerManager.findCustomerByPhoneNr(n));
				customerListView.fillList(cm);
				customerListView.select(0);
			}
		}
	}

	private void byBarcode() {
		Optional<String> barcode = Dialogs.oneInputDialog("Customer search by bicycle barcode", "Search customer by bicycle barcode.", "Input the bicycle barcode to find its corresponding owner");
		if (barcode.isPresent()) {
			String b = barcode.get();
			if(null == customerManager.findCustomerByBarcode(b)){
				if(Dialogs.confirmDialog("No customer found","No customer found","No customer with barcode '" + b + "' found. Would you like to search for another barcode instead?")){
					byBarcode();
				}
			} else{
				Set<Customer> cm = new HashSet<Customer>();
				cm.add(customerManager.findCustomerByBarcode(b));
				customerListView.fillList(cm);
				customerListView.select(0);
			}
		}
	}

	private void byMissingPayment() {
		Set<Customer> cm = new HashSet<Customer>();
		for(Customer customer : customerManager.allCustomers()){
			if(customer.getMissingPayment()){
				cm.add(customer);
			}
		}
		if(Collections.emptySet().equals(cm)){
			Dialogs.alert("No missing payments found","No missing payments found","No customers with missing payments found.");
		} else{
			customerListView.fillList(cm);
		}
	}
	
	private void byBicyclesInGarage() {
		Set<Customer> cm = new HashSet<Customer>();
		Set<Bicycle> bm = new HashSet<Bicycle>();
		for(Customer customer : customerManager.allCustomers()) {
			for(Bicycle bicycle : customer.getBicycles()) {
				if(bicycle.checkStatus()) {
					cm.add(customer);
					bm.add(bicycle);
				}
			}
		}
		customerListView.fillList(cm);
	}
}
