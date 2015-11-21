package MAIN;

import java.io.Serializable;

/**
 * 
 * @author Kamil Piotrowski
 * Klasa okreslająca punkt mapy, dziedziczy po niej wile innych klas
 *
 */
public abstract class PunktMapy implements Serializable{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7305599167095344286L;
	/**
	 * ID punktu
	 */
	private int id;
	/**
	 * Współrzędna X punktu
	 */
	private double koorX;
	/**
	 * Współrzędna Y punktu
	 */
	private double koorY;
	/**
	 * Nazwa miejsca na mapie
	 */
	private String name;
	/**
	 * Objekt używany jako monitor
	 */
	private Monitor Hulk = new Monitor();
	/**
	 * Obiekt używany jako monitor
	 */
	private Monitor Veronica = new Monitor();
	
	
	
	/**
	 * Konstruktor
	 * @param d		- współrzędna X
	 * @param e		- współrzędna Y
	 * @param name	- nazwa lotniska
	 * @param id	- id lotniska
	 */
	public PunktMapy(double d,double e, String name,int id){
		this.setKoorX(d);
		this.setKoorY(e);
		this.setName(name);
		this.setId(id);
	}
	public Monitor getHulk() {
		return Hulk;
	}
	public void setHulk(Monitor hulk) {
		Hulk = hulk;
	}
	public Monitor getVeronica() {
		return Veronica;
	}
	public void setVeronica(Monitor veronica) {
		Veronica = veronica;
	}
	public int getid() {
		return id;
	}
	public void setId(int id){
		this.id=id;
	}
	public double getKoorX() {
		return koorX;
	}
	public void setKoorX(double d) {
		this.koorX = d;
	}
	public double getKoorY() {
		return koorY;
	}
	public void setKoorY(double d) {
		this.koorY = d;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
