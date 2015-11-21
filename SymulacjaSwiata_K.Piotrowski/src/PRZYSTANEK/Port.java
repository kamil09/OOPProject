package PRZYSTANEK;

import java.io.Serializable;

/**
 * 
 * @author Kamil Piotrowski
 * Klasa określająca port dzieczyczy po mieście
 */
public class Port extends Miasto implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3448728778133957503L;

	/**
	 * Konstruktor
	 * @param x		- współrzędna X
	 * @param y		- współrzędna Y
	 * @param name	- nazwa lotniska
	 * @param id	- id lotniska
	 */
	public Port(int x,int y, String name, int id){
		super(x, y, name, id);
	}
}
