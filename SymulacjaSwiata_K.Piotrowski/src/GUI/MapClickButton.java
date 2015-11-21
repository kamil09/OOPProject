package GUI;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;

import javax.swing.JButton;

import MAIN.PunktMapy;

@SuppressWarnings("serial")
/**
 * 
 * @author Kamil Piotrowski
 * Przycisk na mapie w który mozna kliknąć
 *
 */
public class MapClickButton extends JButton implements Serializable{

	/**
	 * Referencja do punktu na mapie w który można kliknać
	 */
	private PunktMapy city;
	
	/**
	 * Konstruktor
	 * @param koorX	położenie punktu X
	 * @param koorY	polożenie punktu Y
	 * @param sizeX wielkość punktu X
	 * @param sizeY wielkosć punktu Y
	 * @param city referencja do obiektu
	 */
	public MapClickButton(int koorX, int koorY, int sizeX, int sizeY, PunktMapy city){
		this.setBounds(koorX,koorY,sizeX,sizeY);
		this.setCity(city);
		this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		this.setBorder(null);
		this.setFocusable(false);
		this.setContentAreaFilled(false);
		this.setDoubleBuffered(true);
		this.setToolTipText(city.getName()+" ID: #"+city.getid());
		
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				InfoPanel.printInfo(city);
			}
		});
	}

	public PunktMapy getCity() {
		return city;
	}

	public void setCity(PunktMapy city) {
		this.city = city;
	}
}
