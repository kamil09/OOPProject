package POJAZD;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import DROGA.Droga;
import GUI.MapClickVehicle;
import MAIN.Miasto;
import MAIN.PunktMapy;

/**
 * Klasa grupująca wszystkie pojazdy
 * @author Kamil Piotrowski
 *
 */
public abstract class Pojazd extends PunktMapy implements Runnable{
	
	/**
	 * Obecne miejsce, jeśli w trakcje lotu/rejsu = null
	 */
	private PunktMapy obecneMiejsce=null;
	
	/**
	 * Ilosc maksymalnego paliwa
	 */
	private double maxPaliwo;
	
	/**
	 * Ilosc obecnego paliwa
	 */
	private double paliwo;
	
	/**
	 * Maksymalna prędkość pojazdu
	 */
	private int maxSpeed;
	/**
	 * Ilość załogi
	 */
	private int liczbaZalogi;
	/**
	 * Trasa po której porusza się pojazd
	 */
	private List<Droga> trasa = new ArrayList<Droga>();
	/**
	 * Trasa powrotna - kopia trasy w odwrotnej kolejności
	 */
	private List<Droga> trasaPowrotna = new ArrayList<Droga>();
	
	/**
	 * Kostruktor
	 * @param x - położenie X Pojazdu
	 * @param y - położenie Y Pojazdu
	 * @param name - nazwa pojazdu
	 * @param id - id pojazdu
	 * @param miasto - miasto w którym zaczyna pojazd
	 * Losuje także paliwo z przedziału 1000 - 1500
	 * Ustawia obecne miejsce
	 */
	public Pojazd(int x,int y, String name, int id, Miasto miasto){
		super(x, y, name, id);
		Random generator = new Random();
		this.maxPaliwo=generator.nextInt(501)+1000;
		this.paliwo=maxPaliwo;
		this.obecneMiejsce=miasto;
	}

	public PunktMapy getObecneMiejsce() {
		return obecneMiejsce;
	}

	public void setObecneMiejsce(PunktMapy obecneMiejsce) {
		this.obecneMiejsce = obecneMiejsce;
	}

	public double getMaxPaliwo() {
		return maxPaliwo;
	}

	public void setMaxPaliwo(double maxPaliwo) {
		this.maxPaliwo = maxPaliwo;
	}

	public double getPaliwo() {
		return paliwo;
	}

	public void setPaliwo(double paliwo) {
		this.paliwo = paliwo;
	}

	public int getLiczbaZalogi() {
		return liczbaZalogi;
	}

	public void setLiczbaZalogi(int liczbaZalogi) {
		this.liczbaZalogi = liczbaZalogi;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	
	public void usunPojazd(){
	}
	
	public abstract MapClickVehicle rysuj(double zoom, int koorX, int koorY);
	public abstract ImageIcon returnIcon(double zoom);

	public List<Droga> getTrasa() {
		return trasa;
	}

	public void setTrasa(List<Droga> trasa) {
		this.trasa = trasa;
	}

	public List<Droga> getTrasaPowrotna() {
		return trasaPowrotna;
	}

	public void setTrasaPowrotna(List<Droga> trasaPowrotna) {
		this.trasaPowrotna = trasaPowrotna;
	}

}
