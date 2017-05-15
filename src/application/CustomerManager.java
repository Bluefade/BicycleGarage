package application;

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
 */
public class CustomerManager {
	private int size;
	private Set<Customer> customers;
	private LinkedList<String> PINcodes;
	private LinkedList<String> barcodes;

	public static void main(String[] args) {
		CustomerManager customer = new CustomerManager();
		customer.generatePIN();
		customer.generateBarcode();
	}

	/**
	 *	Constructor initiating customers, PINcodes and barcodes.
	 */
	public CustomerManager() {
		customers = new HashSet<>();
		PINcodes = new LinkedList<>();
		barcodes = new LinkedList<>();
	}

	/**
	 * returns the number of customers in the system.
	 * @return the size of customers.
	 */
	public int getSize(){
		return size;
	}
	/**
	 * findBarcodesByName finds the barcodes belonging to a customer with the name "name".
	 * @param name
	 * @return a hashset of barcodes belonging to a name.
	 */
	public Set<String> findBarcodesByName(String name){
		Set<String> barcodes = new HashSet<>();
		for(Customer c : customers) {
			if(name == c.getName()) {
				for(Bicycle b: c.getBicycles()) {
					barcodes.add(b.getBarcode());
				}
			}
		}
		return barcodes;
	}
	/**
	 * findNamebyBarcode finds the name of a customer with the barcode "barcode".
	 * @param barcode
	 * @return the name of a customer with the given barcode.
	 */
	public String findNameByBarcode(String barcode){
		for(Customer c : customers) {
			for(Bicycle b : c.getBicycles()) {
				if(barcode == b.getBarcode()) {
					return c.getName();
				}
			}
		}
		return null; // lagt till
	}

	/**
	 * FindNameByPhonenr finds the name of a customer with the phoneNr "phoneNr"
	 * @param phoneNr
	 * @return the name of the customer with the given phoneNr.
	 */
	public String findNameByPhonenr(String phoneNr){
		for(Customer c : customers) {
			if(phoneNr == c.getPhoneNr()) {
				return c.getName();
			}
		}
		return null; // lagt till
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
			System.out.println(PINcodes.getLast());
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
			System.out.println(barcodes.getLast());
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
	 * @param name
	 * @param phoneNr
	 * @return true if the customer was added, false if they were not.
	 */
	public boolean addCustomer(String name, String phoneNr) {
		for(Customer currentKey : customers) {
			if(currentKey.getName()==name && currentKey.getPhoneNr()==phoneNr) {
				return false;
			}
		}
		Customer newCustomer = new Customer(name, phoneNr, PINcodes.pollLast());
		return customers.add(newCustomer);
	}
	/**
	 * Removes a customer from the system.
	 * @param customer
	 * @return true if the customer was removed, false if they were not.
	 */
	public boolean removeCustomer(Customer customer) {
		return customers.remove(customer);
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

}
