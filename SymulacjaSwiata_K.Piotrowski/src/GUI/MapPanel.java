package GUI;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.UIManager;

import MAIN.PunktMapy;
import MAIN.Swiat;

/**
 * 
 * @author Kamil Piotrowski
 * Okno z mapą świata
 *
 */
@SuppressWarnings("serial")
public class MapPanel extends JPanel implements Runnable {
	
	/**
	 * Rozmiar mapy X
	 */
	private final int maxMapX=4000;
	/**
	 * Rozmiar mapy Y
	 */
	private final int maxMapY=4000;
	/**
	 * Lewy górny róg mapy rzeczywistej(dużej, nie przeskalowanej) od którego rysujemy. X
	 */
	private int mapStartX=0;
	/**
	 * Lewy górny róg mapy rzeczywistej(dużej, nie przeskalowanej) od którego rysujemy. Y
	 */
	private int mapStartY=0;
	/**
	 * Wysokosc obszaru dużej (nieprzeskalowanej) mapy ktory wyświetlamy na ekranie
	 */
	private static int displayMapWidth=0;
	/**
	 * Szerokość obszaru dużej (nieprzeskalowanej) mapy ktory wyświetlamy na ekranie
	 */
	private static int displayMapHeight=4000;
	/**
	 * Punkt kliknięcia na mapie - używanie przy określaniu odległości przeciągnięcia. X
	 */
	private int mouseClickX=0;
	/**
	 * Punkt kliknięcia na mapie - używanie przy określaniu odległości przeciągnięcia. Y
	 */
	private int mouseClickY=0;
	/**
	 * Obecnny ZOOM
	 */
	private double mapZOOM=5.6;
	/**
	 * Maksymalny zoom
	 */
	private double maxZOOM=5.6;
	/**
	 * minimalny zoom
	 */
	private double minZOOM=1;
	/**
	 * Ostatnia szerokosć okna mapy
	 */
	private int mapWidth=720;
	/**
	 * Ostatnia wysokosć okna mapy
	 */
	private int mapHeight=700;
	/**
	 * Wielkosć przycisku na mapie
	 */
	private int cityButtonSize=200;
	/**
	 * Czy przerysować miasta
	 */
	private boolean cityRedrow=true;
	
