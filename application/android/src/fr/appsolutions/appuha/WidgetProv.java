package fr.appsolutions.appuha;

import static fr.appsolutions.appuha.utilitaires.Utilitaires.LOCAL_PLANNING_NAME;

import java.util.ArrayList;
import java.util.Calendar;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;
import fr.appsolutions.appuha.autres.Cours;
import fr.appsolutions.appuha.jsonreader.JsonReader;
import fr.appsolutions.appuha.utilitaires.Utilitaires;

public class WidgetProv extends AppWidgetProvider {

	
	private Calendar calendar = Calendar.getInstance();
	int day_name = calendar.get(Calendar.DAY_OF_WEEK);
	int hour = calendar.get(Calendar.HOUR_OF_DAY);
	int minutes = calendar.get(Calendar.MINUTE);
	String timestamp_sting = hour + "H" + minutes;
	
	
	@SuppressWarnings("static-access")
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);

		Log.i(Utilitaires.TAG, "Widget update receive");
		final int N = appWidgetIds.length;

		
		
		for (int i = 0; i < N; i++) {
				
			try{
				
				String planning = (String) context.getSharedPreferences(LOCAL_PLANNING_NAME, context.MODE_PRIVATE).getString("planning", null);
				JsonReader jsr = new JsonReader(planning);
				ArrayList<Cours> cours = null;
				
				switch(day_name){
				case Calendar.MONDAY:
					cours = jsr.getDayCours(JsonReader.S1, JsonReader.LUNDI);
					break;
				case Calendar.TUESDAY:
					cours = jsr.getDayCours(JsonReader.S1, JsonReader.MARDI);
					break;
				case Calendar.WEDNESDAY:
					cours = jsr.getDayCours(JsonReader.S1, JsonReader.MERCREDI);
					break;
				case Calendar.THURSDAY:
					cours = jsr.getDayCours(JsonReader.S1, JsonReader.JEUDI);
					break;
				case Calendar.FRIDAY:
					cours = jsr.getDayCours(JsonReader.S1, JsonReader.VENDREDI);
					break;
				case Calendar.SATURDAY:
					cours = jsr.getDayCours(JsonReader.S1, JsonReader.SAMEDI);
					break;
				case Calendar.SUNDAY:
					cours = jsr.getDayCours(JsonReader.S1, JsonReader.LUNDI);
					break;
				}
				
				
				int current_timestamp = Integer.valueOf(timestamp_sting.replace("H", ""));
				Cours nextCours = null;
				
				for(int j=0; j<cours.size(); j++){
					if(current_timestamp < Integer.valueOf(cours.get(j).getStart().replace("H", ""))){
						nextCours = cours.get(j);
						break;
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
			}
			
		}

	}


}
