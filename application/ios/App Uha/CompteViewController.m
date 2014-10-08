//
//  CompteViewController.m
//  App Uha
//
//  Created by Morgan Enderlin, Clément Mouline on 11/11/13.
//  Copyright (c) 2013 Morgan Enderlin, Clément Mouline, App-Solutions. All rights reserved.
//

#import "CompteViewController.h"
#import "Reachability.h"
#import "JsonReader.h"

@interface CompteViewController ()

@end

@implementation CompteViewController


- (void)viewDidLoad
{
    [scroller setScrollEnabled:YES];
    [scroller setContentSize:CGSizeMake(320, 650)];
    
    
    NSUserDefaults *settings = [NSUserDefaults standardUserDefaults];
    
    //genération de la liste des groupes
    
    groupeList = [[NSMutableArray alloc] init];
    
    [groupeList addObject:@""];
    
    // CJ
    [groupeList addObject:@"CJ11"];
    [groupeList addObject:@"CJ12"];
    [groupeList addObject:@"CJ13"];
    [groupeList addObject:@"CJ14"];
    [groupeList addObject:@"CJ21"];
    [groupeList addObject:@"CJ22"];
    [groupeList addObject:@"CJ23"];
    [groupeList addObject:@"CJ24"];
    [groupeList addObject:@"LP CASF"];
    [groupeList addObject:@"LP MI"];
    
    
    
    // GB
    [groupeList addObject:@"GB111"];
    [groupeList addObject:@"GB112"];
    [groupeList addObject:@"GB121"];
    [groupeList addObject:@"GB122"];
    [groupeList addObject:@"GB211"];
    [groupeList addObject:@"GB212"];
    [groupeList addObject:@"GB221"];
    [groupeList addObject:@"GB222"];
    [groupeList addObject:@"LP BIOTECH 1"];
    [groupeList addObject:@"LP BIOTECH 2"];
    
    // GTE
    [groupeList addObject:@"GTE11-A"];
    [groupeList addObject:@"GTE11-B"];
    [groupeList addObject:@"GTE21-A"];
    [groupeList addObject:@"GTE21-B"];
    [groupeList addObject:@"GTE1A-C"];
    [groupeList addObject:@"GTE2A-C"];
    [groupeList addObject:@"EN2D-A"];
    [groupeList addObject:@"EN2D-B"];
    
    
    // HSE
    [groupeList addObject:@"HSE111"];
    [groupeList addObject:@"HSE112"];
    [groupeList addObject:@"HSE121"];
    [groupeList addObject:@"HSE122"];
    [groupeList addObject:@"HSE131"];
    [groupeList addObject:@"HSE132"];
    [groupeList addObject:@"HSE211"];
    [groupeList addObject:@"HSE212"];
    [groupeList addObject:@"HSE221"];
    [groupeList addObject:@"HSE222"];
    [groupeList addObject:@"HSE231"];
    [groupeList addObject:@"HSE232"];
    [groupeList addObject:@"HSEA1"];
    [groupeList addObject:@"HSEA2"];
    [groupeList addObject:@"LP AQ APP"];
    [groupeList addObject:@"LP AQ 11"];
    [groupeList addObject:@"LP AQ 12"];
    
    
    // RT Power :D
    [groupeList addObject:@"RT111"];
    [groupeList addObject:@"RT112"];
    [groupeList addObject:@"RT121"];
    [groupeList addObject:@"RT122"];
    [groupeList addObject:@"RT211"];
    [groupeList addObject:@"RT212"];
    [groupeList addObject:@"RT221"];
    [groupeList addObject:@"RT222"];
    [groupeList addObject:@"RT11A"];
    [groupeList addObject:@"RT12A"];
    [groupeList addObject:@"RT21A"];
    [groupeList addObject:@"RT22A"];
    [groupeList addObject:@"LP ASUR 1"];
    [groupeList addObject:@"LP ASUR 2"];
    [groupeList addObject:@"LP ISVD 1"];
    [groupeList addObject:@"LP ISVD 2"];
    
    
    // "Fucking" TC
    [groupeList addObject:@"TC131"];
    [groupeList addObject:@"TC132"];
    [groupeList addObject:@"TC141"];
    [groupeList addObject:@"TC142"];
    [groupeList addObject:@"TC151"];
    [groupeList addObject:@"TC152"];
    [groupeList addObject:@"TC231"];
    [groupeList addObject:@"TC232"];
    [groupeList addObject:@"TC241"];
    [groupeList addObject:@"TC242"];
    [groupeList addObject:@"TC251"];
    [groupeList addObject:@"TC252"];
    [groupeList addObject:@"TC111A"];
    [groupeList addObject:@"TC112A"];
    [groupeList addObject:@"TC121A"];
    [groupeList addObject:@"TC122A"];
    [groupeList addObject:@"TC211A"];
    [groupeList addObject:@"TC212A"];
    [groupeList addObject:@"TC221A"];
    [groupeList addObject:@"TC222A"];
    
    [groupeList addObject:@"TC11TRI"];
    [groupeList addObject:@"TC12TRI"];
    [groupeList addObject:@"TC21TRI"];
    [groupeList addObject:@"TC22TRI"];
    
    [groupeList addObject:@"LP HT 1.1"];
    [groupeList addObject:@"LP HT 1.2"];
    [groupeList addObject:@"LP TECH2 G11"];
    [groupeList addObject:@"LP TECH2 G12"];
    [groupeList addObject:@"LP VINCOM"];
    
    
    
    firstNameField.text = [settings objectForKey:@"FirstNameFieldCompte"];
    nameField.text = [settings objectForKey:@"NameFieldCompte"];
    emailField.text = [settings objectForKey:@"EmailFieldCompte"];
    passwordField.text = [settings objectForKey:@"PasswordFieldCompte"];
    
    //definition du NSDictionary contenant les variables de Variables.plist
    variables = [NSDictionary dictionaryWithContentsOfFile:[[NSBundle mainBundle] pathForResource:@"Variables" ofType:@"plist"]];
    
    [super viewDidLoad];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
}




