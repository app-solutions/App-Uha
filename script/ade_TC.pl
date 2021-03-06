#!/usr/bin/env perl

########################################################
#                                                      #
#     Script créer par Clément Mouline                 #
#     Script créer à partir de de ade2ics              #
#     Licence GPL 3.0                                  #
#     Version 1.0.2        	                       #
#     Téléchargement TC & LP TC                        #
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

#Definition des options
my $scriptType = "TC";

my $adeUrl = 'https://www.emploisdutemps.uha.fr/ade/';
my $adeProject = 'IUT Colmar 2014-2015';

my $login = ''; # set the username eg. jean.dupond@uha.fr
my $password = ''; # set the user's password

my @groupe = ( "TC131", "TC132", "TC141", "TC142", "TC151", "TC152", "TC231", "TC232", "TC241", "TC242", "TC251", "TC252", "TC111A","TC112A","TC121A","TC122A","TC211A","TC212A","TC221A","TC222A","TC11TRI" ,"TC12TRI" ,"TC21TRI" ,"TC22TRI", "LP TECH2 G11","LP TECH2 G12","LP VINCOM","LP HT 1.1","LP HT 1.2");
my @numero = ( "274",   "275",   "250",   "255",   "268",   "269",   "280",   "281",   "282",   "283",   "834",   "835",   "286",   "287",   "1185",  "1186",  "288",   "289",   "1195",  "1197",  "738",     "739",     "1276",    "1277",    "737",         "737",         "1048",     "727",      "1177");

# Web Browser Object creation
my $mech = WWW::Mechanize->new(agent => 'ADEics 0.2', cookie_jar => {});


# STEP 1 - CAS server connexion for authenticating
$mech->get($adeUrl . 'standard/index.jsp');
die "Error 1 : Unable to connect to CAS server (Script " . $scriptType . ")." if (!$mech->success());

# STEP 2 - Submit fields for authenticating
$mech->submit_form(fields => {username => $login, password => $password});
die "Error 2 : Error when authenticating (Script " . $scriptType . ")." if (!$mech->success());

# STEP 3 - Project selection
$mech->get($adeUrl . 'standard/projects.jsp');
die "Error 3 : Error when project selection (Script " . $scriptType . ")." if (!$mech->success());

my $p = HTML::TokeParser->new(\$mech->content);
my $token = $p->get_tag("select");

my $projid = -1;
my %availableproject;
while (($projid == -1) && (my $token = $p->get_tag("option"))) {
	my $pname = $p->get_trimmed_text;
	if($pname eq $adeProject) {
		$projid = $token->[1]{value};
	}
	$availableproject{$pname} = 1;
}

$mech->submit_form(fields => {projectId => $projid});


my $temp = 0;

foreach my $numberGroupe (@numero){
	
	download($numberGroupe);

	# Parsing HTML to ASCF (App-Solutions Calendar Format)
	$p = HTML::TokeParser->new(\$mech->content);
	my $ascfFile;

	$ascfFile = $ascfFile . "BEGIN:APP-UHA\n";
	$ascfFile = $ascfFile . "VERSION:ADE-1.0\n\n";
	$ascfFile = $ascfFile . ascf_output($mech->content, $1);
	$ascfFile = $ascfFile . "END:APP-UHA\n";

	open(FILE,">/App-Uha/ASCF/" . $groupe[$temp] . ".ascf") or die "Is the path existing ?";
	binmode(FILE, ":utf8");	
	print FILE $ascfFile;
	close(FILE);
	
	$temp = $temp + 1;
}



