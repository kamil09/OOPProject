package GUI;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.UIManager;

import MAIN.Aplikacja;
import MAIN.PunktMapy;
import MAIN.Swiat;
import POJAZD.Pojazd;

/**
 * 
 * @author Kamil Piotrowski
 * Okno z mapą świata
 *
 */
@SuppressWarnings("serial")
public class MapPanel extends JLayeredPane implements Runnable {
	
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
	volatile private double mapZOOM=5.6;
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
	 * Informuje nas, czy należy przerysować wszystkie pojazdy (tworzymy od nowa odpowiednie buttony)
	 */
	private boolean redrowAllVehicles=false;
	/**
	 * Lista wyświetlanych pnktów na mapie które są pojazdami
	 */
	private List<MapClickVehicle> wyswietlanePojazdy= new ArrayList<MapClickVehicle>();
	/**
	 * Lista pojazdów które są przeznaczone do narysowania na mapie, ale jeszcze nie są w liści "wyswietlanePojazdy"
	 * Pojazd nowo dodany jest dodawany do tej listy a nastepnie jego button jest dodawany do wyświetlanych Pojazdów
	 */
	private static List<Pojazd> doRysowania = new ArrayList<Pojazd>();
	
	/**
	 * Konstruktor
	 * Dodaje akcje myszy (przeciąganie, kliknięcie scrollowanie)
	 */
	public MapPanel(){
		setBackground(UIManager.getColor("Button.focus"));
		setLayout(null);
		setDoubleBuffered(true);
		setLayer(this, 0);
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
				if(mapZOOM<maxZOOM){
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
        
        g.drawImage(Aplikacja.getSwiat().getBufferImage(),
        	       0, 0, x, y,
        	       mapStartX, mapStartY, displayMapWidth+mapStartX, displayMapHeight+mapStartY,
        	       null);
        
        
        //TESTOWE RYSOWANIE USUNĄĆ PÓŹNIEJ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//        for(Droga linia : Swiat.getListaTras()){
//        	
//        	g.drawLine((int)((linia.getA().getKoorX()+linia.getOdX()-this.mapStartX)/mapZOOM), 
//        			   (int)((linia.getA().getKoorY()+linia.getOdY()-this.mapStartY)/mapZOOM), 
//        			   (int)((linia.getB().getKoorX()+linia.getOdX()-this.mapStartX)/mapZOOM), 
//        			   (int)((linia.getB().getKoorY()+linia.getOdY()-this.mapStartY)/mapZOOM));
//        
//        	
//        }
    }
	
	/**
	 * Metoda wątka sleep(10)
	 * Przerysowanie następuje tylko gry cityRedrow==true
	 */
	public void run() {
		while(true) {
           try {
            	if(this.cityRedrow==true){
            		this.drowAll();
            	}  
            	this.drowVehicles();
            	this.revalidate();
        		this.repaint();
                Thread.sleep(40);
           } catch (InterruptedException e) {
                e.printStackTrace();
           }
        }
	}
	/**
	 * Rysuje wszystkie obiekty na mapie (miasta u skrzyzowania)
	 */
	public void drowAll(){
		this.removeAll();
		drowCities();
		rysujSkrzyzowania();
		this.setRedrowAllVehicles(true);
	}
	
	/**
	 * Rysuje miasta na mapie
	 */
	public void drowCities(){
		int size=this.cityButtonSize;
		size/=this.mapZOOM;
		
		int koorX;
		int koorY;
		
		for(PunktMapy city : Aplikacja.getSwiat().getCityList()){
			koorX=(int)((city.getKoorX()-this.mapStartX)/this.mapZOOM);
			koorY=(int)((city.getKoorY()-this.mapStartY)/this.mapZOOM);
			
			//Wyświetlamy tylko to co widać :)
			if(koorX>-size/2 && koorY>-size/2 && koorX < this.getWidth()+size/2 && koorY < this.getHeight()+size/2){
				koorX-=size/2;
				koorY-=size/2;
				
				JButton punkt = new MapClickButton(koorX,koorY,size,size,city);
				add(punkt,JLayeredPane.DEFAULT_LAYER);
				if(this.cityRedrow==true) this.cityRedrow=false;
			}
		}
	}
	/**
	 * Rysuje wszystkie pojazdy na mapie
	 */
	public void drowVehicles(){
		int koorX;
		int koorY;
		//RYSOWANIE NOWYCH POJAZDÓW (KTÓRYCH JESZCZE NIE MA NA MAPIE)
		
		for(Pojazd pojazd : MapPanel.doRysowania ){
			if(pojazd.getTrasa().isEmpty() ){
				koorX=(int)((pojazd.getKoorX()-this.mapStartX)/this.mapZOOM);
				koorY=(int)((pojazd.getKoorY()-this.mapStartY)/this.mapZOOM);
			}
			else{
				koorX=(int)((pojazd.getKoorX()-this.mapStartX));
				koorY=(int)((pojazd.getKoorY()-this.mapStartY));
				if( !pojazd.isCzyZaparkowano() ){
					koorX+=pojazd.getTrasa().get(0).getOdX();
					koorY+=pojazd.getTrasa().get(0).getOdY();
				}
				koorX/=this.mapZOOM;
				koorY/=this.mapZOOM;
			}
			MapClickVehicle tmp=pojazd.rysuj(mapZOOM, koorX, koorY);
			this.wyswietlanePojazdy.add(tmp);
			this.add(tmp, JLayeredPane.PALETTE_LAYER);
		}
		
		MapPanel.doRysowania.clear();
		
		//PRZERYSOWYWANIE STARYCH POJAZDOW
		List<MapClickVehicle> doUsuniecua= new ArrayList<MapClickVehicle>();
		
		for(MapClickVehicle button : this.wyswietlanePojazdy){
			if( (button.getPojazd().getKoorX()<-100) ||  (button.getPojazd().getKoorY()<-100) ) doUsuniecua.add(button);
			else{
				if(this.isRedrowAllVehicles())
					this.remove(button);
				if(button.getPojazd().getTrasa().isEmpty() ){
					koorX=(int)((button.getPojazd().getKoorX()-this.mapStartX)/this.mapZOOM);
					koorY=(int)((button.getPojazd().getKoorY()-this.mapStartY)/this.mapZOOM);
				}
				else{
					koorX=(int)((button.getPojazd().getKoorX()-this.mapStartX));
					koorY=(int)((button.getPojazd().getKoorY()-this.mapStartY));
					if( !button.getPojazd().isCzyZaparkowano() ){
						koorX+=button.getPojazd().getTrasa().get(0).getOdX();
						koorY+=button.getPojazd().getTrasa().get(0).getOdY();
					}
					koorX/=this.mapZOOM;
					koorY/=this.mapZOOM;
				}
				koorX-=(button.getPojazd().getSize()/(2*mapZOOM));
				koorY-=(button.getPojazd().getSize()/(2*mapZOOM));
				button.setIcon(button.getPojazd().returnIcon(mapZOOM));
				button.setBounds(koorX, koorY, (int)(70/mapZOOM), (int)(70/mapZOOM));
				if(this.isRedrowAllVehicles())
					this.add(button, JLayeredPane.PALETTE_LAYER);
			}
		}
		this.wyswietlanePojazdy.removeAll(doUsuniecua);
		this.remove(doUsuniecua);
		this.setRedrowAllVehicles(false);
	}
	/**
	 * Usuwa z mapy buttony określające pojazdy
	 * @param doUsuniecua Usuwa z mapy całość listy doUsuniecia
	 */
	private void remove(List<MapClickVehicle> doUsuniecua) {
		for(MapClickVehicle but : doUsuniecua){
			this.remove(but);
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
		
		for(PunktMapy city : Aplikacja.getSwiat().getSkrzyzowanieList()){
			koorX=(int)((city.getKoorX()-this.mapStartX)/this.mapZOOM);
			koorY=(int)((city.getKoorY()-this.mapStartY)/this.mapZOOM);
			
			//Wyświetlamy tylko to co widać :)
			if(koorX>-size/2 && koorY>-size/2 && koorX < this.getWidth()+size/2 && koorY < this.getHeight()+size/2){
				koorX-=size/2;
				koorY-=size/2;
				
				JButton punkt = new MapClickButton(koorX,koorY,size,size,city);
				add(punkt,JLayeredPane.DEFAULT_LAYER);
				if(this.cityRedrow==true) this.cityRedrow=false;
			}
		}
	}

	public List<MapClickVehicle> getWyswietlanePojazdy() {
		return wyswietlanePojazdy;
	}

	public void setWyswietlanePojazdy(List<MapClickVehicle> wyswietlanePojazdy) {
		this.wyswietlanePojazdy = wyswietlanePojazdy;
	}
	public void addWyswietlanePojazdy(MapClickVehicle button){
		this.wyswietlanePojazdy.add(button);
	}

	public static List<Pojazd> getDoRysowania() {
		return doRysowania;
	}

	public static void setDoRysowania(List<Pojazd> doRysowania) {
		MapPanel.doRysowania = doRysowania;
	}
	public static void addDoRysowania(Pojazd pojazd){
		MapPanel.doRysowania.add(pojazd);
	}

	public boolean isRedrowAllVehicles() {
		return redrowAllVehicles;
	}

	public void setRedrowAllVehicles(boolean redrowAllVehicles) {
		this.redrowAllVehicles = redrowAllVehicles;
	}
}
