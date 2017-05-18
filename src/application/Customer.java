package application;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * <h1>Customer</h1>
 * The Customer class contains the definition 
 * and fundamental functions of the customer
 * @version 1.0
 * @author Group 9
 */
public class Customer implements Comparable<Customer> , Serializable{
	private String name;
	private String PIN;
	private Set<Bicycle> bicycles;
	private String phoneNr;
	private boolean missingPayment;
	private static final long serialVersionUID = 2L;
	
	/**
	 * Creates a new Customer
	 * @param name The name of the Customer
	 * @param phoneNr The phone number of the Customer
	 * @param PIN The PIN code of the Customer
	 */
	public Customer(String name, String phoneNr, String PIN){ 
		this.name = name;
		this.phoneNr = phoneNr;
		this.PIN = PIN;
		missingPayment = false;
		bicycles = new HashSet<>();
	}
	
	/**
	 * Creates a new Customer
	 * @param name The name of the Customer
	 * @param phoneNr The phone number of the Customer
	 * @param PIN The PIN code of the Customer
	 * @param missingPayment The pay status of the customer, true if customer has missing payments 
	 */
	public Customer(String name, String phoneNr, String PIN, boolean missingPayment){ 
		this.name = name;
		this.phoneNr = phoneNr;
		this.PIN = PIN;
		this.missingPayment = missingPayment;
		bicycles = new HashSet<>();
	}

	/**
	 * Receives the name of the Customer
	 * @return A string of the name of the Customer
	 */
	public String getName(){
		return name;
	}

	/**
	 * Receives the Customer's phone number 
	 * @return A string of the Customer's phone number
	 */
	public String getPhoneNr(){
		return phoneNr;
	}

	/**
	 * Receives the Customer's PIN code
	 * @return A string of the Customer's PIN code
	 */
	public String getPIN(){
		return PIN;
	}

	/**
	 * Changes the Customer's PIN code
	 * @param PIN A string of the Customer's new PIN code
	 */
	public void setPIN(String PIN){
		this.PIN = PIN;
	}

	/**
	 * Receives the Customer's bicycle(s)
	 * @return A set of the Customer's bicycle(s)
	 */
	public Set<Bicycle> getBicycles(){
		return bicycles;
	}

	/**
	 * Adds new bicycle(s) to Customer
	 * @param bicyle A set of the Customer's new bicycle(s)
	 */
	public void addBicycle(Set<Bicycle> bicycle){
		for(Bicycle nextBicycle: bicycle){
			bicycles.add(nextBicycle);
		}
	}

	/**
	 * Receives information on whether or not the Customer has missing payments
	 * @return True if the Customer has missing payments
	 */
	public boolean getMissingPayment(){
		return missingPayment;
	}

	/**
	 * Updates information on whether or not the Customer has missing payments
	 * @param paymentStatus True if the Customer has missing payments, false otherwise
	 */
	public void setMissingPayment(boolean paymentStatus){
		missingPayment = paymentStatus;
	}

	/**
	 * Compares this Customer to another Customer by comparing their names
	 * @param c The other Customer to compare to
	 * @return An integer bigger, smaller or equal to zero dependent on the alphabetical order of the names
	 */
	@Override
	public int compareTo(Customer c) {
		return this.getName().compareToIgnoreCase(c.getName());
	}

	/**
	 * Returns the name of the Customer
	 * @return A sting of the name of the Customer
	 */
	public String toString() { 
		return name;
	} 
}
