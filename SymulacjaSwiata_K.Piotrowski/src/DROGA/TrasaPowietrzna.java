package DROGA;
import java.io.Serializable;

import MAIN.PunktMapy;

/**
 * 
 * @author Kamil Piotrowski
 * Klasa określająca drogę pomiedzy lotniskami, dziedziczy po Drodze
 *
 */
public class TrasaPowietrzna extends Droga implements Serializable{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 683003902990164388L;

	/**
	 * Konstruktor
	 * @param A - punkt mapy A
	 * @param B - lista wszystkich następników punktu A
	 */
	public TrasaPowietrzna(PunktMapy A, PunktMapy B){
		super(A,B);
	}
}
