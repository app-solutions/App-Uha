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

public class SendMessage {

	public static final int HTTP_POST_OK = 1;
	public static final int HTTP_POST_FAIL = 2;
	private HttpClient client;
	private HttpPost post;
	private HttpResponse response;
	private Thread thread;
	
	public SendMessage(){
		client = new DefaultHttpClient();
		post = new HttpPost(SRV_URL_SEND_MESSAGE);
		post.setHeader("User-Agent", VERS);
	}

	
	public void requestSrv(String message, String type ,final Handler handler){

		final ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();
		data.add(new BasicNameValuePair("message", message));
		data.add(new BasicNameValuePair("type", type));
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
					Log.i(TAG, "Requette reussie (SendMessage)");
					
					//traitement de la réponse
					message.what = HTTP_POST_OK;
					message.obj = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
					
				} catch (UnsupportedEncodingException e) {
					message.what = HTTP_POST_FAIL;
					Log.e(TAG, "Erreur lors de l'encodage de la requette (SendMessage)");
				} catch (ClientProtocolException e) {
					message.what = HTTP_POST_FAIL;
					Log.e(TAG, "Erreur dans le protocole HTTP (SendMessage)");
				} catch (IOException e) {
					message.what = HTTP_POST_FAIL;
					Log.e(TAG, "Le serveur ne repond pas (SendMessage)");
				}
				finally{
					handler.sendMessage(message);
				}
			}	
		};
		thread.start();
	}
	
	
}
