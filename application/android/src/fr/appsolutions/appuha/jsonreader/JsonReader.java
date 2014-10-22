package fr.appsolutions.appuha.jsonreader;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import fr.appsolutions.appuha.autres.Cours;

public class JsonReader {
	
	private JSONObject appUha;
	
	public static final String S1 = "semaine1";
	public static final String S2 = "semaine2";
	public static final String S3 = "semaine3";
	public static final String S4 = "semaine4";
	
	public static final String LUNDI = "lundi";
	public static final String MARDI = "mardi";
	public static final String MERCREDI = "mercredi";
	public static final String JEUDI = "jeudi";
	public static final String VENDREDI = "vendredi";
	public static final String SAMEDI = "samedi";
	
	public JsonReader(String json) throws JSONException{
		appUha = (JSONObject) new JSONTokener(json).nextValue();
		appUha = appUha.getJSONObject("AppUha");
	}
	
	/**
	 * les codes ci-dessous, sont des codes de retour génériques, donnés pour toutes requettes serveur.
	 * 
	 */
	
	//code de retour de la requette
	public String getResultCode() throws JSONException{
		return (String) appUha.get("resultCode");
	}
	
	//header de la requette
	public String getResultHeader() throws JSONException{
		return (String) appUha.get("resultHeader");
	}
	
	//titre de la requette
	public String getResultTitle() throws JSONException{
		return (String) appUha.getString("resultTitle");
	}
	
	//String de le la requette
	public String getResultString() throws JSONException{
		return (String) appUha.get("resultString");
		
	}
	
	
	/**
	 * Les requettes ci-dessous sont des requettes utilisées pour recuperer les elements du Service GetUserInfo
	 * 
	 */
	
	//retourne le prenom
	public String getUserInfoPrenom() throws JSONException{
		return (String) appUha.getJSONObject("infos").get("prenom");
	}
	
	//retourne le nom
	public String getUserInfoNom() throws JSONException{
		return (String) appUha.getJSONObject("infos").get("nom");
	}
	
	//retourne l'email
	public String getUserInfoEmail() throws JSONException{
		return (String) appUha.getJSONObject("infos").get("email");
		
	}
	
	//retourne le groupe de la personne
	public String getUserInfoGroupe() throws JSONException{
		return (String) appUha.getJSONObject("infos").get("groupe");
	}
	
	//retourne le nombre de notification restante 
	public String getUserInfoNotif() throws JSONException{
		return (String) appUha.getJSONObject("infos").get("notifs");
	}
	
	
	/**
	 * Les requettes suivantes, sont utilisées pour recuperer les cours d'une semaine du service GetPlanning
	 */
	
	//retourne le nom d'une semaine
	public String getWeekName(String semaine) throws JSONException{
		return (String) appUha.getJSONObject("semaines").getJSONObject(semaine).get("nom");
	}
	
	
	//retourne la date d'un jour
	public String getDayName(String semaine, String jour) throws JSONException{
		return (String) appUha.getJSONObject("semaines").getJSONObject(semaine).getJSONObject("jours").getJSONObject(jour).get("nom");
	}
	
	
	//retourne les cours d'un jour
	public ArrayList<Cours> getDayCours(String semaine, String jour) throws JSONException{
		
		JSONArray jsra = new JSONArray( (String) appUha.getJSONObject("semaines").getJSONObject(semaine).getJSONObject("jours").getJSONObject(jour).getString("cours") );
		

		ArrayList<Cours> ar = new ArrayList<Cours>();
		
		for(int i=0; i<jsra.length(); i++){
			JSONObject j = jsra.getJSONObject(i);
			ar.add(new Cours( (String) j.getString("id"), (String) j.getString("start"), (String) j.getString("stop"), (String) j.getString("durée"), (String) j.getString("nom"), (String) j.getString("salle"), (String) j.getString("enseignant")));
		}
		
		return ar;
	}
	
	public String test(String jour) throws JSONException{
		return (String) appUha.getJSONObject("semaines").getJSONObject("semaine1").getJSONObject("jours").getJSONObject(jour).getString("cours");
	}
	
}
