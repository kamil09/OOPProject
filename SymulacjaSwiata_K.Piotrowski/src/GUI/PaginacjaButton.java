package GUI;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.UIManager;

@SuppressWarnings("serial")
/**
 * 
 * @author Kamil Piotrowski
 * Klasa z przyciskiem paginacji
 *
 */
public class PaginacjaButton extends JButton{
	
	/**
	 * Typ przycisku
	 * false - poprzednia strona
	 * true - następna strona
	 */
	private boolean typ;
	/**
	 * Referencja do panelu użytkowników
	 */
	private UsersPanel panel;
	
	/**
	 * Konstruktor
	 */
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
	/**
	 * Ustawienie przyciku jako poprzedni
	 */
	public void setPrev(){
		this.typ=false;
		this.setText("<");
		this.setToolTipText("Poprzednia strona");
	}
	/**
	 * ustawienie przycisku jako następny
	 */
	public void setNext(){
		this.typ=true;
		this.setText(">");
		this.setBounds(110,620,50,30);
		this.setToolTipText("Następna strona");
	}
	/**
	 * Ustawienie położenia przycisków
	 * @param h 	- wysokość od góry panelu
	 * @param panel - referencja do panelu użytkowników
	 */
	public void setValues(int h,UsersPanel panel){
		this.setBounds(getX(), h, getWidth(), getHeight());
		this.panel=panel;
	}
	
	
	
}
