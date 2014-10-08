package fr.appsolutions.appuha;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import static fr.appsolutions.appuha.utilitaires.Utilitaires.*;

public class Credits extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_credits);

	}




	public void easterEgg(){
		Log.i(TAG, "EasterEgg");
		TextView dev1 = (TextView)findViewById(R.id.textView5);
		TextView dev2 = (TextView)findViewById(R.id.textView7);
		TextView dev3 = (TextView)findViewById(R.id.textView8);
		
		TextView rem1 = (TextView)findViewById(R.id.textView10);
		TextView rem2 = (TextView)findViewById(R.id.textView11);
		
		
		dev1.setText("Dark Vador");
		dev2.setText("Albert Einstein");
		dev3.setText("Bambi");
		rem1.setText("Au Père Noël");
		rem2.setText("Aux petits hommes verts");
		
	}
















	



	@Override
	protected void onRestart() {
		LOCAL_PLANNING_RAM = getSharedPreferences(LOCAL_PLANNING_NAME, MODE_PRIVATE).getString("planning", null);
		easterEgg();
		super.onRestart();
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
			startActivity(new Intent(this,Compte.class));
			overridePendingTransition(0,0);
			finish();
			return true ;

		case R.id.action_credits:
			return true;

		}
		return super.onMenuItemSelected(featureId, item);
	}



}
