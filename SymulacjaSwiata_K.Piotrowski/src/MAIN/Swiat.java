package MAIN;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import DROGA.Droga;
import DROGA.TrasaMorska;
import DROGA.TrasaPowietrzna;
import GUI.MapPanel;
import PASAZER.Pasazer;
import POJAZD.Lotniskowiec;
import POJAZD.Pojazd;
import POJAZD.StatekWycieczkowy;

/**
 * 
 * @author Kamil Piotrowski
 * Klasa świata programu
 * Znajdują się w niej wszystkie najważniejsze dane
 *
 */
public class Swiat implements Runnable {
	
	/**
	 * Obraz tła
	 */
	private static BufferedImage bufferImage;
	/**
	 * Obray pojazdów
	 * [0] - lotniskowiec
	 * [1] - statek pasazersi
	 * [2] - mysliwiec
	 * [3] - samolot pasazerski
	 */
	private static List<BufferedImage> pojazdyImage = new ArrayList<BufferedImage>();
	/**
	 * Do generowania ID pojazdów
	 */
	private static int idGenerator=0;
	/**
	 * Lista wszystkich pasażerów
	 */
	private static List<Pasazer> listaPasazerow = new ArrayList<Pasazer>();
	/**
	 * Lista wszystkich pojazdów
	 */
	private static List<Pojazd> listaPojazdow = new ArrayList<Pojazd>();
	/**
	 * Lista wszystkich tras
	 */
	private static List<Droga> listaTras = new ArrayList<Droga>();
	/**
	 * Lista wszystkich miast
	 */
	private static List<Miasto> cityList = new ArrayList<Miasto>();
	/**
	 * Lista skrzyżowań
	 */
	private static List<PunktMapy> skrzyzowanieList = new ArrayList<PunktMapy>();
	/**
	 * Lista do wątków
	 */
	private static List<Runnable> runnerList = new ArrayList<Runnable>();
	/**
	 * Lista do wątków
	 */
	private static List<Thread> threadsList = new ArrayList<Thread>();
	/**
	 * Czy istnieje lotniskowiec, lewsza jedna zmienna niż szukanie w liście pojazdów
	 * Nie można stworzyć samolotu wojskowego bez lotniskowca!
	 */
	private static boolean czyIstniejeLotniskowiec=false;
	/**
	 * Nie można dodawać nowego pojazdu podczas przechodzenia po pętli i wyświetlania na ekranie!!!
	 */
	private static boolean canAddPojazd=true;
	
