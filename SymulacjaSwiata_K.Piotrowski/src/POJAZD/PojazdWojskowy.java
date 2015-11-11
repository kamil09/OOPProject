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
	 * @param d - położenie X Pojazdu
	 * @param e - położenie Y Pojazdu
	 * @param name - nazwa pojazdu
	 * @param id - id pojazdu
	 * @param miasto - referencja na miasto w którym tworzy się pojazd
	 */
	public PojazdWojskowy(double d,double e, String name,int id, Miasto miasto){
		super(d, e, name,id, miasto );
	}


	public Uzbrojenie getBron() {
		return bron;
	}

	public void setBron(Uzbrojenie bron) {
		this.bron = bron;
	}
}
