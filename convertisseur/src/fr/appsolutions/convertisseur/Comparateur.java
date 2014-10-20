package fr.appsolutions.convertisseur;

import java.util.ArrayList;


public class Comparateur
{
	private ArrayList<ArrayList<Cours>> coursArNew;
	private ArrayList<ArrayList<Cours>> coursArOld;

	public Comparateur(ArrayList<ArrayList<Cours>> coursArNew, ArrayList<ArrayList<Cours>> coursArOld)
	{
		this.coursArNew = coursArNew;
		this.coursArOld = coursArOld;
	}

	public String compareNew(int groupeId)
	{
		ArrayList<String> temp = new ArrayList<String>();

		if (coursArNew.get(groupeId).size() != 0)
		{

			temp.clear();
			String tempString = "";

			for (int n=0; n<coursArNew.get(groupeId).size(); n++)
			{
				boolean test = false;


				for (int o=0; o<coursArOld.get(groupeId).size(); o++)
				{
					if (coursArOld.get(groupeId).get(o).getId().contains(coursArNew.get(groupeId).get(n).getId())) {
						test = false;
						break;
					}

					test = true;
				}

				if (test) {
					temp.add(coursArNew.get(groupeId).get(n).getNom() + " le " + coursArNew.get(groupeId).get(n).getJour() + " à " + coursArNew.get(groupeId).get(n).getDebut());
				}
			}

			if (temp.isEmpty()) {
				return null;
			}

			tempString = "Le(s) cours suivant a/ont été ajouté(s) à votre emploi du temps:";

			for (int z = 0; z < temp.size(); z++) {
				tempString = tempString + "\n  ● " + (String)temp.get(z);
			}

			return tempString;
		}

		return null;
	}

	public String compareOld(int groupeId)
	{
		ArrayList<String> temp = new ArrayList<String>();

		if (coursArOld.get(groupeId).size() != 0)
		{

			temp.clear();
			String tempString = "";


			for (int o=0; o<coursArOld.get(groupeId).size(); o++)
			{
				boolean test = false;


				for (int n=0; n<coursArNew.get(groupeId).size(); n++)
				{
					if (coursArNew.get(groupeId).get(n).getId().contains(coursArOld.get(groupeId).get(o).getId())) {
						test = false;
						break;
					}

					test = true;
				}

				if (test) {
					temp.add(coursArOld.get(groupeId).get(o).getNom() + " le " + coursArOld.get(groupeId).get(o).getJour() + " à " + coursArOld.get(groupeId).get(o).getDebut());
				}
			}


			if (temp.isEmpty()) {
				return null;
			}

			tempString = "Le(s) cours suivant a/ont été supprimé(s) de votre emploi du temps:";

			for (int z = 0; z < temp.size(); z++) {
				tempString = tempString + "\n  ● " + (String)temp.get(z);
			}

			return tempString;
		}

		return null;
	}
}
