package interfaces;

public interface PincodeTerminal {
	public static final int RED_LED = 0,
							GREEN_LED = 1;
	
	/**
	 *  Registers a new observer to call when a user has pressed a key. 
	 *  @param observer an observer to add
	 */
	public void registerObserver(PincodeObserver observer);
	
	/**
	 * 	Turn on LED for time seconds.
	 * 	@param color The color of the LED, 0=red, 1=green
	 * 	@param time The number of seconds the LED should be turned on
	 */
	public void lightLED(int color, int time);
}

