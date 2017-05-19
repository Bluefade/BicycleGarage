package interfaces;

public interface BarcodePrinter {
	/** 
	 * Prints a barcode. The barcode should be a string of 
	 * 5 characters in the interval ['0', '9']. 
	 * @param s The barcode to print
	 */
	public void printBarcode(String s);
}

