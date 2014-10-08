package fr.appsolutions.appuha;

import static fr.appsolutions.appuha.utilitaires.Utilitaires.*;

import fr.appsolutions.appuha.alertdialogmanager.AlertDialogManager;
import fr.appsolutions.appuha.connectiondetector.ConnectionDetector;
import fr.appsolutions.appuha.jsonreader.JsonReader;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Accueil extends Activity {

	private Context context; //context

	// Objet d'autres classes
	private SharedPreferences pref;
	private JsonReader jsr;
	
	// Objets de la vue
	private ImageView iImg;
	private TextView tBonjour;
	private TextView tEmail;
	private TextView tGroupe;
	private TextView tNotifications;

	
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_accueil);
		/**
		 * initialisation
		 */
		context = this;
		
		iImg = (ImageView)findViewById(R.id.image_profil);
		tBonjour = (TextView)findViewById(R.id.bonjour);
		tEmail= (TextView)findViewById(R.id.email);
		tGroupe = (TextView)findViewById(R.id.groupe);
		tNotifications = (TextView)findViewById(R.id.notifications);
		
		//les données sauvegardées des informations utilisateur
		pref = getSharedPreferences(LOCAL_USERINFOS_NAME, MODE_PRIVATE);
		
		//test si les plannings sont toujours en RAM
		if(LOCAL_PLANNING_RAM == null){
			startActivity(new Intent(this,Main.class));
			overridePendingTransition(0,0);
			finish();
		}
		
		
		try{
			
			jsr = new JsonReader(pref.getString("userInfos", null));
			String resultCode = jsr.getResultCode();
			String resultTitle = jsr.getResultTitle();
			String resultString = jsr.getResultString();
			
			if(resultCode.equals("1")){
				tBonjour.setText("Bonjour " + jsr.getUserInfoPrenom());
				tEmail.setText(jsr.getUserInfoEmail());
				tGroupe.setText(jsr.getUserInfoGroupe());
				tNotifications.setText("Il vous reste " + jsr.getUserInfoNotif() + " notification(s) à envoyer cette semaine");
				setAppropriatePicture(jsr.getUserInfoGroupe());
			}
			else{
				new AlertDialogManager().alert(context, resultTitle, resultString, "ok", 2);
			}
			
		} catch(Exception e){
			
		}
		
		
		if(!new ConnectionDetector(context).isConnectingToInternet()){
			new AlertDialogManager().alert(this, "Absence de connexion internet", "Il semblerait que votre téléphone ne soit pas connecté à internet. Les emplois du temps seront chargés localement.", "ok" , 0);
			Log.w(TAG, "Attention, aucune connexion à internet");
		}
	
	}


	private void setAppropriatePicture(String groupe){
		
		if(groupe.contains("RT") || groupe.contains("LP ASUR") || groupe.contains("LP ISVD")){
			iImg.setImageResource(R.drawable.profil_rt);
		}
		else if(groupe.contains("TC") || groupe.contains("LP TECH2") || groupe.contains("LP VINCOM") || groupe.contains("LP HT")){
			iImg.setImageResource(R.drawable.profil_tc);
		}
		else if(groupe.contains("GB") || groupe.contains("LP BIOTECH")){
			iImg.setImageResource(R.drawable.profil_gb);
		}
		else if(groupe.contains("GTE")){
			iImg.setImageResource(R.drawable.profil_gte);
		}
		else if(groupe.contains("HSE") || groupe.contains("LP AQ")){
			iImg.setImageResource(R.drawable.profil_hse);
		}
		else if(groupe.contains("CJ") || groupe.contains("LP MI") || groupe.contains("LP CASF")){
			iImg.setImageResource(R.drawable.profil_cj);
		}
		else{
			iImg.setImageResource(R.drawable.profil_defaut);
		}
		
	}



	public void compte(View v){
		startActivity(new Intent(this,Compte.class));
		overridePendingTransition(0,0);
		finish();
	}


	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_accueil:
			return true ;

		case R.id.action_planning:
			startActivity(new Intent(this,Planning.class));
			overridePendingTransition(0,0);
			finish();
			return true ;

		case R.id.action_notifications:
			startActivity(new Intent(this,Notifications.class));
			overridePendingTransition(0,0);
			finish();
			return true ;

		case R.id.action_compte:
			startActivity(new Intent(this,Compte.class));
			overridePendingTransition(0,0);
			finish();
			return true ;

		case R.id.action_credits:
			startActivity(new Intent(this,Credits.class));
			overridePendingTransition(0,0);
			finish();
		}
		return super.onMenuItemSelected(featureId, item);
	}

}
