package POJAZD;

import java.awt.image.BufferedImage;
import java.io.Serializable;

import MAIN.Aplikacja;
import PRZYSTANEK.Miasto;
import PRZYSTANEK.Skrzyzowanie;

/**
 * 
 * @author Kamil Piotrowski
 * Klasa określająca statek wycieczkowy
 *
 */
public class StatekWycieczkowy extends PojazdPasazerski implements Statek, Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7665925032471972215L;
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
	 * @param port - referencja na port w którym tworzy się statek
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

	
	/**
	 * Pętla wątku
	 */
	public void run() {
		while(this.isRunnable()){
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
									Skrzyzowanie sk = (Skrzyzowanie) this.getTrasa().get(0).getB();
									if( (sk.isZajete() == false) || (sk.getIdObiektu() == this.getid()) ){
										synchronized(this.getTrasa().get(0).getB().getHulk() ){
											this.wejdzNaSkrzyzowanie();
										}
									}
									else {
										Thread.sleep(2000);
										sk.setZajete(false);
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
	/**
	 * Zmiana trasy
	 */
	public void zmienTrase(){
		zmianaTrasyStatku(this);
	}
	/**
	 * Zwraca obraz statku
	 */
	public BufferedImage getImage() {
		return Aplikacja.getSwiat().getPojazdyImages()[1];
	}
	public String getFirma() {
		return firma;
	}

	public void setFirma(String firma) {
		this.firma = firma;
	}
	
	
}
