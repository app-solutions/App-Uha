package fr.appsolutions.appuha;

import org.json.JSONException;

import android.os.Bundle;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import fr.appsolutions.appuha.alertdialogmanager.*;
import fr.appsolutions.appuha.appsolutionssrvrequest.SendMessage;
import fr.appsolutions.appuha.jsonreader.JsonReader;
import fr.appsolutions.appuha.timer.Timer;
import static fr.appsolutions.appuha.utilitaires.Utilitaires.LOCAL_PLANNING_RAM;
import static fr.appsolutions.appuha.utilitaires.Utilitaires.TAG;
import static fr.appsolutions.appuha.utilitaires.Utilitaires.TIMER;


@SuppressLint("HandlerLeak")
public class Notifications extends Activity {

	private Context context;
	private JsonReader jsr;
	private AlertDialogManager adm;
	private SendMessage msg;
	private Timer tmr;
	private Intent i;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_notifications);

		/**
		 * initialisation
		 */
		context = this;
		adm = new AlertDialogManager();
		tmr = new Timer(60000);
		i = getIntent();

		////test si les plannings sont toujours en RAM
		if(LOCAL_PLANNING_RAM == null){
			startActivity(new Intent(this,Main.class));
			overridePendingTransition(0,0);
			finish();
		}

		/**
		 * autre
		 */
		Spinner sp = (Spinner) findViewById(R.id.type);
		ArrayAdapter<CharSequence> ar = ArrayAdapter.createFromResource(this, R.array.type, android.R.layout.simple_expandable_list_item_1 );
		ar.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		sp.setAdapter(ar);


		/**
		 * code d'initialisation
		 */

		if(i.getStringExtra("message") != null){
			new AlertDialogManager().alert(context, i.getStringExtra("utilisateur"), i.getStringExtra("message"), "ok", 2);
		}


	}




	public void notificationSend(View v){

		final ProgressBar p = (ProgressBar) findViewById(R.id.progress);

		final EditText Emessage = (EditText)findViewById(R.id.message);
		Spinner Stype = (Spinner) findViewById(R.id.type);

		if(Emessage.length() == 0){
			Emessage.setError("Ecrivez un texte :)");
			return;
		}

		if(!TIMER){
			adm.alert(context, "Attention !", "Vous avez déja envoyé une notification il y a moins de 1 minute", "ok", 0);
			return;
		}
		else{
			tmr.doTime();
		}

		String message = Emessage.getText().toString();
		String type = Stype.getSelectedItem().toString();

		Toast.makeText(this, "Envoi de votre notification ...", Toast.LENGTH_SHORT).show();
		p.setVisibility(View.VISIBLE);

		msg = new SendMessage();
		msg.requestSrv(message, type, new Handler(){
			public void handleMessage(android.os.Message msg){
				switch(msg.what){
				case SendMessage.HTTP_POST_OK:
					String result = (String) msg.obj;
					try {
						jsr = new JsonReader(result);
						String resultCode = jsr.getResultCode();
						String resultString = jsr.getResultString();
						if(resultCode.equals("0")){
							adm.alert(context, "Attention !", resultString , "ok" , 0);
						}
						else if(resultCode.equals("1")){
							Toast.makeText(context, "Notification envoyée !", Toast.LENGTH_LONG).show();
						}
					} catch (JSONException e) {
						Log.e(TAG, "Erreur lors du parsing des informations");
					} catch (Exception e){
						Log.e(TAG, "Erreur Ce fichier n'est pas compatible Json");
					}
					break;
				case SendMessage.HTTP_POST_FAIL:
					adm.alert(context, "Attention !", "Echec de l'envoie de la notification, le serveur ne reponds pas.", "ok", 0);
					break;
				}
				p.setVisibility(View.GONE);
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
