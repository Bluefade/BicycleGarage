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
	 *  Turn on LED for time seconds.
	 * Color: 
	 * color = RED_LED = 0 => red 
	 * color = GREEN_LED = 1 => green 
	 */
	public void lightLED(int color, int time);
}
