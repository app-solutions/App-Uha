#Script Journalier MySQL
#Version 1.0


#mise en minuscule des prenom et nom des utilisateurs
UPDATE users SET nom = lower(nom);
UPDATE users SET prenom = lower(prenom);

#suppression des devices sans utilisateur
DELETE FROM devices WHERE owner is null;
