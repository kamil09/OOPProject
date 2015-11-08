package POJAZD;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import DROGA.Droga;
import DROGA.TrasaPowietrzna;
import MAIN.Port;
import MAIN.PunktMapy;
import MAIN.Skrzyzowanie;
import MAIN.Swiat;

/**
 * 
 * @author Kamil Piotrowski
 * Klasa określająca samolpot wojskowy
 *
 */
public class SamolotWojskowy extends PojazdWojskowy implements Samolot{
	/**
	 * Punkt do którego najpierw leci nasz mysliwiec (zaraz po stworzreniu)
	 */
	private PunktMapy pierwszyPunkt = null;
	/**
	 * Czas postoju na lotnisku wojskowym
	 */
	private double time=1000;
	
	/**
	 * Kostruktor
	 * @param d - położenie X Pojazdu
	 * @param e - położenie Y Pojazdu
	 * @param name - nazwa pojazdu
	 * @param id - id pojazdu
	 * Losuje także paliwo z przedziału 1000 - 1500
	 */
	public SamolotWojskowy(double d,double e, String name, int id, Lotniskowiec lotniskowiec){
		super(d, e, name, id, null);
		this.setSize(40);
		this.setMaxSpeed(5);
		this.znajdzNajblizszyPunkt();
		this.setBron(lotniskowiec.getBron());
	}
	
	public void run() {
		while(this.isRunnable()){
			try {
				if( !this.getTrasa().isEmpty() ){
					switch(this.getStan()){
						case 1:
							if(this.getObecneMiejsce()!=null && this.getObecneMiejsce().getid()>=9 && this.time>0 ){
								this.time--;
							}else{
								this.setPaliwo(this.getMaxPaliwo());
								this.time=1000;
								this.wyparkuj();
								this.setStan(2);
							}
							break;
						case 2:
							//Sprawdzenie czy docieramy do punktu końcowego (miasto lub skrzyzowanie)
							if( !this.czyPunktPostoju() ) {
								this.move(1);
							}
							else{
								//Synchronizacja wejscia na skrzyzowanie lub do miasta!
								if(this.getTrasa().get(0).getB() instanceof Skrzyzowanie ){
									synchronized(this.getTrasa().get(0).getB().getHulk() ){
										this.wejdzNaSkrzyzowanie();
									}
								}
								else{
									synchronized(this.getTrasa().get(0).getB().getHulk() ){
										this.wejdzDoMiasta();
									}
								}
							}
							break;
					}	
				}
				else{
					this.przepiszTrase(this);
					this.setStan(1);
				}
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public BufferedImage getImage() {
		return Swiat.getPojazdyImages().get(2);
	
	}
	
	private void znajdzNajblizszyPunkt(){
		double najS=9999;
		PunktMapy punktTmp=null;
		for (Droga trasa : Swiat.getListaTrasPowietrznych() ){
			double odX=trasa.getA().getKoorX()-this.getKoorX();
			double odY=trasa.getA().getKoorY()-this.getKoorY();
			odX=Math.abs(odX);
			odY=Math.abs(odY);
			double diffP=Math.pow(Math.pow(odX, 2)+Math.pow(odY, 2) , 0.5);
			if(diffP<=najS){
				punktTmp=trasa.getA();
				najS=diffP;
			}
			//Tak na szybko powtórzenie dla 2 punktu
			odX=trasa.getB().getKoorX()-this.getKoorX();
			odY=trasa.getB().getKoorY()-this.getKoorY();
			odX=Math.abs(odX);
			odY=Math.abs(odY);
			diffP=Math.pow(Math.pow(odX, 2)+Math.pow(odY, 2) , 0.5);
			if(diffP<=najS){
				punktTmp=trasa.getB();
				najS=diffP;
			}
		}
		this.setPierwszyPunkt(punktTmp);
		this.getTrasa().add( new TrasaPowietrzna( new Port((int)this.getKoorX(), (int)this.getKoorY() ,"none", Swiat.generateId() )  ,this.getPierwszyPunkt())  );
		this.trasaDoLotniskaWojskowego();
		this.losujTraseWojskowa();
		this.setStan(2);
	}
	
	private void losujTraseWojskowa() {
		this.getTrasaPowrotna().clear();
		this.getTrasaTmp().clear();
		List<Droga> trasaCopy = new ArrayList<Droga>();
		boolean czyKopia=false;
		if(!this.getTrasa().isEmpty()){
			for(Droga trasa : this.getTrasa() ){
				trasaCopy.add(trasa);
			}
			czyKopia=true;
		}
		this.getTrasa().clear();
		
		Random generator = new Random();
		List<Droga> dostepneTrasy = new ArrayList<Droga>();
		for(Droga trasaPowietrzna : Swiat.getListaTrasPowietrznych() ){
			if( (trasaPowietrzna.getA().equals(trasaCopy.get(trasaCopy.size()-1).getB()) ))
				dostepneTrasy.add(trasaPowietrzna);
		}
		if(dostepneTrasy.size()>0){
			this.getTrasa().add(dostepneTrasy.get(generator.nextInt(dostepneTrasy.size() )));
		}
		this.trasaDoLotniskaWojskowego();
		for(Droga trasa : this.getTrasa() ){
			this.getTrasaTmp().add(trasa);
		}
		//Ustawienie trasy powrotnej
		znajdzTrasePowrotna(this);
		if(czyKopia==true){
			this.getTrasa().clear();
			this.setTrasa(trasaCopy);
		}
		this.getTrasa().get(0).getPojazdyNaDrodze().add(this);
	}

	private void trasaDoLotniskaWojskowego(){
		Random generator = new Random();
		while(true){
			List<Droga> dostepneTrasy = new ArrayList<Droga>();
			//Mozliwe trasy
			boolean czyPrzerwac=false;
			for(Droga trasaPowietrzna : Swiat.getListaTrasPowietrznych() ){
				if((trasaPowietrzna.getA().equals(this.getTrasa().get(this.getTrasa().size()-1).getB()) 
					&& (trasaPowietrzna.getB().getid()!=this.getTrasa().get(0).getA().getid()))){
					dostepneTrasy.add(trasaPowietrzna);
					if( (trasaPowietrzna.getB().getid()>=9)  && (trasaPowietrzna.getB().getid()<=12) ){
						this.getTrasa().add(trasaPowietrzna);
						czyPrzerwac=true;
						break;
					}
				}
			}
			if(czyPrzerwac==true){
				break;
			}
			if(dostepneTrasy.size()>0){
				this.getTrasa().add(dostepneTrasy.get(generator.nextInt(dostepneTrasy.size() )));
			}
			else break;
		}
	}

	public PunktMapy getPierwszyPunkt() {
		return pierwszyPunkt;
	}

	public void setPierwszyPunkt(PunktMapy pierwszyPunkt) {
		this.pierwszyPunkt = pierwszyPunkt;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}
}
