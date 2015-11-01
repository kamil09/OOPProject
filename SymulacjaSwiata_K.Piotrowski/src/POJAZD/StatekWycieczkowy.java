package POJAZD;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import GUI.MapClickVehicle;
import MAIN.Miasto;

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
	}

	public String getFirma() {
		return firma;
	}

	public void setFirma(String firma) {
		this.firma = firma;
	}
	
	public void run() {
		// TODO Auto-generated method stub
		
	}
	public MapClickVehicle rysuj(double zoom, int koorX, int koorY){
		ImageIcon ikona = new ImageIcon();
		int katObrotu = 0;
		if( !this.getTrasa().isEmpty() ) katObrotu=(int) this.getTrasa().get(0).getKatProstej();
		
		return null;
	}
	public ImageIcon returnIcon(double zoom){
		return null;
	}
}
