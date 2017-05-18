package application;

import java.io.Serializable;

/**
 * <h1>Bicycle</h1>
 * The Bicycle class contains the definition 
 * and fundamental information about the Bicycle.
 * 
 * @version 1.0
 * @author Group 9
 * */
public class Bicycle implements Serializable {
	private String barcode;
	private boolean status;
	private static final long serialVersionUID = 3L;
	
	/**
	 * Creates a new bicycle used to authenticate
	 * the Bicycle at the entrance of the garage
	 * @param barcode The barcode for the Bicycle
	 */
    public Bicycle(String barcode) {
    	this.barcode = barcode;
    	status = false;
    }
    
    /**
	 * Returns the Bicycle's barcode
	 * @return The barcode for the Bicycle
	 */
    public String getBarcode(){
    	return barcode;
    }
    
    /**
	 * Returns the Bicycle's check-in status
	 * @return The check-in status of Bicycle, true if Bicycle is in the garage
	 */
    public boolean checkStatus(){
    	return status;
    }
    
    /**
	 * Changes the check-in status of the Bicycle
	 * @param status The check-in status of Bicycle, true if Bicycle is in the garage
	 */
    public void setStatus(boolean status){
    	this.status = status;
    }
    
    /**
	 * Returns the barcode of the Bicycle
	 * @return The Bicycle's barcode
	 */
    public String toString() {
	    return barcode;
	} 
}
