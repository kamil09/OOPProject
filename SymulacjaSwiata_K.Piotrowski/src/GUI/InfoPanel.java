package GUI;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;

import DROGA.Droga;
import MAIN.PunktMapy;
import MAIN.Swiat;
import PASAZER.Pasazer;
import POJAZD.Pojazd;
import POJAZD.PojazdPasazerski;
import POJAZD.PojazdWojskowy;
import POJAZD.StatekWycieczkowy;

@SuppressWarnings("serial")
/**
 * 
 * @author Kamil Piotrowski
 * Panel z informacjami (po prawej stronie okna)
 */
public class InfoPanel extends JPanel implements Runnable{
	
	/**
	 * Scroll Panel z informacjami
	 */
	JScrollPane scrollPane;
	/**
	 * Wewnętrzny panel (wewnątrz scroll Panel) z informacjami
	 */
	static JPanel panelInfo;
	/**
	 * Typ wyświetlanych informacji - 
	 * Dzieki temu nie przerysowujemy bez potrzeby
	 */
	static int typ=-1;
	/**
	 * Obecnie wyświetlany pasażer
	 */
	static Pasazer pasazer;
	/**
	 * Obecnie wyświetlany pojazd
	 */
	static Pojazd pojazd;
	/**
	 * Obecnie wyświetlany punkt mapy (miasto lub sprzyzowanie)
	 */
	static PunktMapy punkt;