//methodes pickerView
- (NSInteger)numberOfComponentsInPickerView:(UIPickerView *)pickerView {
    return 1;
}

- (NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component {
    return [groupeList count];
}
- (NSString *)pickerView:(UIPickerView *)pickerView titleForRow:(NSInteger)row forComponent:(NSInteger)component {
    return [groupeList objectAtIndex:row];
}

-(void)pickerView:(UIPickerView *)pickerView didSelectRow:(NSInteger)row
      inComponent:(NSInteger)component{
    groupeSelected = [groupeList objectAtIndex:row];
}







- (IBAction)dismissKeyboard:(id)sender {
    
    [self resignFirstResponder];
    
}

- (IBAction)onTouchView:(id)sender {
    [self.view endEditing:YES];
}

- (IBAction)send:(id)sender {
    
    
    NSUserDefaults *settings = [NSUserDefaults standardUserDefaults];
    NSString * text = nil;
    
    
    if ([firstNameField.text isEqualToString:@""])
    {
        text = @"Il manque le Prénom";
        [[[UIAlertView alloc]initWithTitle:@"Attention !" message:text delegate:nil cancelButtonTitle:@"Ok" otherButtonTitles:nil]show];
    }
    else if ([nameField.text isEqualToString:@""]){
        
        text = @"Il manque le nom";
        [[[UIAlertView alloc]initWithTitle:@"Attention !" message:text delegate:nil cancelButtonTitle:@"Ok" otherButtonTitles:nil]show];
        
    }
    else if ([emailField.text isEqualToString:@""]){
        
        text = @"Il manque l'adresse mail";;
        [[[UIAlertView alloc]initWithTitle:@"Attention !" message:text delegate:nil cancelButtonTitle:@"Ok" otherButtonTitles:nil]show];
        
    }
    else if ([passwordField.text isEqualToString:@""]){
        
        text = @"Il manque le mot de passe";
        [[[UIAlertView alloc]initWithTitle:@"Attention !" message:text delegate:nil cancelButtonTitle:@"Ok" otherButtonTitles:nil]show];
        
    }
    else if ([groupeSelected isEqualToString:@""]){
        text = @"Il manque le groupe";
        [[[UIAlertView alloc]initWithTitle:@"Attention !" message:text delegate:nil cancelButtonTitle:@"Ok" otherButtonTitles:nil]show];
        
    }
    else if (groupeSelected == nil){
        
        text = @"Il manque le groupe";
        [[[UIAlertView alloc]initWithTitle:@"Attention !" message:text delegate:nil cancelButtonTitle:@"Ok" otherButtonTitles:nil]show];
        
    }
    else{
        
        if ([emailField.text rangeOfString:firstNameField.text].location == NSNotFound) {
            
            text = @"Le prénom ne correspond pas à celui dans l'adresse mail !";
            [[[UIAlertView alloc]initWithTitle:@"Attention !" message:text delegate:nil cancelButtonTitle:@"Ok" otherButtonTitles:nil]show];
            
        } else if ([emailField.text rangeOfString:nameField.text].location == NSNotFound){
            
            text = @"Le nom ne correspond pas à celui dans l'adresse mail !";
            [[[UIAlertView alloc]initWithTitle:@"Attention !" message:text delegate:nil cancelButtonTitle:@"Ok" otherButtonTitles:nil]show];
            
        } else if ([emailField.text rangeOfString:@"@uha.fr"].location == NSNotFound){
            
            text = @"L'adresse mail ne correspond pas à une adresse mail uha !";
            [[[UIAlertView alloc]initWithTitle:@"Attention !" message:text delegate:nil cancelButtonTitle:@"Ok" otherButtonTitles:nil]show];
            
        } else {
            
            //test de la connectivitée internet
            Reachability *reachability = [Reachability reachabilityForInternetConnection];
            NetworkStatus networkStatus = [reachability currentReachabilityStatus];
            if (networkStatus == NotReachable) {
                [[[UIAlertView alloc] initWithTitle:@"Attention !" message:@"Il est impossible de mettre votre profil à jour car vous n'êtes pas connecté à internet." delegate:self cancelButtonTitle:@"ok" otherButtonTitles:nil, nil] show];
                return;
            }
            
            
            //on informe l'utilisateur qu'il a cliqué sur le bouton
            UIAlertView *alerte = [[UIAlertView alloc] initWithTitle:@"Compte" message:@"Verification en cours" delegate:self cancelButtonTitle:nil otherButtonTitles:nil, nil];
            [alerte show];
            
            
            [settings setObject:firstNameField.text forKey:@"FirstNameFieldCompte"];
            [settings setObject:nameField.text forKey:@"NameFieldCompte"];
            [settings setObject:emailField.text forKey:@"EmailFieldCompte"];
            [settings setObject:passwordField.text forKey:@"PasswordFieldCompte"];
            
            NSString *uniqueIdentifier = [UIDevice currentDevice].identifierForVendor.UUIDString;
            
            //formation de la requette pour poster les eléments
            NSMutableURLRequest *request = [[NSMutableURLRequest alloc] initWithURL:[NSURL URLWithString:[variables objectForKey:@"srvUpdateUserAccount"]]];
            [request setHTTPMethod:@"POST"];
            NSString *request_body = [NSString stringWithFormat:@"prenom=%@&nom=%@&email=%@&password=%@&groupe=%@&uniqueIdentifier=%@", firstNameField.text, nameField.text, emailField.text, passwordField.text, groupeSelected, uniqueIdentifier];
            [request setHTTPBody:[request_body dataUsingEncoding:NSASCIIStringEncoding]];
            NSError *error = nil;
            NSURLResponse *response;
            NSData *theData = [NSURLConnection sendSynchronousRequest:request returningResponse:&response error:&error];
            
            
            JsonReader *jr = [[JsonReader alloc] initWithData:theData];
            
            if ([[jr getResultCode] isEqualToString:@"1"]) {
                
                NSMutableURLRequest *request_planning = [[NSMutableURLRequest alloc] initWithURL:[NSURL URLWithString:[variables objectForKey:@"srvGetPlanning"]]];
                [request_planning setHTTPMethod:@"POST"];
                NSString *request_body_planning = [NSString stringWithFormat:@"uniqueIdentifier=%@", uniqueIdentifier ];
                [request_planning setHTTPBody:[request_body_planning dataUsingEncoding:NSASCIIStringEncoding]];
                NSError *error_planning = nil;
                NSURLResponse *response_planning;
                NSData *data_planning = [NSURLConnection sendSynchronousRequest:request_planning returningResponse:&response_planning error:&error_planning ];
                
                NSString *encodage = [[NSString alloc] initWithData:data_planning encoding:NSASCIIStringEncoding];
                data_planning = [encodage dataUsingEncoding:NSUTF8StringEncoding];
                
                [settings setObject:data_planning forKey:@"GetPlanning"];
                
            }
            
            
            NSMutableURLRequest *request_user = [[NSMutableURLRequest alloc] initWithURL:[NSURL URLWithString:[variables objectForKey:@"srvUserInfos"]]];
            [request_user setHTTPMethod:@"POST"];
            NSString *request_body_user = [NSString stringWithFormat:@"uniqueIdentifier=%@", uniqueIdentifier ];
            [request_user setHTTPBody:[request_body_user dataUsingEncoding:NSASCIIStringEncoding]];
            NSError *error_user = nil;
            NSURLResponse *response_user;
            NSData *data_user = [NSURLConnection sendSynchronousRequest:request_user returningResponse:&response_user error:&error_user ];
            
            [settings setObject:data_user forKey:@"GetUserInfos"];
            
            
            [alerte dismissWithClickedButtonIndex:0 animated:YES];
            [[[UIAlertView alloc]initWithTitle:[jr getResultTitle] message:[jr getResultString] delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil]show];
            
        }
        
        
    }
    
    
}

@end
