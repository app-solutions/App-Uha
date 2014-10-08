package fr.appsolutions.appuha;

import static fr.appsolutions.appuha.utilitaires.Utilitaires.*;

import fr.appsolutions.appuha.Main;
import fr.appsolutions.appuha.R;
import fr.appsolutions.appuha.appsolutionssrvrequest.*;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
@SuppressLint("HandlerLeak")
public class GCMIntentService extends GCMBaseIntentService {
	
	
	DeviceRegister dr;
	
	public GCMIntentService() {
		super(GCM_ID);
	}
	
	/**
	 * cette methode est applélée quand le device c'est enregistré aupres du serveur GCM
	 **/
	@Override
	protected void onRegistered(Context context, String token) {
	    dr = new DeviceRegister();
	    dr.requestSrv(token, new Handler(){
	    	public void handleMessage(android.os.Message msg){
				switch(msg.what){
				case DeviceRegister.HTTP_POST_OK:
					break;
				case DeviceRegister.HTTP_POST_FAIL:
					break;
				}
			};
	    });
	}
	
	
	
	/**
	 * cette methode est applélée quand le device c'est de-enregistré du serveur GCM
	 **/
	@Override
	protected void onUnregistered(Context context, String registrationId) {
	}
	
	
	/**
	 * cette methode est appelée quand le device recois une notification push
	 */
	@Override
	protected void onMessage(Context context, Intent intent) {
		String message = intent.getExtras().getString("message");
		String utilisateur = intent.getExtras().getString("utilisateur");
		
		if(android.os.Build.VERSION.SDK_INT < 16){
			generateSmallNotification(context, message, utilisateur);
		}
		else{
			generateBigNotification(context, message, utilisateur);
			
		}
	}
	
	@Override
	protected void onDeletedMessages(Context context, int total) {
	}

	
	@Override
	public void onError(Context context, String errorId) {
		Log.e(TAG, errorId);
	}

	@Override
	protected boolean onRecoverableError(Context context, String errorId) {
		return false;
	}
	
	
	private static void generateSmallNotification(Context context, String message, String utilisateur){
		
		int icon = R.drawable.ic_launcher;
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(icon, utilisateur + " : " + message, when);
        
        String title = context.getString(R.string.app_name);
        
        Intent notificationIntent = new Intent(context, Main.class);
        notificationIntent.putExtra("message", message);
        notificationIntent.putExtra("utilisateur", utilisateur);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent =
                PendingIntent.getActivity(context, 0, notificationIntent, 0);
        notification.setLatestEventInfo(context, title, message, intent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        
        // Play default notification sound
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notification.defaults |= Notification.DEFAULT_LIGHTS;
        
        //gestion de la led de notification
        notification.ledARGB = Color.BLUE;
        notification.ledOnMS = 500 ;
        notification.ledOffMS= 500 ;
        
        //notification.sound = Uri.parse("android.resource://" + context.getPackageName() + "notif.mp3");
        
        // Vibrate if vibrate is enabled
        
        notificationManager.notify(0, notification);
	
	}
	
	
	private static void generateBigNotification(Context context, String message, String utilisateur) {

      NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
      			.setAutoCancel(true)  
                .setContentTitle("App-Uha")  
                .setSmallIcon(R.drawable.ic_launcher) //.setLargeIcon(icon1)  
                .setContentText(message)
                .setSubText("Par: " + utilisateur)
                .setSound(Uri.parse("android.resource://" + context.getPackageName() + "notif.mp3"));
     
      NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();  
      bigText.bigText(message);  
      bigText.setBigContentTitle("App-Uha");  
      bigText.setSummaryText("Par: " + utilisateur);  
      mBuilder.setStyle(bigText);
  
      Intent resultIntent = new Intent(context, Main.class);  
      resultIntent.putExtra("message", message);
      resultIntent.putExtra("utilisateur", utilisateur);


      TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);  

      stackBuilder.addParentStack(Main.class);
      
      
      stackBuilder.addNextIntent(resultIntent);  
      PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,  
                PendingIntent.FLAG_UPDATE_CURRENT);  
      mBuilder.setContentIntent(resultPendingIntent);  

      NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

      Notification nt = mBuilder.build(); //test
      
      nt.defaults |= Notification.DEFAULT_SOUND; //test
      nt.defaults |= Notification.DEFAULT_LIGHTS; //test
      nt.defaults |= Notification.DEFAULT_VIBRATE; //test
      
      //gestion de la led de notification
      nt.ledARGB = Color.BLUE;
      nt.ledOnMS = 500 ;
      nt.ledOffMS= 500 ;
      
      mNotificationManager.notify(1337, nt); //test
      
      //mNotificationManager.notify(1337, mBuilder.build());

     
	}

}
