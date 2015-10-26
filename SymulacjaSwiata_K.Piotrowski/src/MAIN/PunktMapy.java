package MAIN;
public abstract class PunktMapy {
	private int id;
	
	private int koorX;
	private int koorY;
	private String name;
	
	public int getid() {
		return id;
	}
	public void setId(int id){
		this.id=id;
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
	
	public PunktMapy(int x,int y, String name,int id){
		this.setKoorX(x);
		this.setKoorY(y);
		this.setName(name);
		this.setId(id);
	}
}
