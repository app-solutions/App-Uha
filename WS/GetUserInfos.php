<?php

if( (isset($_POST['uniqueIdentifier']) && $_POST['uniqueIdentifier'] != null) ){

	$uniqueIdentifier = $_POST['uniqueIdentifier'];

	include_once './scripts/fonctions.php';
	$db = new Fonctions();

	if($db->deviceExist($uniqueIdentifier)){
		if($db->deviceEnable($uniqueIdentifier)){
			if($db->ownerExist($uniqueIdentifier)){
				$infos = $db->getUserInfos($uniqueIdentifier);

				$payload['AppUha'] = array(
										"resultCode" => "1",
										"resultHeader" => "GetUSerInfos",
										"resultTitle" => "",
										"resultString" => "",

										"infos" => array(
													"prenom" => $infos['prenom'],
													"nom" => $infos['nom'],
													"email" => $infos['email'],
													"groupe" =>$infos['groupe'],
													"notifs" =>$infos['notifs'],
												)

									);
				echo json_encode($payload);

			}
			else{
				$payload['AppUha'] = array(
										"resultCode" => "0",
										"resultHeader" => "GetUSerInfos",
										"resultTitle" => "App-uha",
										"resultString" => "Bonjour, pour commencer à utiliser l'application veuillez vous authentifier dans l'onglet compte"
									);
				echo json_encode($payload);
			}
		}
		else{
			$payload['AppUha'] = array(
									"resultCode" => "0",
									"resultHeader" => "GetUSerInfos",
									"resultTitle" => "App-uha",
									"resultString" => "Vous n'êtes actuellement pas authentifié, veuillez vous authentifier dans l'onglet compte"
								);
			echo json_encode($payload);
		}
	}
	else{
		$payload['AppUha'] = array(
									"resultCode" => "0",
									"resultHeader" => "GetUSerInfos",
									"resultTitle" => "App-uha",
									"resultString" => "Bonjour, pour commencer à utiliser l'application veuillez vous authentifier dans l'onglet compte"
								);
		echo json_encode($payload);
	}

	$db->__destruct();

}
else{
	$payload['AppUha'] = array(
								"resultCode" => "0",
								"resultHeader" => "UpdateUserAccount",
								"resultTitle" => "",
								"resultString" => "invalid request, missing values"
							);
	echo json_encode($payload);
}

?>
