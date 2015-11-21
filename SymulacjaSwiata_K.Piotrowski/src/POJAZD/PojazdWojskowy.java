package POJAZD;

import java.io.Serializable;

import ENUM.Uzbrojenie;
import PRZYSTANEK.Miasto;

/**
 * 
 * @author Kamil Piotrowski
 * Klasa charakteryzująca Pojazd wojskowy
 *
 */
public abstract class PojazdWojskowy extends Pojazd implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 619507747501080120L;
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
