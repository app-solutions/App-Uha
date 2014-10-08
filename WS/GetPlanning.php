<?php

if( (isset($_POST['uniqueIdentifier']) && $_POST['uniqueIdentifier'] != null) ){
	$uniqueIdentifier = $_POST['uniqueIdentifier'];

	include_once './scripts/fonctions.php';
	$db = new Fonctions();

	if($db->deviceExist($uniqueIdentifier)){
		if($db->deviceEnable($uniqueIdentifier)){
			echo $db->getPlanning($uniqueIdentifier);
		}
		else{
			$payload['AppUha'] = array(
								"resultCode" => "0",
								"resultHeader" => "GetPlanning",
								"resultTitle" => "Attention !",
								"resultString" => "vous n'êtes actuellement pas authentifié"
							);
			echo json_encode($payload);
		}
	}
	else{
		$payload['AppUha'] = array(
								"resultCode" => "0",
								"resultHeader" => "GetPlanning",
								"resultTitle" => "Attention !",
								"resultString" => "Pour pouvoir accedez aux plannings veuillez vous autentifier"
							);
		echo json_encode($payload);
	}

	$db->__destruct();
}
else{
	$payload['AppUha'] = array(
								"resultCode" => "0",
								"resultHeader" => "GetPlanning",
								"resultTitle" => "",
								"resultString" => "requette invalide missing values"
							);
	echo json_encode($payload);
}

?>
