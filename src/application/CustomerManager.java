package application;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;
import java.util.Random;


/**
 * <h1>CustomerManager</h1>
 * The CustomerManager manages Customers and their respective information. 
 * This class includes the logic used to store all customers, add and remove 
 * Customers, add and remove barcodes as well as generating barcodes, 
 * find customer by name, barcode or phone number, show all Customers 
 * and generating PIN codes for Customers.
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
	 *	Constructor initiating Customers, PIN codes and barcodes.
	 */
	public CustomerManager() {
		customers = new HashSet<>();
		PINcodes = new LinkedList<>();
		barcodes = new LinkedList<>();
		generatePIN();
		generateBarcode();
		customers.add(new Customer("Anna Svensson", "070090807", "2020", true, true));
		customers.add(new Customer("Tobias Olsson", "070444444", "4444", true, false));
		
		
		
	}

	/**
	 * Returns the number of Customers in the system.
	 * @return the number of Customers in the system.
	 */
	public int getSize(){
		return size;
	}

	/**
	 * Finds the Customer with the given name.
	 * @param name The name for the Customer 
	 * @return Customer with the given name, or null if such a Customer does not exist.
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
	 * Finds the Customer with the given barcode.
	 * @param barcode The barcode for the Customer
	 * @return the Customer with the given barcode, or null if such a Customer does not exist.
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
	 * Finds the Customer with the given phone number.
	 * @param phoneNr The phone number for the Customer
	 * @return the Customer with the given phone number, or null if such a Customer does not exist.
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
	 * Generates 50 random PIN-codes to be assigned to Customers.
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
	 * Generates 50 random barcodes to be assigned to new Customers.
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
	 * allNames returns a set containing all names of the Customers in the system.
	 * @return a TreeSet containing all names of Customers in the system
	 */
	public Set<String> allNames() {
		Set<String> names = new TreeSet<String>();
		for(Customer currentKey : customers){
			names.add(currentKey.getName());
		}
		return names;
	}
	
	/**
	 * Adds a Customer with a name and phoneNr to the system.
	 * @param name The name for the Customer
	 * @param phoneNr The phone number for the Customer
	 * @return true if the Customer was added, false if they were not.
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
			return customers.add(newCustomer);
		}
	}
	
	/**
	 * Removes a Customer from the system.
	 * @param customer The Customer that wishes to be removed
	 * @return true if the Customer was removed, false if they were not.
	 */
	public boolean removeCustomer(Customer customer) {
		boolean removed = customers.remove(customer);
		if(removed == true) {
			size--;
		}
		return removed;
	}
	
	/**
	 * Returns a list of all Customers as a TreeSet. To be used in CustomerListView for the
	 * method removeCustomer to work.
	 * @return a treeset of all Customers in the system.
	 */
	public Set<Customer> allCustomers() {
		Set<Customer> listCustomers = new TreeSet<Customer>();
		for(Customer currentKey : customers) {
			listCustomers.add(currentKey);
		}
		return customers;
	}
	
	/**
	 * Adds Bicycle to Customer, by finding Customer by name.
	 * @param Customer The Customer that the Bicycle will be added to
	 * @return true if bike was added, false if it wasn't.
	 */
	public boolean addBicycle(Customer customer) {
		HashSet<Bicycle> bicycles = new HashSet<Bicycle>();
		bicycles.add(new Bicycle(barcodes.getLast()));
		if(size<50 && customer.getBicycles().size()<2) {
			customer.addBicycle(bicycles);
			size++;
			barcodes.removeLast();
			System.out.println(size);
			return true;
		}
		return false;
	}
	
	/**
	 * Removes Bicycle from customer.
	 * @param barcode The barcode for the Bicycle that will be removed
	 * @return true if Bicycle was removed, otherwise false.
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
	
	/** Returns a set with the Bicycles in the garage.
	 * @return a set with the Bicycles in the garages*/
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
