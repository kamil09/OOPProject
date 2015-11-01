package MAIN;
import GUI.GlowneOkno;

/**
 * 
 * @author Kamil Piotrowski
 * Glówna klasa programu
 * 
 */
public abstract class Aplikacja {
	
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
		Runnable runner = swiat;
		Thread thread = new Thread(runner);
		thread.start();
		GlowneOkno.launchWindow();
	}


	
	
	
}
