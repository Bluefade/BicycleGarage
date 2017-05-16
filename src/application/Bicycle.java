package application;

/**
 * <h1>Bicycle</h1>
 * The Bicycle class contains the definition 
 * and fundamental information about the bicycle.
 * */
public class Bicycle {
	private String barcode;
	private boolean status;
	
	/**
	 * Creates a new bicycle used to authenticate
	 * the bicycle at the entrance of the garage.
	 * @param barcode of the bicycle
	 */
    public Bicycle(String barcode) {
    	this.barcode = barcode;
    	status = false;
    }
    
    /**
	 * Returns the bicycle's barcode
	 * @return barcode of the bicycle
	 */
    public String getBarcode(){
    	return barcode;
    }
    
    /**
	 * Returns the bicycle's check-in status
	 * @return check-in status of bicycle
	 */
    public boolean checkStatus(){
    	return status;
    }
    
    /**
	 * Changes the check-in status of the bicycle
	 * @param check-in status of bicycle
	 */
    public void setStatus(boolean status){
    	this.status = status;
    }
    
    /**
	 * Returns the barcode of the bicycle
	 * @return the bicycle's barcode
	 */
    public String toString() {
	    return barcode;
	} 
}
