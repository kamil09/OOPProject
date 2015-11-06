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
	 * @param x - położenie X Pojazdu
	 * @param y - położenie Y Pojazdu
	 * @param name - nazwa pojazdu
	 * @param id - id pojazdu
	 * Losuje także paliwo z przedziału 1000 - 1500
	 */
	public SamolotWojskowy(int x,int y, String name, int id, Lotniskowiec lotniskowiec){
		super(x, y, name, id, null);
		this.setSize(40);
	}
	
	public void run() {
		while(true){
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


	@Override
	public BufferedImage getImage() {
		return Swiat.getPojazdyImages().get(2);
	
	}
}
