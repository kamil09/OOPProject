package POJAZD;

import java.awt.image.BufferedImage;

import MAIN.Miasto;

/**
 * 
 * @author Kamil Piotrowski
 * Klasa określająca samolot pasażerski
 *
 */
public class SamolotPasazerski extends PojazdPasazerski implements Samolot{

	/**
	 * Kostruktor
	 * @param x - położenie X Pojazdu
	 * @param y - położenie Y Pojazdu
	 * @param name - nazwa pojazdu
	 * @param id - id pojazdu
	 * Losuje także paliwo z przedziału 1000 - 1500
	 */
	public SamolotPasazerski(int x,int y, String name, int id, Miasto miasto){
		super(x, y, name, id, miasto);
		this.setSize(60);
	}
	
	public void run() {
		while(true){
			try {
				this.setKoorY(this.getKoorY()+4);
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public BufferedImage getImage() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
