package POJAZD;

import java.awt.image.BufferedImage;

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
		
	}
	
	public void run() {
		while(this.isRunnable()){
			try {
				if( !this.getTrasa().isEmpty() ){
					switch(this.getStan()){
						case 1:
							this.wyparkuj();
							this.setStan(2);
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
		this.setStan(2);
	}

	public PunktMapy getPierwszyPunkt() {
		return pierwszyPunkt;
	}

	public void setPierwszyPunkt(PunktMapy pierwszyPunkt) {
		this.pierwszyPunkt = pierwszyPunkt;
	}
}
