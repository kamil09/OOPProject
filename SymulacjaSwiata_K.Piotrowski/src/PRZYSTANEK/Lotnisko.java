package PRZYSTANEK;

import java.io.Serializable;

/**
 * 
 * @author Kamil Piotrowski
 * Klasa lotniska - dziedziczy po miescie
 *
 */
public class Lotnisko extends Miasto implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2803676409661849527L;

	/**
	 * Konstruktor
	 * @param x		- współrzędna X
	 * @param y		- współrzędna Y
	 * @param name	- nazwa lotniska
	 * @param id	- id lotniska
	 */
	public Lotnisko(int x,int y, String name, int id){
		super(x, y, name, id);
	}

}
