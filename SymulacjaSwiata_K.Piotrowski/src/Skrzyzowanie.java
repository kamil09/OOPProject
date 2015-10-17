
public class Skrzyzowanie extends PunktMapy{

	private PunktMapy east;
	private PunktMapy west;
	private PunktMapy south;
	private PunktMapy north;
	
	public Skrzyzowanie(int x,int y, String name,PunktMapy north,PunktMapy south,PunktMapy east,PunktMapy west){
		super(x, y, name);
		
		this.setNorth(north);
		this.setSouth(south);
		this.setEast(east);
		this.setWest(west);
		
	}

	
	public PunktMapy getEast() {
		return east;
	}

	public void setEast(PunktMapy east) {
		this.east = east;
	}

	public PunktMapy getWest() {
		return west;
	}

	public void setWest(PunktMapy west) {
		this.west = west;
	}

	public PunktMapy getSouth() {
		return south;
	}

	public void setSouth(PunktMapy south) {
		this.south = south;
	}

	public PunktMapy getNorth() {
		return north;
	}

	public void setNorth(PunktMapy north) {
		this.north = north;
	}
}
