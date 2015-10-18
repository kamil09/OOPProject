import java.util.ArrayList;
import java.util.List;

public class Droga {

	private PunktMapy A; 
	private List<PunktMapy> B = new ArrayList<PunktMapy>();

	public Droga(PunktMapy a, List<PunktMapy> b) {
		this.setA(a);
		this.setB(b);
	}

	public PunktMapy getA() {
		return A;
	}
	public void setA(PunktMapy a) {
		A = a;
	}
	public List<PunktMapy> getB() {
		return B;
	}
	public void setB(List<PunktMapy> b) {
		B = b;
	}

	
}
