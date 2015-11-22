package POJAZD;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import DROGA.Droga;
import DROGA.TrasaPowietrzna;
import MAIN.Aplikacja;
import MAIN.PunktMapy;
import PRZYSTANEK.Miasto;
import PRZYSTANEK.Port;
import PRZYSTANEK.Skrzyzowanie;

/**
 * 
 * @author Kamil Piotrowski
 * Klasa określająca samolpot wojskowy
 *
 */
public class SamolotWojskowy extends PojazdWojskowy implements Samolot, Serializable{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2242288946606773411L;
	/**
	 * Punkt do którego najpierw leci nasz mysliwiec (zaraz po stworzreniu)
	 */
	private PunktMapy pierwszyPunkt = null;
	/**
	 * Czas postoju na lotnisku wojskowym
	 */
	private double time=1000;
	/**
	 * Informuje czy pojazd znajduje się już na glównej trase
	 * Uzywane przy wykrywaniu kolizji zanim mysliwiec znajdzie się na głównej trasie
	 */
	volatile private boolean naTrasie=false;
	
	/**
	 * Kostruktor
	 * @param d - położenie X Pojazdu
	 * @param e - położenie Y Pojazdu
	 * @param name - nazwa pojazdu
	 * @param id - id pojazdu
	 * @param lotniskowiec - referencja na lotniskowiec w którym tworzony jest myśliwiec
	 * Losuje także paliwo z przedziału 1000 - 1500
	 */
	public SamolotWojskowy(double d,double e, String name, int id, Lotniskowiec lotniskowiec){
		super(d, e, name, id, null); 
		this.setSize(40);
		this.setMaxSpeed(7);
		this.znajdzNajblizszyPunkt();
		this.setBron(lotniskowiec.getBron());
	}
	/**
	 * Pętla watku
	 */
	public void run() {
		while(this.isRunnable()){
			try {
				
				if( !this.getTrasa().isEmpty() ){
					switch(this.getStan()){
						case 1:
							if(this.getObecneMiejsce()!=null && this.getObecneMiejsce().getid()>=9 && this.time>0 ){
								this.time--;
								this.setPaliwo(this.getMaxPaliwo());
							}else{
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
	/**
	 * Zamienia trasę samolotu wojskowego
	 */
	public void zmienTrase(){
		Droga cpS =  this.getTrasa().get(0);
		int stan = this.getStan();
		boolean additional=false;
		synchronized(this){
			this.getTrasa().clear();
			if( !(cpS.getA() instanceof Miasto) ){
				for(Droga drogaNeed : Aplikacja.getSwiat().getListaTrasPowietrznych() ){
					if((drogaNeed.getB().getid() == cpS.getA().getid()) 
					&& (drogaNeed.getA() instanceof Miasto) 
					&& (drogaNeed.getA().getid()!=cpS.getB().getid())){
						this.getTrasa().add(drogaNeed);
						additional=true;
						break;
					}
				}
			}
			this.getTrasa().add(cpS);
			this.trasaDoLotniskaWojskowego();
			for(Droga trasa : this.getTrasa() ){
				this.getTrasaTmp().add(trasa);
			}
			znajdzTrasePowrotna(this);
			if(additional==true) this.getTrasa().remove(0);
			this.setStan(stan);
		}
	}
	public BufferedImage getImage() {
		return Aplikacja.getSwiat().getPojazdyImages()[2];
	}
	/**
	 * Znajduje najbliższy punkt do którego samolot ma lecieć zaraz po stworzeniu, wyznacza tą drogę
	 */
	private void znajdzNajblizszyPunkt(){
		double najS=9999;
		PunktMapy punktTmp=null;
		for (Droga trasa : Aplikacja.getSwiat().getListaTrasPowietrznych() ){
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
		this.getTrasa().add( new TrasaPowietrzna( new Port((int)this.getKoorX(), (int)this.getKoorY() ,"none", Aplikacja.getSwiat().generateId() )  ,this.getPierwszyPunkt())  );
		this.trasaDoLotniskaWojskowego();
		this.losujTraseWojskowa();
		this.setStan(2);
	}
	/**
	 * Losuje trase dla somolotu wojskowego
	 * Dokładnie to losuje 1 drogę a następnie wywołuje metode trasaDoLotniskaWojskowego();
	 */
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
		for(Droga trasaPowietrzna : Aplikacja.getSwiat().getListaTrasPowietrznych() ){
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
	/**
	 * Wyznacza trase do jakiegoś lotniska wojskowego od obecnego punktu
	 * Uwaga pierwsza droga musi być już wybrana, nie można zaczynać z pustą trasą!!!
	 */
	private void trasaDoLotniskaWojskowego(){
		Random generator = new Random();
		while(true){
			List<Droga> dostepneTrasy = new ArrayList<Droga>();
			//Mozliwe trasy
			boolean czyPrzerwac=false;
			for(Droga trasaPowietrzna : Aplikacja.getSwiat().getListaTrasPowietrznych() ){
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
		
		for (int i = 2; i<this.getTrasa().size()-1;i++){
			if(		(this.getTrasa().get(i).getA().getid() == this.getTrasa().get(i-1).getB().getid())
				&&  (this.getTrasa().get(i).getB().getid() == this.getTrasa().get(i-1).getA().getid()) ){
				this.getTrasa().remove(i);
				this.getTrasa().remove(i-1);
				i+=1;
			}
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
	public boolean isNaTrasie() {
		return naTrasie;
	}
	public void setNaTrasie(boolean naTrasie) {
		this.naTrasie = naTrasie;
	}
}
