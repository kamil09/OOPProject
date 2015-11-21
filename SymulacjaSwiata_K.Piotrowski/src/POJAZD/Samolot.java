package POJAZD;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import DROGA.Droga;
import MAIN.Aplikacja;
import MAIN.PunktMapy;
import PRZYSTANEK.Miasto;

/**
 * 
 * @author Kamil Piotrowski
 * Interfejs który grupuje samoloty
 * 
 */
public interface Samolot extends Serializable{
	
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
			for(Droga trasaPowietrzna : Aplikacja.getSwiat().getListaTrasPowietrznych() ){
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
			for(Droga drogaSw : Aplikacja.getSwiat().getListaTrasPowietrznych() ){
				if( (samolot.getTrasa().get(i).getB().getid()==drogaSw.getA().getid()) && (samolot.getTrasa().get(i).getA().getid()==drogaSw.getB().getid()) ){
					samolot.getTrasaPowrotna().add(drogaSw);
				}	
			}
		}
	}
	/**
	 * Metoda która odpowiada za awaryjne lądowanie samolotu
	 * @param samolot Referencja do samolotu
	 */
	public default void awaryjneLadowanie(Pojazd samolot){
		//Tylko gdy samolot jest w trasie
		if(samolot.getObecneMiejsce()==null){
			//Lista list możliwych tras
			synchronized (samolot.getVeronica()) {
			List<ArrayList<Droga>> trasy = new ArrayList<ArrayList<Droga>>();
			//Level 1
			trasy.add( new ArrayList<Droga>() );
			trasy.add( new ArrayList<Droga>() );
			trasy.get(0).add(samolot.getTrasa().get(0));
			for(Droga trasaTmp : Aplikacja.getSwiat().getListaTrasPowietrznych()){
				if( (trasaTmp.getA().getid() == samolot.getTrasa().get(0).getB().getid()) &&
					(trasaTmp.getB().getid() == samolot.getTrasa().get(0).getA().getid())
				){
					trasy.get(1).add(trasaTmp);
					break;
				}
			}
			if(!trasy.get(1).isEmpty()){
				
				//Wyznaczenie grafu najbliższych miast mozliwych do odwiedzenia.
				while(samolot.isRunnable()){
					List<ArrayList<Droga>> doDodania = new ArrayList<ArrayList<Droga>>();
					List<ArrayList<Droga>> doUsuniecia = new ArrayList<ArrayList<Droga>>();
					boolean czyPrzerwac=true;
					for(ArrayList<Droga> lista : trasy  ){
						PunktMapy last = lista.get(lista.size()-1).getB();
						PunktMapy lastPrev = lista.get(lista.size()-1).getA();
						if( !(last instanceof Miasto) ){
							for(Droga tmp : Aplikacja.getSwiat().getListaTrasPowietrznych() ){			
								if( (tmp.getA().getid()==last.getid()) && 
									(tmp.getB().getid()!=lastPrev.getid())	){
									ArrayList<Droga> nowaLista = new ArrayList<Droga>();
									nowaLista.addAll(lista);
									nowaLista.add(tmp);
									doDodania.add(nowaLista);
									doUsuniecia.add(lista);
									czyPrzerwac=false;
								}				
							}
						}
					}
					trasy.addAll(doDodania);
					trasy.removeAll(doUsuniecia);
					if(czyPrzerwac) break;
				}
				//Usuniecie złych tras
				if(samolot instanceof SamolotPasazerski){
					List<ArrayList<Droga>> doUsuniecia = new ArrayList<ArrayList<Droga>>();
					for(ArrayList<Droga> lista : trasy  ){
						if(lista.get(lista.size()-1).getB().getid() > 8 ) doUsuniecia.add(lista);
					}
					trasy.removeAll(doUsuniecia);
				}
				else{
					List<ArrayList<Droga>> doUsuniecia = new ArrayList<ArrayList<Droga>>();
					for(ArrayList<Droga> lista : trasy  ){
						if(lista.get(lista.size()-1).getB().getid() < 9 ) doUsuniecia.add(lista);
					}
					trasy.removeAll(doUsuniecia);
				}
				//Wybranie najkrótszej trasy
				ArrayList<Droga> najkrotsza = new ArrayList<Droga>();
				int minSum=2147483647;
				for(ArrayList<Droga> trasaTmp : trasy){
					int sum=0;
					for(Droga drogaTmp : trasaTmp){
						if( drogaTmp.equals(trasaTmp.get(0)) ){
							if( drogaTmp.getB().getid() == samolot.getTrasa().get(0).getB().getid() ) sum+=samolot.returnDifferenceThisB();
							else sum+=samolot.returnDifferenceThisA();
						}
						else sum+=drogaTmp.getDifferenceBPoints();
					}
					if(sum<minSum){
						minSum=sum;
						najkrotsza=trasaTmp;
					}
				}
				//Dodawanie najkrótszej trasy do pojazdu
				//Oraz naprawa odbudowa trasy
				//Gdy zawracamy
				ArrayList<Droga> nowaTrasa = new ArrayList<Droga>();
				
				if( !najkrotsza.get(0).equals(samolot.getTrasa().get(0)) ){
					ArrayList<Droga> copy = new ArrayList<Droga>();
					copy.addAll(samolot.getTrasa());
					nowaTrasa.addAll(najkrotsza);
					for( int i=najkrotsza.size()-1 ; i>=1 ; i-- ){
						for(Droga tmp : Aplikacja.getSwiat().getListaTrasPowietrznych() ){			
							if( (tmp.getA().getid()== najkrotsza.get(i).getB().getid() ) && 
								(tmp.getB().getid()== najkrotsza.get(i).getA().getid() )){
								nowaTrasa.add(tmp);
							}
						}
					}
					nowaTrasa.addAll(copy);
				}
				
				//Gdy cześć trasy pokrywa się z normalną
				else{
					ArrayList<Droga> copy = new ArrayList<Droga>();
					copy.addAll(samolot.getTrasa());
					nowaTrasa.addAll(najkrotsza);
					int i=0;
					ArrayList<Droga> doUsuniecia = new ArrayList<Droga>();
					while(samolot.isRunnable()){
						if( i> copy.size()-1) break;
						if( i> najkrotsza.size()-1 ) break;
						if(copy.get(i).equals(najkrotsza.get(i))) doUsuniecia.add(copy.get(i));
						i++;
					}
					copy.removeAll(doUsuniecia);
					
					if(copy.size()>0){
						boolean czyBreak = false;
						for( i=najkrotsza.size()-1 ; i>=1 ; i-- ){
							for(Droga tmp : Aplikacja.getSwiat().getListaTrasPowietrznych() ){			
								if( (tmp.getA().getid()== najkrotsza.get(i).getB().getid() ) && 
									(tmp.getB().getid()== najkrotsza.get(i).getA().getid() )){
									if( tmp.getA().getid() == copy.get(0).getA().getid() ){
										czyBreak=true;
										break;
									}
									nowaTrasa.add(tmp);
								}
							}
							if(czyBreak) break;
						}
					}
					nowaTrasa.addAll(copy);
				}
				
				
				samolot.setNewTrasa(nowaTrasa);
			}
		}
	}}

}
