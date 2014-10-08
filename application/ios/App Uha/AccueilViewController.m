//
//  AccueilViewController.m
//  App Uha
//
//  Created by Clément Mouline on 11/11/13.
//  Copyright (c) 2013 Clément Mouline, App-Solutions. All rights reserved.
//

#import "AccueilViewController.h"
#import "JsonReader.h"
#import "Reachability.h"

@interface AccueilViewController ()

@end

@implementation AccueilViewController


//CETTE METHODES EST APPELEE LA PREMIERE FOIS QUE L'ON DEMANDE LA VUE
- (void)viewDidLoad
{
    //test de la connectivitée internet
    Reachability *reachability = [Reachability reachabilityForInternetConnection];
    NetworkStatus networkStatus = [reachability currentReachabilityStatus];
    if (networkStatus == NotReachable) {
        [[[UIAlertView alloc] initWithTitle:@"Attention !" message:@"aucune connection à internet" delegate:self cancelButtonTitle:@"ok" otherButtonTitles:nil, nil] show];
    }
    
    [super viewDidLoad];
}


//CETTE METHODE EST APPELEE A CHAQUE FOIS QUE LA VUE VA S'AFFICHIER
-(void)viewWillAppear:(BOOL)animated{
    
    //on recupere les données du service GetUserInfos et on les place dans un JsonReader
    NSUserDefaults *settings = [NSUserDefaults standardUserDefaults];
    JsonReader *jr = [[JsonReader alloc] initWithData:[settings objectForKey:@"GetUserInfos"]];
    
    if ([[jr getResultCode] isEqualToString:@"1"]) {
        
        //definition des textField de l'accueil.
        name.text = [NSString stringWithFormat:@"Bonjour %@", [jr getUserFirstName]];
        email.text = [NSString stringWithFormat:@"%@",[jr getUserEmail]];
        groupe.text = [NSString stringWithFormat:@"%@", [jr getUserGroupe]];
        notification.text = [NSString stringWithFormat:@"Il vous reste %@ notification(s) à envoyer cette semaine", [jr getUserNotifs]];
        
        
        //definition de l'image en fonction du groupe
        if ([[jr getUserGroupe] rangeOfString:@"CJ"].location != NSNotFound || [[jr getUserGroupe] rangeOfString:@"LP MI"].location != NSNotFound || [[jr getUserGroupe] rangeOfString:@"LP CASF"].location != NSNotFound ) {
            [image setImage:[UIImage imageNamed:@"IconCJ"]];
        }
        else if ([[jr getUserGroupe] rangeOfString:@"GB"].location != NSNotFound || [[jr getUserGroupe] rangeOfString:@"LP BIOTECH"].location != NSNotFound){
            [image setImage:[UIImage imageNamed:@"IconGB"]];
        }
        else if([[jr getUserGroupe] rangeOfString:@"GTE"].location != NSNotFound){
            [image setImage:[UIImage imageNamed:@"IconGTE"]];
        }
        else if([[jr getUserGroupe] rangeOfString:@"HSE"].location != NSNotFound || [[jr getUserGroupe] rangeOfString:@"LP AQ"].location != NSNotFound){
            [image setImage:[UIImage imageNamed:@"IconHSE"]];
        }
        else if([[jr getUserGroupe] rangeOfString:@"RT"].location != NSNotFound || [[jr getUserGroupe] rangeOfString:@"LP ISVD"].location != NSNotFound || [[jr getUserGroupe] rangeOfString:@"LP ASUR"].location != NSNotFound ){
            [image setImage:[UIImage imageNamed:@"IconRT"]];
        }
        else if([[jr getUserGroupe] rangeOfString:@"TC"].location != NSNotFound || [[jr getUserGroupe] rangeOfString:@"LP TECH2"].location != NSNotFound || [[jr getUserGroupe] rangeOfString:@"LP VINCOM"].location != NSNotFound || [[jr getUserGroupe] rangeOfString:@"LP HT"].location != NSNotFound){
            [image setImage:[UIImage imageNamed:@"IconTC"]];
        }
        else{
            [image setImage:[UIImage imageNamed:@"IconDefault"]];
        }
        
    }
    else if([[jr getResultCode] isEqualToString:@"0"]){
        
        image.image = nil;
        name.text = @"";
        email.text = @"";
        groupe.text = @"";
        notification.text = @"";
        
        UIAlertView *alerte = [[UIAlertView alloc] initWithTitle:[jr getResultTitle] message:[jr getResultString] delegate:self cancelButtonTitle:@"ok" otherButtonTitles:nil, nil];
        [alerte show];
        
    }
    
    [self.view setNeedsDisplay];
    
}


- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
}

@end
