package POJAZD;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import GUI.MapClickVehicle;
import MAIN.Miasto;
import MAIN.Swiat;

/**
 * 
 * @author Kamil Piotrowski
 * Klasa określająca lotniskowiec
 *
 */
public class Lotniskowiec extends PojazdWojskowy implements Statek{

	public Lotniskowiec(int x,int y, String name, int id, Miasto port){
		super(x, y, name, id, port);
		
		
	}
	
	public void run() {
		while(true){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public MapClickVehicle rysuj(double zoom, int koorX, int koorY){
		int size=(int)(70/zoom);		
		ImageIcon ikona = this.returnIcon(zoom);
		MapClickVehicle button= new MapClickVehicle(koorX,koorY, size, size, this);
		button.setIcon(ikona);
		return button;
	}
	public ImageIcon returnIcon(double zoom){
		int size=(int)(70/zoom);
		int size2=70;
		int katObrotu = 0;
		if( !this.getTrasa().isEmpty() ) katObrotu=(int) this.getTrasa().get(0).getKatProstej();
		katObrotu*=-1;
		
		BufferedImage gr = new BufferedImage(size,size,BufferedImage.TYPE_INT_RGB);
		Graphics2D bg = gr.createGraphics();
		bg.setColor(null);
		bg.setBackground(null);
		bg.fillRect(0, 0, size, size);
		bg.rotate(Math.toRadians(katObrotu), size/2, size/2);
		bg.drawImage(Swiat.getLotniskowiecImage(),0,0,size, size,0,0,size2, size2, null);
	    bg.dispose();
		ImageIcon ikona = new ImageIcon(gr);
		
		return ikona;
	}
	
}
