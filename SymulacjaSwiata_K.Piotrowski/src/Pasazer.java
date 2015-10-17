import java.rmi.server.UID;
import java.util.Random;

public class Pasazer implements Runnable{
	
	//Dane personalne pasażera
	//Tylko po odczytu - losowane przy tworzeniu pasażera
	private UID Id;
	private ImieRandom imie;		
	private NazwiskoRandom nazwisko;
	private int wiek;
	private long pesel;
	
	//Typ podruży : 0- prywatna 1-służbowa
	private boolean typPodrozy;
	private int czasPostoju;
	
	//Informacje o podróży
	private PunktMapy miastoRodzinne;
	private PunktMapy obecnyPunkt;
	private PunktMapy miastoDocelowe;
	private Droga trasa;
	private Droga[] trasaPowrotna;
	
	/*
	 * Status :
	 * 0- czeka na wylosowanie trasy
	 * 1- Oczekuje na transport
	 * 2- W trasie docelowej
	 * 3- W trasie powrotnej
	 * 4- Oczekuje w miejscu docelowym
	 * 5- Powrócił do domu, oczekuje na zmianę statusu w celu wylosowania kolejnej trasy
	 */
	private int status;

//SETTERY I GETTERY
	public UID getId(){
		return Id;
	}
	public ImieRandom getImie() {
		return imie;
	}
	public NazwiskoRandom getNazwisko() {
		return nazwisko;
	}
	public int getWiek() {
		return wiek;
	}
	public long getPesel() {
		return pesel;
	}

	
	public boolean isTypPodrozy() {
		return typPodrozy;
	}

	public void setTypPodrozy(boolean typPodrozy) {
		this.typPodrozy = typPodrozy;
	}

	public int getCzasPostoju() {
		return czasPostoju;
	}

	public void setCzasPostoju(int czasPostoju) {
		this.czasPostoju = czasPostoju;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
//KONSTRUKTOR
//LOSUJE IMIE, NAZWISKO, WIEK i PESEL ; ID;
	public Pasazer(){
		Random generator = new Random();
		
		this.Id = new UID();
		this.wiek=generator.nextInt(84)+16;
		this.status=0;
		this.pesel=(long)(2015-(1900+this.wiek))*1000000000;
		this.pesel+=(long)(generator.nextInt(888888888)+111111111);
		this.imie=ImieRandom.values()[generator.nextInt(142)+1];
		this.nazwisko=NazwiskoRandom.values()[generator.nextInt(159)+1];
		this.miastoRodzinne=Swiat.getCityList().get(generator.nextInt(15));
		this.obecnyPunkt=this.miastoRodzinne;
	}
	
	public PunktMapy getMiastoRodzinne() {
		return miastoRodzinne;
	}
	
	public void run() {
		while(true) {
            try {
                Thread.sleep(2000);
                //System.out.println(this.imie+"  "+this.nazwisko);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
	}

}
