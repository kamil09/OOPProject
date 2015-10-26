package MAIN;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import PASAZER.Pasazer;
import POJAZD.Pojazd;


public final class Swiat {
	
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
			for(int i=0;i<100;i++) addPasazer();
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
	
	
	
	@SuppressWarnings("serial")
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
		cityList.add(new Lotnisko(3471,2850, "Blue City", 5) );
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
	
		//Generowanie tras
		//Trasa jest to pojedyńczy odcinek np od skrzyzowania do miasta
		//Trasa obejmuje punkt A oraz wszystkie następniki B 
		//Trasy morskie:
		listaTras.add(new TrasaMorska( cityList.get(17), new ArrayList<PunktMapy>(){{
				add(skrzyzowanieList.get(6));
			}}));
		listaTras.add(new TrasaMorska( cityList.get(13), new ArrayList<PunktMapy>(){{
			add(skrzyzowanieList.get(6));
			add(skrzyzowanieList.get(7));
		}}));
		listaTras.add(new TrasaMorska( cityList.get(14), new ArrayList<PunktMapy>(){{
			add(skrzyzowanieList.get(7));
			add(skrzyzowanieList.get(8));
		}}));
		listaTras.add(new TrasaMorska( cityList.get(15), new ArrayList<PunktMapy>(){{
			add(skrzyzowanieList.get(8));
		}}));
		listaTras.add(new TrasaMorska( cityList.get(16), new ArrayList<PunktMapy>(){{
			add(skrzyzowanieList.get(6));
			add(skrzyzowanieList.get(8));
		}}));
		listaTras.add(new TrasaMorska( skrzyzowanieList.get(6), new ArrayList<PunktMapy>(){{
			add(skrzyzowanieList.get(7));
			add(cityList.get(13));
			add(cityList.get(17));
			add(cityList.get(16));
		}}));
		listaTras.add(new TrasaMorska( skrzyzowanieList.get(7), new ArrayList<PunktMapy>(){{
			add(skrzyzowanieList.get(6));
			add(skrzyzowanieList.get(8));
			add(cityList.get(13));
			add(cityList.get(14));
		}}));
		listaTras.add(new TrasaMorska( skrzyzowanieList.get(8), new ArrayList<PunktMapy>(){{
			add(skrzyzowanieList.get(7));
			add(cityList.get(14));
			add(cityList.get(15));
			add(cityList.get(16));
		}}));		
//		//Trasy lotnicze
		listaTras.add(new TrasaPowietrzna( cityList.get(7), new ArrayList<PunktMapy>(){{
			add(skrzyzowanieList.get(3));
		}}));
		listaTras.add(new TrasaPowietrzna( cityList.get(12), new ArrayList<PunktMapy>(){{
			add(skrzyzowanieList.get(3));
		}}));
		listaTras.add(new TrasaPowietrzna( cityList.get(5), new ArrayList<PunktMapy>(){{
			add(skrzyzowanieList.get(5));
		}}));
		listaTras.add(new TrasaPowietrzna( cityList.get(11), new ArrayList<PunktMapy>(){{
			add(skrzyzowanieList.get(5));
		}}));
		listaTras.add(new TrasaPowietrzna( cityList.get(8), new ArrayList<PunktMapy>(){{
			add(skrzyzowanieList.get(4));
			add(skrzyzowanieList.get(5));
			add(skrzyzowanieList.get(1));
		}}));
		listaTras.add(new TrasaPowietrzna( cityList.get(6), new ArrayList<PunktMapy>(){{
			add(skrzyzowanieList.get(3));
			add(skrzyzowanieList.get(4));
		}}));
		listaTras.add(new TrasaPowietrzna( skrzyzowanieList.get(3), new ArrayList<PunktMapy>(){{
			add(cityList.get(6));
			add(cityList.get(7));
			add(cityList.get(12));
			add(skrzyzowanieList.get(4));
		}}));
		listaTras.add(new TrasaPowietrzna( skrzyzowanieList.get(4), new ArrayList<PunktMapy>(){{
			add(cityList.get(6));
			add(cityList.get(8));
			add(skrzyzowanieList.get(3));
			add(skrzyzowanieList.get(5));
		}}));
		listaTras.add(new TrasaPowietrzna( skrzyzowanieList.get(5), new ArrayList<PunktMapy>(){{
			add(cityList.get(5));
			add(cityList.get(8));
			add(cityList.get(11));
			add(skrzyzowanieList.get(4));
		}}));
		listaTras.add(new TrasaPowietrzna( cityList.get(1), new ArrayList<PunktMapy>(){{
			add(skrzyzowanieList.get(0));
		}}));
		listaTras.add(new TrasaPowietrzna( cityList.get(0), new ArrayList<PunktMapy>(){{
			add(skrzyzowanieList.get(0));
		}}));
		listaTras.add(new TrasaPowietrzna( cityList.get(2), new ArrayList<PunktMapy>(){{
			add(skrzyzowanieList.get(0));
			add(skrzyzowanieList.get(1));
			add(skrzyzowanieList.get(2));
		}}));
		listaTras.add(new TrasaPowietrzna( cityList.get(9), new ArrayList<PunktMapy>(){{
			add(skrzyzowanieList.get(0));
		}}));
		listaTras.add(new TrasaPowietrzna( cityList.get(4), new ArrayList<PunktMapy>(){{
			add(skrzyzowanieList.get(2));
		}}));
		listaTras.add(new TrasaPowietrzna( cityList.get(10), new ArrayList<PunktMapy>(){{
			add(skrzyzowanieList.get(2));
		}}));
		listaTras.add(new TrasaPowietrzna( cityList.get(3), new ArrayList<PunktMapy>(){{
			add(skrzyzowanieList.get(1));
			add(skrzyzowanieList.get(2));
		}}));
		listaTras.add(new TrasaPowietrzna( skrzyzowanieList.get(0), new ArrayList<PunktMapy>(){{
			add(cityList.get(0));
			add(cityList.get(1));
			add(cityList.get(2));
			add(cityList.get(9));
			add(skrzyzowanieList.get(1));
		}}));
		listaTras.add(new TrasaPowietrzna( skrzyzowanieList.get(1), new ArrayList<PunktMapy>(){{
			add(cityList.get(2));
			add(cityList.get(3));
			add(cityList.get(8));
			add(skrzyzowanieList.get(0));
		}}));
		listaTras.add(new TrasaPowietrzna( skrzyzowanieList.get(2), new ArrayList<PunktMapy>(){{
			add(cityList.get(2));
			add(cityList.get(3));
			add(cityList.get(4));
			add(cityList.get(10));
		}}));
		//DOROGI LĄDOWE - NATYCHMIASTOWE (ZMIANA LOTNISKA NA PORT ITP)
		listaTras.add(new Droga( cityList.get(1), new ArrayList<PunktMapy>(){{
			add(cityList.get(17));
		}}));
		listaTras.add(new Droga( cityList.get(17), new ArrayList<PunktMapy>(){{
			add(cityList.get(1));
		}}));
		listaTras.add(new Droga( cityList.get(3), new ArrayList<PunktMapy>(){{
			add(cityList.get(13));
		}}));
		listaTras.add(new Droga( cityList.get(13), new ArrayList<PunktMapy>(){{
			add(cityList.get(3));
		}}));
		listaTras.add(new Droga( cityList.get(14), new ArrayList<PunktMapy>(){{
			add(cityList.get(4));
		}}));
		listaTras.add(new Droga( cityList.get(4), new ArrayList<PunktMapy>(){{
			add(cityList.get(14));
		}}));
		listaTras.add(new Droga( cityList.get(5), new ArrayList<PunktMapy>(){{
			add(cityList.get(15));
		}}));
		listaTras.add(new Droga( cityList.get(15), new ArrayList<PunktMapy>(){{
			add(cityList.get(5));
		}}));
		listaTras.add(new Droga( cityList.get(8), new ArrayList<PunktMapy>(){{
			add(cityList.get(16));
		}}));
		listaTras.add(new Droga( cityList.get(16), new ArrayList<PunktMapy>(){{
			add(cityList.get(8));
		}}));
		
		
		
		
		
		
		
	}
	public static List<Droga> getListaTras() {
		return listaTras;
	}
	public static List<PunktMapy> getSkrzyzowanieList() {
		return skrzyzowanieList;
	}
	
}
