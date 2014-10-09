package fr.appsolutions.convertisseur;

import java.util.ArrayList;
import java.util.Iterator;


public class Main
{

	public static void main(String[] args)
	{

		BDDAS bdd = new BDDAS(Settings.databaseUrl, Settings.databaseUser, Settings.databaseUserPassword);

		ArrayList<String> groupe = new ArrayList<String>();
		ArrayList<String> planningNew = new ArrayList<String>();
		ArrayList<String> planningOld = new ArrayList<String>();


		groupe.add("CJ11"); //0
		groupe.add("CJ12"); //1
		groupe.add("CJ13"); //2
		groupe.add("CJ14"); //3
		groupe.add("CJ21"); //4
		groupe.add("CJ22"); //5
		groupe.add("CJ23"); //6
		groupe.add("CJ24"); //7
		groupe.add("CJ1A"); //8
		groupe.add("CJ2A"); //9
		groupe.add("LP MI"); //10
		groupe.add("LP CASF"); //11


		groupe.add("GB111"); //12
		groupe.add("GB112"); //13
		groupe.add("GB121"); //14
		groupe.add("GB122"); //15
		groupe.add("GB211"); //16
		groupe.add("GB212"); //17
		groupe.add("GB221"); //18
		groupe.add("GB222"); //19
		groupe.add("LP BIOTECH 1"); //20
		groupe.add("LP BIOTECH 2"); //21


		groupe.add("GTE11-A"); //22
		groupe.add("GTE11-B"); //23
		groupe.add("GTE21-A"); //24
		groupe.add("GTE21-B"); //25
		groupe.add("GTE1A-C"); //26
		groupe.add("GTE2A-C"); //27

		groupe.add("EN2D-A"); //28
		groupe.add("EN2D-B"); //29


		groupe.add("HSE111"); //30
		groupe.add("HSE112"); //31
		groupe.add("HSE121"); //32
		groupe.add("HSE122"); //33
		groupe.add("HSE131"); //34
		groupe.add("HSE132"); //35
		groupe.add("HSE211"); //36
		groupe.add("HSE212"); //37
		groupe.add("HSE221"); //38
		groupe.add("HSE222"); //39
		groupe.add("HSE231"); //40
		groupe.add("HSE232"); //41
		groupe.add("HSEA1"); //42
		groupe.add("HSEA2"); //43

		groupe.add("LP AQ APP"); //44
		groupe.add("LP AQ 11"); //45
		groupe.add("LP AQ 12"); //46



		groupe.add("RT111"); //47
		groupe.add("RT112"); //48
		groupe.add("RT121"); //49
		groupe.add("RT122"); //50
		groupe.add("RT211"); //51
		groupe.add("RT212"); //52
		groupe.add("RT221"); //53
		groupe.add("RT222"); //54
		groupe.add("RT11A"); //55
		groupe.add("RT12A"); //56
		groupe.add("RT21A"); //57
		groupe.add("RT22A"); //58

		groupe.add("LP ASUR 1"); //59
		groupe.add("LP ASUR 2"); //60

		groupe.add("LP ISVD 1"); //61
		groupe.add("LP ISVD 2"); //62


		groupe.add("TC131"); //63
		groupe.add("TC132"); //64
		groupe.add("TC141"); //65
		groupe.add("TC142"); //66
		groupe.add("TC151"); //67
		groupe.add("TC152"); //68
		groupe.add("TC231"); //69
		groupe.add("TC232"); //70
		groupe.add("TC241"); //71
		groupe.add("TC242"); //72
		groupe.add("TC251"); //73
		groupe.add("TC252"); //74
		groupe.add("TC111A"); //75
		groupe.add("TC112A"); //76
		groupe.add("TC121A"); //77
		groupe.add("TC122A"); //78
		groupe.add("TC211A"); //79
		groupe.add("TC212A"); //80
		groupe.add("TC221A"); //81
		groupe.add("TC222A"); //82

		groupe.add("TC11TRI"); //83
		groupe.add("TC12TRI"); //84
		groupe.add("TC21TRI"); //85
		groupe.add("TC22TRI"); //86
		groupe.add("LP TECH2 G11"); //87
		groupe.add("LP TECH2 G12"); //88
		groupe.add("LP VINCOM"); //89
		groupe.add("LP HT 1.1"); //90
		groupe.add("LP HT 1.2"); //91







		System.out.print("STEP 1 -> Openning database ");
		if (bdd.connect().booleanValue())
		{



			System.out.print("\nSTEP 2 -> ASCF translation to JSON ");
			Iterator<String> iterator = groupe.iterator();
			while (iterator.hasNext()) {
				String path = (String)iterator.next();
				planningNew.add(new Convertisseur("/App-Uha/ASCF/" + path + ".ascf").start());
			}
			System.out.print("[OK]");




			if(Settings.enableNotifications){
				System.out.print("\nSTEP 3 -> Dowloading old plannings ");
				planningOld = bdd.getPlanning(groupe);
			} else{
				System.out.print("\nSTEP 3 -> Aborded, notifications disable");
			}




			System.out.print("\nSTEP 4 -> Uploading new planning on database ");
			bdd.updatePlanning(groupe, planningNew);


			System.out.print("\nSTEP 5 -> Closing database ");
			bdd.close();

			try
			{
				if(Settings.enableNotifications){
					ArrayList<ArrayList<Cours>> coursArOld = new ArrayList<ArrayList<Cours>>();
					ArrayList<ArrayList<Cours>> coursArNew = new ArrayList<ArrayList<Cours>>();

					for (int i = 0; i < groupe.size(); i++) {
						coursArNew.add(new ArrayList<Cours>(new JsonReader((String)planningNew.get(i)).getWeekCours("semaine1")));
						coursArOld.add(new ArrayList<Cours>(new JsonReader((String)planningOld.get(i)).getWeekCours("semaine1")));
					}

					ArrayList<String> ajout = new ArrayList<String>();
					ArrayList<String> suppression = new ArrayList<String>();


					System.out.print("\nSTEP 6 -> Testing differences between classes");
					Comparateur comp = new Comparateur(coursArNew, coursArOld);
					for (int i = 0; i < groupe.size(); i++) {
						ajout.add(comp.compareNew(i));
						suppression.add(comp.compareOld(i));
					}
					System.out.print("[OK]");


					System.out.print("\nSTEP 7 -> Notifications generations ");
					new NotificationsGenerator(groupe, ajout, suppression).generate();
				} else{
					System.out.println("\nSTEP 6 et 7 -> Aborded, notifications disable");
				}
			}
			catch (Exception e)
			{
				System.out.print("[FAIL]\n" + e.toString());
			}
		}

		System.out.println("\nProcessing ended");
	}
}