package PASAZER;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import DROGA.Droga;
import ENUM.ImieRandom;
import ENUM.NazwiskoRandom;
import MAIN.Miasto;
import MAIN.PunktMapy;
import MAIN.Swiat;
import POJAZD.Pojazd;
import POJAZD.PojazdPasazerski;

/**
 * 
 * @author Kamil Piotrowski
 * Klasa pasażer
 * Jedna z wazniejszych w programie
 *
 */
public class Pasazer implements Runnable{
	
	/**
	 * Id pasażera
	 */
	private UID Id;
	/**
	 * Imie pasażera
	 */
	private ImieRandom imie;
	/**
	 * Nazwisko pasazera
	 */
	private NazwiskoRandom nazwisko;
	/**
	 * Wiek pasazera
	 */
	private int wiek;
	/**
	 * pesel pasażera
	 */
	private long pesel;
	
	/**
	 * Typ podruży : false- prywatna true-służbowa
	 */
	private boolean typPodrozy;
	/**
	 * Czas postoju w miejscu docelowym
	 */
	private double czasPostoju;
	
	/**
	 * Miasto rodzinne pasażera
	 */
	private Miasto miastoRodzinne;
	/**
	 * Punkt w którym pasażer obecnie sę znajduje (miasto)
	 */
	private Miasto obecnyPunkt;
	/**
	 * Obecny pojazd, w którym znajsuje się pasażer
	 */
	private PojazdPasazerski obecnyPojazd;
	/**
	 * Miasto docelowe do którego zmierza pasażer
	 */
	private Miasto miastoDocelowe;
	
	/**
	 * Trasa : a - b - c - d - e - f - g - h - ciąg samych miast. bez obecnego, ale łącznie z końcowym!!!
	 */
	private List<PunktMapy> trasa = new ArrayList<PunktMapy>();
	/**
	 * To samo co trasa tylko odwrotnie
	 */
	private List<PunktMapy> trasaPowrotna = new ArrayList<PunktMapy>();

	
	/**
	 * Status (określna stan pasazera w danej chwili):
	 * 0- czeka na wylosowanie trasy
	 * 1- W punkcie - czeka
	 * 2- W pojezdzie 
	 * 3- Oczekuje w miejscu docelowym
	 * 4- W miejscu docelowym - oczekuje na wylosowanie trasy 
	 * 5- Powrócił do domu, oczekuje na zmianę statusu w celu wylosowania kolejnej trasy
	 * 
	 */
	private int status;

//SETTERY I GETTERY
	public UID getId(){
		return Id;
	}
	public ImieRandom getImie() {
		return imie;
	}
	public NazwiskoRandom getNazwisko() {
		return nazwisko;
	}
	public int getWiek() {
		return wiek;
	}
	public long getPesel() {
		return pesel;
	}
	public boolean isTypPodrozy() {
		return typPodrozy;
	}

	public void setTypPodrozy(boolean typPodrozy) {
		this.typPodrozy = typPodrozy;
	}

