package POJAZD;

import MAIN.PunktMapy;

/**
 * Klasa grupująca wszystkie pojazdy
 * @author Kamil Piotrowski
 *
 */
public abstract class Pojazd extends PunktMapy{
	
	/**
	 * Obecne miejsce, jeśli w trakcje lotu/rejsu = null
	 */
	private PunktMapy obecneMiejsce=null;
	
	/**
	 * Kostruktor
	 * @param x - położenie X Pojazdu
	 * @param y - położenie Y Pojazdu
	 * @param name - nazwa pojazdu
	 * @param id - id pojazdu
	 */
	public Pojazd(int x,int y, String name, int id){
		super(x, y, name, id);
	}

	public PunktMapy getObecneMiejsce() {
		return obecneMiejsce;
	}

	public void setObecneMiejsce(PunktMapy obecneMiejsce) {
		this.obecneMiejsce = obecneMiejsce;
	}

}