	/**
	 * Konstruktor
	 * Dodaje akcje myszy (przeciąganie, kliknięcie scrollowanie)
	 */
	public MapPanel(){
		setBackground(UIManager.getColor("Button.focus"));
		setLayout(null);
		setDoubleBuffered(true);
		
		addMouseWheelListener(new MouseAdapter() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				
		        float steps = e.getWheelRotation();
		        mapZOOM+=steps/10;
		        if(mapZOOM>maxZOOM) mapZOOM=maxZOOM;
		        if(mapZOOM<minZOOM) mapZOOM=minZOOM;
		        
		        cityRedrow=true;
		      }
		});
		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {	
				mouseClickX=e.getX();
			    mouseClickY=e.getY();
			    setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
			    
			    cityRedrow=true;
			  }
			public void mouseReleased(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				
				cityRedrow=true;
			  }
			
		});
		addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e){
				
				int przesuniecieX=e.getX()-mouseClickX;
				int przesuniecieY=e.getY()-mouseClickY;
				
				mapStartX-=(przesuniecieX/30*mapZOOM);
				mapStartY-=(przesuniecieY/30*mapZOOM);
				
				if(mapStartX<0) mapStartX=0;
				if(mapStartY<0) mapStartY=0;
				if(mapStartX+displayMapWidth>maxMapX){
					mapStartX=maxMapX-displayMapWidth;
				}
				if(mapStartY+displayMapHeight>maxMapY){
					mapStartY=maxMapY-displayMapHeight;
				}
				
				cityRedrow=true;
			}
		});
	}

	/**
	 * Przerysuwuje komponent
	 * Rysuje mapę odpowiednio przeskalowaną
	 */
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        int x=this.getWidth();
        int y=this.getHeight();
        if(x!=this.mapWidth || y!=this.mapHeight){
        	this.cityRedrow=true;
        	this.mapWidth=x;
        	this.mapHeight=y;
        }
          
        int width=(int)(x*mapZOOM);
        int height=(int)(y*mapZOOM);
        
        if(width+mapStartX > maxMapX ){
        	mapStartX=maxMapX-width;
        }
        if(height+mapStartY >maxMapY ){
        	mapStartY=maxMapY-height;
        }
        if(mapStartX<0) mapStartX=0;
		if(mapStartY<0) mapStartY=0;
        if(width+mapStartX > maxMapX ){
        	mapZOOM=maxMapX/(float)x;
        }
        if(height+mapStartY >maxMapY ){
        	mapZOOM=maxMapY/(float)y;
        }
    
       
        
        displayMapHeight=(int)(y*mapZOOM);
        displayMapWidth=(int)(x*mapZOOM);   
        
        g.drawImage(Swiat.getBufferImage(),
        	       0, 0, x, y,
        	       mapStartX, mapStartY, displayMapWidth+mapStartX, displayMapHeight+mapStartY,
        	       null);
        
        
        //TESTOWE RYSOWANIE USUNĄĆ PÓŹNIEJ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        /*
        for(Droga linia : Swiat.getListaTras()){
        	for (PunktMapy punktB : linia.getB() ){
	        	g.drawLine((int)((linia.getA().getKoorX()-this.mapStartX)/mapZOOM), 
	        			   (int)((linia.getA().getKoorY()-this.mapStartY)/mapZOOM), 
	        			   (int)((punktB.getKoorX()-this.mapStartX)/mapZOOM), 
	        			   (int)((punktB.getKoorY()-this.mapStartY)/mapZOOM));
        	}
        }
        */
        

	}
	
	/**
	 * Metoda wątka sleep(10)
	 * Przerysowanie następuje tylko gry cityRedrow==true
	 */
	public void run() {
		while(true) {
           try {
            	if(this.cityRedrow==true){
            		drowAll();
            	}
            	
                Thread.sleep(10);
           } catch (InterruptedException e) {
                e.printStackTrace();
           }
        }
	}
	/**
	 * Rysuje wszystkie obiekty na mapie
	 */
	public void drowAll(){
		this.removeAll();;
		
		drowCities();
		rysujSkrzyzowania();
		
		this.revalidate();
		this.repaint();
	}
	
	/**
	 * Rysuje miasta na mapie
	 */
	public void drowCities(){
		int size=this.cityButtonSize;
		size/=this.mapZOOM;
		
		int koorX;
		int koorY;
		
		for(PunktMapy city : Swiat.getCityList()){
			koorX=(int)((city.getKoorX()-this.mapStartX)/this.mapZOOM);
			koorY=(int)((city.getKoorY()-this.mapStartY)/this.mapZOOM);
			
			//Wyświetlamy tylko to co widać :)
			if(koorX>-size/2 && koorY>-size/2 && koorX < this.getWidth()+size/2 && koorY < this.getHeight()+size/2){
				koorX-=size/2;
				koorY-=size/2;
				
				JButton punkt = new MapClickButton(koorX,koorY,size,size,city);
				add(punkt);
				if(this.cityRedrow==true) this.cityRedrow=false;
			}
		}
		
	}
	/**
	 * Rysuje Skrzyżowania na mapie
	 */
	public void rysujSkrzyzowania(){
		int size=120;
		size/=this.mapZOOM;
		
		int koorX;
		int koorY;
		
		for(PunktMapy city : Swiat.getSkrzyzowanieList()){
			koorX=(int)((city.getKoorX()-this.mapStartX)/this.mapZOOM);
			koorY=(int)((city.getKoorY()-this.mapStartY)/this.mapZOOM);
			
			//Wyświetlamy tylko to co widać :)
			if(koorX>-size/2 && koorY>-size/2 && koorX < this.getWidth()+size/2 && koorY < this.getHeight()+size/2){
				koorX-=size/2;
				koorY-=size/2;
				
				JButton punkt = new MapClickButton(koorX,koorY,size,size,city);
				add(punkt);
				if(this.cityRedrow==true) this.cityRedrow=false;
			}
		}
		
		
	}
}
