import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.List;

public class Droga {

	private UID Id;

	public List<PunktMapy> listaMiejsc = new ArrayList<PunktMapy>();

	public UID getId() {
		return Id;
	}
	
	
}
