package MAIN;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import GUI.GlowneOkno;

/**
 * 
 * @author Kamil Piotrowski
 * Glówna klasa programu
 * 
 */
public abstract class Aplikacja implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6169664043917105401L;
	/**
	 * Obiekt świata - w nim przechowywane są wszystkie dane
	 */
	static Swiat swiat;
	
	/**
	 * 
	 * @param args - argumenty podane z uruchomieniem programu - nie wykorzystywane
	 * Metoda startowa programu - tworzy świat i odpala okno
	 */
	public static void main(String[] args) throws Exception{ 
		swiat = new Swiat();
		Runnable runner = swiat;
		Thread thread = new Thread(runner);
		thread.start();
		GlowneOkno.launchWindow();
		
		String nazwaPliku = "/tmp/serializacjaData.cos";
		
	    ObjectOutputStream out = new ObjectOutputStream( new FileOutputStream(nazwaPliku));
	    out.writeObject(swiat);
	    out.close();
	    
	    ObjectInputStream in = new ObjectInputStream(new FileInputStream(nazwaPliku));
	    Swiat newSwiat = (Swiat) in.readObject();
	    newSwiat.loadImages();
	    swiat = newSwiat;
	    in.close();
	}
	
	public static Swiat getSwiat(){
		return swiat;
	}


	
	
	
}
