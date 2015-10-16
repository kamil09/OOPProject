import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public class MapClickButton extends JButton{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2902774912212112677L;

	PunktMapy city;
	
	public MapClickButton(int koorX, int koorY, int sizeX, int sizeY, PunktMapy city){
		this.setBounds(koorX,koorY,sizeX,sizeY);
		this.city=city;
		this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		this.setBorder(null);
		this.setFocusable(false);
		this.setContentAreaFilled(false);
		this.setDoubleBuffered(true);
		this.setToolTipText(city.getName());
		
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				InfoPanel.printInfo(city);
			}
		});
	}
}
