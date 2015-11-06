package POJAZD;

import java.awt.image.BufferedImage;
import java.util.Random;

import ENUM.Uzbrojenie;
import MAIN.Miasto;
import MAIN.Skrzyzowanie;
import MAIN.Swiat;

/**
 * 
 * @author Kamil Piotrowski
 * Klasa określająca lotniskowiec
 *
 */
public class Lotniskowiec extends PojazdWojskowy implements Statek{

	public Lotniskowiec(double d,double e, String name, int id, Miasto port){
		super(d, e, name, id, port);
		this.setSize(70);
		Random generator = new Random();
		this.setBron(Uzbrojenie.values()[generator.nextInt(6)] );
		this.setStan(0);
		this.setMaxSpeed(2);
		this.losujTrase(this);
		this.zaparkuj();
	}
	public void run() {
		while(true){
			try {
				if( !this.getTrasa().isEmpty() ){
					switch(this.getStan()){
						case 1:
							//Lotniskowiec nie parkuje
							this.wyparkuj();
							this.setStan(2);
							break;
						case 2:
							if( !this.czyPunktPostoju() ) 
								this.move(1);
							else{
								if(this.getTrasa().get(0).getB() instanceof Skrzyzowanie ){
									synchronized(this.getTrasa().get(0).getB().getHulk() ){
										this.wejdzNaSkrzyzowanie();
									}
								}
								else{
									//this.zaparkuj();
								}
							}
							break;
							
						case 3:
							
							break;
					}	
				}
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public BufferedImage getImage() {
		return Swiat.getPojazdyImages().get(0);
	}
	
	
	
	
}
