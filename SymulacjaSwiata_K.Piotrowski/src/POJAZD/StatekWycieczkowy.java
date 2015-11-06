package POJAZD;

import java.awt.image.BufferedImage;

import MAIN.Miasto;
import MAIN.Skrzyzowanie;
import MAIN.Swiat;

/**
 * 
 * @author Kamil Piotrowski
 * Klasa określająca statek wycieczkowy
 *
 */
public class StatekWycieczkowy extends PojazdPasazerski implements Statek{

	/**
	 * Nazwa firmy do której należy statek
	 */
	private String firma;
	
	/**
	 * Kostruktor
	 * @param d - położenie X Pojazdu
	 * @param e - położenie Y Pojazdu
	 * @param name - nazwa pojazdu
	 * @param id - id pojazdu
	 * Losuje także paliwo z przedziału 1000 - 1500
	 */
	public StatekWycieczkowy(double d,double e, String name,int id, Miasto port){
		super(d, e, name, id, port);
		this.setSize(60);
		this.setFirma("Firma z "+port.getName());
		this.setMaxSpeed(3);
		this.losujTrase(this);
		this.zaparkuj();
	}

	public String getFirma() {
		return firma;
	}

	public void setFirma(String firma) {
		this.firma = firma;
	}
	
	public void run() {
		while(true){
			try {
				if( !this.getTrasa().isEmpty() ){
					switch(this.getStan()){
						case 1:
							
							this.wyparkuj();
							this.setStan(2);
							break;
						case 2:
							if( !this.czyPunktPostoju() ) 
								this.move(1);
							else{
								if(this.getTrasa().get(0).getB() instanceof Skrzyzowanie ){
									synchronized(this.getTrasa().get(0).getB().getHulk() ){
										this.wejdzNaSkrzyzowanie();
									}
								}
								else{
									//this.zaparkuj();
								}
							}
							break;
							
						case 3:
							
							break;
					}	
				}
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public BufferedImage getImage() {
		return Swiat.getPojazdyImages().get(1);
	}
	
	
}
