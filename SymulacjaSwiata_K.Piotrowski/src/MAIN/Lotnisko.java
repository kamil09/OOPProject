package MAIN;
/**
 * 
 * @author Kamil Piotrowski
 * Klasa lotniska - dziedziczy po punkcie mapy
 *
 */
public class Lotnisko extends Miasto{

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
