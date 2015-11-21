package PRZYSTANEK;

import java.io.Serializable;

import MAIN.PunktMapy;

/**
 * 
 * @author no-one
 * Klasa określająca skrzyżowanie dziedziczy po punkcie mapy
 *
 */
public class Skrzyzowanie extends PunktMapy implements Serializable{
	
	
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3961080860450191926L;

	/**
	 * Konstruktor
	 * @param x		- współrzędna X
	 * @param y		- współrzędna Y
	 * @param name	- nazwa lotniska
	 * @param id	- id lotniska
	 */
	public Skrzyzowanie(int x,int y, String name,int id){
		super(x, y, name, id);
	}
	
}
