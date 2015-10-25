package WSZYTKO;
import GUI.GlowneOkno;

/**
 * 
 * @author Kamil Piotrowski
 * Glówna klasa programu
 * 
 */
public class Aplikacja {
	
	/**
	 * Obiekt świata - w nim przechowywane są wszystkie dane
	 */
	static Swiat swiat;
	
	/**
	 * 
	 * @param args - argumenty podane z uruchomieniem programu - nie wykorzystywane
	 * Metoda startowa programu - tworzy świat i odpala okno
	 */
	public static void main(String[] args){ 
		swiat = new Swiat();
		GlowneOkno.launchWindow();
	}


	
	
	
}
