package MAIN;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import GUI.GlowneOkno;
import GUI.MapPanel;
import POJAZD.Pojazd;

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
	static private Swiat swiat;
	
	static private String nazwaPliku = "serializacjaData.boom";
	
	/**
	 * 
	 * @param args - argumenty podane z uruchomieniem programu - nie wykorzystywane
	 * Metoda startowa programu - tworzy świat i odpala okno
	 */
	public static void main(String[] args) throws Exception{ 
		swiat = new Swiat();
		GlowneOkno.launchWindow();
	}
	/**
	 * Zwraca obiekt świata
	 * @return obiekt świata
	 */
	public static Swiat getSwiat(){
		return swiat;
	}

	/**
	 * Wykonuje serializację (zapisanie serializacji)
	 * @throws Exception
	 */
	public static void serializacja() throws Exception{
		ObjectOutputStream out = new ObjectOutputStream( new FileOutputStream(nazwaPliku));
	    out.writeObject(swiat);
	    out.close();
	}
	
	/**
	 * Wykonuje deserializację (wczytanie symulacji)
	 * @throws Exception
	 */
	public static void deserializacja() throws Exception{
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(nazwaPliku));
	    Swiat newSwiat = (Swiat) in.readObject();
	    newSwiat.loadImages();
	    swiat=null;
	    
	    swiat = newSwiat;
	    in.close();
	    for(Pojazd p : swiat.getListaPojazdow() ){
	    	MapPanel.addDoRysowania(p);
	    }
	}
	
	
	
}
