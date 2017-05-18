package application;

import java.io.Serializable;

/**
 * <h1>Bicycle</h1>
 * The Bicycle class contains the definition 
 * and fundamental information about the bicycle.
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
	 * the bicycle at the entrance of the garage.
	 * @param Barcode The barcode for the bicycle
	 */
    public Bicycle(String barcode) {
    	this.barcode = barcode;
    	status = false;
    }
    
    /**
	 * Returns the bicycle's barcode
	 * @return barcode The barcode for the bicycle
	 */
    public String getBarcode(){
    	return barcode;
    }
    
    /**
	 * Returns the bicycle's check-in status
	 * @return check-in The check-in status of bicycle, true if in the garage
	 */
    public boolean checkStatus(){
    	return status;
    }
    
    /**
	 * Changes the check-in status of the bicycle
	 * @param Check-in The check-in status of bicycle
	 */
    public void setStatus(boolean status){
    	this.status = status;
    }
    
    /**
	 * Returns the barcode of the bicycle
	 * @return barcode The bicycle's barcode
	 */
    public String toString() {
	    return barcode;
	} 
}
