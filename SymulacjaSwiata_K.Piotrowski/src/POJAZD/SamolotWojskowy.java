package POJAZD;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import GUI.MapClickVehicle;

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
