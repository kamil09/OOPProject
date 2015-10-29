package POJAZD;

/**
 * 
 * @author Kamil Piotrowski
 * Klasa określająca samolot pasażerski
 *
 */
public class SamolotPasazerski extends PojazdPasazerski implements Samolot{

	/**
	 * Kostruktor
	 * @param x - położenie X Pojazdu
	 * @param y - położenie Y Pojazdu
	 * @param name - nazwa pojazdu
	 * @param id - id pojazdu
	 * Losuje także paliwo z przedziału 1000 - 1500
	 */
	public SamolotPasazerski(int x,int y, String name, int id){
		super(x, y, name, id);
	}
}
