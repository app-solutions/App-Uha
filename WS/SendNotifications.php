<?php
if( (isset($_POST['uniqueIdentifier']) && $_POST['uniqueIdentifier'] != null) && (isset($_POST['message']) && $_POST['message'] != null) && (isset($_POST['type']) && $_POST['type'] != null) ){

	$uniqueIdentifier = $_POST['uniqueIdentifier'];
	$message = $_POST['message'];
	$type = $_POST['type'];

	include_once './scripts/fonctions.php';
	$db = new Fonctions();

	if($db->deviceExist($uniqueIdentifier)){
		if($db->ownerExist($uniqueIdentifier)){
			if($db->deviceEnable($uniqueIdentifier)){
				if($db->userEnableNotifications($uniqueIdentifier)){

					$db->sendNotifications($uniqueIdentifier, $message, $type);

					$payload['AppUha'] = array(
							"resultCode" => "1",
							"resultHeader" => "SendMotifications",
							"resultTitle" => "Notifications",
							"resultString" => "votre notification a bien été envoyée"
						);
					echo json_encode($payload);
				}
				else{
					$payload['AppUha'] = array(
							"resultCode" => "0",
							"resultHeader" => "SendMotifications",
							"resultTitle" => "Attentions !",
							"resultString" => "Votre quota de notifications hebdomadaire est dépassé"
						);
					echo json_encode($payload);
				}
			}
			else{
				$payload['AppUha'] = array(
								"resultCode" => "0",
								"resultHeader" => "SendMotifications",
								"resultTitle" => "Attention !",
								"resultString" => "Pour pouvoir utiliser le service de notifications, vous devez être authentifié"
							);
				echo json_encode($payload);
			}
		}
		else{
			$payload['AppUha'] = array(
								"resultCode" => "0",
								"resultHeader" => "SendMotifications",
								"resultTitle" => "Attention !",
								"resultString" => "Pour pouvoir utiliser le service de notifications, vous devez être authentifié"
							);
			echo json_encode($payload);
		}
	}
	else{
		$payload['AppUha'] = array(
									"resultCode" => "0",
									"resultHeader" => "SendMotifications",
									"resultTitle" => "Erreur fatale",
									"resultString" => "Une erreur c'est produite, relancez l'appilcation"
								);
		echo json_encode($payload);
	}

	$db->__destruct();

}
else{
	$payload['AppUha'] = array(
									"resultCode" => "0",
									"resultHeader" => "SendMotifications",
									"resultTitle" => "",
									"resultString" => "invalid request, missing values"
								);
	echo json_encode($payload);
}


?>
