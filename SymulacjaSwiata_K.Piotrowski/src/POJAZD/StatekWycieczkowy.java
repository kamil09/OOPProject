package POJAZD;

/**
 * 
 * @author Kamil Piotrowski
 * Klasa określająca statek wycieczkowy
 *
 */
public class StatekWycieczkowy extends PojazdPasazerski implements Statek{

	/**
	 * Kostruktor
	 * @param x - położenie X Pojazdu
	 * @param y - położenie Y Pojazdu
	 * @param name - nazwa pojazdu
	 * @param id - id pojazdu
	 * Losuje także paliwo z przedziału 1000 - 1500
	 */
	public StatekWycieczkowy(int x,int y, String name,int id){
		super(x, y, name, id);
	}
}
