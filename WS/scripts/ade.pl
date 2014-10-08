#!/usr/bin/env perl

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

my %opts;
my @tree;

# Output to UTF-8
binmode(STDOUT, ":encoding(UTF-8)");

GetOptions(\%opts, 'l=s', 'p:s');


my $mech = WWW::Mechanize->new(agent => 'ADEics 0.2', cookie_jar => {});

# login in
$mech->get('https://cas.uha.fr/cas');
$mech->submit_form(fields => {username => $opts{'l'}, password => $opts{'p'}});
print $mech->content;
