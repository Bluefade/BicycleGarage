package interfaces;

public interface BarcodeScanner {
	/** 
	 *  Registers a new observer to call when a user has used the scanner. 
	 *  @param observer an observer to add
	 */
	public void registerObserver(BarcodeObserver observer);	
}