	public double getCzasPostoju() {
		return czasPostoju;
	}
	public void setCzasPostoju(double czasPostoju) {
		this.czasPostoju = czasPostoju;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	public List<PunktMapy> getTrasa() {
		return trasa;
	}
	public void setTrasa(List<PunktMapy> trasa) {
		this.trasa = trasa;
	}
	public Miasto getObecnyPunkt() {
		return obecnyPunkt;
	}
	public void setObecnyPunkt(Miasto obecnyPunkt) {
		this.obecnyPunkt = obecnyPunkt;
	}
	public Miasto getMiastoDocelowe() {
		return miastoDocelowe;
	}
	public void setMiastoDocelowe(Miasto miastoDocelowe) {
		this.miastoDocelowe = miastoDocelowe;
	}
	public PojazdPasazerski getObecnyPojazd() {
		return obecnyPojazd;
	}
	public void setObecnyPojazd(PojazdPasazerski obecnyPojazd) {
		this.obecnyPojazd = obecnyPojazd;
	};

	/**
	 * Konstruktor
	 * Losuje imie, nazwisko, pesel, miasto rodzinne, id
	 */
	public Pasazer(){
		Random generator = new Random();
		
		this.Id = new UID();
		this.wiek=generator.nextInt(84)+16;
		this.status=0;
		this.pesel=(long)(2015-(1900+this.wiek))*1000000000;
		this.pesel+=(long)(generator.nextInt(888888888)+111111111);
		this.imie=ImieRandom.values()[generator.nextInt(142)+1];
		this.nazwisko=NazwiskoRandom.values()[generator.nextInt(159)+1];
		int miastoR = generator.nextInt(12);
		//9 - 12 są lotniska wojskowe
		if(miastoR >=9 && miastoR<=12) miastoR+=4; 
		this.miastoRodzinne=Swiat.getCityList().get(miastoR);
		this.setObecnyPunkt(this.miastoRodzinne);
	}
	
	public PunktMapy getMiastoRodzinne() {
		return miastoRodzinne;
	}
	
	/**
	 * Metoda w której żyje pasażer
	 */
	public void run() {
		while(true) {
            try {
            	/**
            	 * Akcje dla poszczególnych stanów pasazera
            	 */
            	switch(this.getStatus()){
            		/**
            		 * Losowanie trasy
            		 */
            		case 0:
            			this.setMiastoDocelowe(null);
            			this.losujTrase();
            			this.setStatus(1);
            			break;
            		/**
            		 * Pobyt w miescie
            		 */
            		case 1:
            			if(this.trasa.size()>0){
            				/**
            				 * Sprawdzenie czy dotarł już do końca trasy
            				 */
	            			if( this.getTrasa().get(this.trasa.size()-1).getid() == this.getObecnyPunkt().getid() ){
	            				this.trasa.clear();
	            				this.setStatus(4);
	            				//Dodatkowe sprawdzenie czy powrócił juz do domu
	            				if(this.getObecnyPunkt().getid() == this.getMiastoRodzinne().getid() ){
	            					this.setStatus(5);
	            				}
	            				break;
	            			}
            				/**
            				 * Sprawdzenie czy nie przesiada się z lotniska / port lub odwrotnie
            				 * Jeśli tak to skaczemy / teleportujemy się / idziemy na piechote lądem / pod ziemią 
            				 */
	            			if( this.getObecnyPunkt().getClass().getName() != this.getTrasa().get(0).getClass().getName() ){
	            				this.setObecnyPunkt((Miasto)this.getTrasa().get(0));
	            				this.trasa.remove(0);
	            				break;
	            			}
	            			/*
	            			 * Przeszukanie czy w obecnym miejscu znajduje się odpowiedni pojazd
	            			 */
	            			for(Pojazd pojazd : this.getObecnyPunkt().getListaPojazdow() ){
	            				if(pojazd instanceof PojazdPasazerski){
	            					if(((PojazdPasazerski) pojazd).getWolneMiejsca() > 0){
	            						this.wsiadzDoPojazdu((PojazdPasazerski)pojazd);
	            						break;
	            					}
	            				}
	            			}
            			}
            			break;
            		case 2:
            			/**
            			 * Sprawdzenie czy pojazd znajduje się w miejscu docelowym
            			 */
            			if(this.getObecnyPojazd().getObecneMiejsce().getid() == this.getTrasa().get(0).getid() ){
            				wysiadzZPojazdu();
            			}
            			break;
            		case 3:
            			/**
            			 * odliczanie
            			 */
            			this.czasPostoju-=0.02;
            			if(this.czasPostoju<0) this.setStatus(1);
            			break;
            		case 4:
            			/**
            			 * Przepisanie trasy powrotnej
            			 */
            			this.zamienTrasy();
            			this.setStatus(3);
            			break;
            		case 5:
            			/**
            			 * Cofniecie się do początku
            			 */
            			this.setStatus(0);
            			break;
            	}
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
	}
	
	/**
	 * Losuje trase i na jej podstawie określa cel podróży :)
	 * Zapobiega zapętleniu i dotarciu do lotnisk wojskowych
	 * Trasa jest losowana razem ze skrzyzowaniami ze względu na taką reprezentacje danych (pomocne przy pojazdach)
	 * które następnie są usuwane.
	 * Trasa popwrotna jest taka sama, tylko odwrotna :)
	 * Losuje także czas w miejsu docelowym oraz typ podrozy
	 */
	public void losujTrase(){
		Random generator = new Random();
		int dlugoscTrasy= generator.nextInt(5)+2;
		PunktMapy poprzedniPunkt = this.miastoRodzinne;
		this.czasPostoju=generator.nextInt(200);
		if(this.czasPostoju < 100) this.typPodrozy = true;
		else this.typPodrozy = false;
		this.trasa.clear();
		this.trasaPowrotna.clear();
		while(dlugoscTrasy>0){
			//Wybieranie możliwych punktów, zapobieganie cyklom i udaniem się na lotnisko wojskowe :)
			List<PunktMapy> dostepnePunkty = new ArrayList<PunktMapy>();
			
			
			//Mozliwe trasy, zabezpieczenie przed skakaniem miastoA-portB-miastoA-portB, oraz cyklami
			for(Droga trasa : Swiat.getListaTras() ){
				if(trasa.getA().equals(poprzedniPunkt) && trasa.getB().getid()!=poprzedniPunkt.getid() ) {
					if(trasa.getB().getid() >=9 && trasa.getB().getid()<=12 ) continue;
					boolean goodPoint = true;
					for (PunktMapy badPoint : this.trasa){
						if(badPoint.getid() == trasa.getB().getid() ) goodPoint=false;
					}
					if(goodPoint==true) dostepnePunkty.add(trasa.getB());	
				}
			}
			if(dostepnePunkty.size()>0 ){
				int wybranyPunkt=generator.nextInt(dostepnePunkty.size());
				this.trasa.add(dostepnePunkty.get(wybranyPunkt));
				poprzedniPunkt=this.trasa.get(this.trasa.size()-1);
				if(poprzedniPunkt.getid()<20) dlugoscTrasy--;
			}
			else{
				this.trasa.remove(this.trasa.size()-1);
				poprzedniPunkt=this.trasa.get(this.trasa.size()-1);
			}	
		}
		//usuwanie skrzyżowań z listy
		List<PunktMapy> doUsuniecia = new ArrayList<PunktMapy>();
		for (PunktMapy punkt : this.trasa) if(punkt.getid()>=20 ) doUsuniecia.add(punkt);
		this.trasa.removeAll(doUsuniecia);
		//ustawienie miasta docelowego
		this.miastoDocelowe=(Miasto)this.trasa.get(this.trasa.size()-1);
		//trasa powrotna
		for(int i = this.trasa.size()-2 ; i>=0; i-- ){
			this.trasaPowrotna.add(this.trasa.get(i));
		}
		this.trasaPowrotna.add(miastoRodzinne);
	}
	/**
	 * Kopiuje trasę Powrotną do trasy
	 * Robimy to dlatego, że podczas podróży operujemy na obiekcie trasa, z którego usuwamy odwiedzone punkty
	 */
	public void zamienTrasy(){
		this.trasa.clear();
		this.trasa=this.trasaPowrotna;
	}
	
	/**
	 * Metoda - pasażer wysiada z pojazdu
	 */
	public void wysiadzZPojazdu(){
		//Ustawienie obecnego punktu
		this.setObecnyPunkt( (Miasto)this.getTrasa().get(0) );
		this.trasa.remove(0);
		//Zwiększamy liczbę wolnych miejsc w pojeździe
		this.getObecnyPojazd().setWolneMiejsca(this.getObecnyPojazd().getWolneMiejsca()+1);
		//Usuwamy pasażera z pojazdu i pojazd z pasażera
		this.getObecnyPojazd().getListaPasazerow().remove(this);
		this.setObecnyPojazd(null);
		this.setStatus(1);
	}
	/**
	 * Metoda - pasażer wsiada do pojazdu
	 * @param pojazd - referencja do pojazdu do którego wsiada pasażer
	 */
	public void wsiadzDoPojazdu( PojazdPasazerski pojazd ){
		//dodajemy pasazera do pojazdu, zmniejszamy ilosc wolnych miejsc
		pojazd.getListaPasazerow().add(this);
		pojazd.setWolneMiejsca(this.getObecnyPojazd().getWolneMiejsca()-1);
		//ustawiamy obecny pojazd
		this.setObecnyPojazd(pojazd);
		this.setObecnyPunkt(null);
		this.setStatus(2);
	}
	
	
	public List<PunktMapy> getTrasaPowrotna() {
		return trasaPowrotna;
	}
	public void setTrasaPowrotna(List<PunktMapy> trasaPowrotna) {
		this.trasaPowrotna = trasaPowrotna;
	}
	
	

}
