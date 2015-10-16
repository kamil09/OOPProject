import java.rmi.server.UID;

public class PunktMapy {
	private UID Id;
	
	private int koorX;
	private int koorY;
	private String name;
	
	
	public UID getId() {
		return Id;
	}


	public int getKoorX() {
		return koorX;
	}


	public void setKoorX(int koorX) {
		this.koorX = koorX;
	}


	public int getKoorY() {
		return koorY;
	}


	public void setKoorY(int koorY) {
		this.koorY = koorY;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	public PunktMapy(int x,int y, String name){
		this.setKoorX(x);
		this.setKoorY(y);
		this.setName(name);
	}
}
