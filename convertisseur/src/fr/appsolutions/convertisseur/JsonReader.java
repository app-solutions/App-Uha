package fr.appsolutions.convertisseur;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JsonReader
{
	private JSONObject appUha;

	public JsonReader(String json) throws JSONException
	{
		this.appUha = ((JSONObject)new JSONTokener(json).nextValue());
		this.appUha = this.appUha.getJSONObject("AppUha");
	}





	public String getResultCode()
			throws JSONException
	{
		return (String)this.appUha.get("resultCode");
	}

	public String getResultHeader() throws JSONException
	{
		return (String)this.appUha.get("resultHeader");
	}

	public String getResultTitle() throws JSONException
	{
		return this.appUha.getString("resultTitle");
	}

	public String getResultString() throws JSONException
	{
		return (String)this.appUha.get("resultString");
	}







	public String getUserInfoPrenom()
			throws JSONException
	{
		return (String)this.appUha.getJSONObject("infos").get("prenom");
	}

	public String getUserInfoNom() throws JSONException
	{
		return (String)this.appUha.getJSONObject("infos").get("nom");
	}

	public String getUserInfoEmail() throws JSONException
	{
		return (String)this.appUha.getJSONObject("infos").get("email");
	}

	public String getUserInfoGroupe()
			throws JSONException
	{
		return (String)this.appUha.getJSONObject("infos").get("groupe");
	}

	public String getUserInfoNotif() throws JSONException
	{
		return (String)this.appUha.getJSONObject("infos").get("notifs");
	}





	public String getWeekName(String semaine)
			throws JSONException
	{
		return (String)this.appUha.getJSONObject("semaines").getJSONObject(semaine).get("nom");
	}

	public String getDayName(String jour)
			throws JSONException
	{
		return (String)this.appUha.getJSONObject("semaines").getJSONObject("semaine1").getJSONObject("jours").getJSONObject(jour).get("nom");
	}



	public ArrayList<Cours> getDayCours(String jour)
			throws JSONException
			{
		JSONArray jsra = new JSONArray(this.appUha.getJSONObject("semaines").getJSONObject("semaine1").getJSONObject("jours").getJSONObject(jour).getJSONArray("cours").toString());


		ArrayList<Cours> ar = new ArrayList<Cours>();

		for (int i = 0; i < jsra.length(); i++) {
			JSONObject j = jsra.getJSONObject(i);
			ar.add(new Cours(j.getString("id"), j.getString("start"), j.getString("stop"), "non ajouté", j.getString("durée"), j.getString("nom"), j.getString("salle"), j.getString("enseignant")));
		}

		return ar;
			}

	public ArrayList<Cours> getWeekCours(String week) throws JSONException
	{
		JSONArray jsraLundi = new JSONArray(this.appUha.getJSONObject("semaines").getJSONObject(week).getJSONObject("jours").getJSONObject("lundi").getJSONArray("cours").toString());
		JSONArray jsraMardi = new JSONArray(this.appUha.getJSONObject("semaines").getJSONObject(week).getJSONObject("jours").getJSONObject("mardi").getJSONArray("cours").toString());
		JSONArray jsraMercredi = new JSONArray(this.appUha.getJSONObject("semaines").getJSONObject(week).getJSONObject("jours").getJSONObject("mercredi").getJSONArray("cours").toString());
		JSONArray jsraJeudi = new JSONArray(this.appUha.getJSONObject("semaines").getJSONObject(week).getJSONObject("jours").getJSONObject("jeudi").getJSONArray("cours").toString());
		JSONArray jsraVendredi = new JSONArray(this.appUha.getJSONObject("semaines").getJSONObject(week).getJSONObject("jours").getJSONObject("vendredi").getJSONArray("cours").toString());
		JSONArray jsraSamedi = new JSONArray(this.appUha.getJSONObject("semaines").getJSONObject(week).getJSONObject("jours").getJSONObject("samedi").getJSONArray("cours").toString());

		ArrayList<Cours> ar = new ArrayList<Cours>();

		for (int i = 0; i < jsraLundi.length(); i++) {
			ar.add(new Cours(jsraLundi.getJSONObject(i).getString("id"), jsraLundi.getJSONObject(i).getString("start"), jsraLundi.getJSONObject(i).getString("stop"), "non ajouté", jsraLundi.getJSONObject(i).getString("durée"), jsraLundi.getJSONObject(i).getString("nom"), jsraLundi.getJSONObject(i).getString("salle"), jsraLundi.getJSONObject(i).getString("enseignant")));
		}

		for (int i = 0; i < jsraMardi.length(); i++) {
			ar.add(new Cours(jsraMardi.getJSONObject(i).getString("id"), jsraMardi.getJSONObject(i).getString("start"), jsraMardi.getJSONObject(i).getString("stop"), "non ajouté", jsraMardi.getJSONObject(i).getString("durée"), jsraMardi.getJSONObject(i).getString("nom"), jsraMardi.getJSONObject(i).getString("salle"), jsraMardi.getJSONObject(i).getString("enseignant")));
		}

		for (int i = 0; i < jsraMercredi.length(); i++) {
			ar.add(new Cours(jsraMercredi.getJSONObject(i).getString("id"), jsraMercredi.getJSONObject(i).getString("start"), jsraMercredi.getJSONObject(i).getString("stop"), "non ajouté", jsraMercredi.getJSONObject(i).getString("durée"), jsraMercredi.getJSONObject(i).getString("nom"), jsraMercredi.getJSONObject(i).getString("salle"), jsraMercredi.getJSONObject(i).getString("enseignant")));
		}

		for (int i = 0; i < jsraJeudi.length(); i++) {
			ar.add(new Cours(jsraJeudi.getJSONObject(i).getString("id"), jsraJeudi.getJSONObject(i).getString("start"), jsraJeudi.getJSONObject(i).getString("stop"), "non ajouté", jsraJeudi.getJSONObject(i).getString("durée"), jsraJeudi.getJSONObject(i).getString("nom"), jsraJeudi.getJSONObject(i).getString("salle"), jsraJeudi.getJSONObject(i).getString("enseignant")));
		}

		for (int i = 0; i < jsraVendredi.length(); i++) {
			ar.add(new Cours(jsraVendredi.getJSONObject(i).getString("id"), jsraVendredi.getJSONObject(i).getString("start"), jsraVendredi.getJSONObject(i).getString("stop"), "non ajouté", jsraVendredi.getJSONObject(i).getString("durée"), jsraVendredi.getJSONObject(i).getString("nom"), jsraVendredi.getJSONObject(i).getString("salle"), jsraVendredi.getJSONObject(i).getString("enseignant")));
		}

		for (int i = 0; i < jsraSamedi.length(); i++) {
			ar.add(new Cours(jsraSamedi.getJSONObject(i).getString("id"), jsraSamedi.getJSONObject(i).getString("start"), jsraSamedi.getJSONObject(i).getString("stop"), "non ajouté", jsraSamedi.getJSONObject(i).getString("durée"), jsraSamedi.getJSONObject(i).getString("nom"), jsraSamedi.getJSONObject(i).getString("salle"), jsraSamedi.getJSONObject(i).getString("enseignant")));
		}

		return ar;
	}

	public String test(String jour) throws JSONException {
		return this.appUha.getJSONObject("semaines").getJSONObject("semaine1").getJSONObject("jours").getJSONObject(jour).getString("cours");
	}
}