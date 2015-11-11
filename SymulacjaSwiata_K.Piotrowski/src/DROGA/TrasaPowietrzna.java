package DROGA;
import MAIN.PunktMapy;

/**
 * 
 * @author Kamil Piotrowski
 * Klasa określająca drogę pomiedzy lotniskami, dziedziczy po Drodze
 *
 */
public class TrasaPowietrzna extends Droga{
	
	/**
	 * Konstruktor
	 * @param A - punkt mapy A
	 * @param B - lista wszystkich następników punktu A
	 */
	public TrasaPowietrzna(PunktMapy A, PunktMapy B){
		super(A,B);
	}
}
