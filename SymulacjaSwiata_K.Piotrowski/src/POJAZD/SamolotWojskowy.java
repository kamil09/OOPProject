package POJAZD;

import java.awt.image.BufferedImage;

import MAIN.Swiat;

/**
 * 
 * @author Kamil Piotrowski
 * Klasa określająca samolpot wojskowy
 *
 */
public class SamolotWojskowy extends PojazdWojskowy implements Samolot{

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
					//Jesli mamy gdzie się poruszyć
						
						
						
						
					}
				//Jesli brak trasy
				else
				//	this.losujTrase();
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
		
	}
}
