package interfaces;

public interface ElectronicLock {	
	/**   
	 *  Open the lock for timeOpen seconds.
	 *  @param timeOpen The number of seconds the door should be open 
	 */
	public void open(int timeOpen);
}
