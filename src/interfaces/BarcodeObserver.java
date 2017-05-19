package interfaces;

public interface BarcodeObserver {
	/**   
	 * Will be called when a user has used the barcode scanner.
	 * s is a string of 5 characters in the interval ['0', '9'].
	 * @param s The barcode to handle
	 */
	public void handleBarcode(String s);
}