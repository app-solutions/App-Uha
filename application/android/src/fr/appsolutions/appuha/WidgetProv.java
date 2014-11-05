package fr.appsolutions.appuha;

import static fr.appsolutions.appuha.utilitaires.Utilitaires.LOCAL_WIDGET_NAME;
import static fr.appsolutions.appuha.utilitaires.Utilitaires.LOCAL_PLANNING_NAME;

import java.util.ArrayList;
import java.util.Calendar;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.widget.RemoteViews;
import fr.appsolutions.appuha.autres.Cours;
import fr.appsolutions.appuha.jsonreader.JsonReader;
import fr.appsolutions.appuha.utilitaires.Utilitaires;

public class WidgetProv extends AppWidgetProvider {


	private Calendar calendar = Calendar.getInstance();
	private int day_name = calendar.get(Calendar.DAY_OF_WEEK);
	private int hour = calendar.get(Calendar.HOUR_OF_DAY);
	private int minutes = calendar.get(Calendar.MINUTE);
	private String timestamp_sting = hour + "H" + minutes;
	private SharedPreferences pref;
	private Editor edit;


	@SuppressWarnings("static-access")
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);

		Log.i(Utilitaires.TAG, "Widget update receive");
		final int N = appWidgetIds.length;

		for (int i = 0; i < N; i++) {

			try{

				pref = context.getSharedPreferences(LOCAL_WIDGET_NAME, context.MODE_PRIVATE);
				edit = pref.edit();
				String planning = (String) context.getSharedPreferences(LOCAL_PLANNING_NAME, context.MODE_PRIVATE).getString("planning", null);
				JsonReader jsr = new JsonReader(planning);
				ArrayList<Cours> allCours = jsr.getAllWeeknDaysCours();
				ArrayList<Cours> dayCours = null;

				switch(day_name){
				case Calendar.MONDAY:
					dayCours = jsr.getDayCours(JsonReader.S1, JsonReader.LUNDI);
					break;
				case Calendar.TUESDAY:
					dayCours = jsr.getDayCours(JsonReader.S1, JsonReader.MARDI);
					break;
				case Calendar.WEDNESDAY:
					dayCours = jsr.getDayCours(JsonReader.S1, JsonReader.MERCREDI);
					break;
				case Calendar.THURSDAY:
					dayCours = jsr.getDayCours(JsonReader.S1, JsonReader.JEUDI);
					break;
				case Calendar.FRIDAY:
					dayCours = jsr.getDayCours(JsonReader.S1, JsonReader.VENDREDI);
					break;
				case Calendar.SATURDAY:
					dayCours = jsr.getDayCours(JsonReader.S1, JsonReader.SAMEDI);
					break;
				case Calendar.SUNDAY:
					dayCours = jsr.getDayCours(JsonReader.S1, JsonReader.LUNDI);
					break;
				}


				int current_timestamp = Integer.valueOf(timestamp_sting.replace("H", ""));
				Cours nextCours = null;

				if(dayCours.isEmpty()){ // if the there is no class in the day
					String id = pref.getString("planning", null);
					if(id != null){
						for(int j=0; i<allCours.size(); j++){
							if(id.equals(allCours.get(j).getId())){
								nextCours = allCours.get(j + 1); 
							}
						}
					}
				}

				for(int j=0; j<dayCours.size(); j++){ 
					if(current_timestamp < Integer.valueOf(dayCours.get(j).getStart().replace("H", ""))){
						nextCours = dayCours.get(j);
						edit.putString("coursID", allCours.get(j).getId());
						break;
					}
					else if(j == dayCours.size() -1){ //si on est en fin de journée, on prends le prochain cours
						Log.d(Utilitaires.TAG, "plus de cours la journée");
						for(int z =0; z< allCours.size(); z++){
							if(allCours.get(z).getId().equals(dayCours.get(j).getId())){
								nextCours = allCours.get(z + 1);
								edit.putString("coursID", allCours.get(z + 1).getId());
								break;
							}
						}
					}
				}


				// update layout
				RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.layout_widget);
				views.setTextViewText(R.id.textCoursName, "Prochain cours: " + nextCours.getNom());
				views.setTextViewText(R.id.textCoursStart,"à: " + nextCours.getStart());
				views.setTextViewText(R.id.textCoursRoom, "salle: " + nextCours.getSalle());
				appWidgetManager.updateAppWidget(appWidgetIds[i], views);

			} catch (Exception e){
				e.printStackTrace();
				Log.e(Utilitaires.TAG, e.toString());
			}

		}

	}


}