	/**
	 * Konstruktor panelu - ustawia wygląd i tworzy 4 przyciski akcji
	 */
	public InfoPanel(){
		setBorder(new MatteBorder(20, 2, 0, 0, (Color) UIManager.getColor("Button.foreground")));
		setBackground(UIManager.getColor("Button.foreground"));
		setMinimumSize(new Dimension(250, 690));
		setSize(new Dimension(250, 690));
		setPreferredSize(new Dimension(260, 690));
		setFont(new Font("Dialog", Font.PLAIN, 13));
		setForeground(UIManager.getColor("Button.light"));
		setDoubleBuffered(true);
		
		JButton but1 = new JButton("+ samolot pasażerski");
		but1.setPreferredSize(new Dimension(200, 25));
		but1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		but1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Swiat.addSamolotPasazerski();
			}
		});
		add(but1);
		
		JButton but2 = new JButton("+ samolot wojskowy");
		but2.setPreferredSize(new Dimension(200, 25));
		but2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		but2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Swiat.addSamolotWojskowy();
			}
		});
		add(but2);
		
		JButton but3 = new JButton("+ statek wycieczkowy");
		but3.setPreferredSize(new Dimension(200, 25));
		but3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		but3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Swiat.addStatekPasazerski();
			}
		});
		add(but3);
		
		JButton but4 = new JButton("+ lotniskowiec");
		but4.setPreferredSize(new Dimension(200, 25));
		but4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		but4.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Swiat.addLotniskowiec();
			}
		});
		add(but4);
		
		JLabel lab1 = new JLabel("Szczegółowe informacje");
		lab1.setHorizontalTextPosition(SwingConstants.CENTER);
		lab1.setHorizontalAlignment(SwingConstants.CENTER);
		lab1.setPreferredSize(new Dimension(200, 40));
		lab1.setForeground(Color.WHITE);
		add(lab1);
		
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setPreferredSize(new Dimension(280, 510));
		scrollPane.setDoubleBuffered(true);
		scrollPane.setAutoscrolls(true);
		scrollPane.setBorder(new MatteBorder(2, 0, 2, 0, (Color) UIManager.getColor("Button.disabledText")));
		scrollPane.setBackground(UIManager.getColor("Button.foreground"));
		add(scrollPane);
		
		panelInfo = new JPanel();
		panelInfo.setBackground(UIManager.getColor("Button.foreground"));
		panelInfo.setForeground(Color.WHITE);
		panelInfo.setDoubleBuffered(true);
		panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
		panelInfo.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
		
		scrollPane.setViewportView(panelInfo);
		
	}

	/**
	 * Metoda startowa wątka
	 */
	public void run() {
		while(true) {
            try {
            	switch(typ){
	    			case 0:
	    				printInfo(pasazer);
	    				break;
	    			case 1:
	    				printInfo(pojazd);
	    				break;
	    			case 3:
	    				printInfo(punkt);
	    				break;
	    		}
                Thread.sleep(1200);     
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
	}
	
	/**
	 * Nadpisanie metody przerysowującej okno
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		scrollPane.setPreferredSize(new Dimension(getWidth(), getHeight()-190 )); 
	}

	/**
	 * 
	 * Wyświetla infomacje o konkretnym podrózniku w oknie informacyjnym
	 * @param pasazer2 - referencja do obiektu pasażer, którego wyświetlamy
	 */
	public static void printInfo(Pasazer pasazer2){
		
		
		pasazer=pasazer2;
	
		
		JLabel label = new InfoLabel( "Imie: "+pasazer.getImie() );
		JLabel label2 = new InfoLabel( "Nazwisko: "+pasazer.getNazwisko() );
		JLabel label3 = new InfoLabel( "Wiek: "+pasazer.getWiek() );
		JLabel label4 = new InfoLabel( "Pesel: "+pasazer.getPesel() );
		JLabel label5 = new InfoLabel( "Miasto rodzinne: "+ pasazer.getMiastoRodzinne().getName());
		JLabel label6;
		if( pasazer.getMiastoDocelowe()!=null){
			label6 = new InfoLabel( "Miasto docelowe: "+ pasazer.getMiastoDocelowe().getName());
		}
		else{
			label6 = new InfoLabel( "Miasto docelowe: brak");
		}
		JLabel label7 = new InfoLabel( "Czas w miejscu docelowym: "+pasazer.getCzasPostoju() );
		JLabel label8;
		if ( pasazer.isTypPodrozy() ) 
			label8 = new InfoLabel("Typ podróży : służobowa");
		else
			label8 = new InfoLabel("Typ posróży : prywatna");
		JLabel label9;	
		if( pasazer.getObecnyPunkt()!=null){
			label9 = new InfoLabel( "Miejsce: " +pasazer.getObecnyPunkt().getName() );
		}
		else label9 = new InfoLabel( "Miejsce: w drodze" );
		JLabel label10;
		if(pasazer.getObecnyPojazd()!=null){
			label10 = new InfoLabel( "Pojazd: " +pasazer.getObecnyPojazd().getName() );
		}
		else{
			label10 = new InfoLabel( "Pojazd: brak"  );
		}
		JLabel label11 = new InfoLabel( "Trasa: " );
		
		panelInfo.removeAll();
		
		panelInfo.add(label);
		panelInfo.add(Box.createVerticalStrut(10));
		panelInfo.add(label2);
		panelInfo.add(Box.createVerticalStrut(10));
		panelInfo.add(label3);
		panelInfo.add(Box.createVerticalStrut(10));
		panelInfo.add(label4);
		panelInfo.add(Box.createVerticalStrut(10));
		panelInfo.add(label5);
		panelInfo.add(Box.createVerticalStrut(10));
		panelInfo.add(label6);
		panelInfo.add(Box.createVerticalStrut(10));
		panelInfo.add(label7);
		panelInfo.add(Box.createVerticalStrut(10));
		panelInfo.add(label8);
		panelInfo.add(Box.createVerticalStrut(10));
		panelInfo.add(label9);
		panelInfo.add(Box.createVerticalStrut(10));
		panelInfo.add(label10);
		panelInfo.add(Box.createVerticalStrut(10));
		panelInfo.add(label11);
		panelInfo.add(Box.createVerticalStrut(5));
		
		for( PunktMapy punkt : pasazer.getTrasa() ){
			JLabel label12 = new InfoLabel( "   --->  "+punkt.getName() );
			panelInfo.add(label12);
		}
		
		panelInfo.revalidate();
		panelInfo.repaint();
		typ=0;
		
	}
	
	/**
	 * Wypisuje informacje o samolocie
	 * @param samolot2 - referencja na wyświetlany obiekt
	 */
	public static void printInfo(Pojazd pojazd2){
	
		pojazd=pojazd2;
		panelInfo.removeAll();
		
		panelInfo.add( new InfoLabel( "Nazwa: "+pojazd.getName() ));
		panelInfo.add(Box.createVerticalStrut(10));
		if (pojazd instanceof StatekWycieczkowy ) {
			panelInfo.add( new InfoLabel( "Firma: "+((StatekWycieczkowy) pojazd).getFirma() ));
			panelInfo.add(Box.createVerticalStrut(10));
		}
		panelInfo.add( new InfoLabel( "ID: "+pojazd.getid() ));
		panelInfo.add(Box.createVerticalStrut(10));
		panelInfo.add( new InfoLabel( "Współrzędna X: "+pojazd.getKoorX() ));
		panelInfo.add(Box.createVerticalStrut(10));
		panelInfo.add( new InfoLabel( "Współrzędna Y: "+pojazd.getKoorY() ));
		panelInfo.add(Box.createVerticalStrut(10));
		panelInfo.add( new InfoLabel( "Predkosc: "+ pojazd.getMaxSpeed()));
		panelInfo.add(Box.createVerticalStrut(10));
		panelInfo.add( new InfoLabel( "Paliwo:"+ pojazd.getPaliwo()));
		panelInfo.add(Box.createVerticalStrut(10));
		panelInfo.add( new InfoLabel( "Pojemność baku: "+ pojazd.getMaxPaliwo()));
		panelInfo.add(Box.createVerticalStrut(10));
		panelInfo.add( new InfoLabel( "Liczba personelu: "+ pojazd.getLiczbaZalogi()));
		panelInfo.add(Box.createVerticalStrut(10));
		JLabel label9;
		if( pojazd.getObecneMiejsce() != null ) label9 = new InfoLabel( "Obecne miasto: "+ pojazd.getObecneMiejsce().getName());
		else label9 = new InfoLabel( "Obecne miejsce: w trakcie podróży");
		panelInfo.add( label9 );
		panelInfo.add(Box.createVerticalStrut(10));
		
		
		
		
		if(pojazd instanceof PojazdPasazerski ){
			panelInfo.add( new InfoLabel( "Liczba wolnych miejsc: "+((PojazdPasazerski) pojazd).getWolneMiejsca() ));
			panelInfo.add(Box.createVerticalStrut(10));
			panelInfo.add( new InfoLabel( "Maksymalna liczba miejsc: "+((PojazdPasazerski) pojazd).getMaxMiejsc()));
			panelInfo.add(Box.createVerticalStrut(10));
			panelInfo.add( new InfoLabel( "Lista pasażerów: "));
			panelInfo.add(Box.createVerticalStrut(5));
			for(Pasazer osoba : ((PojazdPasazerski) pojazd).getListaPasazerow()){
				panelInfo.add( new InfoLabel( "   --->  "+osoba.getImie() + osoba.getNazwisko() ));
			}
			panelInfo.add(Box.createVerticalStrut(10));
		}
		if(pojazd instanceof PojazdWojskowy){
			panelInfo.add( new InfoLabel( "Uzbrojenie: "+((PojazdWojskowy) pojazd).getBron() ));
			panelInfo.add(Box.createVerticalStrut(10));
		}
		
		panelInfo.add( new InfoLabel( "Trasa: "));
		panelInfo.add(Box.createVerticalStrut(5));
		for( Droga droga : pojazd.getTrasa() ){
			panelInfo.add( new InfoLabel( "   --->  "+droga.getB().getName() ));
		}
		
		panelInfo.add(Box.createVerticalStrut(30));
		JButton but1 = new JButton("Usun pojazd");
		but1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		but1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Swiat.removePojazd(pojazd);
			}
		});
		panelInfo.add(but1);
		panelInfo.add(Box.createVerticalStrut(10));
		JButton but2 = new JButton("Zmień trase");
		but2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		but2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//Swiat.trasaChange(pojazd);
			}
		});
		panelInfo.add(but2);
		panelInfo.add(Box.createVerticalStrut(10));
		
		
		panelInfo.revalidate();
		panelInfo.repaint();
		typ = 1;
		
	}
	
	/**
	 * Wypisuje onformacje o punkcie na mapie (miasto, skrzyżowanie)
	 * @param punkt2 - referencja do wypisywanego obiektu
	 */
	public static void printInfo(PunktMapy punkt2){
			
		
		
		panelInfo.removeAll();
		punkt=punkt2;
		
		JLabel label = new InfoLabel( "Nazwa: "+punkt.getName());
		JLabel label2 = new InfoLabel( "Typ : "+punkt.getClass().getName()); 
		JLabel label3 = new InfoLabel( "Wspólrzędna X: "+punkt.getKoorX() );
		JLabel label4 = new InfoLabel( "Współrzędna Y: "+punkt.getKoorY() );
		
		panelInfo.add(label);
		panelInfo.add(Box.createVerticalStrut(10));
		panelInfo.add(label2);
		panelInfo.add(Box.createVerticalStrut(10));
		panelInfo.add(label3);
		panelInfo.add(Box.createVerticalStrut(10));
		panelInfo.add(label4);
		panelInfo.add(Box.createVerticalStrut(10));
		
		panelInfo.revalidate();
		panelInfo.repaint();
		typ =3;
	}
	

}


