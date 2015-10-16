import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;

public class InfoLabel extends JLabel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2871460105041416571L;

	public InfoLabel(String text){
		setForeground(Color.WHITE);
		setText(text);
		this.setPreferredSize( new Dimension(240,21));
	}
}
