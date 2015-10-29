package POJAZD;

import java.util.Random;

import MAIN.PunktMapy;

/**
 * Klasa grupująca wszystkie pojazdy
 * @author Kamil Piotrowski
 *
 */
public abstract class Pojazd extends PunktMapy{
	
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
	 * Ilość załogi
	 */
	private int liczbaZalogi;
	
	/**
	 * Kostruktor
	 * @param x - położenie X Pojazdu
	 * @param y - położenie Y Pojazdu
	 * @param name - nazwa pojazdu
	 * @param id - id pojazdu
	 * Losuje także paliwo z przedziału 1000 - 1500
	 */
	public Pojazd(int x,int y, String name, int id){
		super(x, y, name, id);
		Random generator = new Random();
		this.maxPaliwo=generator.nextInt(501)+1000;
		this.paliwo=maxPaliwo;
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

}
