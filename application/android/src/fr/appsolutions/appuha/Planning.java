package fr.appsolutions.appuha;

import java.util.List;

import org.json.JSONException;

import com.mukesh.widget.AmazingAdapter;
import com.mukesh.widget.AmazingListView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import fr.appsolutions.appuha.alertdialogmanager.AlertDialogManager;
import fr.appsolutions.appuha.jsonreader.JsonReader;

import static fr.appsolutions.appuha.utilitaires.Utilitaires.LOCAL_PLANNING_NAME;
import static fr.appsolutions.appuha.utilitaires.Utilitaires.LOCAL_PLANNING_RAM;
import static fr.appsolutions.appuha.utilitaires.Utilitaires.PLANNING_WEEK;
import static fr.appsolutions.appuha.utilitaires.Utilitaires.TAG;


public class Planning extends Activity {

	private static JsonReader jr;


	AmazingListView lsComposer;
	SectionComposerAdapter adapter;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Context context = this;

		switch(PLANNING_WEEK){
		case 2:
			Toast.makeText(context, "La semaine prochaine", Toast.LENGTH_SHORT).show();
			break;
		case 3:
			Toast.makeText(context, "Dans 2 semaines", Toast.LENGTH_SHORT).show();
			break;
		case 4:
			Toast.makeText(context, "Dans 3 semaines", Toast.LENGTH_SHORT).show();
			break;
		}

		//test si les plannings sont toujours en RAM
		if(LOCAL_PLANNING_RAM == null){
			startActivity(new Intent(this,Main.class));
			overridePendingTransition(0,0);
			finish();
		}


		try {
			jr = new JsonReader((String) getSharedPreferences(LOCAL_PLANNING_NAME, MODE_PRIVATE).getString("planning", null));
			if(jr.getResultCode().equals("0")){
				new AlertDialogManager().alert(context, jr.getResultTitle(), jr.getResultString(), "ok", 1);
				return;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}


		SharedPreferences pref = getSharedPreferences("firstTime", MODE_PRIVATE);
		boolean test = (boolean) pref.getBoolean("firstTime_planning", false);
		System.out.println(test);
		if(!test)
		{
			Editor edit = pref.edit();
			edit.putBoolean("firstTime_planning", true);
			edit.commit();
			new AlertDialogManager().alert(context, "Astuce", "Pour passer à l'emploi du temps suivant, appuyez à nouveau sur le bouton \"planning\"", "ok", 2);
		}

		try{
			setContentView(R.layout.layout_planning);

			lsComposer = (AmazingListView) findViewById(R.id.lsComposer);
			lsComposer.setPinnedHeaderView(LayoutInflater.from(this).inflate(
					R.layout.item_composer_header, lsComposer, false));
			lsComposer.setAdapter(adapter = new SectionComposerAdapter());
		}
		catch(Exception e){
			Log.e(TAG, e.toString());
		}

	}

	class SectionComposerAdapter extends AmazingAdapter {
		List<Pair<String, List<Composer>>> all = Data.getAllData();

		@Override
		public int getCount() {
			int res = 0;
			for (int i = 0; i < all.size(); i++) {
				res += all.get(i).second.size();
			}
			return res;
		}

		@Override
		public Composer getItem(int position) {
			int c = 0;
			for (int i = 0; i < all.size(); i++) {
				if (position >= c && position < c + all.get(i).second.size()) {
					return all.get(i).second.get(position - c);
				}
				c += all.get(i).second.size();
			}
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		protected void onNextPageRequested(int page) {
		}

		@Override
		protected void bindSectionHeader(View view, int position,
				boolean displaySectionHeader) {
			if (displaySectionHeader) {
				view.findViewById(R.id.header).setVisibility(View.VISIBLE);
				TextView lSectionTitle = (TextView) view
						.findViewById(R.id.header);
				lSectionTitle
				.setText(getSections()[getSectionForPosition(position)]);
			} else {
				view.findViewById(R.id.header).setVisibility(View.GONE);
			}
		}

		@Override
		public View getAmazingView(int position, View convertView,
				ViewGroup parent) {
			View res = convertView;
			if (res == null)
				res = getLayoutInflater().inflate(R.layout.item_composer, null);

			TextView cDate = (TextView) res.findViewById(R.id.cDate);
			TextView cName = (TextView) res.findViewById(R.id.cName);
			TextView cRoom = (TextView) res.findViewById(R.id.cRoom);
			TextView cTeacher = (TextView) res.findViewById(R.id.cTeacher);

			Composer composer = getItem(position);
			cDate.setText(composer.date);
			cName.setText(composer.name);
			cRoom.setText(composer.room);
			cTeacher.setText(composer.teacher);

			return res;
		}

		@Override
		public void configurePinnedHeader(View header, int position, int alpha) {
			TextView lSectionHeader = (TextView) header;
			lSectionHeader
			.setText(getSections()[getSectionForPosition(position)]);
		}

		@Override
		public int getPositionForSection(int section) {
			if (section < 0)
				section = 0;
			if (section >= all.size())
				section = all.size() - 1;
			int c = 0;
			for (int i = 0; i < all.size(); i++) {
				if (section == i) {
					return c;
				}
				c += all.get(i).second.size();
			}
			return 0;
		}

		@Override
		public int getSectionForPosition(int position) {
			int c = 0;
			for (int i = 0; i < all.size(); i++) {
				if (position >= c && position < c + all.get(i).second.size()) {
					return i;
				}
				c += all.get(i).second.size();
			}
			return -1;
		}

		@Override
		public String[] getSections() {
			String[] res = new String[all.size()];
			for (int i = 0; i < all.size(); i++) {
				res[i] = all.get(i).first;
			}
			return res;
		}

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

			if(PLANNING_WEEK == 4){
				PLANNING_WEEK = 1;
			}
			else{
				PLANNING_WEEK = PLANNING_WEEK +1;
			}

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