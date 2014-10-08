#Script Mensule MySQL
#Version 1.0

#Supression de utilisateurs n ayant pas utilisé l application ce mois
DELETE FROM devices WHERE lastUse = "";

#remise à zéro des lasUse
UPDATE devices SET lastUse = "";