	/**
	 * Konstruktor świata
	 * Wczytuje obraz tła
	 */
	public Swiat(){
		try {
			bufferImage = ImageIO.read(new File("src/mapa2.png"));
			pojazdyImage.add(ImageIO.read(new File("src/lotniskowiec.png")));
			pojazdyImage.add(ImageIO.read(new File("src/lotniskowiec.png")));
			pojazdyImage.add(ImageIO.read(new File("src/mysliwiec.png")));
			//pojazdyImage.add(ImageIO.read(new File("src/samolot.png")));
			generujListeMiast();
			for(int i=0;i<100;i++) addPasazer();
		} catch (IOException ex) { 
			System.out.println("Nie można wczytać pliku mapy");
			System.exit(2);
		}    
	}
	public static BufferedImage getBufferImage(){
		return bufferImage;
	}
	/**
	 * Dodawanie pasażera, start wątku
	 */
	public static void addPasazer(){
		Pasazer nowaOsoba= new Pasazer();
		listaPasazerow.add(nowaOsoba);
		runnerList.add( nowaOsoba );
		threadsList.add(new Thread(runnerList.get(runnerList.size()-1)));
		threadsList.get(threadsList.size()-1).start();;
	}
	/**
	 * Dodawanie samolotu pasażerskiego
	 */
	public static void addSamolotPasazerski(){
		System.out.println("Dodano samolot pasażerski!");
	}
	/**
	 * Dodawanie samolotu wojskowego
	 */
	public static void addSamolotWojskowy(){
		if(czyIstniejeLotniskowiec==true){
			System.out.println("Dodano samolot wojskowy!");
		}
		else
			System.out.println("Nie możesz stworzyć samolotu wojskowego jesli nie posiadasz lotniskowca!");
	}
	/**
	 * Dodawanie statku pasażerskiego
	 */
	public static void addStatekPasazerski(){
		Port port = Swiat.getRandomPort();
		if (port!=null){
			StatekWycieczkowy statek= new StatekWycieczkowy(port.getKoorX(), port.getKoorY(), "Statek Wycieczkowy"+Swiat.generateId(), Swiat.idGenerator, port);
			addPojazd(port,statek);
		
		}
	}
	/**
	 * Dodawanie lotniskowca
	 */
	public static void addLotniskowiec(){
		Port port = Swiat.getRandomPort();
		if(port!=null){
			Lotniskowiec lotniskowiec = new Lotniskowiec(port.getKoorX(), port.getKoorY(), "Lotniskowiec"+Swiat.generateId(), Swiat.idGenerator, port);
			addPojazd(port,lotniskowiec);
		}
	}
	private static void addPojazd(Miasto miasto, Pojazd pojazd){
		while(!canAddPojazd)
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			};
			listaPojazdow.add(pojazd);
			miasto.getListaPojazdow().add(pojazd);
			miasto.setPojemosc(miasto.getPojemosc()-1);
			runnerList.add(pojazd);
			threadsList.add(new Thread(runnerList.get(runnerList.size()-1)));
			threadsList.get(threadsList.size()-1).start();
			if(czyIstniejeLotniskowiec==false) setCzyIstniejeLotniskowiec(true);
			MapPanel.addDoRysowania(pojazd);
			
	}
	
	public static List<Pasazer> getListaPasazerow(){
		return listaPasazerow;
	}
	/**
	 * Generuje listę miast oraz tras
	 */
	public void generujListeMiast(){
		/**
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
		 * 11.	X 935	Y 2849
		 * 12.	X 1859	Y 2332 
		 * 
		 * PORTY:
		 * 1.	X 2379	Y 987
		 * 2.	X 3141	Y 1140
		 * 3.	X 3366	Y 2508
		 * 4.	X 2109	Y 2256
		 * 5.	X 781	Y 1683
		 * SKRZYZOWANIA
		 * 1.	X 1497	Y 654
		 * 2.	X 1944	Y 705
		 * 3.	X 2799	Y 528
		 * 4.	X 1578	Y 3369
		 * 5.	X 1974	Y 3264
		 * 6.	X 2682	Y 3069
		 * SKRZYZOWANIA MORSKIE
		 * 1.	X 2283	Y 1332
		 * 2.	X 2571	Y 1275
		 * 3.	X 2757	Y 1563
		 */
		//LOTNISKA:
		cityList.add(new Lotnisko(507,519, "Blue City", 0) );				
		cityList.add(new Lotnisko(663,1455, "Red City", 1) );				
		cityList.add(new Lotnisko(1959,201, "Black City", 2) );
		cityList.add(new Lotnisko(2373,753, "White City", 3) );
		cityList.add(new Lotnisko(3558,852, "Orange City", 4) );
		cityList.add(new Lotnisko(3471,2850, "Brown City", 5) );
		cityList.add(new Lotnisko(2046,3747, "Purple City", 6) );
		cityList.add(new Lotnisko(935,2849, "Yellow City", 7) );
		cityList.add(new Lotnisko(1859,2332, "The Middle of Nowhere", 8) );
		//LOTNISKA WOJSKOWE
		cityList.add(new Lotnisko(1132,268, "Green Guns", 9) );
		cityList.add(new Lotnisko(3048,384, "Red Guns", 10) );
		cityList.add(new Lotnisko(3255,3618, "Blue Guns", 11) );
		cityList.add(new Lotnisko(888,3558, "Yellow Guns", 12) );
		//PORTY
		cityList.add(new Port(2379, 987, "Blue Port", 13));
		cityList.add(new Port(3141, 1140, "Red Port", 14));
		cityList.add(new Port(3366, 2508, "Green Port", 15));
		cityList.add(new Port(2109, 2256, "Black Port", 16));
		cityList.add(new Port(781, 1683, "Gray Port", 17));
		//Skrzyzowania
		skrzyzowanieList.add(new Skrzyzowanie(1497,654,"Skrzyzowanie Adama",20));
		skrzyzowanieList.add(new Skrzyzowanie(1944,705,"Skrzyzowanie Ewy",21));
		skrzyzowanieList.add(new Skrzyzowanie(2799,528,"Skrzyzowanie Eryka",22));
		skrzyzowanieList.add(new Skrzyzowanie(1578,3369,"Skrzyzowanie Zbigniewa",23));
		skrzyzowanieList.add(new Skrzyzowanie(1974,3264,"Skrzyzowanie Michala",24));
		skrzyzowanieList.add(new Skrzyzowanie(2682,3069,"Skrzyzowanie Wodzislawy",25));
		skrzyzowanieList.add(new Skrzyzowanie(2283,1332,"Skrzyzowanie Jozefa",26));
		skrzyzowanieList.add(new Skrzyzowanie(2571,1275,"Skrzyzowanie Tacjana ",27));
		skrzyzowanieList.add(new Skrzyzowanie(2757,1563,"Skrzyzowanie Stelli",28));
	
		/**
		 * Generowanie tras
		 * Trasa jest to pojedyńczy odcinek np od skrzyzowania do miasta
		 */
		//Trasy morskie:
		listaTras.add(new TrasaMorska( cityList.get(17), skrzyzowanieList.get(6)));
		listaTras.add(new TrasaMorska( cityList.get(13), skrzyzowanieList.get(6)));
		listaTras.add(new TrasaMorska( cityList.get(13), skrzyzowanieList.get(7)));		
		listaTras.add(new TrasaMorska( cityList.get(14), skrzyzowanieList.get(7)));
		listaTras.add(new TrasaMorska( cityList.get(14), skrzyzowanieList.get(8)));
		listaTras.add(new TrasaMorska( cityList.get(15), skrzyzowanieList.get(8)));
		listaTras.add(new TrasaMorska( cityList.get(16), skrzyzowanieList.get(6)));
		listaTras.add(new TrasaMorska( cityList.get(16), skrzyzowanieList.get(8)));
		listaTras.add(new TrasaMorska( skrzyzowanieList.get(6), skrzyzowanieList.get(7)));
		listaTras.add(new TrasaMorska( skrzyzowanieList.get(6), cityList.get(13)));
		listaTras.add(new TrasaMorska( skrzyzowanieList.get(6), cityList.get(17)));
		listaTras.add(new TrasaMorska( skrzyzowanieList.get(6), cityList.get(16)));
		listaTras.add(new TrasaMorska( skrzyzowanieList.get(7), skrzyzowanieList.get(6)));
		listaTras.add(new TrasaMorska( skrzyzowanieList.get(7), skrzyzowanieList.get(8)));
		listaTras.add(new TrasaMorska( skrzyzowanieList.get(7), cityList.get(13)));
		listaTras.add(new TrasaMorska( skrzyzowanieList.get(7), cityList.get(14)));
		listaTras.add(new TrasaMorska( skrzyzowanieList.get(8), skrzyzowanieList.get(7)));
		listaTras.add(new TrasaMorska( skrzyzowanieList.get(8), cityList.get(14)));
		listaTras.add(new TrasaMorska( skrzyzowanieList.get(8), cityList.get(15)));
		listaTras.add(new TrasaMorska( skrzyzowanieList.get(8), cityList.get(16)));
//		//Trasy lotnicze
		listaTras.add(new TrasaPowietrzna( cityList.get(7), skrzyzowanieList.get(3)));
		listaTras.add(new TrasaPowietrzna( cityList.get(12), skrzyzowanieList.get(3)));
		listaTras.add(new TrasaPowietrzna( cityList.get(5), skrzyzowanieList.get(5)));
		listaTras.add(new TrasaPowietrzna( cityList.get(11), skrzyzowanieList.get(5)));
		listaTras.add(new TrasaPowietrzna( cityList.get(8), skrzyzowanieList.get(4)));
		listaTras.add(new TrasaPowietrzna( cityList.get(8), skrzyzowanieList.get(5)));
		listaTras.add(new TrasaPowietrzna( cityList.get(8), skrzyzowanieList.get(1)));
		listaTras.add(new TrasaPowietrzna( cityList.get(6), skrzyzowanieList.get(3)));
		listaTras.add(new TrasaPowietrzna( cityList.get(6), skrzyzowanieList.get(4)));
		listaTras.add(new TrasaPowietrzna( skrzyzowanieList.get(3), cityList.get(6)));
		listaTras.add(new TrasaPowietrzna( skrzyzowanieList.get(3), cityList.get(7)));
		listaTras.add(new TrasaPowietrzna( skrzyzowanieList.get(3), cityList.get(12)));
		listaTras.add(new TrasaPowietrzna( skrzyzowanieList.get(3), skrzyzowanieList.get(4)));	
		listaTras.add(new TrasaPowietrzna( skrzyzowanieList.get(4), cityList.get(6)));
		listaTras.add(new TrasaPowietrzna( skrzyzowanieList.get(4), cityList.get(8)));
		listaTras.add(new TrasaPowietrzna( skrzyzowanieList.get(4), skrzyzowanieList.get(3)));
		listaTras.add(new TrasaPowietrzna( skrzyzowanieList.get(4), skrzyzowanieList.get(5)));	
		listaTras.add(new TrasaPowietrzna( skrzyzowanieList.get(5), cityList.get(5)));
		listaTras.add(new TrasaPowietrzna( skrzyzowanieList.get(5), cityList.get(8)));
		listaTras.add(new TrasaPowietrzna( skrzyzowanieList.get(5), cityList.get(11)));
		listaTras.add(new TrasaPowietrzna( skrzyzowanieList.get(5), skrzyzowanieList.get(4)));
		listaTras.add(new TrasaPowietrzna( cityList.get(1), skrzyzowanieList.get(0)));
		listaTras.add(new TrasaPowietrzna( cityList.get(0), skrzyzowanieList.get(0)));
		listaTras.add(new TrasaPowietrzna( cityList.get(2), skrzyzowanieList.get(0)));
		listaTras.add(new TrasaPowietrzna( cityList.get(2), skrzyzowanieList.get(1)));
		listaTras.add(new TrasaPowietrzna( cityList.get(2), skrzyzowanieList.get(2)));
		listaTras.add(new TrasaPowietrzna( cityList.get(9), skrzyzowanieList.get(0)));
		listaTras.add(new TrasaPowietrzna( cityList.get(4), skrzyzowanieList.get(2)));
		listaTras.add(new TrasaPowietrzna( cityList.get(10), skrzyzowanieList.get(2)));
		listaTras.add(new TrasaPowietrzna( cityList.get(3), skrzyzowanieList.get(1)));
		listaTras.add(new TrasaPowietrzna( cityList.get(3), skrzyzowanieList.get(2)));
		listaTras.add(new TrasaPowietrzna( skrzyzowanieList.get(0), cityList.get(0)));
		listaTras.add(new TrasaPowietrzna( skrzyzowanieList.get(0), cityList.get(1)));
		listaTras.add(new TrasaPowietrzna( skrzyzowanieList.get(0), cityList.get(2)));
		listaTras.add(new TrasaPowietrzna( skrzyzowanieList.get(0), cityList.get(9)));
		listaTras.add(new TrasaPowietrzna( skrzyzowanieList.get(0), skrzyzowanieList.get(1)));
		listaTras.add(new TrasaPowietrzna( skrzyzowanieList.get(1), cityList.get(2)));
		listaTras.add(new TrasaPowietrzna( skrzyzowanieList.get(1), cityList.get(3)));
		listaTras.add(new TrasaPowietrzna( skrzyzowanieList.get(1), cityList.get(8)));
		listaTras.add(new TrasaPowietrzna( skrzyzowanieList.get(1), skrzyzowanieList.get(0)));
		listaTras.add(new TrasaPowietrzna( skrzyzowanieList.get(2), cityList.get(2)));
		listaTras.add(new TrasaPowietrzna( skrzyzowanieList.get(2), cityList.get(3)));
		listaTras.add(new TrasaPowietrzna( skrzyzowanieList.get(2), cityList.get(4)));
		listaTras.add(new TrasaPowietrzna( skrzyzowanieList.get(2), cityList.get(10)));
		//DOROGI LĄDOWE - NATYCHMIASTOWE (ZMIANA LOTNISKA NA PORT ITP)
		listaTras.add(new Droga( cityList.get(1), cityList.get(17)));
		listaTras.add(new Droga( cityList.get(17), cityList.get(1)));
		listaTras.add(new Droga( cityList.get(3), cityList.get(13)));
		listaTras.add(new Droga( cityList.get(13), cityList.get(3)));
		listaTras.add(new Droga( cityList.get(14), cityList.get(4)));
		listaTras.add(new Droga( cityList.get(4), cityList.get(14)));
		listaTras.add(new Droga( cityList.get(5), cityList.get(15)));
		listaTras.add(new Droga( cityList.get(15), cityList.get(5)));
		listaTras.add(new Droga( cityList.get(8), cityList.get(16)));
		listaTras.add(new Droga( cityList.get(16), cityList.get(8)));	
	}
	public static List<Droga> getListaTras() {
		return listaTras;
	}
	public static List<PunktMapy> getSkrzyzowanieList() {
		return skrzyzowanieList;
	}
	public static List<Pojazd> getListaPojazdow(){
		return listaPojazdow;
	}
	public static List<Miasto> getCityList(){
		return cityList;
	}
	public static int generateId(){
		idGenerator++;
		return idGenerator;
	}
	
	public static Port getRandomPort(){
		List<Port> tmpList = new ArrayList<Port>();
		for(Miasto miasto : Swiat.getCityList()) 
			if(miasto instanceof Port && miasto.getPojemosc()>0 ) tmpList.add((Port) miasto);
		
		Random generator = new Random();
		if (!tmpList.isEmpty())
			return tmpList.get(generator.nextInt(tmpList.size()));
		else return null;
	}
	public static Lotnisko getRandomAirPort(){
		List<Lotnisko> tmpList = new ArrayList<Lotnisko>();
		for(Miasto miasto : Swiat.getCityList()) 
			if(miasto instanceof Lotnisko && miasto.getPojemosc()>0 ) tmpList.add((Lotnisko) miasto);
			
		Random generator = new Random();
		if (!tmpList.isEmpty())
			return tmpList.get(generator.nextInt(tmpList.size()));
		else return null;
	}
	public static boolean isCzyIstniejeLotniskowiec() {
		return czyIstniejeLotniskowiec;
	}
	public static void setCzyIstniejeLotniskowiec(boolean czyIstniejeLotniskowiec) {
		Swiat.czyIstniejeLotniskowiec = czyIstniejeLotniskowiec;
	}
	public static boolean isCanAddPojazd() {
		return canAddPojazd;
	}
	public static void setCanAddPojazd(boolean canAddPojazd) {
		Swiat.canAddPojazd = canAddPojazd;
	}
	
	@Override
	public void run() {
		
	}
	public static List<BufferedImage> getPojazdyImages() {
		return pojazdyImage;
	}
	public static void setPojazdyImages(List<BufferedImage> pojazdy) {
		Swiat.pojazdyImage = pojazdy;
	}
	
}
