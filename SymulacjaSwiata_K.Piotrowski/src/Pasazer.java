import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.List;
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
	private double czasPostoju;
	
	//Informacje o podróży
	private PunktMapy miastoRodzinne;
	private PunktMapy obecnyPunkt;
	private PunktMapy miastoDocelowe;
	
	//Trasa : a - b - c - d - e - f - g - h - ciąg samych miast. bez obecnego, ale łącznie z końcowym!!!
	private List<PunktMapy> trasa = new ArrayList<PunktMapy>();

	
	/*
	 * Status :
	 * 0- czeka na wylosowanie trasy
	 * 1- W punkcie - czeka
	 * 2- W pojezdzie 
	 * 3- Oczekuje w miejscu docelowym
	 * 4- W miejscu docelowym - oczekuje na wylosowanie trasy 
	 * 5- Powrócił do domu, oczekuje na zmianę statusu w celu wylosowania kolejnej trasy
	 * 
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

	public double getCzasPostoju() {
		return czasPostoju;
	}

	public void setCzasPostoju(double czasPostoju) {
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
		int miastoR = generator.nextInt(12);
		//9 - 12 są lotniska wojskowe
		if(miastoR >=9 && miastoR<=12) miastoR+=4; 
		this.miastoRodzinne=Swiat.getCityList().get(miastoR);
		this.setObecnyPunkt(this.miastoRodzinne);
	}
	
	public PunktMapy getMiastoRodzinne() {
		return miastoRodzinne;
	}
	
	public void run() {
		while(true) {
            try {
            	//Akcje dla poszczególnych stanów pasazera
            	switch(this.getStatus()){
            		case 0:
            			this.setMiastoDocelowe(null);
            			this.losujTrase();
            			break;
            		case 1:
            			//Sprawdzenie czy nie przesiada się z lotniska / port lub odwrotnie
            			if( this.getObecnyPunkt().getClass().getName() != this.getTrasa().get(0).getClass().getName() ){
            				this.setObecnyPunkt(this.getTrasa().get(0));
            				this.trasa.remove(0);}
            			//Sprawdzenie czy dotarł już do końca trasy
            			if( this.getTrasa().get(0).getid() == this.getObecnyPunkt().getid() ){
            				this.trasa.clear();
            				this.setStatus(4);
            				//Dodatkowe sprawdzenie czy powrócił do domu
            				if(this.getObecnyPunkt().getid() == this.getMiastoRodzinne().getid() ){
            					this.setStatus(5);
            				}
            			}
            			//Przeszukanie czy w obecnym miejscu znajduje się odpowiedni pojazd
            			/*
            			 * XKOOOOOOOOOOOOOOOOOOOOOOOOODDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD
            			 */
            			
            			break;
            		case 2:
            			
            			break;
            		case 3:
            			this.czasPostoju-=0.02;
            			if(this.czasPostoju<0) this.setStatus(2);
            			break;
            		case 4:
            			this.losujTrasePowrotna();
            			this.setStatus(3);
            			break;
            		case 5:
            			this.setStatus(0);
            			break;
            	}
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
	}
	
	public void losujTrase(){
		
	}
	public void losujTrasePowrotna(){
		
	}
	
	public List<PunktMapy> getTrasa() {
		return trasa;
	}
	public void setTrasa(List<PunktMapy> trasa) {
		this.trasa = trasa;
	}
	public PunktMapy getObecnyPunkt() {
		return obecnyPunkt;
	}
	public void setObecnyPunkt(PunktMapy obecnyPunkt) {
		this.obecnyPunkt = obecnyPunkt;
	}
	public PunktMapy getMiastoDocelowe() {
		return miastoDocelowe;
	}
	public void setMiastoDocelowe(PunktMapy miastoDocelowe) {
		this.miastoDocelowe = miastoDocelowe;
	};

}
