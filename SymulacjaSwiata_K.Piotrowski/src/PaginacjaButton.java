import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.UIManager;

public class PaginacjaButton extends JButton{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3707204619908789738L;
	private boolean typ;
	private UsersPanel panel;
	
	public PaginacjaButton(){
		setForeground(Color.WHITE);
		setBackground(UIManager.getColor("Button.foreground"));
		setBounds(30, 620, 50, 30);
		setFont(new Font("Dialog", Font.PLAIN, 16));
		setContentAreaFilled(false);
		//setOpaque(true);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setBorder(null);
		setFocusable(false);
		
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(typ == false){
					panel.prevPage();
				}
				else{
					panel.nextPage();
				}
			}
			public void mouseEntered(MouseEvent e) {
				setForeground(Color.RED);
			}
			public void mouseExited(MouseEvent e) {
				setForeground(Color.WHITE);
			}
		});
	}

	public void setPrev(){
		this.typ=false;
		this.setText("<");
		this.setToolTipText("Poprzednia strona");
	}
	public void setNext(){
		this.typ=true;
		this.setText(">");
		this.setBounds(110,620,50,30);
		this.setToolTipText("Następna strona");
	}
	public void setValues(int h,UsersPanel panel){
		this.setBounds(getX(), h, getWidth(), getHeight());
		this.panel=panel;
	}
	
	
	
}
