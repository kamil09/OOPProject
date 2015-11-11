package POJAZD;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import DROGA.Droga;
import MAIN.Miasto;
import MAIN.Swiat;

/**
 * 
 * @author Kamil Piotrowski
 * Interfejs który grupuje samoloty
 * 
 */
public interface Samolot {
	
	/**
	 * Znajduje trasę dla samolotu
	 * Pomija lotniska wojskowe
	 * @param samolot - referencja na samolot
	 */
	public default void losujTrase(Pojazd samolot) {
		Random generator = new Random();
		int dlugoscTrasy= generator.nextInt(1)+3;
		//samolot.getTrasa().clear();
		samolot.getTrasaPowrotna().clear();
		samolot.getTrasaTmp().clear();
		while(dlugoscTrasy>0){
			List<Droga> dostepneTrasy = new ArrayList<Droga>();
			//Mozliwe trasy
			for(Droga trasaPowietrzna : Swiat.getListaTrasPowietrznych() ){
				if(!samolot.getTrasa().isEmpty()){
					if((trasaPowietrzna.getA().equals(samolot.getTrasa().get(samolot.getTrasa().size()-1).getB()) )
						&& 	(!trasaPowietrzna.getB().equals(samolot.getTrasa().get(samolot.getTrasa().size()-1).getA())
						&& (trasaPowietrzna.getB().getid()<9 || trasaPowietrzna.getB().getid()>12)		)
							) {
						dostepneTrasy.add(trasaPowietrzna);
					}
				}
				else{
					if((trasaPowietrzna.getA().equals(samolot.getObecneMiejsce()) && (trasaPowietrzna.getB().getid()<9 || trasaPowietrzna.getB().getid()>12) ) ) {
						dostepneTrasy.add(trasaPowietrzna);
					}
				}
			}
			if(dostepneTrasy.size()>0){
				samolot.getTrasa().add(dostepneTrasy.get(generator.nextInt(dostepneTrasy.size() )));
				if(samolot.getTrasa().get(samolot.getTrasa().size()-1).getB() instanceof Miasto ){
					dlugoscTrasy--;
				}
			}
			else break;
		}
		samolot.setStan(1);
		samolot.getTrasa().get(0).getPojazdyNaDrodze().add(samolot);
		for(Droga trasa : samolot.getTrasa() ){
			samolot.getTrasaTmp().add(trasa);
		}
		//Ustawienie trasy powrotnej
		znajdzTrasePowrotna(samolot);	
	}
	/**
	 * Znajduje trasę powrotną dla samolotu na podstawie trazy docelowej
	 * @param samolot - referencja do obiektu typu samolot
	 */
	public default void znajdzTrasePowrotna(Pojazd samolot){
		for(int i=samolot.getTrasa().size()-1 ; i>=0 ; i-- ){
			for(Droga drogaSw : Swiat.getListaTrasPowietrznych() ){
				if( (samolot.getTrasa().get(i).getB().getid()==drogaSw.getA().getid()) && (samolot.getTrasa().get(i).getA().getid()==drogaSw.getB().getid()) ){
					samolot.getTrasaPowrotna().add(drogaSw);
				}	
			}
		}
	}
	/**
	 * Metoda która odpowiada za awaryjne lądowanie samolotu
	 */
	public default void awaryjneLadowanie(){
		
	}

}
