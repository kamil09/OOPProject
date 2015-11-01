package GUI;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import POJAZD.Pojazd;

/**
 * 
 * @author Kamil Piotrowski
 *	Ikona (statek / samolot w którą klikamy na mapie)
 */
@SuppressWarnings("serial")
public class MapClickVehicle extends JButton{

	
	/**
	 * Referencja do punktu na mapie w który można kliknać
	 */
	private Pojazd pojazd;
	
	/**
	 * Konstruktor
	 * @param koorX	położenie punktu X
	 * @param koorY	polożenie punktu Y
	 * @param sizeX wielkość punktu X
	 * @param sizeY wielkosć punktu Y
	 * @param pojazd referencja do obiektu
	 */
	public MapClickVehicle(int koorX, int koorY, int sizeX, int sizeY, Pojazd pojazd){
		this.setBounds(koorX,koorY,sizeX,sizeY);
		this.setPojazd(pojazd);
		this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		this.setBorder(null);
		this.setFocusable(false);
		this.setContentAreaFilled(false);
		this.setDoubleBuffered(true);
		this.setToolTipText(pojazd.getName()+" ID: #"+pojazd.getid());
		
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				InfoPanel.printInfo(pojazd);
			}
		});
	}

	public Pojazd getPojazd() {
		return pojazd;
	}

	public void setPojazd(Pojazd pojazd) {
		this.pojazd = pojazd;
	}
}
