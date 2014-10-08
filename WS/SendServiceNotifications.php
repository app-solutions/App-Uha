<?php
if( isset($_POST['key']) && isset($_POST['messageForAndroid']) && isset($_POST['messageForIos']) && isset($_POST['groupe']) ){
	
	$key = $_POST['key'];
	$messageForAndroid = $_POST['messageForAndroid'];
	$messageForIos = $_POST['messageForIos'];
	$groupe = $_POST['groupe'];

	include_once './scripts/fonctions.php';
	$db = new Fonctions();

	if($key == "jqvu202G0ptQOMUQAAj46jw01N1m72Kf"){
	

		$db->sendServiceNotifications($messageForAndroid, $messageForIos , $groupe);

		$payload['AppUha'] = array(
									"resultCode" => "1",
									"resultHeader" => "SendServiceNotifications",
									"resultTitle" => "",
									"resultString" => "la notification a bien été envoyée"
								);
		echo json_encode($payload);

	}
	else{
		
		$payload['AppUha'] = array(
									"resultCode" => "0",
									"resultHeader" => "SendServiceNotifications",
									"resultTitle" => "",
									"resultString" => "invalid request, wrong key"
								);
		echo json_encode($payload);	

	}
}
else{

	$payload['AppUha'] = array(
									"resultCode" => "0",
									"resultHeader" => "SendServiceNotifications",
									"resultTitle" => "",
									"resultString" => "invalid request, missing values"
								);
	echo json_encode($payload);

}
?>
