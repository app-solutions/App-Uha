package fr.appsolutions.appuha;

import static fr.appsolutions.appuha.utilitaires.Utilitaires.LOCAL_PLANNING_RAM;
import static fr.appsolutions.appuha.utilitaires.Utilitaires.PLANNING_WEEK;
import fr.appsolutions.appuha.autres.Cours;
import fr.appsolutions.appuha.jsonreader.JsonReader;
import android.os.*;
import android.util.*;

import java.util.*;

import org.json.JSONException;

public class Data {
	public static final String TAG = Data.class.getSimpleName();
	
	public static List<Pair<String, List<Composer>>> getAllData() {
		List<Pair<String, List<Composer>>> res = new ArrayList<Pair<String, List<Composer>>>();
		
		for (int i = 0; i < 6; i++) {
			res.add(getOneSection(i));
		}
		
		return res;
	}
	
	public static List<Composer> getFlattenedData() {
		 List<Composer> res = new ArrayList<Composer>();
		 
		 for (int i = 0; i < 6; i++) {
			 res.addAll(getOneSection(i).second);
		 }
		 
		 return res;
	}
	
	public static Pair<Boolean, List<Composer>> getRows(int page) {
		List<Composer> flattenedData = getFlattenedData();
		if (page == 1) {
			return new Pair<Boolean, List<Composer>>(true, flattenedData.subList(0, 5));
		} else {
			SystemClock.sleep(2000); // simulate loading
			return new Pair<Boolean, List<Composer>>(page * 5 < flattenedData.size(), flattenedData.subList((page - 1) * 5, Math.min(page * 5, flattenedData.size())));
		}
	}
	
	public static Pair<String, List<Composer>> getOneSection(int index) {
		
		String semaine;
		
		switch(PLANNING_WEEK){
		case 1:
			semaine = "semaine1";
			break;
		case 2:
			semaine = "semaine2";
			break;
		case 3:
			semaine = "semaine3";
			break;
		case 4:
			semaine = "semaine4";
			break;
		default:
			semaine = "semaine1";
		}
		
		String lundiName = null;
		String mardiName = null;
		String mercrediName = null;
		String jeudiName = null;
		String vendrediName = null;
		String samediName = null;
		
		ArrayList<Cours> coursLundi = null;
		ArrayList<Cours> coursMardi = null;
		ArrayList<Cours> coursMercredi = null;
		ArrayList<Cours> coursJeudi = null;
		ArrayList<Cours> coursVendredi = null;
		ArrayList<Cours> coursSamedi = null;
		
		try {
			JsonReader jr = new JsonReader(LOCAL_PLANNING_RAM);
			lundiName = jr.getDayName(semaine,"lundi");
			mardiName = jr.getDayName(semaine,"mardi");
			mercrediName = jr.getDayName(semaine,"mercredi");
			jeudiName = jr.getDayName(semaine,"jeudi");
			vendrediName = jr.getDayName(semaine,"vendredi");
			samediName = jr.getDayName(semaine,"samedi");
			
			coursLundi = jr.getDayCours(semaine,"lundi");
			coursMardi = jr.getDayCours(semaine,"mardi");
			coursMercredi = jr.getDayCours(semaine,"mercredi");
			coursJeudi = jr.getDayCours(semaine,"jeudi");
			coursVendredi = jr.getDayCours(semaine,"vendredi");
			coursSamedi = jr.getDayCours(semaine,"samedi");
			
		} catch (JSONException e) {
			e.printStackTrace();
			Log.i(TAG, "WTF");
		}
		
		String[] titles = {lundiName, mardiName, mercrediName,jeudiName, vendrediName, samediName};
		
		ArrayList<Composer> ar = new ArrayList<Composer>();
		
		if(index == 0){
			for(int i=0; i<coursLundi.size(); i++){
				ar.add(new Composer(coursLundi.get(i).getStart() + " - " + coursLundi.get(i).getStop(), coursLundi.get(i).getNom(), coursLundi.get(i).getSalle(), coursLundi.get(i).getEnseignant()));
			}
		}
		else if(index == 1){
			for(int i=0; i<coursMardi.size(); i++){
				ar.add(new Composer(coursMardi.get(i).getStart() + " - " + coursMardi.get(i).getStop(), coursMardi.get(i).getNom(), coursMardi.get(i).getSalle(), coursMardi.get(i).getEnseignant()));
			}
		}
		else if(index == 2){
			for(int i=0; i<coursMercredi.size(); i++){
				ar.add(new Composer(coursMercredi.get(i).getStart() + " - " + coursMercredi.get(i).getStop(), coursMercredi.get(i).getNom(), coursMercredi.get(i).getSalle(), coursMercredi.get(i).getEnseignant()));
			}
		}
		else if(index == 3){
			for(int i=0; i<coursJeudi.size(); i++){
				ar.add(new Composer(coursJeudi.get(i).getStart() + " - " + coursJeudi.get(i).getStop(), coursJeudi.get(i).getNom(), coursJeudi.get(i).getSalle(), coursJeudi.get(i).getEnseignant()));
			}
		}
		else if(index == 4){
			for(int i=0; i<coursVendredi.size(); i++){
				ar.add(new Composer(coursVendredi.get(i).getStart() + " - " + coursVendredi.get(i).getStop(), coursVendredi.get(i).getNom(), coursVendredi.get(i).getSalle(), coursVendredi.get(i).getEnseignant()));
			}
		}
		else if(index == 5){
			for(int i=0; i<coursSamedi.size(); i++){
				ar.add(new Composer(coursSamedi.get(i).getStart() + " - " + coursSamedi.get(i).getStop(), coursSamedi.get(i).getNom(), coursSamedi.get(i).getSalle(), coursSamedi.get(i).getEnseignant()));
			}
		}
		
		
		return new Pair<String, List<Composer>>(titles[index], ar);
	}

}
