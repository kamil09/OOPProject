package MAIN;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
	 * Monitor serializacji
	 */
	static private Monitor serializacjaMonitor = new Monitor();
	
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
	 * @throws InterruptedException 
	 * @throws Exception Wyjątek
	 */
	@SuppressWarnings("deprecation")
	public static void serializacja() throws InterruptedException{
			ObjectOutputStream out;
			
			for(Thread th : Aplikacja.getSwiat().getThreadList()) th.suspend();
			try {
				out = new ObjectOutputStream( new FileOutputStream(nazwaPliku));
				out.writeObject(swiat);
			    out.close();
			    GlowneOkno.showDialog("Pomyslnie zapisano symulacje");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
				GlowneOkno.showDialog("Niepowodzenie podczas zapisu symulacji");
			}
			for(Thread th : Aplikacja.getSwiat().getThreadList()) th.resume();	
	    
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
			GlowneOkno.showDialog("Niepowodzenie podczas odczytu symulacji");
			//e.printStackTrace();
		}
	    if(canLoad==true){
			Swiat newSwiat = (Swiat) in.readObject();
			in.close();
		    newSwiat.loadImages();
		    while(!swiat.getListaPojazdow().isEmpty()){
		    	swiat.getListaPojazdow().get(0).removePojazd(0);
		    }
		    while(!swiat.getListaPasazerow().isEmpty()){
		    	swiat.getListaPasazerow().get(0).removePasazer();
		    }
		    
		    swiat = newSwiat;
		    swiat.setThreadList(null);
		    List<Thread> thList = new ArrayList<Thread>();
		    
		    for(Object th : swiat.getListaPojazdow()){
		    	Runnable runner = (Runnable) th;
		    	Thread thread = new Thread(runner);
		    	thList.add(thread);
		    	thread.start();
		    }
		    for(Object th : swiat.getListaPasazerow() ){
		    	Runnable runner = (Runnable) th;
		    	Thread thread = new Thread(runner);
		    	thList.add(thread);
		    	thread.start();
		    }
		    swiat.setThreadList(thList);
		    synchronized (swiat.getCanAddPojazdObject()){
		    	for(Pojazd p : swiat.getListaPojazdow()) MapPanel.addDoRysowania((Pojazd)p);
	    	}
		    GlowneOkno.showDialog("Pomyslnie wczytano symulacje");
	    }
	}
	public static Monitor getSerializacjaMonitor() {
		return serializacjaMonitor;
	}
	public static void setSerializacjaMonitor(Monitor serializacjaMonitor) {
		Aplikacja.serializacjaMonitor = serializacjaMonitor;
	}
	
	
	
}
