package application;

import java.util.HashMap;
import java.util.LinkedList;

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
	private HashMap<String, Customer> customers;
	private LinkedList<String> PINcodes;
	private HashMap<String, Bicycle> bicycles;
	private LinkedList<String> barcodes;

	public CustomerManager() {
		customers = new HashMap<>();
		bicycles = new HashMap<>();
	}
	
	public int getSize(){
		return size;
	}
	
	public Customer[] allCustomers{
		return;
	}
	
	public findBarcodes(){
		
	}
	
	public findNameByBarcode(){
		
	}
	
	public findNameByPhonenr(){
		
	}
	
	public generatePIN(){
		
	}
	
	public generateBarcode(){
		
	}

}
