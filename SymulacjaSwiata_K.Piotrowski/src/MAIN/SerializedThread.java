package MAIN;

import java.io.Serializable;

public class SerializedThread extends Thread implements Serializable{

	public SerializedThread(Runnable runner) {
		super(runner);
	}
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 9219563585400483232L;

}
