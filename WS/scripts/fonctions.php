<?php

class Fonctions{

	private $db;

	function __construct() {
        include_once './scripts/connect.php';
		include_once './scripts/config.php';
        $this->db = new Connect();
        $this->db->connect();
    }

	function __destruct() {
    }

	/*
	* Fonctions Web service
	*/

	public function addDevice($uniqueIdentifier, $typeOfDevice, $token){

			setlocale (LC_TIME, 'fr_FR.utf8','fra'); 
                        $date = strftime("%d %B - %T");

			mysql_query("INSERT INTO devices(uniqueIdentifier, type, token, lastUse) VALUES('$uniqueIdentifier', '$typeOfDevice', '$token', '$date')");

	}

	public function updateDevice($uniqueIdentifier, $token){

			setlocale (LC_TIME, 'fr_FR.utf8','fra'); 
			$date = strftime("%d %B - %T");
			
			mysql_query("UPDATE devices SET token='$token', lastUse='$date' WHERE uniqueIdentifier='$uniqueIdentifier'");
	
	}

	public function addUser($prenom, $nom, $email, $groupe){
			mysql_query("INSERT INTO users(prenom, nom, email, groupe, notifs) VALUES('$prenom', '$nom', '$email', '$groupe', 5)");
	}

	public function getUserInfos($uniqueIdentifier){

		$query = mysql_query("SELECT * FROM users WHERE email=(SELECT owner FROM devices WHERE uniqueIdentifier='$uniqueIdentifier')");

		while($row = mysql_fetch_assoc($query)){
			$prenom = $row['prenom'];
			$nom = $row['nom'];
			$email = $row['email'];
			$groupe = $row['groupe'];
			$notifs = $row['notifs'];
		}

		return array("prenom" => $prenom, "nom" => $nom, "email" => $email, "groupe" => $groupe, "notifs" => $notifs);

	}

