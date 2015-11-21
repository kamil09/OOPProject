package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;
import java.io.Serializable;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.UIManager;


/**
 * 
 * @author Kamil Piotrowski
 * * Tworzy glówne okno programu oraz panele wewnętrzne:
 *  - panel użytkowników
 *  - panel z informacjami
 *  - panel z mapą
 */
public class GlowneOkno implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2719496847320954454L;
	/**
	 * Główna ramka okna
	 */
	private JFrame frmSymulacjaSwiataSamolotow;
	/**
	 * Panel z lisą użytkowników
	 */
	private JPanel usersPanel;
	/**
	 * Panej z informacjami - o użytkowniku, punkcie, samolocie itp
	 */
	private JPanel infoPanel;
	/**
	 * Panel z mapą świata
	 */
	private JLayeredPane mapPanel;
	private JToolBar toolBar;

	/**
	 * Launch the application.
	 */
	public static void launchWindow() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GlowneOkno window = new GlowneOkno();
					window.frmSymulacjaSwiataSamolotow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * Ustawia wygląd okna, oraz tworzy 3 panele - każdy w innym wątku
	 */
	public GlowneOkno() {
		frmSymulacjaSwiataSamolotow = new JFrame();
		frmSymulacjaSwiataSamolotow.getContentPane().setBackground(UIManager.getColor("Button.background"));
		frmSymulacjaSwiataSamolotow.setPreferredSize(new Dimension(1000, 700));
		frmSymulacjaSwiataSamolotow.setLocation(new Point(0, 0));
		frmSymulacjaSwiataSamolotow.setMinimumSize(new Dimension(1000, 700));
		frmSymulacjaSwiataSamolotow.setBackground(UIManager.getColor("Button.background"));
		frmSymulacjaSwiataSamolotow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		usersPanel = new UsersPanel();
		infoPanel = new InfoPanel();
		mapPanel = new MapPanel();
		mapPanel.setBackground(UIManager.getColor("Button.foreground"));

		//Każde z 3 części GUI to oddzielne wątki :)
		Runnable[] runners = new Runnable[3];
		Thread[] threads = new Thread[3];
		
		runners[0] = (Runnable) usersPanel;
		runners[1] = (Runnable) infoPanel;
		runners[2] = (Runnable) mapPanel;
		for (int i = 0; i < 3; i++) {
			threads[i] = new Thread(runners[i]);
			threads[i].start();
		}
		frmSymulacjaSwiataSamolotow.getContentPane().add(usersPanel, BorderLayout.WEST);
		frmSymulacjaSwiataSamolotow.getContentPane().add(infoPanel, BorderLayout.EAST);
		frmSymulacjaSwiataSamolotow.getContentPane().add(mapPanel, BorderLayout.CENTER);
		
		
		toolBar = new TopToolBar();
		frmSymulacjaSwiataSamolotow.getContentPane().add(toolBar, BorderLayout.NORTH);

		frmSymulacjaSwiataSamolotow.setVisible(true);

	}
}
