package PRZYSTANEK;

import java.io.Serializable;

import MAIN.PunktMapy;

/**
 * 
 * @author no-one
 * Klasa określająca skrzyżowanie dziedziczy po punkcie mapy
 *
 */
public class Skrzyzowanie extends PunktMapy implements Serializable{
	
	
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3961080860450191926L;

	/**
	 * Informuje czy obecne skrzyzowanie jest zajete, czy nie
	 * Potrzebne przy wznawianiu symulacji
	 */
	private boolean zajete=false;
	/**
	 * id Obiektu który zajął skrzyżowanie
	 */
	private int idObiektu=-1;
	
	/**
	 * Konstruktor
	 * @param x		- współrzędna X
	 * @param y		- współrzędna Y
	 * @param name	- nazwa lotniska
	 * @param id	- id lotniska
	 */
	public Skrzyzowanie(int x,int y, String name,int id){
		super(x, y, name, id);
	}

	public boolean isZajete() {
		return zajete;
	}

	public void setZajete(boolean zajete) {
		this.zajete = zajete;
	}

	public int getIdObiektu() {
		return idObiektu;
	}

	public void setIdObiektu(int idObiektu) {
		this.idObiektu = idObiektu;
	}
	
}
