<?php

// DATABASE INFORMATION
define("HOST", "localhost");
define("USER", ""); // user (app-uha)
define("PASSWORD", ""); // user password
define("DATABASE", ""); // database name (app-uha)

//CLEF SERVICE
define("SERVICE_KEY", "");


//CLEF GOOGLE NOTIFICATIONS PUSH
define("GOOGLE_API_KEY", ""); // Google api key, used for push notifications


//CERTIFICATS APPLE NOTIFICATIONS PUSH

//define("APNS_SRV", "gateway.sandbox.push.apple.com"); // Apple Push Sotifications Services gateway for developement
define("APNS_SRV", "gateway.push.apple.com"); // Apple Push Notyifications Services gateway for production

//define("APPLE_PEM", "./certificate/notifications_dev.pem"); // Apple certificate location for developement
define("APPLE_PEM", "./certificate/notifications_prod.pem"); // Apple certificate location for production




//SCRIPT PERL D'AUTHENTIFICATION
define("LOC_PERL", "./scripts/ade.pl");

?>
