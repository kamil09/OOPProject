package MAIN;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
	/**
	 * Nazwa pliku w którym zapisujemy symulację
	 */
	static private String nazwaPliku = "serializacjaData.boom";
	
	/**
	 * 
	 * @param args - argumenty podane z uruchomieniem programu - nie wykorzystywane
	 * Metoda startowa programu - tworzy świat i odpala okno
	 */
	public static void main(String[] args){ 
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
	 * @throws Exception Wyjątek
	 */
	public static void serializacja() throws Exception{
		ObjectOutputStream out = new ObjectOutputStream( new FileOutputStream(nazwaPliku));
	    out.writeObject(swiat);
	    out.close();
	    GlowneOkno.showDialog("Pomyslnie zapisano symulacje");
	}
	
	/**
	 * Wykonuje deserializację (wczytanie symulacji)
	 * @throws IOException 				Wyjątki
	 * @throws ClassNotFoundException	Wyjątki
	 */
	public static void deserializacja() throws IOException, ClassNotFoundException{
		ObjectInputStream in = null;
		boolean canLoad = true;
		try {
			in = new ObjectInputStream(new FileInputStream(nazwaPliku));
		} catch (FileNotFoundException e) {
			canLoad=false;
			GlowneOkno.showDialog("Nie znaleziono pliku serializacji!");
			//e.printStackTrace();
		} catch (IOException e) {
			canLoad=false;
			//e.printStackTrace();
		}
	    if(canLoad==true){
			Swiat newSwiat = (Swiat) in.readObject();
		    newSwiat.loadImages();
		    while(!swiat.getListaPojazdow().isEmpty()){
		    	swiat.getListaPojazdow().get(0).removePojazd(0);
		    }
		    while(!swiat.getListaPasazerow().isEmpty()){
		    	swiat.getListaPasazerow().get(0).removePasazer();
		    }
		    swiat = newSwiat;
		    in.close();
		    for(Object th : swiat.getListaPojazdow()){
		    	Runnable runner = (Runnable) th;
		    	SerializedThread thread = new SerializedThread(runner);
		    	thread.start();
		    	MapPanel.addDoRysowania((Pojazd)th);
		    }
		    for(Object th : swiat.getListaPasazerow() ){
		    	Runnable runner = (Runnable) th;
		    	SerializedThread thread = new SerializedThread(runner);
		    	thread.start();
		    }
		    
		    GlowneOkno.showDialog("Pomyslnie wczytano symulacje");
	    }
	}
	
	
	
}
