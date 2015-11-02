package POJAZD;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;

import DROGA.Droga;
import GUI.MapClickVehicle;
import MAIN.Miasto;
import MAIN.PunktMapy;

/**
 * Klasa grupująca wszystkie pojazdy
 * @author Kamil Piotrowski
 *
 */
public abstract class Pojazd extends PunktMapy implements Runnable{
	
	/**
	 * Obecne miejsce, jeśli w trakcje lotu/rejsu = null
	 */
	private PunktMapy obecneMiejsce=null;
	
	/**
	 * Ilosc maksymalnego paliwa
	 */
	private double maxPaliwo;
	
	/**
	 * Ilosc obecnego paliwa
	 */
	private double paliwo;
	
	/**
	 * Maksymalna prędkość pojazdu
	 */
	private int maxSpeed;
	/**
	 * Ilość załogi
	 */
	private int liczbaZalogi;
	/**
	 * Trasa po której porusza się pojazd
	 */
	private List<Droga> trasa = new ArrayList<Droga>();
	/**
	 * Trasa powrotna - kopia trasy w odwrotnej kolejności
	 */
	private List<Droga> trasaPowrotna = new ArrayList<Droga>();
	/**
	 * Wielkość obrazu danego pojazdu
	 */
	private int size=70;
	
	/**
	 * Kostruktor
	 * @param x - położenie X Pojazdu
	 * @param y - położenie Y Pojazdu
	 * @param name - nazwa pojazdu
	 * @param id - id pojazdu
	 * @param miasto - miasto w którym zaczyna pojazd
	 * Losuje także paliwo z przedziału 1000 - 1500
	 * Ustawia obecne miejsce
	 */
	public Pojazd(int x,int y, String name, int id, Miasto miasto){
		super(x, y, name, id);
		Random generator = new Random();
		this.maxPaliwo=generator.nextInt(501)+1000;
		this.liczbaZalogi=generator.nextInt(10)+10;
		this.paliwo=maxPaliwo;
		this.obecneMiejsce=miasto;
	}

	public PunktMapy getObecneMiejsce() {
		return obecneMiejsce;
	}

	public void setObecneMiejsce(PunktMapy obecneMiejsce) {
		this.obecneMiejsce = obecneMiejsce;
	}

	public double getMaxPaliwo() {
		return maxPaliwo;
	}

	public void setMaxPaliwo(double maxPaliwo) {
		this.maxPaliwo = maxPaliwo;
	}

	public double getPaliwo() {
		return paliwo;
	}

	public void setPaliwo(double paliwo) {
		this.paliwo = paliwo;
	}

	public int getLiczbaZalogi() {
		return liczbaZalogi;
	}

	public void setLiczbaZalogi(int liczbaZalogi) {
		this.liczbaZalogi = liczbaZalogi;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	
	public void usunPojazd(){
	}
	/**
	 * Zwraca button z pojazdem do wyswietlenia na mapie
	 * @param zoom	- zoom mapy
	 * @param koorX	- wspołrzędna X
	 * @param koorY	- wspólrzędna Y
	 * @return
	 */
	public MapClickVehicle rysuj(double zoom, int koorX, int koorY){
		int size=(int)(this.getSize()/zoom);		
		ImageIcon ikona = this.returnIcon(zoom);
		MapClickVehicle button= new MapClickVehicle(koorX,koorY, size, size, this);
		button.setIcon(ikona);
		return button;
	};
	/**
	 * Zwraca obruconą, przeskalowaną ikonę pojazdu
	 * @param zoom - zomm na mapie
	 * @return
	 */
	public ImageIcon returnIcon(double zoom){
		int size=(int)(this.getSize()/zoom);
		int katObrotu = 0;
		if( !this.getTrasa().isEmpty() ) katObrotu=(int) this.getTrasa().get(0).getKatProstej();
		katObrotu*=-1;
		
		BufferedImage gr = new BufferedImage(size,size,BufferedImage.TYPE_INT_ARGB);
		Graphics2D bg = gr.createGraphics();
		
		bg.setColor(null);
		bg.setBackground(null);
		bg.rotate(Math.toRadians(katObrotu), size/2, size/2);
		bg.drawImage(this.getImage(),0,0,size, size,0,0,this.getSize(), this.getSize(), null);
	    bg.dispose();
		ImageIcon ikona = new ImageIcon(gr);
		
		return ikona;
	};
	/**
	 * Zwraca obraz danepo pojazdy
	 * @return obraz danego pojazdu
	 */
	public abstract BufferedImage getImage();

	public List<Droga> getTrasa() {
		return trasa;
	}

	public void setTrasa(List<Droga> trasa) {
		this.trasa = trasa;
	}

	public List<Droga> getTrasaPowrotna() {
		return trasaPowrotna;
	}

	public void setTrasaPowrotna(List<Droga> trasaPowrotna) {
		this.trasaPowrotna = trasaPowrotna;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
