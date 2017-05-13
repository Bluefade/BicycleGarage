package application;

/**
* <h1>Customer</h1>
* The Customer class contains the definition 
* and fundamental information about the customer.
*
*/
public class Customer {
	private String name;
	private int PIN;
	private Bicycle bicycles[];
	private int phoneNr;
	private boolean missingPayment;

	/**
	 * Creates a new Customer
	 * @param name of Customer
	 * @param PIN code of Customer
	 * @param bicycle(s) of the Customer
	 * @param phone number of Customer
	 * @param true if the customer has missing payments
	 */
    public Customer(String name, int PIN, Bicycle bicycles[], int phoneNr, boolean missingPayment){ 
    	this.name = name;
    	this.PIN = PIN;
    	this.bicycles = bicycles;
    	this.phoneNr = phoneNr;
    	this.missingPayment = missingPayment;
	}
	
	/**
	 * Receives the name of Customer
	 * @return name of Customer
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Receives the customer's PIN code
	 * @return customer's PIN code
	 */
	public int getPIN(){
		return PIN;
	}
	
	/**
	 * Receives the customer's phone number 
	 * @return customer's phone number
	 */
	public int getPhoneNr(){
		return phoneNr;
	}
	
	/**
	 * Receives the customer's bicycle(s)
	 * @return customer's bicycle(s)
	 */
	public Bicycle[] getBicycles(){
		return bicycles;
	}
	
	/**
	 * Receives information on whether or not the customer has missing payments
	 * @return true if the customer has missing payments
	 */
	public boolean getMissingPayment(){
		return missingPayment;
	}
	
}
