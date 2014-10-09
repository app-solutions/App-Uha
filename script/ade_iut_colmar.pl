#!/usr/bin/env perl

########################################################
#                                                      #
#     Script créer par Clément Mouline                 #
#     Script créer à partir de de ade2ics              #
#     Licence GPL 3.0                                  #
#     Version 1.0.2       	                       #
#     Téléchargement CJ & LP CJ                        #
#                                                      #
########################################################


use strict;
use warnings;

use WWW::Mechanize;
use HTTP::Cookies;
use HTML::TokeParser;
use Term::ReadKey;
use Getopt::Long;
use Time::Local;
use POSIX;
use CGI qw(param header);

binmode(STDOUT, ":utf8");

# Options
my $scriptType = "IUT COLMAR";

my $adeUrl = 'https://www.emploisdutemps.uha.fr/ade/';
my $adeProject = 'IUT Colmar 2014-2015';

my $login = ''; # set the username eg. jean.dupond@uha.fr
my $password = ''; # set the user's password

# Departements Definitions

#CJ
my @groupeCJ = ( "CJ11", "CJ12", "CJ13", "CJ14", "CJ21", "CJ22", "CJ23", "CJ24", "CJ1A", "CJ2A", "LP MI", "LP CASF");
my @numeroCJ = ( "791",  "792",  "793",  "794",  "796",  "797",  "798",  "799",  "1056", "1239", "142",   "920");

#GB
my @groupeGB = ( "GB111", "GB112", "GB121", "GB122", "GB211", "GB212", "GB221", "GB222", "LP BIOTECH 1", "LP BIOTECH 2");
my @numeroGB = ( "147",   "148",   "152",   "153",   "160",   "161",   "158",   "159",   "622",          "386");

#GTE
my @groupeGTE = ( "GTE11-A", "GTE11-B", "GTE21-A", "GTE21-B", "GTE1A-C", "GTE2A-C", "EN2D-A", "EN2D-B");
my @numeroGTE = ( "1221",    "1222",    "86",      "87",      "60",      "756",     "110",    "238");

#HSE
my @groupeHSE = ( "HSE111", "HSE112", "HSE121", "HSE122", "HSE131", "HSE132", "HSE211", "HSE212", "HSE221", "HSE222", "HSE231", "HSE232", "HSEA1", "HSEA2", "LP AQ APP", "LP AQ 11", "LP AQ 12");
my @numeroHSE = ( "173",    "174",    "175",    "176",    "177",    "178",    "179",    "180",    "181",    "182",    "183",    "184",    "857",   "535",   "1015",      "265",      "266"  );

#RT
my @groupeRT = ( "RT111", "RT112", "RT121", "RT122", "RT211", "RT212", "RT221", "RT222", "RT11A", "RT12A", "RT21A","RT22A", "LP ISVD 1", "LP ISVD 2", "LP ASUR 1", "LP ASUR 2");
my @numeroRT = ( "205",   "206",   "203",   "204",   "214",   "215",   "212",   "213",   "218",   "219",   "221",  "222",   "119",       "209",       "746",       "747"  );

#TC
my @groupe = ( "TC131", "TC132", "TC141", "TC142", "TC151", "TC152", "TC231", "TC232", "TC241", "TC242", "TC251", "TC252", "TC111A","TC112A","TC121A","TC122A","TC211A","TC212A","TC221A","TC222A","TC11TRI" ,"TC12TRI" ,"TC21TRI" ,"TC22TRI", "LP TECH2 G11","LP TECH2 G12","LP VINCOM","LP HT 1.1","LP HT 1.2");
my @numero = ( "274",   "275",   "250",   "255",   "268",   "269",   "280",   "281",   "282",   "283",   "834",   "835",   "286",   "287",   "1185",  "1186",  "288",   "289",   "1195",  "1197",  "738",     "739",     "1276",    "1277",    "737",         "737",         "1048",     "727",      "1177");


