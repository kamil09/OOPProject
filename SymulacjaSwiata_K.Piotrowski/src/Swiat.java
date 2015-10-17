import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;



public class Swiat {
	
	private static BufferedImage bufferImage;
	private static Graphics2D imageGraphics;
	
	private static List<Pasazer> listaPasazerow = new ArrayList<Pasazer>();
	private static List<Pojazd> listaPojazdow = new ArrayList<Pojazd>();
	private static List<Droga> listaTras = new ArrayList<Droga>();
	
	private static List<PunktMapy> cityList = new ArrayList<PunktMapy>();
	private static List<PunktMapy> skrzyzowanieList = new ArrayList<PunktMapy>();

	private static List<Runnable> runnerList = new ArrayList<Runnable>();
	private static List<Thread> threadsList = new ArrayList<Thread>();
	
	public Swiat(){
		try {
			bufferImage = ImageIO.read(new File("src/mapa2.png"));
			setImageGraphics((Graphics2D)bufferImage.getGraphics());
			generujListeMiast();
			addPasazer();
			addPasazer();
			addPasazer();
		} catch (IOException ex) { 
			System.out.println("Nie można wczytać pliku mapy");
			System.exit(2);
		}    
	}
	public static BufferedImage getBufferImage(){
		return bufferImage;
	}
	
	public static void addPasazer(){
		Pasazer nowaOsoba= new Pasazer();
		listaPasazerow.add(nowaOsoba);
		runnerList.add( nowaOsoba );
		threadsList.add(new Thread(runnerList.get(runnerList.size()-1)));
		threadsList.get(threadsList.size()-1).start();;
	}
	public static void addSamolotPasazerski(){
		System.out.println("Dodano samolot pasażerski!");
	}
	public static void addSamolotWojskowy(){
		System.out.println("Dodano samolot wojskowy!");
	}
	public static void addStatekPasazerski(){
		System.out.println("Dodano statek pasazerski!");
	}
	public static void addLotniskowiec(){
		System.out.println("Dodano lotniskowiec!");
	}
	public static List<Pasazer> getListaPasazerow(){
		return listaPasazerow;
	}
	
//	public static void addSamolot(){
//		listaPojazdow.add(new Samolot());
//	}
//	public static void addStatek(){
//		listaPojazdow.add(new Statek());
//	}
	
	public static List<Pojazd> getListaPojazdow(){
		return listaPojazdow;
	}
	public static Graphics2D getImageGraphics() {
		return imageGraphics;
	}
	public static void setImageGraphics(Graphics2D graphics) {
		Swiat.imageGraphics = graphics;
	}
	public static List<PunktMapy> getCityList(){
		return cityList;
	}
	
	
	
	public void generujListeMiast(){
		/*
		 * LOTNISKA:
		 * 1.	X 507	Y 519 
		 * 2.	X 663	Y 1455
		 * 3.	X 1959	Y 201
		 * 4.	X 2373	Y 753
		 * 5.	X 3048	Y 384
		 * 6.	X 3558	Y 852
		 * 7.	X 3471	Y 2850
		 * 8.	X 3255	Y 3618
		 * 9.	X 2046	Y 3747
		 * 10.	X 888	Y 3558
		 * 11.	X 903	Y 2918
		 * 
		 * PORTY:
		 * 1.	X 2379	Y 987
		 * 2.	X 3141	Y 1140
		 * 3.	X 3366	Y 2508
		 * 4.	X 2109	Y 2256
		 * 5.	X 1767	Y 1593
		 * SKRZYZOWANIA
		 * 1.	X 1111	Y 995
		 * 2.	1479	Y 649
		 * 3.	X 1512	Y 1291
		 * 4.	X 1749	Y 687
		 * 5.	X 2766	Y 539
		 * 6.	X 3080	Y 660
		 * 7.	X 2013	Y 3239
		 * 8.	X 1518	Y 3371
		 * SKRZYZOWANIA MORSKIE
		 * 1.	X 2293	Y 1419
		 * 2.	X 2183	Y 1848
		 * 3.	X 2596	Y 1320
		 * 4.	X 2398	Y 1947
		 * 5.	X 2755	Y 1556
		 */
		//LOTNISKA:
		cityList.add(new Lotnisko(507,519, "Blue City") );
		cityList.add(new Lotnisko(663,1455, "Red City") );
		cityList.add(new Lotnisko(1959,201, "Black City") );
		cityList.add(new Lotnisko(2373,753, "White City") );
		cityList.add(new Lotnisko(3048,384, "Green City") );
		cityList.add(new Lotnisko(3558,852, "Orange City") );
		cityList.add(new Lotnisko(3471,2850, "Blue City") );
		cityList.add(new Lotnisko(3255,3618, "Gray City") );
		cityList.add(new Lotnisko(2046,3747, "Purple City") );
		cityList.add(new Lotnisko(888,3558, "Brown City") );
		cityList.add(new Lotnisko(903,2918, "Yellow City") );
		//PORTY
		cityList.add(new Port(2379, 987, "Blue Port"));
		cityList.add(new Port(3141, 1140, "Red Port"));
		cityList.add(new Port(3366, 2508, "Green Port"));
		cityList.add(new Port(2109, 2256, "Black Port"));
		cityList.add(new Port(1767, 1593, "Gray Port"));
		//Skrzyzowania
		skrzyzowanieList.add(new Skrzyzowanie(1111, 995, null, null, null, null, null) );
		skrzyzowanieList.add(new Skrzyzowanie(1479, 649, null, null, null, null, null) );
		skrzyzowanieList.add(new Skrzyzowanie(1512, 1291, null, null, null, null, null) );
		skrzyzowanieList.add(new Skrzyzowanie(1749, 687, null, null, null, null, null) );
		skrzyzowanieList.add(new Skrzyzowanie(2766, 539, null, null, null, null, null) );
		skrzyzowanieList.add(new Skrzyzowanie(3080, 660, null, null, null, null, null) );
		skrzyzowanieList.add(new Skrzyzowanie(2013, 3239, null, null, null, null, null) );
		skrzyzowanieList.add(new Skrzyzowanie(2580, 3416, null, null, null, null, null) );
		skrzyzowanieList.add(new Skrzyzowanie(1518, 3371, null, null, null, null, null) );
		skrzyzowanieList.add(new Skrzyzowanie(1352, 1176, null, null, null, null, null) );
		skrzyzowanieList.add(new Skrzyzowanie(1644, 1056, null, null, null, null, null) );
		//Skrzyzowania morskie  
		skrzyzowanieList.add(new Skrzyzowanie(2293, 1419, null, null, null, null, null) );
		skrzyzowanieList.add(new Skrzyzowanie(2183, 1848, null, null, null, null, null) );
		skrzyzowanieList.add(new Skrzyzowanie(2596, 1320, null, null, null, null, null) );
		skrzyzowanieList.add(new Skrzyzowanie(2398, 1947, null, null, null, null, null) );
		skrzyzowanieList.add(new Skrzyzowanie(2755, 1556, null, null, null, null, null) );
		
		
		
	}
	public static List<Droga> getListaTras() {
		return listaTras;
	}
	public static List<PunktMapy> getSkrzyzowanieList() {
		return skrzyzowanieList;
	}
	
}
