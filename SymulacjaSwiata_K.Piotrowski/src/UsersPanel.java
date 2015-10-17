import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;

public class UsersPanel extends JPanel implements Runnable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6964949260457000540L;
	private int strona=0;
	private int naStronie=24;
	private int pageEnd=0;
	private int endNumber=0;
	private List<UserLabel> labelList= new ArrayList<UserLabel>();
	
	private PaginacjaButton prev;
	private PaginacjaButton next;
	
	public UsersPanel(){
		setBorder(new MatteBorder(0, 20, 0, 2, (Color) UIManager.getColor("Button.foreground")));
		setPreferredSize(new Dimension(20, 690));
		setMinimumSize(new Dimension(220, 690));
		setMaximumSize(new Dimension(220,10000));
		setAlignmentX(Component.LEFT_ALIGNMENT);
		setBackground(UIManager.getColor("Button.foreground"));
		setAutoscrolls(true);
		setFont(new Font("Dialog", Font.PLAIN, 13));
		setForeground(UIManager.getColor("Button.light"));
		setLayout(null);
		setDoubleBuffered(true);
		
		addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				setPreferredSize(new Dimension(240, 690));
				setBorder(new MatteBorder(0, 10, 0, 2, (Color) UIManager.getColor("Button.foreground")));
			}
			public void mouseExited(MouseEvent e) {
				if(e.getX()>=getWidth() || e.getX()<=0 || e.getY()<=0 || e.getY()>=getHeight()){
					setBorder(new MatteBorder(0, 20, 0, 2, (Color) UIManager.getColor("Button.foreground")));
					setPreferredSize(new Dimension(20, 690));
				}
			}
		});
		
		JLabel tytul= new JLabel();
		tytul.setText("Lista pasażerów:");
		tytul.setPreferredSize(new Dimension(200, 70));
		tytul.setFont(new Font("Dialog", Font.BOLD, 15));
		tytul.setForeground(Color.WHITE);
		tytul.setBounds(40, 10, 150, 30);
		add(tytul);
		
		prev= new PaginacjaButton();
		next= new PaginacjaButton();
		prev.setPrev();
		next.setNext();
		add(prev);
		add(next);
		
		prev.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
			}
		});
	}
	
	 public void paintComponent(Graphics g) {      
        super.paintComponent(g);       
        this.naStronie=(this.getHeight()-100)/25;
        pageEnd=(strona+1)*naStronie;
        if(pageEnd>endNumber) pageEnd=endNumber;
        int stronaStart;
        stronaStart=strona*naStronie+1;
        if(pageEnd==0) stronaStart=0;
        
        g.drawString("Wyświetlono "+stronaStart+" - "+pageEnd+"  z "+endNumber,10,this.getHeight()-20);
        prev.setValues(this.getHeight()-80,this);
        next.setValues(this.getHeight()-80,this);
        
    }
	 
	public void printUsers(){
		
		int ilosc=Swiat.getListaPasazerow().size();
        List<Pasazer> lista = Swiat.getListaPasazerow();
        
        for(int i=endNumber;i<ilosc;i++){
        	UserLabel label = new UserLabel("user");
        	label.setUser(lista.get(i));
        	labelList.add(label);
        }
        
        if	(ilosc>this.naStronie) {
        	endNumber=ilosc;
        	ilosc=naStronie;	
        }
        else endNumber=ilosc;
       
        for(int i=0;i<labelList.size();i++){
        	this.remove(labelList.get(i));
        }
        int k=0;
        for (int i=strona*naStronie;i<labelList.size();i++){     	       	
        	if(i>(strona+1)*naStronie-1) break;
        	labelList.get(i).setBounds(20, 70+(k*20), 200, 19);
        	add(labelList.get(i));
        	k++;
        }
        if(k==0) prevPage();
        
        revalidate();
        repaint();
	}
	
	public void nextPage(){
		if(this.pageEnd < endNumber  ) {
			strona++;
			this.printUsers();
		}
	}
	public void prevPage(){
		if(this.strona>0) {
			this.strona--;
			this.printUsers();
		}
	}

	public void run() {
		while(true) {
            try {
            	//Liste odświerzamy so 0.5s
                this.printUsers();
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
	}
	 
}
