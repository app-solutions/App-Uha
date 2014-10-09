#!/bin/bash
#codé par Clément MOULINE
#version 3.0


echo "####################################################################################################"
echo "telechargement du : "
date

# Téléchargement
/App-Uha/ade_iut_colmar.pl

# Conversion
java -jar /App-Uha/conv.jar

date
