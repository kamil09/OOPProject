package MAIN;

import java.util.ArrayList;
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
	private List<Pojazd> listaPojazdow = new ArrayList<Pojazd>();
	
	/**
	 * Określa liczbę pojazdów maksymalnie przebywających w jednym czasie w mieście.
	 */
	private int pojemosc=5;
	
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
}
