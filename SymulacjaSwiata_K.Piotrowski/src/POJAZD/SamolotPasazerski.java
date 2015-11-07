package POJAZD;

import java.awt.image.BufferedImage;

import MAIN.Miasto;
import MAIN.Skrzyzowanie;
import MAIN.Swiat;

/**
 * 
 * @author Kamil Piotrowski
 * Klasa określająca samolot pasażerski
 *
 */
public class SamolotPasazerski extends PojazdPasazerski implements Samolot{

	/**
	 * Kostruktor
	 * @param d - położenie X Pojazdu
	 * @param e - położenie Y Pojazdu
	 * @param name - nazwa pojazdu
	 * @param id - id pojazdu
	 * Losuje także paliwo z przedziału 1000 - 1500
	 */
	public SamolotPasazerski(double d,double e, String name, int id, Miasto miasto){
		super(d, e, name, id, miasto);
		this.setSize(60);
		this.setMaxSpeed(4);
		this.losujTrase(this);
		this.zaparkuj();
	}
	
	public void run() {
		while(true){
			try {
				if( !this.getTrasa().isEmpty() ){
					switch(this.getStan()){
						case 1:
							//Lotniskowiec nie zatrzymuje się w mieście, no chyba, że jest korek i nie ma gdzie płynąć:)
							if(this.getCzasPostoju()<=0){
								this.wyparkuj();
								this.setStan(2);
								this.setCzasPostoju(700);
							}
							else this.setCzasPostoju(this.getCzasPostoju()-1);
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
		return Swiat.getPojazdyImages().get(3);
	}

	
}
