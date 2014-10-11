package fr.appsolutions.appuha;

import static fr.appsolutions.appuha.utilitaires.Utilitaires.LOCAL_PLANNING_NAME;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;
import fr.appsolutions.appuha.utilitaires.Utilitaires;

public class WidgetProv extends AppWidgetProvider {

	@SuppressWarnings("static-access")
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);

		Log.i(Utilitaires.TAG, "Widget update receive");

		final int N = appWidgetIds.length;

		for (int i = 0; i < N; i++) {

			String planning = (String) context.getSharedPreferences(LOCAL_PLANNING_NAME, context.MODE_PRIVATE).getString("planning", null);
			
			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.layout_widget);
			views.setTextViewText(R.id.textWidget, "Prochain cours: " + String.valueOf( (int)(Math.random() * (100)) + 0));
			appWidgetManager.updateAppWidget(appWidgetIds[i], views);

		}

	}


}