# Downloading plannings
sub download {

	# STEP 4 - Group selection ($_[0] represente la 1er variable passé dans la methode)
	$mech->get($adeUrl . 'custom/modules/plannings/direct_planning.jsp?resources='. $_[0]);
	die "Error 4 : Unable to select a group (Script " . $scriptType . ")." if (!$mech->success());


	# STEP 5 - All weeks selection
	$mech->get($adeUrl . 'custom/modules/plannings/pianoWeeks.jsp?searchWeeks=all');
	die "Error 5 : Unable to select all weeks class (Script " . $scriptType . ")." if (!$mech->success());

	# STEP 6 - Display configuration load
	$mech->get($adeUrl . 'custom/modules/plannings/appletparams.jsp');
	die "Error 6 : Unable to load display configuration (Script " . $scriptType . ")." if (!$mech->success());


	$mech->field("showTabActivity", "true");	# Nom
	$mech->field("showTabWeek", "false");		# Semaine
	$mech->field("showTabDay", "false");		# Jour
	$mech->field("showTabStage", "false");		# Stage
	$mech->field("showTabDate", "true");		# Date
	$mech->field("showTabHour", "true");		# Heure
	$mech->field("aC", "false");			# Code
	$mech->field("aTy", "false");			# Type
	$mech->field("aUrl", "false");			# Url
	$mech->field("showTabDuration", "true");	# Durée
	$mech->field("aSize", "false");			# Capacité
	$mech->field("aMx", "false");			# Nombre de siøges
	$mech->field("aSl", "false");			# Siøges disponibles
	$mech->field("aCx", "false");			# Code X
	$mech->field("aCy", "false");			# Code Y
	$mech->field("aCz", "false");			# Code Z
	$mech->field("aTz", "false");			# Fuseau horaire
	$mech->field("aN", "false");			# Notes
	$mech->field("aNe", "false");			# Note de séance

	# Students
	$mech->field("showTabTrainees", "true");	# Nom
	$mech->field("sC", "false");
	$mech->field("sTy", "false");
	$mech->field("sUrl", "false");
	$mech->field("sE", "false");
	$mech->field("sM", "false");
	$mech->field("sJ", "false");
	$mech->field("sA1", "false");
	$mech->field("sA2", "false");
	$mech->field("sZp", "false");
	$mech->field("sCi", "false");
	$mech->field("sSt", "false");
	$mech->field("sCt", "false");
	$mech->field("sT", "false");
	$mech->field("sF", "false");
	$mech->field("sCx", "false");
	$mech->field("sCy", "false");
	$mech->field("sCz", "false");
	$mech->field("sTz", "false");

	# Instructors
	$mech->field("showTabInstructors", "true");	# Nom
	$mech->field("iC", "false");
	$mech->field("iTy", "false");
	$mech->field("iUrl", "false");
	$mech->field("iE", "false");
	$mech->field("iM", "false");
	$mech->field("iJ", "false");
	$mech->field("iA1", "false");
	$mech->field("iA2", "false");
	$mech->field("iZp", "false");
	$mech->field("iCi", "false");
	$mech->field("iSt", "false");
	$mech->field("iCt", "false");
	$mech->field("iT", "false");
	$mech->field("iF", "false");
	$mech->field("iCx", "false");
	$mech->field("iCy", "false");
	$mech->field("iCz", "false");
	$mech->field("iTz", "false");

	# Rooms
	$mech->field("showTabRooms", "true");	# Nom
	$mech->field("roC", "false");
	$mech->field("roTy", "false");
	$mech->field("roUrl", "false");
	$mech->field("roE", "false");
	$mech->field("roM", "false");
	$mech->field("roJ", "false");
	$mech->field("roA1", "false");
	$mech->field("roA2", "false");
	$mech->field("roZp", "false");
	$mech->field("roCi", "false");
	$mech->field("roSt", "false");
	$mech->field("roCt", "false");
	$mech->field("roT", "false");
	$mech->field("roF", "false");
	$mech->field("roCx", "false");
	$mech->field("roCy", "false");
	$mech->field("roCz", "false");
	$mech->field("roTz", "false");

	# Resources
	$mech->field("showTabResources", "true");
	$mech->field("reC", "false");
	$mech->field("reTy", "false");
	$mech->field("reUrl", "false");
	$mech->field("reE", "false");
	$mech->field("reM", "false");
	$mech->field("reJ", "false");
	$mech->field("reA1", "false");
	$mech->field("reA2", "false");
	$mech->field("reZp", "false");
	$mech->field("reCi", "false");
	$mech->field("reSt", "false");
	$mech->field("reCt", "false");
	$mech->field("reT", "false");
	$mech->field("reF", "false");
	$mech->field("reCx", "false");
	$mech->field("reCy", "false");
	$mech->field("reCz", "false");
	$mech->field("reTz", "false");


	# STEP 7 - Send configuration
	$mech->submit_form();
	die "Error 7 : Unable to send display configuration (Script " . $scriptType . ")." if (!$mech->success());

	# STEP 8 - planning constumisation
	$mech->get($adeUrl . 'custom/modules/plannings/info.jsp?order=slot&light=true');
	die "Error 8 : Unable to get costumized plannings (Script " . $scriptType . ")." if (!$mech->success());

}



