import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.JPanel;
import javax.swing.UIManager;

public class MapPanel extends JPanel implements Runnable {
	
	private int width=720;
	private int height=700;
	private int mouseClickX=0;
	private int mouseClickY=0;
	private float mapZOOM=5;
	/**
	 * 
	 */
	private static final long serialVersionUID = -7366974390936682417L;
	
	public MapPanel(){
		setBackground(UIManager.getColor("Button.focus"));
		setLayout(null);
		setDoubleBuffered(true);
		
		addMouseWheelListener(new MouseAdapter() {
			public void mouseWheelMoved(MouseWheelEvent e) {
		        float steps = e.getWheelRotation();
		        float zoom=mapZOOM;
		        zoom+=steps/20;
		        if(zoom>5) zoom=5;
		        if(zoom<1) zoom=1;
		        mapZOOM=zoom;
		      }
		});
		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
			    mouseClickX=e.getX();
			    mouseClickY=e.getY();
			    setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
			  }
			public void mouseReleased(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			  }
			
		});
		addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e){
				
				int przesuniecieX=e.getX()-mouseClickX;
				int przesuniecieY=e.getY()-mouseClickY;
				
				int startX=Swiat.getMapStartX();
				int startY=Swiat.getMapStartY();
				startX-=(przesuniecieX/15*mapZOOM);
				startY-=(przesuniecieY/15*mapZOOM);
				
				if(startX<0) startX=0;
				if(startY<0) startY=0;
				if(startX+Swiat.getDisplayMapWidth()>4000){
					startX=4000-Swiat.getDisplayMapWidth();
				}
				if(startY+Swiat.getDisplayMapHeight()>4000){
					startY=4000-Swiat.getDisplayMapHeight();
				}
				
				Swiat.setMapStartX(startX);
				Swiat.setMapStartY(startY);
			}
			
			
		});
		
	}

	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        int x=this.getWidth();
        int y=this.getHeight();
          
        float zoom=mapZOOM;
        int width=(int)(x*zoom);
        int height=(int)(y*zoom);
        
        int startX=Swiat.getMapStartX();
        int startY=Swiat.getMapStartY();
        
        if(width+startX > 4000 ){
        	startX=4000-width;
        }
        if(height+startY >4000 ){
        	startY=4000-height;
        }
        if(startX<0) startX=0;
		if(startY<0) startY=0;
        if(width+startX > 4000 ){
        	zoom=4000/(float)x;
        }
        if(height+startY >4000 ){
        	zoom=4000/(float)y;
        }
    
        mapZOOM=zoom;
        Swiat.setDisplayMapHeight((int)(y*zoom));
        Swiat.setDisplayMapWidth((int)(x*zoom));   
        Swiat.setMapStartX(startX);
        Swiat.setMapStartY(startY);
        
                
        g.drawImage(Swiat.getBufferImage(),
        	       0, 0, x, y,
        	       Swiat.getMapStartX(), Swiat.getMapStartY(), Swiat.getDisplayMapWidth()+Swiat.getMapStartX(), Swiat.getDisplayMapHeight()+Swiat.getMapStartY(),
        	       null);
	}
	
	public void run() {
		while(true) {
            try {
            	repaint();
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
	}
}
