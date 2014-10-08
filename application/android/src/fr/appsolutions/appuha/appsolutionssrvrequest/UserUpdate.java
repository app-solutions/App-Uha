package fr.appsolutions.appuha.appsolutionssrvrequest;

import static fr.appsolutions.appuha.utilitaires.Utilitaires.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class UserUpdate {

	public static final int HTTP_POST_OK = 1;
	public static final int HTTP_POST_FAIL = 2;
	private HttpClient client;
	private HttpPost post;
	private HttpResponse response;
	private Thread thread;

	public UserUpdate(){
		client = new DefaultHttpClient();
		post = new HttpPost(SRV_URL_USER_UPDATE);
		post.setHeader("User-Agent", VERS);
	}
	
	public void requestSrv(String prenom, String nom, String email, String password, String groupe, final Handler handler){

		final ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();
		data.add(new BasicNameValuePair("prenom", prenom));
		data.add(new BasicNameValuePair("nom", nom));
		data.add(new BasicNameValuePair("email", email));
		data.add(new BasicNameValuePair("password", password));
		data.add(new BasicNameValuePair("groupe", groupe));
		data.add(new BasicNameValuePair("uniqueIdentifier", UNIQUEIDENTIFIER));
		
		thread = new Thread(){
			@Override
			public void run(){
				Message message = new Message();
				try {
					//preparation de la requette
					post.setEntity(new UrlEncodedFormEntity(data,HTTP.UTF_8));

					//execution de la requette
					response = client.execute(post);
					Log.i(TAG, "Requette reussie (UserUpdate)");
					
					//traitement de la reponse
					message.what = HTTP_POST_OK;
					message.obj = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
					
				} catch (UnsupportedEncodingException e) {
					message.what = HTTP_POST_FAIL;
					Log.e(TAG, "Erreur lors de l'encodage de la requette (UserUpdate)");
				} catch (ClientProtocolException e) {
					message.what = HTTP_POST_FAIL;
					Log.e(TAG, "Erreur dans le protocole HTTP (UserUpdate)");
				} catch (IOException e) {
					message.what = HTTP_POST_FAIL;
					Log.e(TAG, "Le serveur ne repond pas (UserUpdate)");
				}
				finally{
					handler.sendMessage(message);
				}
			}	
		};
		thread.start();
	}
	
}