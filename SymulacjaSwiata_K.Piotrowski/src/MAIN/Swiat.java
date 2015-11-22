package MAIN;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
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
import POJAZD.SamolotPasazerski;
import POJAZD.SamolotWojskowy;
import POJAZD.StatekWycieczkowy;
import PRZYSTANEK.Lotnisko;
import PRZYSTANEK.Miasto;
import PRZYSTANEK.Port;
import PRZYSTANEK.Skrzyzowanie;

/**
 * 
 * @author Kamil Piotrowski
 * Klasa świata programu
 * Znajdują się w niej wszystkie najważniejsze dane
 *
 */
public class Swiat implements Runnable, Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2346683793244520697L;
	/**
	 * Obraz tła
	 */
	transient private BufferedImage bufferImage;
	/**
	 * Obray pojazdów
	 * [0] - lotniskowiec
	 * [1] - statek pasazersi
	 * [2] - mysliwiec
	 * [3] - samolot pasazerski
	 */
	transient private BufferedImage[] pojazdyImage;
	/**
	 * Do generowania ID pojazdów
	 */
	volatile private int idGenerator=0;
	/**
	 * Lista wszystkich pasażerów
	 */
	private List<Pasazer> listaPasazerow = new ArrayList<Pasazer>();
	/**
	 * Lista wszystkich pojazdów
	 */
	private List<Pojazd> listaPojazdow = new ArrayList<Pojazd>();
	/**
	 * Lista wszystkich tras
	 */
	private List<Droga> listaTras = new ArrayList<Droga>();
	/**
	 * Lista tras wodnych pomocna przy ustalaniu trasy dla statku
	 */
	private List<TrasaMorska> listaTrasMorskich = new ArrayList<TrasaMorska>();
	/**
	 * Lista tras powietrznych pomocna przy ustalaniu trasy dla samolotu
	 */
	private List<TrasaPowietrzna> listaTrasPowietrznych = new ArrayList<TrasaPowietrzna>();
	/**
	 * Lista wszystkich miast
	 */
	private List<Miasto> cityList = new ArrayList<Miasto>();
	/**
	 * Lista skrzyżowań
	 */
	private List<PunktMapy> skrzyzowanieList = new ArrayList<PunktMapy>();
	/**
	 * Czy istnieje lotniskowiec, lewsza jedna zmienna niż szukanie w liście pojazdów
	 * Nie można stworzyć samolotu wojskowego bez lotniskowca!
	 */
	volatile private boolean czyIstniejeLotniskowiec=false;
	/**
	 * Tymczasowa lista myśliwców które nie trafiły jesczze na główną trasę
	 * Służy wykrywaniu kolizji
	 */
	private List<Pojazd> mysliwiecTempList = new ArrayList<Pojazd>();
	/**
	 * Nie można dodawać nowego pojazdu podczas przechodzenia po pętli i wyświetlania na ekranie!!!
	 */
	private Monitor canAddPojazdObject = new Monitor();
	/**
	 * Konstruktor świata
	 * Wczytuje obraz tła
	 * Generuje trasy
	 */
	public Swiat(){
		loadImages();
		generujListeMiast();
	}	
	/**
	 * Metoda wczytująca obrazy
	 */
	public void loadImages(){
		try {
			//WCZYTYWANIE OBRAZÓW
			pojazdyImage = new BufferedImage[4];
			this.bufferImage = ImageIO.read(new File("src/mapa2.png"));
			this.pojazdyImage[0]=ImageIO.read(new File("src/lotniskowiec.png"));
			this.pojazdyImage[1]=ImageIO.read(new File("src/statek.png"));
			this.pojazdyImage[2]=ImageIO.read(new File("src/mysliwiec.png"));
			this.pojazdyImage[3]=ImageIO.read(new File("src/samolot.png"));
		} catch (IOException ex) { 
			System.out.println("Nie można wczytać obrazów");
			System.exit(2);
		} 
	}
	
	
	/**
	 * @return obraz tła
	 */
	public BufferedImage getBufferImage(){
		return this.bufferImage;
	}
	/**
	 * Dodawanie pasażera, start wątku
	 */
	public void addPasazer(){
		Pasazer nowaOsoba= new Pasazer();
		this.listaPasazerow.add(nowaOsoba);
		Runnable runner = nowaOsoba ;
		Thread thread = new Thread(runner);
		thread.start();
	}
	/**
	 * Dodawanie samolotu pasażerskiego
	 */
	public void addSamolotPasazerski(){
		Lotnisko lotnisko = this.getRandomAirPort(0);
		if(lotnisko != null){
			SamolotPasazerski samolot = new SamolotPasazerski(lotnisko.getKoorX(), lotnisko.getKoorY(), "Samolot pasażerski_"+this.generateId(), this.idGenerator, lotnisko);
			addPojazd(lotnisko, samolot);
		}
	}
	/**
	 * Dodawanie samolotu wojskowego
	 */
	public void addSamolotWojskowy(){
		if(this.czyIstniejeLotniskowiec==true){
			Lotniskowiec lotniskowiec = this.getRandomLotniskowiec();
			if(lotniskowiec!=null){
				synchronized(lotniskowiec.getHulk()){	
					if(lotniskowiec.getDodanySamolotW()==null){
						SamolotWojskowy samolotW = new SamolotWojskowy(lotniskowiec.getKoorX(), lotniskowiec.getKoorY(), "Samolot wojskowy_"+this.generateId(), this.idGenerator, lotniskowiec);
						lotniskowiec.setDodanySamolotW(samolotW);
						if(samolotW.canMove(samolotW.getKoorX(), samolotW.getKoorY(), null)) addPojazd(null, samolotW);
						
					}
					else{
						double diffX=lotniskowiec.getKoorX()-lotniskowiec.getDodanySamolotW().getKoorX();
						double diffY=lotniskowiec.getKoorY()-lotniskowiec.getDodanySamolotW().getKoorY();
						diffX=Math.abs(diffX);
						diffY=Math.abs(diffY);
						double diffP=Math.pow(Math.pow(diffX, 2)+Math.pow(diffY, 2) , 0.5);
						if(diffP>100){
							SamolotWojskowy samolotW = new SamolotWojskowy(lotniskowiec.getKoorX(), lotniskowiec.getKoorY(), "Samolot wojskowy_"+this.generateId(), this.idGenerator, lotniskowiec);
							lotniskowiec.setDodanySamolotW(samolotW);
							if(samolotW.canMove(samolotW.getKoorX(), samolotW.getKoorY(), null)) addPojazd(null, samolotW);
								
							
						}
					}
				}
			}
		}
		else
			System.out.println("Nie możesz stworzyć samolotu wojskowego jesli nie posiadasz lotniskowca!");
	}
	/**
	 * Dodawanie statku pasażerskiego
	 */
	public void addStatekPasazerski(){
		Port port = this.getRandomPort();
		if (port!=null){
			StatekWycieczkowy statek= new StatekWycieczkowy(port.getKoorX(), port.getKoorY(), "Statek Wycieczkowy"+this.generateId(), this.idGenerator, port);
			addPojazd(port,statek);
		
		}
	}
	/**
	 * Dodawanie lotniskowca
	 */
	public void addLotniskowiec(){
		Port port = this.getRandomPort();
		if(port!=null){
			Lotniskowiec lotniskowiec = new Lotniskowiec(port.getKoorX(), port.getKoorY(), "Lotniskowiec"+this.generateId(), this.idGenerator, port);
			addPojazd(port,lotniskowiec);
		}
	}
	/**
	 * Ogólna metoda dodawania pojazdu
	 * @param miasto	Miasto w którym zaczyna pojazd
	 * @param pojazd	Referencja do utworzonego już pojazdu
	 */
	private void addPojazd(Miasto miasto, Pojazd pojazd){
		synchronized (this.canAddPojazdObject){
			if(pojazd instanceof SamolotWojskowy) this.getMysliwiecTempList().add(pojazd);
			if(miasto!=null){
				miasto.setPojemosc(miasto.getPojemosc()-1);
				miasto.getListaPojazdow().add(pojazd);
			}
			this.listaPojazdow.add(pojazd);
			Runnable runner = pojazd;
			Thread thread = new Thread(runner);
			thread.start();
			if(this.czyIstniejeLotniskowiec==false) this.setCzyIstniejeLotniskowiec(true);
			MapPanel.addDoRysowania(pojazd);
		}
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
		
		for(Droga droga : getListaTras() ){
			if(droga instanceof TrasaMorska){
				listaTrasMorskich.add((TrasaMorska)droga);
			}
			if(droga instanceof TrasaPowietrzna){
				listaTrasPowietrznych.add((TrasaPowietrzna) droga);
			}
			
		}
	}
	
	/**
	 * 
	 * @return Losowy Port
	 */
	public synchronized Port getRandomPort(){
		List<Port> tmpList = new ArrayList<Port>();
		for(Miasto miasto : this.getCityList()) 
			if(miasto instanceof Port && miasto.getPojemosc()>0 ) tmpList.add((Port) miasto);
		
		Random generator = new Random();
		if (!tmpList.isEmpty())
			return tmpList.get(generator.nextInt(tmpList.size()));
		else return null;
	}
	/**
	 * 
	 * @param tryb Tryb tzwracanych wartości
	 * @return Zwraca losowe lotnisko w którym jest miejsce na pojazd; Jeśli
	 * tryb==0 to lotniska są tylko cywilke
	 * tryb==1 to lotniska zwracane są tylko wojskowe
	 */
	public synchronized Lotnisko getRandomAirPort(int tryb){
		List<Lotnisko> tmpList = new ArrayList<Lotnisko>();
		for(Miasto miasto : this.getCityList()){
			if(tryb==0){
				if( (miasto instanceof Lotnisko) && (miasto.getPojemosc()>0) && (miasto.getid()<9 ) ) tmpList.add((Lotnisko) miasto);
			}
			else{
				if( (miasto instanceof Lotnisko) && (miasto.getPojemosc()>0) && (miasto.getid()>=9) ) tmpList.add((Lotnisko) miasto);	
			}
		}
		Random generator = new Random();
		if (!tmpList.isEmpty())
			return tmpList.get(generator.nextInt(tmpList.size()));
		else return null;
	}
	/**
	 * 
	 * @return Losowy lotniskowiec
	 */
	public synchronized Lotniskowiec getRandomLotniskowiec(){
		Random generator = new Random();
		List<Lotniskowiec> lista= new ArrayList<Lotniskowiec>();
		//Jest 4:02, nie chce mi się nazw wymyslać
		for(Pojazd cos : this.getListaPojazdow() ){
			if(cos instanceof Lotniskowiec ) lista.add((Lotniskowiec)cos);
		}
		if(!lista.isEmpty()){
			if(lista.size()==1) return lista.get(0);
			else
				return lista.get(generator.nextInt(lista.size()-1));
		}
		else
			return null;
	}
	
	
	@Override
	public void run() {
	}
	public boolean isCzyIstniejeLotniskowiec() {
		return this.czyIstniejeLotniskowiec;
	}
	public void setCzyIstniejeLotniskowiec(boolean czyIstniejeLotniskowiec) {
		this.czyIstniejeLotniskowiec = czyIstniejeLotniskowiec;
	}
	public BufferedImage[] getPojazdyImages() {
		return pojazdyImage;
	}
	public void setPojazdyImages(BufferedImage[] pojazdy) {
		this.pojazdyImage = pojazdy;
	}
	public List<TrasaMorska> getListaTrasMorskich() {
		return this.listaTrasMorskich;
	}
	public void setListaTrasMorskich(List<TrasaMorska> listaTrasMorskich) {
		this.listaTrasMorskich = listaTrasMorskich;
	}
	public List<TrasaPowietrzna> getListaTrasPowietrznych() {
		return this.listaTrasPowietrznych;
	}
	public void setListaTrasPowietrznych(List<TrasaPowietrzna> listaTrasPowietrznych) {
		this.listaTrasPowietrznych = listaTrasPowietrznych;
	}
	public Monitor getCanAddPojazdObject() {
		return canAddPojazdObject;
	}
	public void setCanAddPojazdObject(Monitor canAddPojazdObject) {
		this.canAddPojazdObject = canAddPojazdObject;
	}
	public List<Droga> getListaTras() {
		return listaTras;
	}
	public List<PunktMapy> getSkrzyzowanieList() {
		return this.skrzyzowanieList;
	}
	public List<Pojazd> getListaPojazdow(){
		return this.listaPojazdow;
	}
	public List<Miasto> getCityList(){
		return this.cityList;
	}
	public void setCityList(List<Miasto> cityL){
		this.cityList=cityL;
	}
	public List<Pasazer> getListaPasazerow(){
		return listaPasazerow;
	}
	public synchronized int generateId(){
		this.idGenerator++;
		return this.idGenerator;
	}
	public List<Pojazd> getMysliwiecTempList() {
		return mysliwiecTempList;
	}
	public void setMysliwiecTempList(List<Pojazd> mysliwiecTempList) {
		this.mysliwiecTempList = mysliwiecTempList;
	}

	
	
	
}
