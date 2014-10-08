package fr.appsolutions.appuha;

import com.google.android.gcm.GCMRegistrar;

import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings.Secure;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.widget.TextView;

import fr.appsolutions.appuha.connectiondetector.*;
import fr.appsolutions.appuha.jsonreader.JsonReader;
import fr.appsolutions.appuha.appsolutionssrvrequest.*;
import static fr.appsolutions.appuha.utilitaires.Utilitaires.*;

@SuppressLint("HandlerLeak")
public class Main extends Activity  {

	Context context; //context

	JsonReader jsr; //objet de parsing JSON
	Intent i; //intent
	TextView dlText;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_startbackground);

		/**
		 * initialisation (placer ici les éléments important de l'application)
		 */
		context = this; //definition du context
		UNIQUEIDENTIFIER = Secure.getString(this.getContentResolver(),Secure.ANDROID_ID); //definition de l'unique identifier qui est mis en RAM
		TIMER = true; //definition du timer qui est mis en RAM
		i = getIntent();
		dlText = (TextView) findViewById(R.id.dlText);


		/**
		 * tout ce qui demande une connexion doit se faire exclusivement apres de cette condition !
		 */

		if(new ConnectionDetector(context).isConnectingToInternet()){


			GCMRegistrar.register(this, GCM_ID); //Service d'enregistrement des notification push
			getPlanning();

		}else{
			Log.w(TAG, "Attention, aucune connexion à internet");
			LOCAL_PLANNING_RAM = getSharedPreferences(LOCAL_PLANNING_NAME, MODE_PRIVATE).getString("planning", null);

			startApplication();
		}

	}

	//recup�ration des emplois du temps
	private void getPlanning(){

		dlText.setText("Téléchargement de vos emplois du temps");

		new GetPlanning().requestSrv(new Handler(){
			public void handleMessage(android.os.Message msg){

				SharedPreferences pref = getSharedPreferences(LOCAL_PLANNING_NAME, MODE_PRIVATE);
				Editor edit = pref.edit();

				switch(msg.what){
				case GetPlanning.HTTP_POST_OK:

					//mise en ram des plannings
					LOCAL_PLANNING_RAM = (String) msg.obj;

					//enregistrement des plannings en local
					edit.putString("planning", (String) msg.obj);
					edit.commit();
					
					Log.w(TAG, (String) msg.obj);

					getUserInfo();

					break;
				case GetPlanning.HTTP_POST_FAIL:

					dlText.setText("mode hors ligne");

					//mise en RAM des planning
					LOCAL_PLANNING_RAM = pref.getString("planning", null);

					//d�marage de l'application
					startApplication();

					break;
				}

			};
		});
	}


	private void getUserInfo(){

		dlText.setText("Téléchargement de vos informations personnelles");

		new GetUserInfo().requestSrv(new Handler(){
			public void handleMessage(android.os.Message msg){
				switch(msg.what){
				case GetUserInfo.HTTP_POST_OK:
					String result = (String) msg.obj;

					Log.w(TAG, result);

					//enregistrement des informations
					SharedPreferences pref = getSharedPreferences(LOCAL_USERINFOS_NAME, MODE_PRIVATE);
					Editor edit = pref.edit();
					edit.putString("userInfos", result);
					edit.commit();

					//démarage de l'application
					startApplication();

					break;
				case GetUserInfo.HTTP_POST_FAIL:
					break;
				}
			};
		});

	}


	private void startApplication(){

		if(i.getStringExtra("message") != null){
			startActivity(new Intent(context, Notifications.class).putExtra("message", i.getStringExtra("message")).putExtra("utilisateur", i.getStringExtra("utilisateur")));
			overridePendingTransition(0,0);
			finish();
		}
		else{
			startActivity(new Intent(context, Accueil.class));
			overridePendingTransition(0,0);
			finish();
		}

	}

}
