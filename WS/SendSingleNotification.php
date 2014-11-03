<?php
if( (isset($_GET['utilisateur']) && $_GET['utilisateur'] != null ) && (isset($_GET['email']) && $_GET['email'] != null )  && (isset($_GET['message']) && $_GET['message'] != null ) && isset($_GET['key']) ){

	$utilisateur = $_GET['utilisateur'];
	$email = $_GET['email'];
	$message = $_GET['message'];
	$key = $_GET['key'];

	include_once './scripts/fonctions.php';
	include_once './scripts/config.php';
	$db = new Fonctions();
	
	if($key == SERVICE_KEY){
	
		$db->sendSingleNotification($utilisateur,$email ,$message);
		$payload['AppUha'] = array(
									"resultCode" => "1",
									"resultHeader" => "SendGlobalNotifications",
									"resultTitle" => "",
									"resultString" => "la notification a bien été envoyée"
								);
		echo json_encode($payload);
	
	}
	else{
	
		$payload['AppUha'] = array(
									"resultCode" => "0",
									"resultHeader" => "SendGlobalNotifications",
									"resultTitle" => "",
									"resultString" => "invalid request, wrong key"
								);
		echo json_encode($payload);
	
	}
	

}
else{

	$payload['AppUha'] = array(
									"resultCode" => "0",
									"resultHeader" => "SendGlobalNotifications",
									"resultTitle" => "",
									"resultString" => "invalid request, missing values"
								);
	echo json_encode($payload);

}


?>