	public function sendNotifications($uniqueIdentifier, $message, $type){

		include_once './scripts/notifications.php';
		$no = new Notifications();

		$email = array();
		$ios_devices = array();
		$android_devices = array();
		$userInfos = $this->getUserInfos($uniqueIdentifier);

		$utilisateur = $userInfos['prenom'] . " " . $userInfos['nom'];

		$message_android = $message;
		$message_ios = $utilisateur . " : " . $message;

		if($type == "mon groupe"){

			$query = mysql_query("SELECT * FROM users WHERE groupe=(SELECT groupe FROM users WHERE email=(SELECT owner FROM devices WHERE uniqueIdentifier='$uniqueIdentifier')) AND email !=(SELECT owner FROM devices WHERE uniqueIdentifier='$uniqueIdentifier')");
			while($row = mysql_fetch_assoc($query)){

				array_push($email, $row['email']);

			}

			for($i=0; $i < count($email); $i++){
				$mail = $email[$i];
				$query = mysql_query("SELECT * FROM devices WHERE owner='$mail' AND enable='oui'");

				while($row = mysql_fetch_assoc($query)){

					if($row['type'] == "android"){
						array_push($android_devices, $row['token']);
					}
					else if($row['type'] == "ios"){
						array_push($ios_devices, $row['token']);
					}

				}
			}
			$no->onAndroid($message, $utilisateur ,$android_devices);
			$no->onIos($message_ios, $ios_devices);

		}
		else if($type == "ma promotion"){
					
			$query = mysql_query("select * from users where email=(select owner from devices where uniqueIdentifier='$uniqueIdentifier')");
			while($row = mysql_fetch_assoc($query)){

				$grp = $row['groupe'];
			}		

			$sendGr = "";
			if(strpos($grp, "CJ1") !== false){
				$sendGr = "CJ1%";			
			}
			else if(strpos($grp, "CJ2") !== false){
				$sendGr = "CJ2%";
			}
			else if(strpos($grp, "GB1") !== false){
				$sendGr = "GB1%";
			}
			else if(strpos($grp, "GB2") !== false){
				$sendGr = "GB2%";
			}
			else if(strpos($grp, "GTE1") !== false){
				$sendGr = "GTE1%";
			}
			else if(strpos($grp, "GTE2") !== false){
				$sendGr = "GTE2%";
			}
			else if(strpos($grp, "HSE1") !== false){
				$sendGr = "HSE1%";
			}
			else if(strpos($grp, "HSE2") !== false){
				$sendGr = "HSE2%";
			}
			else if(strpos($grp, "RT1") !== false){
				$sendGr = "RT1%";
			}
			else if(strpos($grp, "RT2") !== false){
				$sendGr = "RT2%";
			}
			else if(strpos($grp, "LP ASUR") !== false){
				$sendGr = "LP ASUR%";
			}
			else if(strpos($grp, "LP ISVD") !== false){
				$sendGr = "LP ISVD%";
			}
			else if(strpos($grp, "TC1") !== false){
				$sendGr = "TC1%";
			}
			else if(strpos($grp, "TC2") !== false){
				$sendGr = "TC2%";
			}
			else if(strpos($grp, "LP TECH2") !== false){
				$sendGr = "LP TECH2%";
			}
			else if(strpos($grp, "LP TECH2") !== false){
				$sendGr = "LP TECH2%";
			}
			else if(strpos($grp, "LP BIOTECH") !== false){
				$sendGr = "LP BIOTECH%";
			}
			else if(strpos($grp, "LP AQ") !== false){
				$sendGr = "LP AQ%";
			}
			else if(strpos($grp, "LP HT") !== false){
				$sendGr = "LP HT%";
			}
			else if(strpos($grp, "HSEA") !== false){
				$sendGr = "HSEA%";
			}

			$query = mysql_query("select * from users where groupe like '$sendGr' AND email !=(SELECT owner FROM devices WHERE uniqueIdentifier='$uniqueIdentifier') ");
			while($row = mysql_fetch_assoc($query)){

				array_push($email, $row['email']);

			}


			for($i=0; $i < count($email); $i++){
				$mail = $email[$i];
				$query = mysql_query("SELECT * FROM devices WHERE owner='$mail' AND enable='oui'");

				while($row = mysql_fetch_assoc($query)){

					if($row['type'] == "android"){
						array_push($android_devices, $row['token']);
					}
					else if($row['type'] == "ios"){
						array_push($ios_devices, $row['token']);
					}

				}
			}

			$no->onAndroid($message, $utilisateur ,$android_devices);
			$no->onIos($message_ios, $ios_devices);	

		}
		$this->archiveNotifications($userInfos['email'], $message);
		$this->userNotificationDec($uniqueIdentifier);
		$no->__destruct();
	}

	
	public function sendGlobalNotifications($utilisateur, $message){
	
		include_once './scripts/notifications.php';
		$no = new Notifications();
		
		$ios_devices = array();
		$android_devices = array();
		
		$message_android = $message;
		$message_ios = $utilisateur . " : " . $message;
		
		$query = mysql_query("SELECT * FROM devices WHERE enable='oui'");
		while($row = mysql_fetch_assoc($query)){
			
			if($row['type'] == "android"){
				array_push($android_devices, $row['token']);
			}
			else if($row['type'] == "ios"){
				array_push($ios_devices, $row['token']);
			}
		}
		
		$no->onAndroid($message, $utilisateur ,$android_devices);
		$no->onIos($message_ios, $ios_devices);
		$no->__destruct();
	
	}

	
	public function sendSingleNotification($utilisateur,$email ,$message){
	
		include_once './scripts/notifications.php';
		$no = new Notifications();

		$ios_devices = array();
		$android_devices = array();

		$message_android = $message;
		$message_ios = $utilisateur . " : " . $message;

		$query = mysql_query("SELECT * FROM devices WHERE owner='$email' AND enable='oui'");

			while($row = mysql_fetch_assoc($query)){

				if($row['type'] == "android"){
					array_push($android_devices, $row['token']);
				}
				else if($row['type'] == "ios"){
					array_push($ios_devices, $row['token']);
				}

			}
		
		$no->onAndroid($message, $utilisateur ,$android_devices);
		$no->onIos($message_ios, $ios_devices);
		$no->__destruct();

	}
	

	public function sendServiceNotifications($messageForAndroid, $messageForIos ,$groupe){

		include_once './scripts/notifications.php';
		$no = new Notifications();

		$email = array();
		$ios_devices = array();
		$android_devices = array();

		$query = mysql_query("SELECT * FROM users WHERE groupe='$groupe'");
			while($row = mysql_fetch_assoc($query)){

				array_push($email, $row['email']);

		}
		

		for($i=0; $i < count($email); $i++){
			$mail = $email[$i];
			$query = mysql_query("SELECT * FROM devices WHERE owner='$mail' AND enable='oui'");

			while($row = mysql_fetch_assoc($query)){

				if($row['type'] == "android"){
					array_push($android_devices, $row['token']);
				}
				else if($row['type'] == "ios"){
					array_push($ios_devices, $row['token']);
				}

			}
		}

		if(!empty($android_devices)){
			$no->onAndroid($messageForAndroid, "App-Uha" ,$android_devices);
		}
		if(!empty($ios_devices)){
			$no->onIos($messageForIos, $ios_devices);	
		}
		
		$no->__destruct();

	}


	public function getPlanning($uniqueIdentifier){
		$query = mysql_query("SELECT * from planning WHERE groupe=(SELECT groupe FROM devices WHERE uniqueIdentifier='$uniqueIdentifier')");
		while($row = mysql_fetch_assoc($query)){

			$planning = $row['planning'];

		}

		return $planning;
	}
	
