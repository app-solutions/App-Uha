package fr.appsolutions.convertisseur;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Convertisseur
{
	private String file;
	private Temps t;
	private ArrayList<Cours> coursLundiS1 = new ArrayList<Cours>();
	private ArrayList<Cours> coursMardiS1 = new ArrayList<Cours>();
	private ArrayList<Cours> coursMercrediS1 = new ArrayList<Cours>();
	private ArrayList<Cours> coursJeudiS1 = new ArrayList<Cours>();
	private ArrayList<Cours> coursVendrediS1 = new ArrayList<Cours>();
	private ArrayList<Cours> coursSamediS1 = new ArrayList<Cours>();

	private ArrayList<Cours> coursLundiS2 = new ArrayList<Cours>();
	private ArrayList<Cours> coursMardiS2 = new ArrayList<Cours>();
	private ArrayList<Cours> coursMercrediS2 = new ArrayList<Cours>();
	private ArrayList<Cours> coursJeudiS2 = new ArrayList<Cours>();
	private ArrayList<Cours> coursVendrediS2 = new ArrayList<Cours>();
	private ArrayList<Cours> coursSamediS2 = new ArrayList<Cours>();

	private ArrayList<Cours> coursLundiS3 = new ArrayList<Cours>();
	private ArrayList<Cours> coursMardiS3 = new ArrayList<Cours>();
	private ArrayList<Cours> coursMercrediS3 = new ArrayList<Cours>();
	private ArrayList<Cours> coursJeudiS3 = new ArrayList<Cours>();
	private ArrayList<Cours> coursVendrediS3 = new ArrayList<Cours>();
	private ArrayList<Cours> coursSamediS3 = new ArrayList<Cours>();

	private ArrayList<Cours> coursLundiS4 = new ArrayList<Cours>();
	private ArrayList<Cours> coursMardiS4 = new ArrayList<Cours>();
	private ArrayList<Cours> coursMercrediS4 = new ArrayList<Cours>();
	private ArrayList<Cours> coursJeudiS4 = new ArrayList<Cours>();
	private ArrayList<Cours> coursVendrediS4 = new ArrayList<Cours>();
	private ArrayList<Cours> coursSamediS4 = new ArrayList<Cours>();

	private String planning;

	public Convertisseur(String file)
	{
		this.file = file;
	}




	public String start()
	{
		this.t = new Temps();

		startPars(this.t.getLundiS1(), "lundi", "S1");
		startPars(this.t.getMardiS1(), "mardi", "S1");
		startPars(this.t.getMercrediS1(), "mercredi", "S1");
		startPars(this.t.getJeudiS1(), "jeudi", "S1");
		startPars(this.t.getVendrediS1(), "vendredi", "S1");
		startPars(this.t.getSamediS1(), "samedi", "S1");

		startPars(this.t.getLundiS2(), "lundi", "S2");
		startPars(this.t.getMardiS2(), "mardi", "S2");
		startPars(this.t.getMercrediS2(), "mercredi", "S2");
		startPars(this.t.getJeudiS2(), "jeudi", "S2");
		startPars(this.t.getVendrediS2(), "vendredi", "S2");
		startPars(this.t.getSamediS2(), "samedi", "S2");

		startPars(this.t.getLundiS3(), "lundi", "S3");
		startPars(this.t.getMardiS3(), "mardi", "S3");
		startPars(this.t.getMercrediS3(), "mercredi", "S3");
		startPars(this.t.getJeudiS3(), "jeudi", "S3");
		startPars(this.t.getVendrediS3(), "vendredi", "S3");
		startPars(this.t.getSamediS3(), "samedi", "S3");

		startPars(this.t.getLundiS4(), "lundi", "S4");
		startPars(this.t.getMardiS4(), "mardi", "S4");
		startPars(this.t.getMercrediS4(), "mercredi", "S4");
		startPars(this.t.getJeudiS4(), "jeudi", "S4");
		startPars(this.t.getVendrediS4(), "vendredi", "S4");
		startPars(this.t.getSamediS4(), "samedi", "S4");

		return startWrite();
	}



	private void startPars(String date, String day, String week)
	{
		String ligne = "";
		String id = "";
		String start = "";
		String stop = "";
		String modif = "";
		String duree = "";
		String nom = "";
		String salle = "";
		String enseignants = "";



		try
		{
			BufferedReader planning = new BufferedReader(new InputStreamReader(new FileInputStream(this.file), "UTF-8"));
			ligne = planning.readLine();

			while ((!ligne.contains("END:APP-UHA")) && (ligne != null))
			{
				if (ligne.contains("BEGIN:COURS-" + date))
				{
					ligne = planning.readLine();

					while (!ligne.contains("END:COURS"))
					{
						if (ligne.contains("ID"))
						{
							id = ligne;
						}
						else if (ligne.contains("TIMESTART"))
						{
							start = ligne;
						}
						else if (ligne.contains("TIMESTOP"))
						{
							stop = ligne;
						}
						else if (ligne.contains("TIMELASTMODIFIED"))
						{
							modif = ligne;
						}
						else if (ligne.contains("DUREE"))
						{
							duree = ligne;
						}
						else if (ligne.contains("NOM"))
						{
							nom = ligne;
						}
						else if (ligne.contains("SALLE"))
						{
							salle = ligne;
						}
						else if (ligne.contains("ENSEIGNANTS")) {
							enseignants = ligne;

							startEpur(id, start, stop, modif, duree, nom, salle, enseignants, day, week);
						}
						ligne = planning.readLine();
					}
				}
				ligne = planning.readLine();
			}
			planning.close();
		}
		catch (FileNotFoundException e)
		{
			System.out.println("Erreur: le fichier " + this.file + " n'es pas accessible surement parce qu'il n'existe pas ou n'est pas dans le bon dossier");
		}
		catch (IOException e) {
			System.out.println("Erreur: Le fichier n'a pu être lu");
		}
	}



	private void startEpur(String id, String start, String stop, String modif, String duree, String nom, String salle, String enseignants, String day, String week)
	{
		char[] tempId = id.toCharArray();
		char[] tempStart = start.toCharArray();
		char[] tempStop = stop.toCharArray();
		char[] tempModif = modif.toCharArray();
		char[] tempDuree = duree.toCharArray();
		char[] tempNom = nom.toCharArray();
		char[] tempSalle = salle.toCharArray();
		char[] tempEnseignants = enseignants.toCharArray();

		id = "";
		start = "";
		stop = "";
		modif = "";
		duree = "";
		nom = "";
		salle = "";
		enseignants = "";

		for (int i = 3; i < tempId.length; i++)
		{
			id = id + tempId[i];
		}


		for (int i = 22; i < tempStart.length; i++)
		{
			start = start + tempStart[i];
		}


		for (int i = 21; i < tempStop.length; i++)
		{
			stop = stop + tempStop[i];
		}


		for (int i = 17; i < tempModif.length; i++)
		{
			modif = modif + tempModif[i];
		}


		for (int i = 6; i < tempDuree.length; i++)
		{
			duree = duree + tempDuree[i];
		}


		for (int i = 4; i < tempNom.length; i++)
		{
			nom = nom + tempNom[i];
		}


		for (int i = 6; i < tempSalle.length; i++)
		{
			salle = salle + tempSalle[i];
		}


		for (int i = 12; i < tempEnseignants.length; i++)
		{
			enseignants = enseignants + tempEnseignants[i];
		}


		if (week.contains("S1")) {
			if (day.contains("lundi")) {
				this.coursLundiS1.add(new Cours(id, start, stop, modif, duree, nom, salle, enseignants));
			}
			else if (day.contains("mardi")) {
				this.coursMardiS1.add(new Cours(id, start, stop, modif, duree, nom, salle, enseignants));
			}
			else if (day.contains("mercredi")) {
				this.coursMercrediS1.add(new Cours(id, start, stop, modif, duree, nom, salle, enseignants));
			}
			else if (day.contains("jeudi")) {
				this.coursJeudiS1.add(new Cours(id, start, stop, modif, duree, nom, salle, enseignants));
			}
			else if (day.contains("vendredi")) {
				this.coursVendrediS1.add(new Cours(id, start, stop, modif, duree, nom, salle, enseignants));
			}
			else if (day.contains("samedi")) {
				this.coursSamediS1.add(new Cours(id, start, stop, modif, duree, nom, salle, enseignants));
			}
		}
		else if (week.contains("S2")) {
			if (day.contains("lundi")) {
				this.coursLundiS2.add(new Cours(id, start, stop, modif, duree, nom, salle, enseignants));
			}
			else if (day.contains("mardi")) {
				this.coursMardiS2.add(new Cours(id, start, stop, modif, duree, nom, salle, enseignants));
			}
			else if (day.contains("mercredi")) {
				this.coursMercrediS2.add(new Cours(id, start, stop, modif, duree, nom, salle, enseignants));
			}
			else if (day.contains("jeudi")) {
				this.coursJeudiS2.add(new Cours(id, start, stop, modif, duree, nom, salle, enseignants));
			}
			else if (day.contains("vendredi")) {
				this.coursVendrediS2.add(new Cours(id, start, stop, modif, duree, nom, salle, enseignants));
			}
			else if (day.contains("samedi")) {
				this.coursSamediS2.add(new Cours(id, start, stop, modif, duree, nom, salle, enseignants));
			}
		}
		else if (week.contains("S3")) {
			if (day.contains("lundi")) {
				this.coursLundiS3.add(new Cours(id, start, stop, modif, duree, nom, salle, enseignants));
			}
			else if (day.contains("mardi")) {
				this.coursMardiS3.add(new Cours(id, start, stop, modif, duree, nom, salle, enseignants));
			}
			else if (day.contains("mercredi")) {
				this.coursMercrediS3.add(new Cours(id, start, stop, modif, duree, nom, salle, enseignants));
			}
			else if (day.contains("jeudi")) {
				this.coursJeudiS3.add(new Cours(id, start, stop, modif, duree, nom, salle, enseignants));
			}
			else if (day.contains("vendredi")) {
				this.coursVendrediS3.add(new Cours(id, start, stop, modif, duree, nom, salle, enseignants));
			}
			else if (day.contains("samedi")) {
				this.coursSamediS3.add(new Cours(id, start, stop, modif, duree, nom, salle, enseignants));
			}
		}
		else if (week.contains("S4")) {
			if (day.contains("lundi")) {
				this.coursLundiS4.add(new Cours(id, start, stop, modif, duree, nom, salle, enseignants));
			}
			else if (day.contains("mardi")) {
				this.coursMardiS4.add(new Cours(id, start, stop, modif, duree, nom, salle, enseignants));
			}
			else if (day.contains("mercredi")) {
				this.coursMercrediS4.add(new Cours(id, start, stop, modif, duree, nom, salle, enseignants));
			}
			else if (day.contains("jeudi")) {
				this.coursJeudiS4.add(new Cours(id, start, stop, modif, duree, nom, salle, enseignants));
			}
			else if (day.contains("vendredi")) {
				this.coursVendrediS4.add(new Cours(id, start, stop, modif, duree, nom, salle, enseignants));
			}
			else if (day.contains("samedi")) {
				this.coursSamediS4.add(new Cours(id, start, stop, modif, duree, nom, salle, enseignants));
			}
		}
	}






	private String startWrite()
	{

		planning =  "{" + 
				"\"AppUha\": {" +
				"\"resultCode\": \"1\"," +
				"\"resultHeader\": \"Planning\"," +
				"\"resultTitle\": \"\"," +
				"\"resultString\": \"\"," +
				"\"semaines\": {" +
				"\"semaine1\": {" +
				"\"nom\": \"semaine 42\"," +
				"\"jours\": {" +
				"\"lundi\": {" +
				"\"nom\": \"" + t.getLundiS1StringName() + "\"," + 
				"\"cours\": [";

		//Semaine 1

		if(!coursLundiS1.isEmpty()){
			for(int i=0; i < coursLundiS1.size() ; i++){
				planning = planning + "{   \"id\":\"" + coursLundiS1.get(i).getId() + "\"," + 
						"   \"start\":\"" + coursLundiS1.get(i).getDebut() + "\"," +
						"   \"stop\":\"" + coursLundiS1.get(i).getFin() + "\"," +
						"   \"durée\":\"" + coursLundiS1.get(i).getDuree() + "\"," +
						"   \"nom\":\"" + coursLundiS1.get(i).getNom().replace("\"", "") + "\"," +
						"   \"salle\":\"" + coursLundiS1.get(i).getSalle() + "\"," +
						"   \"enseignant\":\"" + coursLundiS1.get(i).getProf() + "\"}";

				if(i < coursLundiS1.size() - 1 ){
					planning = planning + ",";
				}
			}
		}
		planning = planning + "]";


		planning =  planning +	"}," +
				"\"mardi\": {" +
				"\"nom\": \""+ t.getMardiS1StringName() +"\"," +
				"\"cours\": [";
		if(!coursMardiS1.isEmpty()){
			for(int i=0; i < coursMardiS1.size() ; i++){
				planning = planning + "{   \"id\":\"" + coursMardiS1.get(i).getId() + "\"," + 
						"   \"start\":\"" + coursMardiS1.get(i).getDebut() + "\"," +
						"   \"stop\":\"" + coursMardiS1.get(i).getFin() + "\"," +
						"   \"durée\":\"" + coursMardiS1.get(i).getDuree() + "\"," +
						"   \"nom\":\"" + coursMardiS1.get(i).getNom().replace("\"", "") + "\"," +
						"   \"salle\":\"" + coursMardiS1.get(i).getSalle() + "\"," +
						"   \"enseignant\":\"" + coursMardiS1.get(i).getProf() + "\"}";

				if(i < coursMardiS1.size() - 1 ){
					planning = planning + ",";
				}
			}
		}

		planning = planning + "]";


		planning =  planning +	"}," +
				"\"mercredi\": {" +
				"\"nom\": \""+ t.getMercrediS1StringName() +"\"," +
				"\"cours\": [";
		if(!coursMercrediS1.isEmpty()){
			for(int i=0; i < coursMercrediS1.size() ; i++){
				planning = planning + "{   \"id\":\"" + coursMercrediS1.get(i).getId() + "\"," + 
						"   \"start\":\"" + coursMercrediS1.get(i).getDebut() + "\"," +
						"   \"stop\":\"" + coursMercrediS1.get(i).getFin() + "\"," +
						"   \"durée\":\"" + coursMercrediS1.get(i).getDuree() + "\"," +
						"   \"nom\":\"" + coursMercrediS1.get(i).getNom().replace("\"", "") + "\"," +
						"   \"salle\":\"" + coursMercrediS1.get(i).getSalle() + "\"," +
						"   \"enseignant\":\"" + coursMercrediS1.get(i).getProf() + "\"}";

				if(i < coursMercrediS1.size() - 1 ){
					planning = planning + ",";
				}
			}
		}
		planning = planning + "]";


		planning =  planning +	"}," +
				"\"jeudi\": {" +
				"\"nom\": \""+ t.getJeudiS1StringName() +"\"," +
				"\"cours\": [";
		if(!coursJeudiS1.isEmpty()){
			for(int i=0; i < coursJeudiS1.size() ; i++){

				planning = planning + "{   \"id\":\"" + coursJeudiS1.get(i).getId() + "\"," + 
						"   \"start\":\"" + coursJeudiS1.get(i).getDebut() + "\"," +
						"   \"stop\":\"" + coursJeudiS1.get(i).getFin() + "\"," +
						"   \"durée\":\"" + coursJeudiS1.get(i).getDuree() + "\"," +
						"   \"nom\":\"" + coursJeudiS1.get(i).getNom().replace("\"", "") + "\"," +
						"   \"salle\":\"" + coursJeudiS1.get(i).getSalle() + "\"," +
						"   \"enseignant\":\"" + coursJeudiS1.get(i).getProf() + "\"}";

				if(i < coursJeudiS1.size() - 1 ){
					planning = planning + ",";
				}
			}
		}
		planning = planning + "]";


		planning =  planning +	"}," +
				"\"vendredi\": {" +
				"\"nom\": \""+ t.getVendrediS1StringName() +"\"," +
				"\"cours\": [";
		if(!coursVendrediS1.isEmpty()){
			for(int i=0; i < coursVendrediS1.size() ; i++){

				planning = planning + "{   \"id\":\"" + coursVendrediS1.get(i).getId() + "\"," + 
						"   \"start\":\"" + coursVendrediS1.get(i).getDebut() + "\"," +
						"   \"stop\":\"" + coursVendrediS1.get(i).getFin() + "\"," +
						"   \"durée\":\"" + coursVendrediS1.get(i).getDuree() + "\"," +
						"   \"nom\":\"" + coursVendrediS1.get(i).getNom().replace("\"", "") + "\"," +
						"   \"salle\":\"" + coursVendrediS1.get(i).getSalle() + "\"," +
						"   \"enseignant\":\"" + coursVendrediS1.get(i).getProf() + "\"}";

				if(i < coursVendrediS1.size() - 1 ){
					planning = planning + ",";
				}
			}
		}
		planning = planning + "]";


		planning =  planning +	"}," +
				"\"samedi\": {" +
				"\"nom\": \""+ t.getSamediS1StringName() + "\"," +
				"\"cours\": [";
		if(!coursSamediS1.isEmpty()){
			for(int i=0; i < coursSamediS1.size() ; i++){
				planning = planning + "{   \"id\":\"" + coursSamediS1.get(i).getId() + "\"," + 
						"   \"start\":\"" + coursSamediS1.get(i).getDebut() + "\"," +
						"   \"stop\":\"" + coursSamediS1.get(i).getFin() + "\"," +
						"   \"durée\":\"" + coursSamediS1.get(i).getDuree() + "\"," +
						"   \"nom\":\"" + coursSamediS1.get(i).getNom().replace("\"", "") + "\"," +
						"   \"salle\":\"" + coursSamediS1.get(i).getSalle() + "\"," +
						"   \"enseignant\":\"" + coursSamediS1.get(i).getProf() + "\"}";

				if(i < coursSamediS1.size() - 1 ){
					planning = planning + ",";
				}
			}
		}
		planning = planning + "]}}},";


		//Semaine 2

		planning = planning + 

				"\"semaine2\": {" +
				"\"nom\": \"semaine 43\"," +
				"\"jours\": {" +
				"\"lundi\": {" +
				"\"nom\": \""+ t.getLundiS2StringName() +"\"," + 
				"\"cours\": [";
		if(!coursLundiS2.isEmpty()){
			for(int i=0; i < coursLundiS2.size() ; i++){
				planning = planning + "{   \"id\":\"" + coursLundiS2.get(i).getId() + "\"," + 
						"   \"start\":\"" + coursLundiS2.get(i).getDebut() + "\"," +
						"   \"stop\":\"" + coursLundiS2.get(i).getFin() + "\"," +
						"   \"durée\":\"" + coursLundiS2.get(i).getDuree() + "\"," +
						"   \"nom\":\"" + coursLundiS2.get(i).getNom().replace("\"", "") + "\"," +
						"   \"salle\":\"" + coursLundiS2.get(i).getSalle() + "\"," +
						"   \"enseignant\":\"" + coursLundiS2.get(i).getProf() + "\"}";

				if(i < coursLundiS2.size() - 1 ){
					planning = planning + ",";
				}
			}
		}
		planning = planning + "]";


		planning =  planning +	"}," +
				"\"mardi\": {" +
				"\"nom\": \""+ t.getMardiS2StringName() +"\"," +
				"\"cours\": [";
		if(!coursMardiS2.isEmpty()){
			for(int i=0; i < coursMardiS2.size() ; i++){
				planning = planning + "{   \"id\":\"" + coursMardiS2.get(i).getId() + "\"," + 
						"   \"start\":\"" + coursMardiS2.get(i).getDebut() + "\"," +
						"   \"stop\":\"" + coursMardiS2.get(i).getFin() + "\"," +
						"   \"durée\":\"" + coursMardiS2.get(i).getDuree() + "\"," +
						"   \"nom\":\"" + coursMardiS2.get(i).getNom().replace("\"", "") + "\"," +
						"   \"salle\":\"" + coursMardiS2.get(i).getSalle() + "\"," +
						"   \"enseignant\":\"" + coursMardiS2.get(i).getProf() + "\"}";

				if(i < coursMardiS2.size() - 1 ){
					planning = planning + ",";
				}
			}
		}

		planning = planning + "]";


		planning =  planning +	"}," +
				"\"mercredi\": {" +
				"\"nom\": \""+ t.getMercrediS2StringName() +"\"," +
				"\"cours\": [";
		if(!coursMercrediS2.isEmpty()){
			for(int i=0; i < coursMercrediS2.size() ; i++){
				planning = planning + "{   \"id\":\"" + coursMercrediS2.get(i).getId() + "\"," + 
						"   \"start\":\"" + coursMercrediS2.get(i).getDebut() + "\"," +
						"   \"stop\":\"" + coursMercrediS2.get(i).getFin() + "\"," +
						"   \"durée\":\"" + coursMercrediS2.get(i).getDuree() + "\"," +
						"   \"nom\":\"" + coursMercrediS2.get(i).getNom().replace("\"", "") + "\"," +
						"   \"salle\":\"" + coursMercrediS2.get(i).getSalle() + "\"," +
						"   \"enseignant\":\"" + coursMercrediS2.get(i).getProf() + "\"}";

				if(i < coursMercrediS2.size() - 1 ){
					planning = planning + ",";
				}
			}
		}
		planning = planning + "]";


		planning =  planning +	"}," +
				"\"jeudi\": {" +
				"\"nom\": \""+ t.getJeudiS2StringName() +"\"," +
				"\"cours\": [";
		if(!coursJeudiS2.isEmpty()){
			for(int i=0; i < coursJeudiS2.size() ; i++){

				planning = planning + "{   \"id\":\"" + coursJeudiS2.get(i).getId() + "\"," + 
						"   \"start\":\"" + coursJeudiS2.get(i).getDebut() + "\"," +
						"   \"stop\":\"" + coursJeudiS2.get(i).getFin() + "\"," +
						"   \"durée\":\"" + coursJeudiS2.get(i).getDuree() + "\"," +
						"   \"nom\":\"" + coursJeudiS2.get(i).getNom().replace("\"", "") + "\"," +
						"   \"salle\":\"" + coursJeudiS2.get(i).getSalle() + "\"," +
						"   \"enseignant\":\"" + coursJeudiS2.get(i).getProf() + "\"}";

				if(i < coursJeudiS2.size() - 1 ){
					planning = planning + ",";
				}
			}
		}
		planning = planning + "]";


		planning =  planning +	"}," +
				"\"vendredi\": {" +
				"\"nom\": \""+ t.getVendrediS2StringName() +"\"," +
				"\"cours\": [";
		if(!coursVendrediS2.isEmpty()){
			for(int i=0; i < coursVendrediS2.size() ; i++){

				planning = planning + "{   \"id\":\"" + coursVendrediS2.get(i).getId() + "\"," + 
						"   \"start\":\"" + coursVendrediS2.get(i).getDebut() + "\"," +
						"   \"stop\":\"" + coursVendrediS2.get(i).getFin() + "\"," +
						"   \"durée\":\"" + coursVendrediS2.get(i).getDuree() + "\"," +
						"   \"nom\":\"" + coursVendrediS2.get(i).getNom().replace("\"", "") + "\"," +
						"   \"salle\":\"" + coursVendrediS2.get(i).getSalle() + "\"," +
						"   \"enseignant\":\"" + coursVendrediS2.get(i).getProf() + "\"}";

				if(i < coursVendrediS2.size() - 1 ){
					planning = planning + ",";
				}
			}
		}
		planning = planning + "]";


		planning =  planning +	"}," +
				"\"samedi\": {" +
				"\"nom\": \""+ t.getSamediS2StringName() + "\"," +
				"\"cours\": [";
		if(!coursSamediS2.isEmpty()){
			for(int i=0; i < coursSamediS2.size() ; i++){
				planning = planning + "{   \"id\":\"" + coursSamediS2.get(i).getId() + "\"," + 
						"   \"start\":\"" + coursSamediS2.get(i).getDebut() + "\"," +
						"   \"stop\":\"" + coursSamediS2.get(i).getFin() + "\"," +
						"   \"durée\":\"" + coursSamediS2.get(i).getDuree() + "\"," +
						"   \"nom\":\"" + coursSamediS2.get(i).getNom().replace("\"", "") + "\"," +
						"   \"salle\":\"" + coursSamediS2.get(i).getSalle() + "\"," +
						"   \"enseignant\":\"" + coursSamediS2.get(i).getProf() + "\"}";

				if(i < coursSamediS2.size() - 1 ){
					planning = planning + ",";
				}
			}
		}
		planning = planning + "]}}},";

		//Semaine 3

		planning = planning + 

				"\"semaine3\": {" +
				"\"nom\": \"semaine 44\"," +
				"\"jours\": {" +
				"\"lundi\": {" +
				"\"nom\": \""+ t.getLundiS3StringName() +"\"," + 
				"\"cours\": [";
		if(!coursLundiS3.isEmpty()){
			for(int i=0; i < coursLundiS3.size() ; i++){
				planning = planning + "{   \"id\":\"" + coursLundiS3.get(i).getId() + "\"," + 
						"   \"start\":\"" + coursLundiS3.get(i).getDebut() + "\"," +
						"   \"stop\":\"" + coursLundiS3.get(i).getFin() + "\"," +
						"   \"durée\":\"" + coursLundiS3.get(i).getDuree() + "\"," +
						"   \"nom\":\"" + coursLundiS3.get(i).getNom().replace("\"", "") + "\"," +
						"   \"salle\":\"" + coursLundiS3.get(i).getSalle() + "\"," +
						"   \"enseignant\":\"" + coursLundiS3.get(i).getProf() + "\"}";

				if(i < coursLundiS3.size() - 1 ){
					planning = planning + ",";
				}
			}
		}
		planning = planning + "]";


		planning =  planning +	"}," +
				"\"mardi\": {" +
				"\"nom\": \""+ t.getMardiS3StringName() +"\"," +
				"\"cours\": [";
		if(!coursMardiS3.isEmpty()){
			for(int i=0; i < coursMardiS3.size() ; i++){
				planning = planning + "{   \"id\":\"" + coursMardiS3.get(i).getId() + "\"," + 
						"   \"start\":\"" + coursMardiS3.get(i).getDebut() + "\"," +
						"   \"stop\":\"" + coursMardiS3.get(i).getFin() + "\"," +
						"   \"durée\":\"" + coursMardiS3.get(i).getDuree() + "\"," +
						"   \"nom\":\"" + coursMardiS3.get(i).getNom().replace("\"", "") + "\"," +
						"   \"salle\":\"" + coursMardiS3.get(i).getSalle() + "\"," +
						"   \"enseignant\":\"" + coursMardiS3.get(i).getProf() + "\"}";

				if(i < coursMardiS3.size() - 1 ){
					planning = planning + ",";
				}
			}
		}

		planning = planning + "]";


		planning =  planning +	"}," +
				"\"mercredi\": {" +
				"\"nom\": \""+ t.getMercrediS3StringName() +"\"," +
				"\"cours\": [";
		if(!coursMercrediS3.isEmpty()){
			for(int i=0; i < coursMercrediS3.size() ; i++){
				planning = planning + "{   \"id\":\"" + coursMercrediS3.get(i).getId() + "\"," + 
						"   \"start\":\"" + coursMercrediS3.get(i).getDebut() + "\"," +
						"   \"stop\":\"" + coursMercrediS3.get(i).getFin() + "\"," +
						"   \"durée\":\"" + coursMercrediS3.get(i).getDuree() + "\"," +
						"   \"nom\":\"" + coursMercrediS3.get(i).getNom().replace("\"", "") + "\"," +
						"   \"salle\":\"" + coursMercrediS3.get(i).getSalle() + "\"," +
						"   \"enseignant\":\"" + coursMercrediS3.get(i).getProf() + "\"}";

				if(i < coursMercrediS3.size() - 1 ){
					planning = planning + ",";
				}
			}
		}
		planning = planning + "]";


		planning =  planning +	"}," +
				"\"jeudi\": {" +
				"\"nom\": \""+ t.getJeudiS3StringName() +"\"," +
				"\"cours\": [";
		if(!coursJeudiS3.isEmpty()){
			for(int i=0; i < coursJeudiS3.size() ; i++){

				planning = planning + "{   \"id\":\"" + coursJeudiS3.get(i).getId() + "\"," + 
						"   \"start\":\"" + coursJeudiS3.get(i).getDebut() + "\"," +
						"   \"stop\":\"" + coursJeudiS3.get(i).getFin() + "\"," +
						"   \"durée\":\"" + coursJeudiS3.get(i).getDuree() + "\"," +
						"   \"nom\":\"" + coursJeudiS3.get(i).getNom().replace("\"", "") + "\"," +
						"   \"salle\":\"" + coursJeudiS3.get(i).getSalle() + "\"," +
						"   \"enseignant\":\"" + coursJeudiS3.get(i).getProf() + "\"}";

				if(i < coursJeudiS3.size() - 1 ){
					planning = planning + ",";
				}
			}
		}
		planning = planning + "]";


		planning =  planning +	"}," +
				"\"vendredi\": {" +
				"\"nom\": \""+ t.getVendrediS3StringName() +"\"," +
				"\"cours\": [";
		if(!coursVendrediS3.isEmpty()){
			for(int i=0; i < coursVendrediS3.size() ; i++){

				planning = planning + "{   \"id\":\"" + coursVendrediS3.get(i).getId() + "\"," + 
						"   \"start\":\"" + coursVendrediS3.get(i).getDebut() + "\"," +
						"   \"stop\":\"" + coursVendrediS3.get(i).getFin() + "\"," +
						"   \"durée\":\"" + coursVendrediS3.get(i).getDuree() + "\"," +
						"   \"nom\":\"" + coursVendrediS3.get(i).getNom().replace("\"", "") + "\"," +
						"   \"salle\":\"" + coursVendrediS3.get(i).getSalle() + "\"," +
						"   \"enseignant\":\"" + coursVendrediS3.get(i).getProf() + "\"}";

				if(i < coursVendrediS3.size() - 1 ){
					planning = planning + ",";
				}
			}
		}
		planning = planning + "]";


		planning =  planning +	"}," +
				"\"samedi\": {" +
				"\"nom\": \""+ t.getSamediS3StringName() + "\"," +
				"\"cours\": [";
		if(!coursSamediS3.isEmpty()){
			for(int i=0; i < coursSamediS3.size() ; i++){
				planning = planning + "{   \"id\":\"" + coursSamediS3.get(i).getId() + "\"," + 
						"   \"start\":\"" + coursSamediS3.get(i).getDebut() + "\"," +
						"   \"stop\":\"" + coursSamediS3.get(i).getFin() + "\"," +
						"   \"durée\":\"" + coursSamediS3.get(i).getDuree() + "\"," +
						"   \"nom\":\"" + coursSamediS3.get(i).getNom().replace("\"", "") + "\"," +
						"   \"salle\":\"" + coursSamediS3.get(i).getSalle() + "\"," +
						"   \"enseignant\":\"" + coursSamediS3.get(i).getProf() + "\"}";

				if(i < coursSamediS3.size() - 1 ){
					planning = planning + ",";
				}
			}
		}
		planning = planning + "]}}},";


		//Semaine 4

		planning = planning + 

				"\"semaine4\": {" +
				"\"nom\": \"semaine 45\"," +
				"\"jours\": {" +
				"\"lundi\": {" +
				"\"nom\": \""+ t.getLundiS4StringName() +"\"," + 
				"\"cours\": [";
		if(!coursLundiS4.isEmpty()){
			for(int i=0; i < coursLundiS4.size() ; i++){
				planning = planning + "{   \"id\":\"" + coursLundiS4.get(i).getId() + "\"," + 
						"   \"start\":\"" + coursLundiS4.get(i).getDebut() + "\"," +
						"   \"stop\":\"" + coursLundiS4.get(i).getFin() + "\"," +
						"   \"durée\":\"" + coursLundiS4.get(i).getDuree() + "\"," +
						"   \"nom\":\"" + coursLundiS4.get(i).getNom().replace("\"", "") + "\"," +
						"   \"salle\":\"" + coursLundiS4.get(i).getSalle() + "\"," +
						"   \"enseignant\":\"" + coursLundiS4.get(i).getProf() + "\"}";

				if(i < coursLundiS4.size() - 1 ){
					planning = planning + ",";
				}
			}
		}
		planning = planning + "]";


		planning =  planning +	"}," +
				"\"mardi\": {" +
				"\"nom\": \""+ t.getMardiS4StringName() +"\"," +
				"\"cours\": [";
		if(!coursMardiS4.isEmpty()){
			for(int i=0; i < coursMardiS4.size() ; i++){
				planning = planning + "{   \"id\":\"" + coursMardiS4.get(i).getId() + "\"," + 
						"   \"start\":\"" + coursMardiS4.get(i).getDebut() + "\"," +
						"   \"stop\":\"" + coursMardiS4.get(i).getFin() + "\"," +
						"   \"durée\":\"" + coursMardiS4.get(i).getDuree() + "\"," +
						"   \"nom\":\"" + coursMardiS4.get(i).getNom().replace("\"", "") + "\"," +
						"   \"salle\":\"" + coursMardiS4.get(i).getSalle() + "\"," +
						"   \"enseignant\":\"" + coursMardiS4.get(i).getProf() + "\"}";

				if(i < coursMardiS4.size() - 1 ){
					planning = planning + ",";
				}
			}
		}

		planning = planning + "]";


		planning =  planning +	"}," +
				"\"mercredi\": {" +
				"\"nom\": \""+ t.getMercrediS4StringName() +"\"," +
				"\"cours\": [";
		if(!coursMercrediS4.isEmpty()){
			for(int i=0; i < coursMercrediS4.size() ; i++){
				planning = planning + "{   \"id\":\"" + coursMercrediS4.get(i).getId() + "\"," + 
						"   \"start\":\"" + coursMercrediS4.get(i).getDebut() + "\"," +
						"   \"stop\":\"" + coursMercrediS4.get(i).getFin() + "\"," +
						"   \"durée\":\"" + coursMercrediS4.get(i).getDuree() + "\"," +
						"   \"nom\":\"" + coursMercrediS4.get(i).getNom().replace("\"", "") + "\"," +
						"   \"salle\":\"" + coursMercrediS4.get(i).getSalle() + "\"," +
						"   \"enseignant\":\"" + coursMercrediS4.get(i).getProf() + "\"}";

				if(i < coursMercrediS4.size() - 1 ){
					planning = planning + ",";
				}
			}
		}
		planning = planning + "]";


		planning =  planning +	"}," +
				"\"jeudi\": {" +
				"\"nom\": \""+ t.getJeudiS4StringName() +"\"," +
				"\"cours\": [";
		if(!coursJeudiS4.isEmpty()){
			for(int i=0; i < coursJeudiS4.size() ; i++){

				planning = planning + "{   \"id\":\"" + coursJeudiS4.get(i).getId() + "\"," + 
						"   \"start\":\"" + coursJeudiS4.get(i).getDebut() + "\"," +
						"   \"stop\":\"" + coursJeudiS4.get(i).getFin() + "\"," +
						"   \"durée\":\"" + coursJeudiS4.get(i).getDuree() + "\"," +
						"   \"nom\":\"" + coursJeudiS4.get(i).getNom().replace("\"", "") + "\"," +
						"   \"salle\":\"" + coursJeudiS4.get(i).getSalle() + "\"," +
						"   \"enseignant\":\"" + coursJeudiS4.get(i).getProf() + "\"}";

				if(i < coursJeudiS4.size() - 1 ){
					planning = planning + ",";
				}
			}
		}
		planning = planning + "]";


		planning =  planning +	"}," +
				"\"vendredi\": {" +
				"\"nom\": \""+ t.getVendrediS4StringName() +"\"," +
				"\"cours\": [";
		if(!coursVendrediS4.isEmpty()){
			for(int i=0; i < coursVendrediS4.size() ; i++){

				planning = planning + "{   \"id\":\"" + coursVendrediS4.get(i).getId() + "\"," + 
						"   \"start\":\"" + coursVendrediS4.get(i).getDebut() + "\"," +
						"   \"stop\":\"" + coursVendrediS4.get(i).getFin() + "\"," +
						"   \"durée\":\"" + coursVendrediS4.get(i).getDuree() + "\"," +
						"   \"nom\":\"" + coursVendrediS4.get(i).getNom().replace("\"", "") + "\"," +
						"   \"salle\":\"" + coursVendrediS4.get(i).getSalle() + "\"," +
						"   \"enseignant\":\"" + coursVendrediS4.get(i).getProf() + "\"}";

				if(i < coursVendrediS4.size() - 1 ){
					planning = planning + ",";
				}
			}
		}
		planning = planning + "]";


		planning =  planning +	"}," +
				"\"samedi\": {" +
				"\"nom\": \""+ t.getSamediS4StringName() + "\"," +
				"\"cours\": [";
		if(!coursSamediS4.isEmpty()){
			for(int i=0; i < coursSamediS4.size() ; i++){
				planning = planning + "{   \"id\":\"" + coursSamediS4.get(i).getId() + "\"," + 
						"   \"start\":\"" + coursSamediS4.get(i).getDebut() + "\"," +
						"   \"stop\":\"" + coursSamediS4.get(i).getFin() + "\"," +
						"   \"durée\":\"" + coursSamediS4.get(i).getDuree() + "\"," +
						"   \"nom\":\"" + coursSamediS4.get(i).getNom().replace("\"", "") + "\"," +
						"   \"salle\":\"" + coursSamediS4.get(i).getSalle() + "\"," +
						"   \"enseignant\":\"" + coursSamediS4.get(i).getProf() + "\"}";

				if(i < coursSamediS4.size() - 1 ){
					planning = planning + ",";
				}
			}
		}
		planning = planning + "]}}}}}}";


		this.planning = this.planning.replaceAll("é", "é");
		this.planning = this.planning.replaceAll("û", "û");



		this.planning = this.planning.replaceAll("'", "''");

		return this.planning;
	}
}