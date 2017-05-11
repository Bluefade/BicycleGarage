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
    
    public String getBarcode(){
    	return barcode;
    }
    
    public boolean checkStatus(){
    	return status;
    }
    
    public void setStatus(boolean status){
    	this.status = status;
    }
}
