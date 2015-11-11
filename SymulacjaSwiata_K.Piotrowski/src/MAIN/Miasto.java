package MAIN;

import java.util.LinkedList;
import java.util.List;

import POJAZD.Pojazd;

/**
 * 
 * @author Kamil Piotrowski
 * Klasa określająca miasto dziedziczy po punkcie mapy
 *
 */
public abstract class Miasto extends PunktMapy{

	/**
	 * Lista pojazdów stacjonujących obecnie w mieście
	 */
	volatile private List<Pojazd> listaPojazdow = new LinkedList<Pojazd>();
	
	/**
	 * Określa liczbę pojazdów maksymalnie przebywających w jednym czasie w mieście.
	 */
	volatile private int pojemosc=4;
	/**
	 * Parking
	 */
	volatile private int[] parking = new int[] { 0,0,0,0 };
	
	/**
	 * Konstruktor
	 * @param x		- współrzędna X
	 * @param y		- współrzędna Y
	 * @param name	- nazwa lotniska
	 * @param id	- id lotniska
	 */
	public Miasto(int x,int y, String name, int id){
		super(x, y, name, id);
	}

	
	public List<Pojazd> getListaPojazdow() {
		return listaPojazdow;
	}

	public void setListaPojazdow(List<Pojazd> listaPojazdow) {
		this.listaPojazdow = listaPojazdow;
	}


	public int getPojemosc() {
		return pojemosc;
	}


	public void setPojemosc(int pojemosc) {
		this.pojemosc = pojemosc;
	}


	public int[] getParking() {
		return parking;
	}


	public void setParking(int[] parking) {
		this.parking = parking;
	}
	
	
	public synchronized void zwiekszPojemnosc(){
		this.pojemosc++;
	}
	public synchronized void zmniejszPojemnosc(){
		this.pojemosc--;
	}
}
