package application;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;
import java.util.Random;


/**
 * <h1>CustomerManager</h1>
 * The CustomerManager manages customers and their respective information. 
 * This class includes the logic used to store all customers, add and remove 
 * customers, add and remove barcodes as well as generating barcodes, 
 * find customer by name, barcode or phone number, show all customers 
 * and generating PIN codes for customers.
 *
 * @version 1.0
 * @author Group 9
 */
public class CustomerManager implements Serializable {
	private int size;
	private Set<Customer> customers;
	private LinkedList<String> PINcodes;
	private LinkedList<String> barcodes;
	private static final long serialVersionUID = 1L;

	/**
	 *	Constructor initiating customers, PINcodes and barcodes.
	 */
	public CustomerManager() {
		customers = new HashSet<>();
		PINcodes = new LinkedList<>();
		barcodes = new LinkedList<>();
		generatePIN();
		generateBarcode();
		customers.add(new Customer("Skyldiga Johan", "0722048352","1234",true));
	}

	/**
	 * returns the number of customers in the system.
	 * @return the size of customers.
	 */
	public int getSize(){
		return size;
	}

	/**
	 * Finds the customer with the given name.
	 * @param name The name for the customer 
	 * @return customer with the given name, or null if such a customer does not exist.
	 */
	public Customer findCustomerByName(String name) {
		for(Customer c : customers) {
			if(c.getName() == name) {
				return c;
			}
		}
		return null;
	}
	/**
	 * Finds the customer with the given barcode.
	 * @param barcode The barcode for the customer
	 * @return customer with the given barcode, or null if such a customer does not exist.
	 */
	public Customer findCustomerByBarcode(String barcode) {
		for(Customer c : customers) {
			for(Bicycle b : c.getBicycles())
				if(b.getBarcode().equals(barcode)) {
					return c;
				}
		}
		return null;
	}
	/**
	 * Finds the customer with the given phone number.
	 * @param phoneNr The phone number for the customer
	 * @return customer with the given phone number, or null if such a customer does not exist.
	 */
	public Customer findCustomerByPhoneNr(String phoneNr) {
		for(Customer c : customers) {
			if(c.getPhoneNr().equals(phoneNr)) {
				return c;
			}
		}
		return null;
	}

	/**
	 * Generates 50 random PIN-code to be assigned to customers.
	 */
	public void generatePIN(){
		Random rand = new Random();
		int count = 0;
		while(count <50) {
			String newPIN = String.format("%04d", rand.nextInt(10000));
			if(!PINcodes.contains(newPIN)) {
				PINcodes.add(count, newPIN);
				count++;
			}
		}
	}	

	/**
	 * Generates 50 random barcodes to be assigned to new customers.
	 * 
	 */
	public void generateBarcode(){
		Random rand = new Random();
		int count = 0;
		while(count <50) {
			String newBarcode = String.format("%05d",rand.nextInt(100000));
			if(!barcodes.contains(newBarcode)) {
				barcodes.add(count, newBarcode);
				count++;
			}
		}

	}

	/**
	 * allNames returns a set containing all names of the customers in the system.
	 * @return a treeset containing all names
	 */
	public Set<String> allNames() {
		Set<String> names = new TreeSet<String>();
		for(Customer currentKey : customers){
			names.add(currentKey.getName());
		}
		return names;
	}
	/**
	 * Adds a customer with a name and phoneNr to the system.
	 * @param name The name for the customer
	 * @param phoneNr The phone number for the customer
	 * @return true if the customer was added, false if they were not.
	 */
	public boolean addCustomer(String name, String phoneNr) {
		if(size>50) {
			return false; //Garage is full
		}
		else {
			for(Customer currentKey : customers) {
				if(currentKey.getPhoneNr().equals(phoneNr) && currentKey.getName().equals(name)) {
					return false;
				}
			}

			Customer newCustomer = new Customer(name, phoneNr, PINcodes.pollLast());
			size++;
			return customers.add(newCustomer);
		}
	}
	/**
	 * Removes a customer from the system.
	 * @param customer The customer that wished to be removed
	 * @return true if the customer was removed, false if they were not.
	 */
	public boolean removeCustomer(Customer customer) {
		boolean removed = customers.remove(customer);
		if(removed == true) {
			size--;
		}
		return removed;
	}
	/**
	 * Returns a list of all customers as a TreeSet. To be used in CustomerListView for the
	 * method removeCustomer to work.
	 * @return a treeset of all customers in the system.
	 */
	public Set<Customer> allCustomers() {
		Set<Customer> listCustomers = new TreeSet<Customer>();
		for(Customer currentKey : customers) {
			listCustomers.add(currentKey);
		}
		return customers;
	}
	/**
	 * Adds bike to customer, by finding customer by name.
	 * @param customer The customer that the bicycle will be added to
	 * @return true if bike was added, false if it wasn't.
	 */
	public boolean addBicycle(Customer customer) {
		HashSet<Bicycle> bicycles = new HashSet<Bicycle>();
		bicycles.add(new Bicycle(barcodes.getLast()));
		if(size<50 && customer.getBicycles().size()<2) {
			customer.addBicycle(bicycles);
			size++;
			barcodes.removeLast();
			return true;
		}
		return false;
	}
	/**
	 * Removes bike from customer.
	 * @param barcode The barcode for the bicycle that will be removed
	 * @return true if bicycle was removed, otherwise false.
	 */
	public boolean removeBicycle(String barcode) {
		Customer customer = findCustomerByBarcode(barcode);
		if(customer != null) {
			for(Bicycle b: customer.getBicycles()) {
				if(barcode.equals(b.getBarcode())) {
					size--;
					return customer.getBicycles().remove(b);
				}
			}
		}
		return false;
	}
	
	/** Returns a set with the bicycles in the garage.
	 * @return a set with the bicycles in the garages*/

	public Set<Bicycle> getBikesInGarage() {
		Set<Bicycle> bicycles = new TreeSet<Bicycle>();
		for(Customer currentKey : customers) {
			for(Bicycle b: currentKey.getBicycles()) {
				if(b.checkStatus()) {
					bicycles.add(b);
				}
			}
		}
		return bicycles;
	}
}
