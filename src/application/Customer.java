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
	private int barcodes[];
	private int phoneNr;
	private boolean missingPayment;

	/**
	 * Receives the name of Customer
	 * @return the name of Customer
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Receives the customer's PIN code
	 * @return the customer's PIN code
	 */
	public int getPIN(){
		return PIN;
	}
	
	/**
	 * Receives the customer's phone number 
	 * @return the customer's phone number
	 */
	public int getPhoneNr(){
		return phoneNr;
	}
	
	/**
	 * Receives the barcodes of the customer's registered bicyle(s)
	 * @return the barcodes of the customer's registered bicyle(s)
	 */
	public int[] getBarcodes(){
		return barcodes;
	}
	
	/**
	 * Receives information on whether or not the customer has missing payments
	 * @return true if the customer has missing payments
	 */
	public boolean getMissingPayment(){
		return missingPayment;
	}
	
}
