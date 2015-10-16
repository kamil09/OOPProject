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

public class InfoPanel extends JPanel implements Runnable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 260127384653453915L;
	JScrollPane scrollPane;
	static JPanel panelInfo;
	static int typ=-1;
	static Pasazer pasazer;
	static Statek statek;
	static Samolot samolot;
	static PunktMapy punkt;

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

	public void run() {
		while(true) {
            try {
            	repaint();
                Thread.sleep(3000);     
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		switch(typ){
			case 0:
				printInfo(pasazer);
				break;
			case 1:
				printInfo(samolot);
				break;
			case 2:
				printInfo(statek);
				break;
			case 3:
				printInfo(punkt);
				break;
		}
		scrollPane.setPreferredSize(new Dimension(getWidth(), getHeight()-190 )); 
	}

	
	public static void printInfo(Pasazer pasazer2){
		
		if(typ != 0 || !pasazer.equals(pasazer2)){
			pasazer=pasazer2;
		
			panelInfo.removeAll();
			JLabel label = new InfoLabel( "Imie: "+pasazer.getImie() );
			JLabel label2 = new InfoLabel( "Nazwisko: "+pasazer.getNazwisko() );
			JLabel label3 = new InfoLabel( "Wiek: "+pasazer.getWiek() );
			JLabel label4 = new InfoLabel( "Pesel: "+pasazer.getPesel() );
			JLabel label5 = new InfoLabel( "Czas w miejscu docelowym: "+pasazer.getCzasPostoju() );
			JLabel label6 = new InfoLabel( "Miasto rodzinne: " );
			JLabel label7 = new InfoLabel( "Miasto docelowe: ");
			JLabel label8 = new InfoLabel( "Obecna trasa: " );
			JLabel label9 = new InfoLabel( "Obecny pojazd: ");
			
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
			
			panelInfo.revalidate();
			panelInfo.repaint();
		}
			typ=0;
		
	}
	public static void printInfo(Samolot samolot2){
		
		if(typ!=1 || !samolot.equals(samolot2)){
		
			panelInfo.removeAll();
			samolot=samolot2;
			
			panelInfo.revalidate();
			panelInfo.repaint();
		}
		typ=1;
	}
	public static void printInfo(Statek statek2){
		
		if(typ!=2 || !statek.equals(statek2)){
		
			panelInfo.removeAll();	
			statek=statek2;
			
			panelInfo.revalidate();
			panelInfo.repaint();
			}
		typ=2;
	}
	public static void printInfo(PunktMapy punkt2){
			
		if(typ!=3 || !punkt.equals(punkt2)){
		
			panelInfo.removeAll();
			punkt=punkt2;
			
			panelInfo.revalidate();
			panelInfo.repaint();
		}
		typ=3;
	}
}


