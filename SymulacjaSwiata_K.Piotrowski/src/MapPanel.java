import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.UIManager;

public class MapPanel extends JPanel implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7366974390936682417L;

	public MapPanel(){
		setBackground(UIManager.getColor("Button.focus"));
	}

	public void paintComponent(Graphics g) {
        super.paintComponent(g);
	}
	
	public void run() {
		while(true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
	}
}