# HTML TO ASCF
sub ascf_output {

	my $final;

	my $data = $_[0];
	my $p = HTML::TokeParser->new(\$data);
	
	$token = $p->get_tag("table");
	$token = $p->get_tag("tr");
	$token = $p->get_tag("tr");

	while ($token = $p->get_tag("tr")) {
		my $date;
		my $id;
		my $course;
		my $hour;
		my $duration;
		my $trainers;
		my $trainees;
		my $rooms;
		my $equipment;
		my $statuts;
		my $groupes;
		my $module;
		my $formation_UV;

		#######################################
		# This part is not generic enough to work well with all installation but has been largy improved in version 3.2
		#######################################

		# showTabDate
		$token = $p->get_tag("span");
		$date = $p->get_trimmed_text; # 12/05/2006

		# showTabActivity
		$token = $p->get_tag("a");
		$id = $token->[1]{href};
		$id =~ /\((\d+)\)/;
		$id = $1;
		$course = $p->get_trimmed_text; # INF 423 Cours 1 et 2

		# showTabHour
		$token = $p->get_tag("td");
		$hour = $p->get_trimmed_text; # 13h30 | 15:30

		# showTabDuration
		$token = $p->get_tag("td");
		$duration = $p->get_trimmed_text; # 2h50min | 2h | 50min

		# showTabTrainees
		$token = $p->get_tag("td");
		$trainees = $p->get_text('td'); #

		# showTabInstructors
		$token = $p->get_tag("td");
		$trainers = $p->get_text('td'); # LEROUX Camille

		# showTabRooms
		$token = $p->get_tag("td");
		$rooms = $p->get_text('td'); # B03-132A

		# showTabResources
		$token = $p->get_tag("td");
		$equipment = $p->get_text('td');

		# showTabCategory5
		$token = $p->get_tag("td");
		$statuts = $p->get_text('td'); # Validé

		# showTabCategory6
		$token = $p->get_tag("td");
		$groupes = $p->get_text('td'); # Groupe UV2 MAJ INF 423

		# showTabCategory7
		$token = $p->get_tag("td");
		$module = $p->get_text('td'); # FIP ELP103 Electronique numerique : Logique combinatoire

		# showTabCategory8
		$token = $p->get_tag("td");
		$formation_UV = $p->get_trimmed_text; # Enseignements INF S3 UV2 MAJ INF Automne Majeure INF UV2
		
		#######################################
		if(0) { #used for debug
		print "Date:		$date\n";
		print "Id:		$id\n";
		print "course:		$course\n";
		print "hour:		$hour\n";
		print "duration:	$duration\n";
		print "trainers:	$trainers\n";
		print "trainees:	$trainees\n";
		print "rooms:		$rooms\n";
		print "equipment:	$equipment\n";
		print "statuts:		$statuts\n";
		print "groupes:	$groupes\n";
		print "module:		$module\n";
		print "formation_UV:	$formation_UV\n";
		print "\n";
		next;
		}
		#######################################
		
		$date =~ m|(\d+)/(\d+)/(\d+)|;
		my $ics_day = sprintf("%02d-%02d-%02d--",$1,$2,$3);
		$hour =~ m|(\d+)[h:](\d+)|;
		
		my $ics_start_hour = $1;
		my $ics_start_minute = $2;
		my $ics_start_date = $ics_day.sprintf("%02dH%02d",$1,$2);

		my $ics_duration_hours;
		my $ics_duration_minutes;
		my $ics_stop_date;
		my $ics_duration;
		
		if ($duration =~ m|^(\d+)h(\d+)|) {
			$ics_duration_hours = $1;
			$ics_duration_minutes = $2;
		} elsif ($duration =~ m|^(\d+)h|) {
			$ics_duration_hours = $1;
			$ics_duration_minutes = 0;
		} elsif ($duration =~ m|^(\d+)m|) {
			$ics_duration_hours = 0;
			$ics_duration_minutes = $1;
		} else {
			die "Error 14 : date $duration can't be parsed";
		}
	
		my $ics_end_hours = $ics_start_hour+$ics_duration_hours;
		my $ics_end_minutes = $ics_start_minute+$ics_duration_minutes;

		while ($ics_end_minutes >= 60) {
			$ics_end_minutes -= 60;
			$ics_end_hours += 1;
		}
	
		$ics_stop_date = $ics_day.sprintf('%02dH%02d',$ics_end_hours, $ics_end_minutes);
		$ics_duration = "".sprintf('%02d', $ics_duration_hours)."H".sprintf('%02d', $ics_duration_minutes);

		my ($tssec,$tsmin,$tshour,$tsmday,$tsmon,$tsyear,$tswday,$tsyday,$tsisdst) = gmtime();
		my $dtstamp = sprintf("%02d-%02d-%02d--%02dH%02d", $tsmday, $tsmon + 1, $tsyear+1900, $tshour, $tsmin);

		$final = $final . "BEGIN:COURS-$ics_day\n";
		$final = $final . "ID:$id\n";
		$final = $final . "TIMESTART:$ics_start_date\n";
		$final = $final . "TIMESTOP:$ics_stop_date\n";
		$final = $final . "TIMELASTMODIFIED:$dtstamp\n";
		$final = $final . "DUREE:$ics_duration\n";
		$final = $final . "NOM:$course\n";		
		$final = $final . "SALLE:$rooms\n";
		$final = $final . "ENSEIGNANTS:$trainers\n";
		$final = $final . "GROUPE:$trainees\n";
		$final = $final . "MODULE:$module\n";
		$final = $final . "FORMATIONS:$formation_UV\n";
		$final = $final . "EQUIPEMENTS:$equipment\n";
		$final = $final . "STATUTS:$statuts\n";
		$final = $final . "END:COURS\n";
		$final = $final . "\n"
	}
	return $final;
}

__END__

=head1 TITLE

App-Uha ADE TC download

=head1 VERSION

Version 1.0.2

=head1 DATE

13/03/2014

=head1 AUTHOR

Kubrick <clement.mouline@app-solutions.fr>

=head1 INFORMATIONS

App-Uha ADE download est un script permetant de télécharger les emploi du temps de vos sections sur toute l'année.
