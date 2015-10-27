package MAIN;

import java.util.ArrayList;
import java.util.List;

import POJAZD.Pojazd;

public abstract class Miasto extends PunktMapy{

	/**
	 * Lista pojazdów stacjonujących obecnie w mieście
	 */
	private List<Pojazd> listaPojazdow = new ArrayList<Pojazd>();
	
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
}
