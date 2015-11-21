package POJAZD;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import MAIN.Aplikacja;
import PASAZER.Pasazer;
import PRZYSTANEK.Miasto;

/**
 * Grupuje wszystkie pojazdy pasażerskie
 * @author Kamil Piotrowski
 *
 */
public abstract class PojazdPasazerski extends Pojazd implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6885064753039399139L;
	/**
	 * Maksymalna ilosć miejsc w pojeździe
	 */
	private int maxMiejsc;
	/**
	 * Ilość pozostałych wolnych miejsc
	 */
	volatile private int wolneMiejsca;
	/**
	 * czas oczekiwania na pasażerów w mieście
	 */
	private int czasPostoju=100;
	/**
	 * Lista pasażerów którzy znajdują się obecnie w pojeździe
	 */
	private List<Pasazer> listaPasazerow = new LinkedList<Pasazer>();
	
	/**
	 * Kostruktor
	 * @param d - położenie X Pojazdu
	 * @param e - położenie Y Pojazdu
	 * @param name - nazwa pojazdu
	 * @param id - id pojazdu
	 * @param miasto - referencja na miasto
	 */
	public PojazdPasazerski(double d,double e, String name, int id, Miasto miasto){
		super(d, e, name, id, miasto);
		Random generator = new Random();
		this.setCzasPostoju(700);
		this.setMaxMiejsc(generator.nextInt(10)+5);
		this.setWolneMiejsca(this.getMaxMiejsc());
		for(int i=0;i<this.getMaxMiejsc();i++) Aplikacja.getSwiat().addPasazer();
	}
	
	
	public int getMaxMiejsc() {
		return maxMiejsc;
	}
	public void setMaxMiejsc(int maxMiejsc) {
		this.maxMiejsc = maxMiejsc;
	}
	public synchronized int getWolneMiejsca() {
		return wolneMiejsca;
	}
	public synchronized void setWolneMiejsca(int wolneMiejsca) {
		this.wolneMiejsca = wolneMiejsca;
	}
	public List<Pasazer> getListaPasazerow() {
		return listaPasazerow;
	}
	public void setListaPasazerow(List<Pasazer> listaPasazerow) {
		this.listaPasazerow = listaPasazerow;
	}
	public int getCzasPostoju() {
		return czasPostoju;
	}
	public void setCzasPostoju(int czasPostoju) {
		this.czasPostoju = czasPostoju;
	}
}
