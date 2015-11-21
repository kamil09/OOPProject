package MAIN;

import java.io.Serializable;

public class SerializedThread extends Thread implements Serializable{

	private Object objekt = new Object();
	
	public SerializedThread(Runnable runner) {
		super(runner);
	}
	public Object getObjekt() {
		return objekt;
	}
	public void setObjekt(Object objekt) {
		this.objekt = objekt;
	}
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 9219563585400483232L;

}
