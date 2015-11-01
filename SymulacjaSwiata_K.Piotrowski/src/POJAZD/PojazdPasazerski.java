package POJAZD;

import java.util.ArrayList;
import java.util.List;

import MAIN.Miasto;
import PASAZER.Pasazer;

/**
 * Grupuje wszystkie pojazdy pasażerskie
 * @author Kamil Piotrowski
 *
 */
public abstract class PojazdPasazerski extends Pojazd{

	/**
	 * Maksymalna ilosć miejsc w pojeździe
	 */
	private int maxMiejsc;
	/**
	 * Ilość pozostałych wolnych miejsc
	 */
	private int wolneMiejsca;
	
	/**
	 * Lista pasażerów którzy znajdują się obecnie w pojeździe
	 */
	private List<Pasazer> listaPasazerow = new ArrayList<Pasazer>();
	
	/**
	 * Kostruktor
	 * @param x - położenie X Pojazdu
	 * @param y - położenie Y Pojazdu
	 * @param name - nazwa pojazdu
	 * @param id - id pojazdu
	 */
	public PojazdPasazerski(int x,int y, String name, int id, Miasto miasto){
		super(x, y, name, id, miasto);
	}
	
	
	public int getMaxMiejsc() {
		return maxMiejsc;
	}
	public void setMaxMiejsc(int maxMiejsc) {
		this.maxMiejsc = maxMiejsc;
	}
	public int getWolneMiejsca() {
		return wolneMiejsca;
	}
	public void setWolneMiejsca(int wolneMiejsca) {
		this.wolneMiejsca = wolneMiejsca;
	}
	public List<Pasazer> getListaPasazerow() {
		return listaPasazerow;
	}
	public void setListaPasazerow(List<Pasazer> listaPasazerow) {
		this.listaPasazerow = listaPasazerow;
	}
}
