<?php
if( (isset($_GET['utilisateur']) && $_GET['utilisateur'] != null )  && (isset($_GET['message']) && $_GET['message'] != null ) && isset($_GET['key']) ){

	$utilisateur = $_GET['utilisateur'];
	$message = $_GET['message'];
	$key = $_GET['key'];

	include_once './scripts/fonctions.php';
	$db = new Fonctions();
	
	if($key == "LVCq3y59PWe5ue3z4gH38sU2rGwyM2P4"){
	
		$db->sendGlobalNotifications($utilisateur, $message);
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