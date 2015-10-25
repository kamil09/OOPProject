package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;


/**
 * 
 * @author Kamil Piotrowski
 * * Tworzy glówne okno programu oraz panele wewnętrzne:
 *  - panel użytkowników
 *  - panel z informacjami
 *  - panel z mapą
 */
public class GlowneOkno {

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
	private JPanel mapPanel;

	/**
	 * Obsuga wątków
	 */
	private Runnable[] runners = new Runnable[3];
	/**
	 * Obsługa wątków
	 */
	private Thread[] threads = new Thread[3];

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

		frmSymulacjaSwiataSamolotow.setVisible(true);

	}
}
