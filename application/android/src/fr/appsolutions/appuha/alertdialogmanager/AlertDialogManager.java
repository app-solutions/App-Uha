package fr.appsolutions.appuha.alertdialogmanager;

import fr.appsolutions.appuha.R;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;

public class AlertDialogManager {
	
	public void alert(final Context context,String titre, String message,String bouton, int type){
		
		/**
		 * type 0 -> icon fail
		 * type 1 -> icon succes
		 * type 2 -> icon App-Uha
		 */
		
		
		if(type == 0){
			Builder ad = new AlertDialog.Builder(context)
			.setTitle(titre)
			.setMessage(message)
			.setNegativeButton(bouton, null)
			.setIcon(R.drawable.fail);
			ad.show();
		}
		else if(type == 1){
			Builder ad = new AlertDialog.Builder(context)
			.setTitle(titre)
			.setMessage(message)
			.setNegativeButton(bouton, null)
			.setIcon(R.drawable.success);
			ad.show();
		}
		else if(type == 2){
			Builder ad = new AlertDialog.Builder(context)
			.setTitle(titre)
			.setMessage(message)
			.setNegativeButton(bouton, null)
			.setIcon(R.drawable.ic_launcher);
			ad.show();
		}
	
	}

}