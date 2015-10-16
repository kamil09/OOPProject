import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;



public class Swiat {
	
	private static BufferedImage bufferImage;
	private static Graphics imageGraphics;
	
	private static List<Pasazer> listaPasazerow = new ArrayList<Pasazer>();
	private static List<Pojazd> listaPojazdow = new ArrayList<Pojazd>();
	
	
	public Swiat(){
		try {
			bufferImage = ImageIO.read(new File("src/mapa.png"));
			setImageGraphics(bufferImage.getGraphics());
		} catch (IOException ex) { 
			System.out.println("Nie można wczytać pliku mapy");
			System.exit(2);
		}    
	}
	public static BufferedImage getBufferImage(){
		return bufferImage;
	}
	
	public static void addPasazer(){
		listaPasazerow.add(new Pasazer());
	}
	public static void addSamolotPasazerski(){
		System.out.println("Dodano samolot pasażerski!");
	}
	public static void addSamolotWojskowy(){
		System.out.println("Dodano samolot wojskowy!");
	}
	public static void addStatekPasazerski(){
		System.out.println("Dodano statek pasazerski!");
	}
	public static void addLotniskowiec(){
		System.out.println("Dodano lotniskowiec!");
	}
	public static List<Pasazer> getListaPasazerow(){
		return listaPasazerow;
	}
	
//	public static void addSamolot(){
//		listaPojazdow.add(new Samolot());
//	}
//	public static void addStatek(){
//		listaPojazdow.add(new Statek());
//	}
	
	public static List<Pojazd> getListaPojazdow(){
		return listaPojazdow;
	}
	public static Graphics getImageGraphics() {
		return imageGraphics;
	}
	public static void setImageGraphics(Graphics imageGraphics) {
		Swiat.imageGraphics = imageGraphics;
	}
	
}
