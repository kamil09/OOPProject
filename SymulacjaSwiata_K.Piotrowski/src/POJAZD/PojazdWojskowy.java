package POJAZD;

import ENUM.Uzbrojenie;
import MAIN.Miasto;

/**
 * 
 * @author Kamil Piotrowski
 * Klasa charakteryzująca Pojazd wojskowy
 *
 */
public abstract class PojazdWojskowy extends Pojazd{

	/**
	 * Rodzaj uzbronjenia jakie posiada pojazd wojskowy
	 */
	private Uzbrojenie bron;
	
	/**
	 * Kostruktor
	 * @param x - położenie X Pojazdu
	 * @param y - położenie Y Pojazdu
	 * @param name - nazwa pojazdu
	 * @param id - id pojazdu
	 */
	public PojazdWojskowy(int x,int y, String name,int id, Miasto miasto){
		super(x, y, name,id, miasto );
	}

	
	public Uzbrojenie getBron() {
		return bron;
	}

	public void setBron(Uzbrojenie bron) {
		this.bron = bron;
	}
}
