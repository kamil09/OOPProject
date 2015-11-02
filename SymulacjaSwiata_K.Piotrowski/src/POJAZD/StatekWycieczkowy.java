package POJAZD;

import java.awt.image.BufferedImage;

import MAIN.Miasto;
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
	 * @param x - położenie X Pojazdu
	 * @param y - położenie Y Pojazdu
	 * @param name - nazwa pojazdu
	 * @param id - id pojazdu
	 * Losuje także paliwo z przedziału 1000 - 1500
	 */
	public StatekWycieczkowy(int x,int y, String name,int id, Miasto port){
		super(x, y, name, id, port);
		this.setSize(60);
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
				this.setKoorY(this.getKoorY()+3);
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public BufferedImage getImage() {
		return Swiat.getPojazdyImages().get(1);
	}
	
}
