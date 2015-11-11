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
	private Object Hulk = new Object();
	/**
	 * Obiekt używany jako monitor
	 */
	private Object Veronica = new Object();
	
	
	
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
	public Object getHulk() {
		return Hulk;
	}
	public void setHulk(Object hulk) {
		Hulk = hulk;
	}
	public Object getVeronica() {
		return Veronica;
	}
	public void setVeronica(Object veronica) {
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
