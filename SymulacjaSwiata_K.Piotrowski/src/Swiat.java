import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;



public class Swiat {
	
	/*
	 * Tutaj bedą przechowywane wszystkie najważniejsze informacje.
	 */
	
	private static int maxMapX=4000;
	private static int maxMapY=4000;
	private static int displayMapWidth=0;
	private static int displayMapHeight=4000;
	private static int mapStartX=0;
	private static int mapStartY=0;
	private static BufferedImage bufferImage;
	
	private static List<Pasazer> listaPasazerow = new ArrayList<Pasazer>();
	private static List<Pojazd> listaPojazdow = new ArrayList<Pojazd>();
	
	
	public Swiat(){
		try {
			bufferImage = ImageIO.read(new File("src/mapa.png"));
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
	public static int getMaxMapX() {
		return maxMapX;
	}
	public static void setMaxMapX(int maxMapX) {
		Swiat.maxMapX = maxMapX;
	}
	public static int getMaxMapY() {
		return maxMapY;
	}
	public static void setMaxMapY(int maxMapY) {
		Swiat.maxMapY = maxMapY;
	}
	public static int getMapStartX() {
		return mapStartX;
	}
	public static void setMapStartX(int mapStartX) {
		Swiat.mapStartX = mapStartX;
	}
	public static int getMapStartY() {
		return mapStartY;
	}
	public static void setMapStartY(int mapStartY) {
		Swiat.mapStartY = mapStartY;
	}
	public static int getDisplayMapWidth() {
		return displayMapWidth;
	}
	public static void setDisplayMapWidth(int displayMapWidth) {
		Swiat.displayMapWidth = displayMapWidth;
	}
	public static int getDisplayMapHeight() {
		return displayMapHeight;
	}
	public static void setDisplayMapHeight(int displayMapHeight) {
		Swiat.displayMapHeight = displayMapHeight;
	}
	
}
