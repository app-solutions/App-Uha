<?php

class Notifications {

    //Constructeur par defaut
    function __construct() {
		include_once './scripts/config.php';
    }

    //destructeur
    function __destruct() {
    }

	//envoie de messages sur android
    public function onAndroid($message, $utilisateur ,$tokens) {

		$url = 'https://android.googleapis.com/gcm/send';

		$fields = array(
						'registration_ids'  => $tokens,
						'data'              => array( "message" => $message, "utilisateur" => $utilisateur ),
						);

		$headers = array(
						'Authorization: key=' . GOOGLE_API_KEY,
						'Content-Type: application/json'
						);

		//ouverture de connection
		$ch = curl_init();

		//envoie des données
		curl_setopt( $ch, CURLOPT_URL, $url );

		curl_setopt( $ch, CURLOPT_POST, true );
		curl_setopt( $ch, CURLOPT_HTTPHEADER, $headers);
		curl_setopt( $ch, CURLOPT_RETURNTRANSFER, true );
		curl_setopt( $ch, CURLOPT_POSTFIELDS, json_encode( $fields ) );

		// Execute post
		$result = curl_exec($ch);

		//Fermeture de connection
		curl_close($ch);
    }


	//envoie de messages sur ios
	public function onIos($message, $tokens){

	$apnsHost = APNS_SRV;
    	$apnsPort = 2195;

    $streamContext = stream_context_create();
    stream_context_set_option($streamContext, 'ssl', 'local_cert', APPLE_PEM);

    $apns = stream_socket_client('ssl://' . $apnsHost . ':' . $apnsPort, $error, $errorString, 60, STREAM_CLIENT_CONNECT, $streamContext);


	$payload['aps'] = array("alert" => array("body" => $message, "action-loc-key" => "ouvrir l'application" ), "badge" => 1, "sound" => "notif.m4a");
	$payload = json_encode($payload);

		foreach($tokens as $token){
			$apnsMessage = chr(0) . chr(0) . chr(32) . pack('H*', str_replace(' ', '', $token)) . chr(0) . chr(strlen($payload)) . $payload;
			fwrite($apns, $apnsMessage);
		}
	}

}
?>
