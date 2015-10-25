package WSZYTKO;
public class Pojazd extends PunktMapy{
	
	//Obecne miejsce, jeśli w trakcje lotu/rejsu = null
	private PunktMapy obecneMiejsce=null;
	
	public Pojazd(int x,int y, String name, int id){
		super(x, y, name, id);
	}

	public PunktMapy getObecneMiejsce() {
		return obecneMiejsce;
	}

	public void setObecneMiejsce(PunktMapy obecneMiejsce) {
		this.obecneMiejsce = obecneMiejsce;
	}

}
