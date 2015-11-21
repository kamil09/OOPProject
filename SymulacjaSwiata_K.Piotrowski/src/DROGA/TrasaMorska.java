package DROGA;
import java.io.Serializable;

import MAIN.PunktMapy;

/**
 * 
 * @author Kamil Piotrowski
 * Klasa określająca drogę pomiędzy portami, dziedziczy po Drodze
 *
 */
public class TrasaMorska extends Droga implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8822549679088029277L;

	/**
	 * Konstruktor
	 * @param A - punkt mapy A
	 * @param B - lista wszystkich następników punktu A
	 */
	public TrasaMorska(PunktMapy A, PunktMapy B){
		super(A,B);
	}
}
