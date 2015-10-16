import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class UserLabel extends JLabel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1430137829278453051L;
	private Pasazer osoba;
	
	public UserLabel(String string){
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setFont(new Font("Dialog", Font.BOLD, 13));
		setHorizontalAlignment(SwingConstants.LEFT);
		setHorizontalTextPosition(SwingConstants.LEFT);
		setLocation(new Point(0, 0));
		setToolTipText("Kliknij aby wyświetlić info o podróżniku.");
		setDoubleBuffered(true);
		setAlignmentX(Component.LEFT_ALIGNMENT);
		setForeground(Color.WHITE);
		setVisible(true);
		setBounds(20,70,180,30);
		
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//System.out.println(osoba.getImie()+" "+osoba.getNazwisko()+" "+osoba.getWiek()+" "+osoba.getPesel());
				InfoPanel.printInfo(osoba);
				
			}
			public void mouseEntered(MouseEvent e) {
				setForeground(Color.cyan);
			}
			public void mouseExited(MouseEvent e) {
				setForeground(Color.WHITE);
			}
		});
	}
	
	public void setUser(Pasazer osoba2){
		this.osoba=osoba2;
		this.setText(this.osoba.getImie()+" "+this.osoba.getNazwisko());
	
	}
	
	
}