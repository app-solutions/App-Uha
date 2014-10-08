#!/bin/bash
#codé par Clément MOULINE
#version 3.0


echo "####################################################################################################"
echo "telechargement du : "
date

# Téléchargement
/App-Uha/ade_CJ.pl | /App-Uha/ade_GB.pl | /App-Uha/ade_GTE.pl | /App-Uha/ade_HSE.pl | /App-Uha/ade_RT.pl | /App-Uha/ade_TC.pl

# Conversion
java -jar /App-Uha/conv.jar

date
