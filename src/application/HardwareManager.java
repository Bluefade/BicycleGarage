package application;

import java.util.Set;

import interfaces.*;
import testdrivers.*;

/**
 * <h1>HardwareManager</h1> Contains all functions and routines for the
 * hardware. The hardware includes barcode readers, PIN code terminals, a
 * barcode printer and an electronic lock.
 *
 * * @version 1.0
 * 
 * @author grupp 9
 */

public class HardwareManager {
	BarcodeScanner entryScanner;
	BarcodeScanner exitScanner;
	ElectronicLock entryLock;
	ElectronicLock exitLock;
	PincodeTerminal entryTerminal;
	PincodeTerminal exitTerminal;
	BarcodePrinter printer;

	Set<Customer> cList;

	String pin;
	int pinCounter;
	String barcode;
	int barcodeCounter;
	boolean barcodeByHand = false;

	/**
	 * Creates a class that manage all the hardware for the garage. The hardware
	 * includes barcode readers, PIN code terminals, a barcode printer and an
	 * electronic lock.
	 * 
	 * @param cList
	 *            The list of registered Customer for the garage
	 **/
	public HardwareManager(Set<Customer> cList) {
		entryScanner = new BarcodeScannerTestDriver("Entry Scanner", 0, 0);
		entryScanner.registerObserver(new EntryBarcodeObserver());
		exitScanner = new BarcodeScannerTestDriver("Exit Scanner", 0, 0);
		exitScanner.registerObserver(new ExitBarcodeObserver());

		entryLock = new ElectronicLockTestDriver("Entry Lock", 0, 0);
		exitLock = new ElectronicLockTestDriver("Exit Lock", 0, 0);

		entryTerminal = new PincodeTerminalTestDriver("Entry Terminal", 0, 0);
		entryTerminal.registerObserver(new EntryTerminalObserver());
		exitTerminal = new PincodeTerminalTestDriver("Exit Terminal", 0, 0);
		exitTerminal.registerObserver(new ExitTerminalObserver());

		printer = new BarcodePrinterTestDriver("Printer", 0, 0);

		this.cList = cList;
	}

	/**Prints a barcode.
	 * @param barcode The barcode that shall be printed, barcode is 5 characters that can be 0-9 **/
	public void printBarcode(String barcode) {
			printer.printBarcode(barcode);
	}

	private Customer findCustomer(String PIN) {
		for (Customer c : cList) {
			if (c.getPIN().compareTo(PIN) == 0) {
				return c;
			}
		}
		return null;

	}

	private Bicycle findBicycle(String barcode) {
		for (Customer c : cList) {
			Set<Bicycle> bicycleList = c.getBicycles();
			for (Bicycle b : bicycleList) {

				if (b.getBarcode().compareTo(barcode) == 0) {
					return b;
				}
			}
		}
		return null;

	}

	private boolean checkPIN(String PIN) { // true om rätt PIN är inskriven
		if (findCustomer(PIN) != null) {
			return true;
		}
		return false;
	}

	private boolean checkBarcode(String barcode) {// true om rätt barcode är
													// inskriven
		if (findBicycle(barcode) != null) {
			return true;
		}
		return false;

	}

	private boolean checkPinBarcode(String PIN, String barcode) {
		// true om pin och barcode överrensstämmer
		Customer c = findCustomer(PIN);
		if (c != null) {
			Set<Bicycle> bicycleList = c.getBicycles();
			for (Bicycle b : bicycleList) {

				if (b.getBarcode().compareTo(barcode) == 0) {
					return true;
				}
			}
		}
		return false;

	}

	private void timeOut() { // "Raderar" minnet för pin och barcode
		pin = "";
		pinCounter = 0;
		barcode = "";
		barcodeCounter = 0;
		barcodeByHand = false;
	}

