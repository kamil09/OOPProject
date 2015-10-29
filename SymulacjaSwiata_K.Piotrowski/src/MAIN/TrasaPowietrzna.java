package MAIN;
import java.util.List;

/**
 * 
 * @author Kamil Piotrowski
 * Klasa określająca drogę pomiedzy lotniskami, dziedziczy po Drodze
 *
 */
public class TrasaPowietrzna extends Droga{
	
	/**
	 * Konstruktor
	 * @param a - punkt mapy A
	 * @param b - lista wszystkich następników punktu A
	 */
	public TrasaPowietrzna(PunktMapy A, List<PunktMapy> B){
		super(A,B);
	}
}
