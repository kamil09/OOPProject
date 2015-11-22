package POJAZD;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Random;

import ENUM.Uzbrojenie;
import MAIN.Aplikacja;
import PRZYSTANEK.Miasto;
import PRZYSTANEK.Skrzyzowanie;

/**
 * 
 * @author Kamil Piotrowski
 * Klasa określająca lotniskowiec
 *
 */
public class Lotniskowiec extends PojazdWojskowy implements Statek, Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5462167983486578028L;
	
	/**
	 * Ostatnio dodany samolot wojskowy
	 * Zapobiga utworzeniu kilku samolotów na raz
	 * Przy tworzeniu sprawdzamy jak lotnikowiec jest daleko od ostatnio utworzonego pojazdu
	 */
	private SamolotWojskowy dodanySamolotW=null;
	
	/**
	 * Konstruktor lotniskowca
	 * @param d		- punkt startowy X
	 * @param e		- punkt startowy Y
	 * @param name	- nazwa Lotniskowca
	 * @param id	- id lotniskowca
	 * @param port	- Port w którym zaczyna podróż lotniskowiec
	 */
	public Lotniskowiec(double d,double e, String name, int id, Miasto port){
		super(d, e, name, id, port);
		this.setSize(70);
		Random generator = new Random();
		this.setBron(Uzbrojenie.values()[generator.nextInt(6)] );
		this.setStan(0);
		this.setMaxSpeed(3);
		this.losujTrase(this);
		synchronized (this.getObecneMiejsce().getHulk() ){
			this.zaparkuj();
		}
	}
	/**
	 * Pętla wątka
	 */
	public void run() {
		while(this.isRunnable()){
			try {
				if( !this.getTrasa().isEmpty() ){
					switch(this.getStan()){
						case 1:
							//Lotniskowiec nie zatrzymuje się w mieście, no chyba, że jest korek i nie ma gdzie płynąć:)
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
				this.removePojazd(0);
				//e.printStackTrace();
			}
		}
		
	}
	public void zmienTrase(){
		zmianaTrasyStatku(this);
	}
	public BufferedImage getImage() {
		return Aplikacja.getSwiat().getPojazdyImages()[0];
	}
	public SamolotWojskowy getDodanySamolotW() {
		return dodanySamolotW;
	}
	public void setDodanySamolotW(SamolotWojskowy dodanySamolotW) {
		this.dodanySamolotW = dodanySamolotW;
	}
	
	
	
	
}