	public function userExclude($email){
	
		$query = mysql_query("SELECT * FROM exclude WHERE email='$email'");
		
		$owner = null;
		while($row = mysql_fetch_assoc($query)){

			$owner = $row['email'];

		}

		if($owner != NULL){
			return TRUE;
		}
		else if($owner == NULL) {
			return FALSE;
		}

		return FALSE;
	
	}
	
	public function userExcludeReason($email){
		$query = mysql_query("SELECT * from exclude WHERE email='$email'");
		while($row = mysql_fetch_assoc($query)){

			$reason = $row['reason'];

		}

	return $reason;
	
	}

	/*
	*
	* Fonctions secondaires (annexes)
	*
	*/



	/*
	* activiation d'un device
	*/
	public function updateDeviceOwnerTrue($uniqueIdentifier, $email, $groupe){
		mysql_query("UPDATE devices SET owner='$email', groupe='$groupe', enable='oui' WHERE uniqueIdentifier='$uniqueIdentifier'");
	}

	/*
	* desactivation d'un device
	*/
	public function updateDeviceOwnerFalse($uniqueIdentifier, $email, $groupe){
		mysql_query("UPDATE devices SET owner='$email', groupe='$groupe', enable='non' WHERE uniqueIdentifier='$uniqueIdentifier'");
	}

	/*
	* test l'autentification d'un utilisateur
	*/
	public function userLogged($email, $password){

		$script = LOC_PERL . " -l '$email' -p '$password'";
		$test = shell_exec($script);

		if(preg_match("#Connexion r&eacute;ussie#", $test)){
			return TRUE;
		}
		else{
			return FALSE;
		}

		return FALSE;

	}


	/*
	*retourne vrai si un device existe dans la table devices
	*/
	public function deviceExist($uniqueIdentifier){

		$nb = mysql_num_rows(mysql_query("SELECT * FROM devices WHERE uniqueIdentifier = '$uniqueIdentifier'"));

		if($nb > 0){
			 return TRUE;
		}
		else{
			return FALSE;
		}

		return FALSE;
	}

	/*
	* retourne vrai si un utilisateur est afilié a ce telephone
	*/
	public function ownerExist($uniqueIdentifier){

		$query = mysql_query("SELECT * FROM devices WHERE uniqueIdentifier='$uniqueIdentifier'");

		while($row = mysql_fetch_assoc($query)){

			$owner = $row['owner'];

		}

		if($owner != NULL){
			return TRUE;
		}
		else if($owner == NULL) {
			return FALSE;
		}

		return FALSE;
	}

	/*
	* retourne vrai si un device est autorisé
	*/
	public function deviceEnable($uniqueIdentifier){

		$query = mysql_query("SELECT * FROM devices WHERE uniqueIdentifier='$uniqueIdentifier'");


		while($row = mysql_fetch_assoc($query)){

			$enable = $row['enable'];

		}

		if($enable == "oui"){
			return TRUE;
		}
		else if($enable == "non"){
			return FALSE;
		}

		return FALSE;
	}


	/*
	*retourne vrai si un utilisateur existe dans la table users
	*/
	public function userExist($email){
		$nb = mysql_num_rows(mysql_query("SELECT * FROM users WHERE email='$email'"));

		if($nb > 0){
			return TRUE;
		}
		else{
			return FALSE;
		}
	}

	/*
	*retourne true si l'utilisateur n'a pas dépassé son quota du notifications
	*/
	public function userEnableNotifications($uniqueIdentifier){
		$query = mysql_query("SELECT * from users WHERE email=(SELECT owner FROM devices WHERE uniqueIdentifier='$uniqueIdentifier')");

		while($row = mysql_fetch_assoc($query)){

			$notifs = $row['notifs'];

		}
		if($notifs > 0){
			return TRUE;
		}
		else{
			return FALSE;
		}
		return FALSE;
	}

	/*
	* cette fonction décremente les notifications d'un utilisateur
	*/
	public function userNotificationDec($uniqueIdentifier){
		$query = mysql_query("SELECT * FROM users WHERE email=(SELECT owner FROM devices WHERE uniqueIdentifier='$uniqueIdentifier')");

		while($row = mysql_fetch_assoc($query)){

			$nb = $row['notifs'];
			$email = $row['email'];

		}

		$nb = $nb - 1;

		mysql_query("UPDATE users SET notifs='$nb' WHERE email='$email'");
	}

	/*
	* cette fonction permet d'archiver les notifications
	*/
	public function archiveNotifications($email, $message){
		$message = str_replace("'","''", $message);
		mysql_query("INSERT INTO notifications(email, message) VALUES('$email', '$message')");
	}
}

?>
