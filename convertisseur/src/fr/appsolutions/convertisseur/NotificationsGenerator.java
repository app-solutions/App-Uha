package fr.appsolutions.convertisseur;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;



public class NotificationsGenerator
{
	private Temps t;
	private ArrayList<String> groupe;
	private ArrayList<String> ajout;
	private ArrayList<String> suppression;
	private int totalNotifications = 0;

	public NotificationsGenerator(ArrayList<String> groupe, ArrayList<String> ajout, ArrayList<String> suppression)
	{
		this.groupe = groupe;
		this.ajout = ajout;
		this.suppression = suppression;

		this.t = new Temps();
	}


	public void generate()
	{
		if (this.t.getCurrentDate().contains("samedi")) {
			return;
		}
		for (int h = 0; h < this.groupe.size(); h++)
		{

			String notificationForAndroid = null;
			String notificationForIos = null;
			boolean notif = false;


			if (this.ajout.get(h) != null) {
				notificationForAndroid = (String)this.ajout.get(h);
				notificationForIos = "Un/des cours à/on été ajouté(s) à votre emploi du temps";
				notif = true;
				this.totalNotifications += 1;
			}
			if ((this.suppression.get(h) != null) && (this.ajout.get(h) == null)) {
				notificationForAndroid = (String)this.suppression.get(h);
				notificationForIos = "un/des cours à/on été supprimé(s) à votre emploi du temps";
				notif = true;
				this.totalNotifications += 1;
			}
			if ((this.suppression.get(h) != null) && (this.ajout.get(h) != null)) {
				notificationForAndroid = notificationForAndroid + "\n" + (String)this.suppression.get(h);
				notificationForIos = "Un/des cours à/on été ajouté(s) et supprimé(s) de votre emploi du temps";
				notif = true;
				this.totalNotifications += 1;
			}



			if (notif)
			{
				try
				{
					System.out.print("\n Send to " + (String)this.groupe.get(h) + "-> ");
					String serveur = "http://127.0.0.1/app-uha/SendServiceNotifications.php";
					String post = "messageForAndroid=" + notificationForAndroid + "&messageForIos=" + notificationForIos + "&groupe=" + (String)this.groupe.get(h) + "&key=" + Settings.ws_service_key;
					URL url = new URL(serveur);
					URLConnection conn = (HttpURLConnection)url.openConnection();
					conn.setDoOutput(true);
					OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
					writer.write(post);
					writer.flush();

					String reponse = "";String ligne = null;
					BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					while ((ligne = reader.readLine()) != null) {
						reponse = reponse + ligne.trim() + "\n";
					}

					if (reponse.contains("\"resultCode\":\"1\"")) {
						System.out.print("[SEND]");
					}
					else {
						System.out.print("[FAIL]");
					}
				}
				catch (Exception e) {
					System.out.print("[FAIL]");
					e.printStackTrace();
				}
			}
		}


		if (this.totalNotifications == 0) {
			System.out.print("[NONE SEND]");
		}
	}
}