package application;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * <h1>Customer</h1>
 * The Customer class contains the definition 
 * and fundamental information about the customer.
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
	 * @param name The name of Customer
	 * @param phoneNr The phone number of Customer
	 * @param PIN The PIN code of Customer
	 */
	public Customer(String name, String phoneNr, String PIN){ 
		this.name = name;
		this.phoneNr = phoneNr;
		this.PIN = PIN;
		missingPayment = false;
		bicycles = new HashSet<>();
	}
	/**Creates a new Customer
	 * @param name The name of Customer
	 * @param phoneNr The phone number of Customer
	 * @param PIN The PIN code of Customer
	 * @param missingPayment The pay status of the customer, true if customer has not paid */
	public Customer(String name, String phoneNr, String PIN, boolean missingPayment){ 
		this.name = name;
		this.phoneNr = phoneNr;
		this.PIN = PIN;
		this.missingPayment = missingPayment;
		bicycles = new HashSet<>();
	}

	/**
	 * Receives the name of Customer
	 * @return A string of the name of Customer
	 */
	public String getName(){
		return name;
	}

	/**
	 * Receives the customer's phone number 
	 * @return A string of the customer's phone number
	 */
	public String getPhoneNr(){
		return phoneNr;
	}

	/**
	 * Receives the customer's PIN code
	 * @return A string of the customer's PIN code
	 */
	public String getPIN(){
		return PIN;
	}

	/**
	 * Changes the customer's PIN code
	 * @param PIN The customer's new PIN code
	 */
	public void setPIN(String PIN){
		this.PIN = PIN;
	}

	/**
	 * Receives the customer's bicycle(s)
	 * @return A set of the customer's bicycle(s)
	 */
	public Set<Bicycle> getBicycles(){
		return bicycles;
	}

	/**
	 * Adds new bicycle(s) to customer
	 * @param bicyle The customer's new bicycle(s)
	 */
	public void addBicycle(Set<Bicycle> bicycle){
		for(Bicycle nextBicycle: bicycle){
			bicycles.add(nextBicycle);
		}
	}

	/**
	 * Receives information on whether or not the customer has missing payments
	 * @return true if the customer has missing payments
	 */
	public boolean getMissingPayment(){
		return missingPayment;
	}

	/**
	 * Updates information on whether or not the customer has missing payments
	 * @param paymentStatus True if the customer has missing payments, false otherwise
	 */
	public void setMissingPayment(boolean paymentStatus){
		missingPayment = paymentStatus;
	}

	/**
	 * Compares this customer to another customer by comparing their names
	 * @param c The other customer to compare to
	 * @return an integer bigger, smaller or equal to zero dependent on the alphabetical order of the names
	 */
	@Override
	public int compareTo(Customer c) {
		return this.getName().compareToIgnoreCase(c.getName());
	}

	/**
	 * Returns the name of the customer
	 * @return A sting of the name of the customer
	 */
	public String toString() { 
		return name;
	} 
}
