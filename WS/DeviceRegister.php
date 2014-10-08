<?php

if( (isset($_POST['uniqueIdentifier']) && $_POST['uniqueIdentifier'] != null) && (isset($_POST['typeOfDevice']) && $_POST['typeOfDevice'] != null) && (isset($_POST['token']) && $_POST['token'] != null) ){

	$uniqueIdentifier = $_POST['uniqueIdentifier'];
	$typeOfDevice = $_POST['typeOfDevice'];
	$token = $_POST['token'];

	include_once './scripts/fonctions.php';
	$db = new Fonctions();

	if(!$db->deviceExist($uniqueIdentifier)){

		$db->addDevice($uniqueIdentifier , $typeOfDevice, $token);
		$payload['AppUha'] = array(
									"resultCode" => "1",
									"resultHeader" => "DeviceRegister",
									"resultTitle" => "",
									"resultString" => "device enregistré"
								);
		echo json_encode($payload);

	}
	else{
		$db->updateDevice($uniqueIdentifier, $token);
		$payload['AppUha'] = array(
									"resultCode" => "1",
									"resultHeader" => "DeviceRegister",
									"resultTitle" => "",
									"resultString" => "Device déjà enregistré"
								);
		echo json_encode($payload);
	}

	$db->__destruct();
}
else{
	$payload['AppUha'] = array(
								"resultCode" => "0",
								"resultHeader" => "DeviceRegister",
								"resultTitle" => "",
								"resultString" => "Requette invalide"
							);
	echo json_encode($payload);
}

?>
