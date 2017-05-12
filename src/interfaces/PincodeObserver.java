package interfaces;

public interface PincodeObserver {
	/* Will be called when a user has pressed a key at the
	 * keypad at pincode reader. The following characters could be
	 * pressed: '0', '1',... '9', '*', '#'. */
	public void handleCharacter(char c);
}
