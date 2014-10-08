package fr.appsolutions.appuha;

import static fr.appsolutions.appuha.utilitaires.Utilitaires.LOCAL_PLANNING_NAME;
import static fr.appsolutions.appuha.utilitaires.Utilitaires.LOCAL_PLANNING_RAM;
import static fr.appsolutions.appuha.utilitaires.Utilitaires.LOCAL_COMPTE_NAME;
import static fr.appsolutions.appuha.utilitaires.Utilitaires.LOCAL_USERINFOS_NAME;
import static fr.appsolutions.appuha.utilitaires.Utilitaires.TAG;

import org.json.JSONException;

import android.os.Bundle;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import fr.appsolutions.appuha.alertdialogmanager.AlertDialogManager;
import fr.appsolutions.appuha.appsolutionssrvrequest.GetPlanning;
import fr.appsolutions.appuha.appsolutionssrvrequest.GetUserInfo;
import fr.appsolutions.appuha.appsolutionssrvrequest.UserUpdate;
import fr.appsolutions.appuha.jsonreader.JsonReader;

@SuppressLint("HandlerLeak")
public class Compte extends Activity {

	private UserUpdate usr;
	private JsonReader jsr;
	private Context context;
	private GetPlanning gp;
	private ProgressBar p;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_compte);

		/**
		 * initialisation
		 */
		context = this;
		p = (ProgressBar) findViewById(R.id.progress);

		//test si les plannings sont toujours en RAM
		if(LOCAL_PLANNING_RAM == null){
			startActivity(new Intent(this,Main.class));
			overridePendingTransition(0,0);
			finish();
		}


		/**
		 * g�n�ration du spinner
		 */
		Spinner sp = (Spinner) findViewById(R.id.groupe);
		ArrayAdapter<CharSequence> ar = ArrayAdapter.createFromResource(this, R.array.groupes, android.R.layout.simple_expandable_list_item_1 );
		ar.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		sp.setAdapter(ar);


		/**
		 * gestion d'un contact exisant
		 */
		SharedPreferences pref = getSharedPreferences(LOCAL_COMPTE_NAME, MODE_PRIVATE);
		EditText eprenom = (EditText)findViewById(R.id.prenom);
		EditText enom = (EditText)findViewById(R.id.nom);
		EditText eemail = (EditText)findViewById(R.id.email);
		EditText epassword = (EditText)findViewById(R.id.password);

		eprenom.setText(pref.getString("prenom", null));
		enom.setText(pref.getString("nom", null));
		eemail.setText(pref.getString("email", null));
		epassword.setText(pref.getString("password", null));
		sp.setSelection(pref.getInt("groupe_pos", 0));


	}



	public void compteUpdate(View v){

		boolean test = false;

		EditText eprenom = (EditText)findViewById(R.id.prenom);
		EditText enom = (EditText)findViewById(R.id.nom);
		EditText eemail = (EditText)findViewById(R.id.email);
		EditText epassword = (EditText)findViewById(R.id.password);
		Spinner sgroupe = (Spinner) findViewById(R.id.groupe);


		String prenom = eprenom.getText().toString().toLowerCase();
		String nom = enom.getText().toString().toLowerCase();
		String email = eemail.getText().toString().toLowerCase();
		String password = epassword.getText().toString();
		String groupe = sgroupe.getSelectedItem().toString();
		int groupe_pos = sgroupe.getSelectedItemPosition();

		//test des champs de valeur
		if(eprenom.length() == 0){
			eprenom.setError(getString(R.string.prenom_error));
			test = true;
		}
		if(enom.length() == 0){
			enom.setError(getString(R.string.nom_error));
			test = true;
		}
		if(email.length() == 0){
			eemail.setError(getString(R.string.email_error));
			test = true;
		}
		if(!email.contains("@uha.fr")){
			if(!email.contains("@uhacolmar.fr")){
				eemail.setError(getString(R.string.email_error_domain));
				test = true;
			}
		}
		if(password.length() == 0){
			epassword.setError(getString(R.string.password_error));
			test = true;
		}
		if(!email.contains(prenom) && !email.contains(nom)){
			eemail.setError(getString(R.string.email_error_name));
			test = true;
		}
		if(groupe.length() == 0 && test == false){
			Toast.makeText(context, "choisisez une groupe", Toast.LENGTH_LONG).show();
			test = true;
		}


		if(test == true)
			return;


		//sauvegarde des informations utilisateur en local
		SharedPreferences pref = getSharedPreferences(LOCAL_COMPTE_NAME, MODE_PRIVATE);
		Editor edit = pref.edit();
		edit.putString("prenom", prenom);
		edit.putString("nom", nom);
		edit.putString("email", email);
		edit.putString("password", password);
		edit.putString("groupe", groupe);
		edit.putInt("groupe_pos", groupe_pos);
		edit.commit();

		Toast.makeText(this, "Mise à jour de votre profil ...", Toast.LENGTH_SHORT).show();
		p.setVisibility(View.VISIBLE);
		usr = new UserUpdate();
		usr.requestSrv(prenom, nom, email, password, groupe, new Handler(){
			public void handleMessage(android.os.Message msg){
				switch(msg.what){
				case UserUpdate.HTTP_POST_OK:
					String result = (String) msg.obj;
					try {
						jsr = new JsonReader(result);
						String resultCode = jsr.getResultCode();
						String resultTitle = jsr.getResultTitle();
						String resultString = jsr.getResultString();
						if(resultCode.equals("1")){
							new AlertDialogManager().alert(context, resultTitle, resultString, "ok", 1);
							getPlanning();
							getUserInfo();
						}
						else if(resultCode.equals("0")){
							new AlertDialogManager().alert(context, resultTitle, resultString, "ok", 0);
							getPlanning();
							getUserInfo();
						}
					} catch (JSONException e) {
						Log.e(TAG, "Erreur lors du parsing des informations");
					} catch (Exception e) {
						Log.e(TAG, "Erreur Ce fichier n'est pas compatible Json");
					}
					break;
				case UserUpdate.HTTP_POST_FAIL:
					new AlertDialogManager().alert(context, "Attention !", "Echec de la mise à jour de votre profil, le serveur ne reponds pas.", "ok", 0);
					p.setVisibility(View.GONE);
					break;
				}
			};
		});

	}






	//recupération des emplois du temps
	private void getPlanning(){

		gp = new GetPlanning();
		gp.requestSrv(new Handler(){
			public void handleMessage(android.os.Message msg){

				SharedPreferences pref = getSharedPreferences(LOCAL_PLANNING_NAME, MODE_PRIVATE);

				switch(msg.what){
				case GetPlanning.HTTP_POST_OK:
					String result = (String) msg.obj;

					//enregistrement des plannings en local
					Editor edit = pref.edit();
					edit.putString("planning", result);
					edit.commit();

					LOCAL_PLANNING_RAM = (String) msg.obj;

					break;
				case GetPlanning.HTTP_POST_FAIL:
					break;
				}
				p.setVisibility(View.GONE);
			};
		});
	}

	private void getUserInfo(){

		new GetUserInfo().requestSrv(new Handler(){
			public void handleMessage(android.os.Message msg){
				switch(msg.what){
				case GetUserInfo.HTTP_POST_OK:
					String result = (String) msg.obj;

					//enregistrement des informations
					SharedPreferences pref = getSharedPreferences(LOCAL_USERINFOS_NAME, MODE_PRIVATE);
					Editor edit = pref.edit();
					edit.putString("userInfos", result);
					edit.commit();

					break;
				case GetUserInfo.HTTP_POST_FAIL:
					break;
				}
			};
		});

	}

























	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_accueil:
			startActivity(new Intent(this,Accueil.class));
			overridePendingTransition(0,0);
			finish();
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
			return true ;

		case R.id.action_credits:
			startActivity(new Intent(this,Credits.class));	
			overridePendingTransition(0,0);
			finish();
		}
		return super.onMenuItemSelected(featureId, item);
	}

}
