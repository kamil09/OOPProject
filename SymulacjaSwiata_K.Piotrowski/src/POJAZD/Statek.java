package POJAZD;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import DROGA.Droga;
import MAIN.Aplikacja;
import PRZYSTANEK.Miasto;

public interface Statek {
	
	/**
	 * Losuje trasę dla statku
	 * @param statek - referencja na statek dla którego szukamy trasy
	 */
	public default void losujTrase(Pojazd statek) {
		Random generator = new Random();
		int dlugoscTrasy= generator.nextInt(1)+2;
		//statek.getTrasa().clear();
		statek.getTrasaPowrotna().clear();
		statek.getTrasaTmp().clear();
		while(dlugoscTrasy>0){
			List<Droga> dostepneTrasy = new ArrayList<Droga>();
			//Mozliwe trasy
			for(Droga trasaMorska : Aplikacja.getSwiat().getListaTrasMorskich() ){
				if(!statek.getTrasa().isEmpty()){
					if(trasaMorska.getA().equals(statek.getTrasa().get(statek.getTrasa().size()-1).getB()) 
						&& 	!trasaMorska.getB().equals(statek.getTrasa().get(statek.getTrasa().size()-1).getA())
							) {
						dostepneTrasy.add(trasaMorska);
					}
				}
				else{
					if(trasaMorska.getA().equals(statek.getObecneMiejsce()) ) {
						dostepneTrasy.add(trasaMorska);
					}
				}
			}
			if(dostepneTrasy.size()>0){
				statek.getTrasa().add(dostepneTrasy.get(generator.nextInt(dostepneTrasy.size() )));
				if(statek.getTrasa().get(statek.getTrasa().size()-1).getB() instanceof Miasto ){
					dlugoscTrasy--;
				}
			}
			else break;
		}
		statek.setStan(1);
		statek.getTrasa().get(0).getPojazdyNaDrodze().add(statek);
		for(Droga trasa : statek.getTrasa() ){
			statek.getTrasaTmp().add(trasa);
		}
		//Ustawienie trasy powrotnej
		znajdzTrasePowrotna(statek);	
	}
	/**
	 * Znajdujemy trase powrotną dla statku
	 * @param statek referencja na statek
	 */
	public default void znajdzTrasePowrotna(Pojazd statek){
		for(int i=statek.getTrasa().size()-1 ; i>=0 ; i-- ){
			for(Droga drogaSw : Aplikacja.getSwiat().getListaTrasMorskich() ){
				if( (statek.getTrasa().get(i).getB().getid()==drogaSw.getA().getid()) && (statek.getTrasa().get(i).getA().getid()==drogaSw.getB().getid()) ){
					statek.getTrasaPowrotna().add(drogaSw);
				}	
			}
		}
	}
	/**
	 * Zamienia trase statku
	 * Uzywane po dotarciu do celu
	 * @param statek referencja na obiekt
	 */
	public default void zmianaTrasyStatku(Pojazd statek){
		Droga cpS =  statek.getTrasa().get(0);
		int stan = statek.getStan();
		boolean additional=false;
		synchronized (statek){
			statek.getTrasa().clear();
			if( !(cpS.getA() instanceof Miasto) ){
				for(Droga drogaNeed : Aplikacja.getSwiat().getListaTrasMorskich() ){
					if((drogaNeed.getB().getid() == cpS.getA().getid()) && (drogaNeed.getA() instanceof Miasto) && (drogaNeed.getA().getid()!=cpS.getB().getid())){
						statek.getTrasa().add(drogaNeed);
						additional=true;
						break;
					}
				}
			}		
			statek.getTrasa().add(cpS);
			losujTrase(statek);
			if(additional==true) statek.getTrasa().remove(0);
			statek.setStan(stan);
		}
	}
}
