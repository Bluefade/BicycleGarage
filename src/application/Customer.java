package application;

import java.util.HashSet;
import java.util.Set;

/**
* <h1>Customer</h1>
* The Customer class contains the definition 
* and fundamental information about the customer.
*
*/
public class Customer {
	private String name;
	private String PIN;
	private Set<Bicycle> bicycles;
	private String phoneNr;
	private boolean missingPayment;

	/**
	 * Creates a new Customer
	 * @param name of Customer
	 * @param phone number of Customer
	 * @param PIN code of Customer
	 */
    public Customer(String name, String phoneNr, String PIN){ 
    	this.name = name;
    	this.phoneNr = phoneNr;
    	this.PIN = PIN;
    	missingPayment = false;
    	bicycles = new HashSet<>();
	}
	
	/**
	 * Receives the name of Customer
	 * @return name of Customer
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Receives the customer's phone number 
	 * @return customer's phone number
	 */
	public String getPhoneNr(){
		return phoneNr;
	}
	
	/**
	 * Receives the customer's PIN code
	 * @return customer's PIN code
	 */
	public String getPIN(){
		return PIN;
	}
	
	/**
	 * Changes the customer's PIN code
	 * @return customer's new PIN code
	 */
	public void setPIN(String PIN){
		this.PIN = PIN;
	}
	
	/**
	 * Receives the customer's bicycle(s)
	 * @return customer's bicycle(s)
	 */
	public Set<Bicycle> getBicycles(){
		return bicycles;
	}
	
	/**
	 * Adds new bicycle(s) to customer
	 * @return customer's new bicycle(s)
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
	 * @return true if the customer has missing payments, false otherwise
	 */
	public void setMissingPayment(boolean paymentStatus){
		missingPayment = paymentStatus;
	}
}
