package MAIN;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Kamil Piotrowski
 * Klasa określająca drogę w naszym ściecie
 * Zawiera punkt A oraz listę jego następników B
 * Jako drogę rozumie się najmniejszy możliwy odcinek trasy np miasto - skrzyżowanie
 *
 */
public class Droga {

	/**
	 * Punkt startowy naszej drogi
	 */
	private PunktMapy A; 
	/**
	 * Następniki punktu A(punktu startowego drogi)
	 */
	private List<PunktMapy> B = new ArrayList<PunktMapy>();

	/**
	 * Konstruktor
	 * @param a - punkt mapy A
	 * @param b - lista wszystkich następników punktu A
	 */
	public Droga(PunktMapy a, List<PunktMapy> b) {
		this.setA(a);
		this.setB(b);
	}

	
	public PunktMapy getA() {
		return A;
	}
	public void setA(PunktMapy a) {
		A = a;
	}
	public List<PunktMapy> getB() {
		return B;
	}
	public void setB(List<PunktMapy> b) {
		B = b;
	}

	
}
