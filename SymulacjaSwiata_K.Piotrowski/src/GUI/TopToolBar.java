package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.UIManager;

import MAIN.Aplikacja;

public class TopToolBar extends JToolBar implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1351240703547104735L;

	public TopToolBar(){
		setOpaque(false);
		setFont(new Font("Dialog", Font.PLAIN, 13));
		setForeground(UIManager.getColor("Button.foreground"));
		setBackground(UIManager.getColor("Button.foreground"));
		setBorder(null);
		setAlignmentX(Component.LEFT_ALIGNMENT);
		setPreferredSize(new Dimension(900, 25));
		setLayout(null);
	
		JButton btnNewButton = new JButton("Zapisz");
		btnNewButton.setForeground(Color.RED);
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setBorder(null);
		btnNewButton.setToolTipText("Kliknij aby zapisać symulację");
		btnNewButton.setBounds(20, 2, 100, 20);
		btnNewButton.setFocusable(false);
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				try {
					Aplikacja.serializacja();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		add(btnNewButton);
		
		
		
		JButton btnNewButton_1 = new JButton("Wczytaj");
		btnNewButton_1.setForeground(Color.RED);
		btnNewButton_1.setContentAreaFilled(false);
		btnNewButton_1.setBorder(null);
		btnNewButton_1.setToolTipText("Kliknij aby wczytać ostatnią symulację");
		btnNewButton_1.setBounds(140, 2, 100, 20);
		btnNewButton_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_1.setFocusable(false);
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				try {
					Aplikacja.deserializacja();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		add(btnNewButton_1);
	
	}
	
	public void paintComponent(Graphics g)
	{
	    g.setColor( getBackground() );
	    g.fillRect(0, 0, getSize().width, getSize().height);
	    super.paintComponent(g);
	}
	
}
