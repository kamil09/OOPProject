package DROGA;
import MAIN.PunktMapy;

/**
 * 
 * @author Kamil Piotrowski
 * Klasa określająca drogę w naszym ściecie
 * Zawiera punkt A oraz listę jego następników B   -> To był jednak zły pomysł, bardzo zły :(
 * Droga określa jednak tylko 1 ! odcinek między 2 punktami
 * Jako drogę rozumie się najmniejszy możliwy odcinek trasy np miasto - skrzyżowanie
 *
 */
public class Droga {

	/**
	 * Punkt startowy naszej drogi
	 */
	private PunktMapy A; 
	/**
	 * Następnik (1) punktu A(punktu startowego drogi)
	 */
	private PunktMapy B;
	
	
	/**
	 * Konstruktor
	 * @param a - punkt mapy A
	 * @param b - następnik punktu A
	 */
	public Droga(PunktMapy a, PunktMapy b) {
		this.setA(a);
		this.setB(b);
	}
	
	public PunktMapy getA() {
		return this.A;
	}
	public void setA(PunktMapy a) {
		this.A = a;
	}
	public PunktMapy getB() {
		return this.B;
	}
	public void setB(PunktMapy b) {
		this.B = b;
	}

	
}
