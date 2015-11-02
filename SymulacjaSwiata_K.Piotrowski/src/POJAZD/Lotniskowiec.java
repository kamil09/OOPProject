package POJAZD;

import java.awt.image.BufferedImage;
import java.util.Random;

import ENUM.Uzbrojenie;
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
		this.setSize(70);
		Random generator = new Random();
		this.setBron(Uzbrojenie.values()[generator.nextInt(6)] );
		
	}
	
	public void run() {
		while(true){
			try {
				this.setKoorX(this.getKoorX()+2);
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public BufferedImage getImage() {
		return Swiat.getPojazdyImages().get(0);
	}
	
	
	
}
