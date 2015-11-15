package DROGA;
import java.util.ArrayList;
import java.util.List;

import MAIN.PunktMapy;
import POJAZD.Pojazd;

/**
 * 
 * @author Kamil Piotrowski
 * Klasa określająca drogę w naszym ściecie
 * Zawiera punkt A oraz listę jego następników B    To był jednak zły pomysł, bardzo zły :(
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
	 * Lista pojazdów znajdujących się na drodze
	 * Używane podaczas wykrywania kolizji
	 */
	private List<Pojazd> pojazdyNaDrodze = new ArrayList<Pojazd>();
	/**
	 * Współczynnik kierunkowy prostej zawierającej naszą drogę
	 */
	private double kierunek;
	/**
	 * Kąt a naszej prostej (kierunek = tg(katProstej))
	 */
	private double katProstej;
	/**
	 * Mówi nam ile trasa (do rysowania ) jest oddalona na osi X od odcinka łączącego miasta (A - B)
	 */
	private int odX=0;
	/**
	 * Mówi nam ile trasa (do rysowania ) jest oddalona na osi Y od odcinka łączącego miasta (A - B)
	 */
	private int odY=0;
	
	/**
	 * 
	 * @param a - punkt A
	 * @param b - następnik punktu a
	 * Droga jest tworzona na podstawie tych 2 punktów
	 */
	public Droga(PunktMapy a, PunktMapy b) {
		this.setA(a);
		this.setB(b);
		this.przeliczProsta();
	}
	/**
	 * Metoda która z funkcji trygonometrycznych wylicza takie wartości jak kąt nachylenia / kierunek 
	 * Oraz najważenijsze : odsunięcie które używamy podczas rysowania pojazdów
	 */
	private void przeliczProsta(){
		int OdProstej=32;
		if(this instanceof TrasaMorska) OdProstej=20;
		
		int diffX=(int) (this.getB().getKoorX()-this.getA().getKoorX());
		int diffY=(int) (this.getB().getKoorY()-this.getA().getKoorY());
		diffY*=-1;
		this.kierunek=(double)diffY/diffX;
		this.katProstej=Math.toDegrees(Math.atan(Math.abs(this.getKierunek())));
		
		// I ĆWIARTKA
		if(diffX>0 && diffY> 0){
			this.odX=(int) (OdProstej*Math.sin(Math.toRadians(this.katProstej)));
			this.odY=(int) (OdProstej*Math.cos(Math.toRadians(this.katProstej)));
		}
		// II ĆWIARTKA
		if(diffX<0 && diffY> 0) {
			odX=(int) (OdProstej*Math.sin(Math.toRadians(this.katProstej)));
			odY=(int) (-OdProstej*Math.cos(Math.toRadians(this.katProstej)));
			this.katProstej=180-this.katProstej;
		}
		// III ĆWIARTKA
		if(diffX<0 && diffY< 0) {
			odX=(int) (-OdProstej*Math.sin(Math.toRadians(this.katProstej)));
			odY=(int) (-OdProstej*Math.cos(Math.toRadians(this.katProstej)));
			this.katProstej+=180;
		}
		// IV ĆWIARTKA
		if(diffX>0 && diffY< 0) {
			odX=(int) (-OdProstej*Math.sin(Math.toRadians(this.katProstej)));
			odY=(int) (OdProstej*Math.cos(Math.toRadians(this.katProstej)));
			this.katProstej=360-this.katProstej;
		}
	}
	public double getDifferenceBPoints(){
		double diffX=this.getB().getKoorX()-this.getA().getKoorX();
		double diffY=this.getB().getKoorY()-this.getA().getKoorY();
		diffX=Math.abs(diffX);
		diffY=Math.abs(diffY);
		double diffP=Math.pow(Math.pow(diffX, 2)+Math.pow(diffY, 2) , 0.5);
		return diffP;
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

	public double getKierunek() {
		return kierunek;
	}

	public int getOdX() {
		return odX;
	}

	public int getOdY() {
		return odY;
	}

	public double getKatProstej() {
		return katProstej;
	}

	public void setKatProstej(double katProstej) {
		this.katProstej = katProstej;
	}

	public List<Pojazd> getPojazdyNaDrodze() {
		return pojazdyNaDrodze;
	}

	public void setPojazdyNaDrodze(List<Pojazd> pojazdyNaDrodze) {
		this.pojazdyNaDrodze = pojazdyNaDrodze;
	}



	
}
