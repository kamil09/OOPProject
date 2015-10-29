package MAIN;

/**
 * 
 * @author Kamil Piotrowski
 * Klasa okreslająca punkt mapy, dziedziczy po niej wile innych klas
 *
 */
public abstract class PunktMapy {
	
	/**
	 * ID punktu
	 */
	private int id;
	/**
	 * Współrzędna X punktu
	 */
	private int koorX;
	/**
	 * Współrzędna Y punktu
	 */
	private int koorY;
	/**
	 * Nazwa miejsca na mapie
	 */
	private String name;
	
	public int getid() {
		return id;
	}
	public void setId(int id){
		this.id=id;
	}

	public int getKoorX() {
		return koorX;
	}


	public void setKoorX(int koorX) {
		this.koorX = koorX;
	}


	public int getKoorY() {
		return koorY;
	}


	public void setKoorY(int koorY) {
		this.koorY = koorY;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Konstruktor
	 * @param x		- współrzędna X
	 * @param y		- współrzędna Y
	 * @param name	- nazwa lotniska
	 * @param id	- id lotniska
	 */
	public PunktMapy(int x,int y, String name,int id){
		this.setKoorX(x);
		this.setKoorY(y);
		this.setName(name);
		this.setId(id);
	}
}
