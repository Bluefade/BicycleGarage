package application;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
		//customer.generateBarcode();
	}

	public CustomerManager() {
		customers = new HashSet<>();
		PINcodes = new LinkedList<>();
		barcodes = new LinkedList<>()
	}

	public int getSize(){
		return size;
	}

	public Customer[] allCustomers{
		return customers;
	}

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

	public String findNameByBarcode(String barcode){
		for(Customer c : customers) {
			for(Bicycle b : c.getBicycles()) {
				if(barcode == b.getBarcode()) {
					return c.getName();
				}
			}
		}
	}

	public String findNameByPhonenr(String phoneNr){
		for(Customer c : customers) {
				if(phoneNr == c.getPhoneNr()) {
					return c.getName();
				}
		}
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
	
	public Set<String> allNames() {
		Set<String> names = new TreeSet<String>();
		for(String currentKey : customers.keySet()){
			names.add(currentKey);
		}
		return names;
	}

}