	private class EntryBarcodeObserver implements BarcodeObserver {
		// scannar barcode, ingång
		@Override
		public void handleBarcode(String s) {
			// Kod för vad som ska hända när en streckkod skannas start
			if (checkBarcode(s)) { // kollar om barcoden existerar
				entryLock.open(15);
				entryTerminal.lightLED(PincodeTerminal.GREEN_LED, 15);
				findBicycle(s).setStatus(true);
			} else {
				entryTerminal.lightLED(PincodeTerminal.RED_LED, 3);
			}

		}
	}

	private class ExitBarcodeObserver implements BarcodeObserver {
		// scannar barcode, utgång
		@Override
		public void handleBarcode(String s) {
			if (checkPinBarcode(pin, s)) {
				if (findBicycle(s).checkStatus()) { // true, cykeln är i garaget
					exitLock.open(15);
					exitTerminal.lightLED(PincodeTerminal.GREEN_LED, 15);
					findBicycle(s).setStatus(false);
				} // cykeln är "Withdrawn"
				exitTerminal.lightLED(PincodeTerminal.GREEN_LED, 3);
				exitTerminal.lightLED(PincodeTerminal.RED_LED, 3);
			} else {
				exitTerminal.lightLED(PincodeTerminal.RED_LED, 3);
			}

			timeOut(); // resetar terminalen

		}
	}

	private class EntryTerminalObserver implements PincodeObserver {
		@Override
		public void handleCharacter(char s) {

			if (s == '#') {
				// vill skriva in barcoden
				barcodeByHand = true;

			} else if (s == '*') {
				// timeOut
				entryTerminal.lightLED(PincodeTerminal.RED_LED, 3);
				timeOut();

			} else {

				if (barcodeByHand) {
					if (barcodeCounter < 5) {
						barcode = barcode + s;
						barcodeCounter++;
					}

					if (barcodeCounter == 5) {
						if (checkBarcode(barcode)) {
							entryTerminal.lightLED(PincodeTerminal.GREEN_LED, 15);
							entryLock.open(15);
							findBicycle(barcode).setStatus(true);
						} else {
							entryTerminal.lightLED(PincodeTerminal.RED_LED, 3);
						}
						timeOut();
					}

				} else {
					if (pinCounter < 4) {
						pin = pin + s;
						pinCounter++;
					}

					if (pinCounter == 4) {
						if (checkPIN(pin)) {
							entryTerminal.lightLED(PincodeTerminal.GREEN_LED, 15);
							entryLock.open(15);
						} else {
							entryTerminal.lightLED(PincodeTerminal.RED_LED, 3);
						}
						timeOut();
					}
				}

			}
		}
	}

	private class ExitTerminalObserver implements PincodeObserver {
		@Override

		public void handleCharacter(char s) {

			if (s == '#') {
				// vill skriva in barcoden
				barcodeByHand = true;

			} else if (s == '*') {
				// timeOut
				exitTerminal.lightLED(PincodeTerminal.RED_LED, 3);
				timeOut();

			} else {

				if (barcodeByHand) {
					if (barcodeCounter < 5) {
						barcode = barcode + s;
						barcodeCounter++;
					}

					if (barcodeCounter == 5 && pinCounter == 4) {
						if (checkPinBarcode(pin, barcode)) {
							if (findBicycle(barcode).checkStatus()) {
								exitTerminal.lightLED(PincodeTerminal.GREEN_LED, 15);
								exitLock.open(15);
								findBicycle(barcode).setStatus(false);
							} else {
								exitTerminal.lightLED(PincodeTerminal.GREEN_LED, 3);
								exitTerminal.lightLED(PincodeTerminal.RED_LED, 3);
							}

						} else {
							exitTerminal.lightLED(PincodeTerminal.RED_LED, 3);
						}
						timeOut();
					}
					if (barcodeCounter == 5 && pinCounter != 4) {
						// ifall man inte har skrivit in pin rätt
						exitTerminal.lightLED(PincodeTerminal.RED_LED, 3);
						timeOut();
					}

				} else {
					if (pinCounter < 4) {
						pin = pin + s;
						pinCounter++;
					} else if (pinCounter <4){
						exitTerminal.lightLED(PincodeTerminal.RED_LED, 3);
						timeOut();
					}
					
					

				}

			}
		}
	}

}
