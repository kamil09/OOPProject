package MAIN;

import java.io.Serializable;

public class Monitor implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8631784090454832259L;

	/**
	 * Obiekt który w danej chwili używa tego Monitora do synchronizacji
	 * Potrzebne przy wczytywaniu serializacji!
	 */
	private PunktMapy wlasciciel = null;

	public PunktMapy getWlasciciel() {
		return wlasciciel;
	}

	public void setWlasciciel(PunktMapy wlasciciel) {
		this.wlasciciel = wlasciciel;
	}
}
