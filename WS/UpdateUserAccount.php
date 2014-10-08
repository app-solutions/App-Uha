<?php
if( (isset($_POST['prenom']) && $_POST['prenom'] != null) && (isset($_POST['nom']) && $_POST['nom'] != null) && (isset($_POST['email']) && $_POST['email'] != null) && (isset($_POST['groupe']) && $_POST['groupe'] != null) && (isset($_POST['password']) && $_POST['password'] != null) && (isset($_POST['uniqueIdentifier']) && $_POST['uniqueIdentifier'] != null) ){

	$prenom = $_POST['prenom'];
	$nom = $_POST['nom'];
	$email = $_POST['email'];
	$groupe = $_POST['groupe'];
	$password = $_POST['password'];
	$uniqueIdentifier = $_POST['uniqueIdentifier'];

	include_once './scripts/fonctions.php';
	$db = new Fonctions();

	if($db->deviceExist($uniqueIdentifier)){
		if(!$db->userExclude($email)){
			if(!$db->userExist($email)){
				if($db->userLogged($email, $password)){

					$db->addUser($prenom, $nom, $email, $groupe);
					$db->updateDeviceOwnerTrue($uniqueIdentifier, $email, $groupe);

					$payload['AppUha'] = array(
										"resultCode" => "1",
										"resultHeader" => "UpdateUserAccount",
										"resultTitle" => "compte",
										"resultString" => "Bienvenue ". $prenom ." ! Vous êtes maintenant enregistré(e)"
									);
					echo json_encode($payload);

				}
				else{
					$db->updateDeviceOwnerFalse($uniqueIdentifier, $email, $groupe);

					$payload['AppUha'] = array(
										"resultCode" => "0",
										"resultHeader" => "UpdateUserAccount",
										"resultTitle" => "compte",
										"resultString" => "Votre combinaison email/password est incorrecte"
									);
					echo json_encode($payload);
				}
			}
			else{
				if($db->userLogged($email, $password)){
					$db->updateDeviceOwnerTrue($uniqueIdentifier, $email, $groupe);

					$payload['AppUha'] = array(
										"resultCode" => "1",
										"resultHeader" => "UpdateUserAccount",
										"resultTitle" => "compte",
										"resultString" => "Votre compte a été modifié avec succès"
									);
					echo json_encode($payload);
				}
				else{
					$db->updateDeviceOwnerFalse($uniqueIdentifier, $email, $groupe);

					$payload['AppUha'] = array(
										"resultCode" => "0",
										"resultHeader" => "UpdateUserAccount",
										"resultTitle" => "compte",
										"resultString" => "Votre combinaison email/password est incorrecte"
									);
					echo json_encode($payload);
				}
			}
		}
		else{
			$payload['AppUha'] = array(
									"resultCode" => "0",
									"resultHeader" => "UpdateUserAccount",
									"resultTitle" => "utilisateur Banni",
									"resultString" => $db->userExcludeReason($email)
								);
			echo json_encode($payload);
		}
		
	}
	else{
		$payload['AppUha'] = array(
									"resultCode" => "0",
									"resultHeader" => "UpdateUserAccount",
									"resultTitle" => "Erreur fatale",
									"resultString" => "Une erreur c'est produite, relancez l'application"
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
