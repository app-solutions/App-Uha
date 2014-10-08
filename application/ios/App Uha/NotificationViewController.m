//
//  NotificationViewController.m
//  App Uha
//
//  Created by Morgan Enderlin, Clément Mouline on 11/11/13.
//  Copyright (c) 2013 Morgan Enderlin, Clément Mouline, App-Solutions. All rights reserved.
//

#import "NotificationViewController.h"
#import "Reachability.h"
#import "JsonReader.h"

@interface NotificationViewController ()

@end

@implementation NotificationViewController

- (void)viewDidLoad
{
    
    //initialisation du picker
    chooseList = [[NSMutableArray alloc] init];
    [chooseList addObject:@""];
    [chooseList addObject:@"mon groupe"];
    [chooseList addObject:@"ma promotion"];
    
    //definition du NSDictionary contenant les variables de Variables.plist
    variables = [NSDictionary dictionaryWithContentsOfFile:[[NSBundle mainBundle] pathForResource:@"Variables" ofType:@"plist"]];
    
    [super viewDidLoad];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


//methodes pickerView
- (NSInteger)numberOfComponentsInPickerView:(UIPickerView *)pickerView {
    return 1;
}

- (NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component {
    return [chooseList count];
}
- (NSString *)pickerView:(UIPickerView *)pickerView titleForRow:(NSInteger)row forComponent:(NSInteger)component {
    return [chooseList objectAtIndex:row];
}

-(void)pickerView:(UIPickerView *)pickerView didSelectRow:(NSInteger)row inComponent:(NSInteger)component{
    selected = [chooseList objectAtIndex:row];
}



- (IBAction)dismissKeyboard:(id)sender {
    [self resignFirstResponder];
}

- (IBAction)onTouchView:(id)sender {
    [self.view endEditing:YES];
}





- (IBAction)send:(id)sender {
    
    
    //test de la connectivitée internet
    Reachability *reachability = [Reachability reachabilityForInternetConnection];
    NetworkStatus networkStatus = [reachability currentReachabilityStatus];
    if (networkStatus == NotReachable) {
        [[[UIAlertView alloc] initWithTitle:@"Attention !" message:@"Impossible d'envoyer votre notification car vous n'êtes pas connecté à internet." delegate:self cancelButtonTitle:@"ok" otherButtonTitles:nil, nil] show];
        return;
    }
    
    
    if ([textField.text isEqualToString:@""])
    {
        [[[UIAlertView alloc]initWithTitle:@"Attention !" message:@"Il n'y à pas de text !" delegate:nil cancelButtonTitle:@"ok" otherButtonTitles:nil]show];
        
    }
    else
    {
        
        if (selected == nil || [selected isEqualToString:@""]){
            [[[UIAlertView alloc]initWithTitle:@"Attention !" message:@"Vous n'avez pas choisis de type d'envoi" delegate:nil cancelButtonTitle:@"ok" otherButtonTitles:nil]show];
            return;
        }
        
        NSUserDefaults *settings = [NSUserDefaults standardUserDefaults];
        
        NSString *uniqueIdentifier = [UIDevice currentDevice].identifierForVendor.UUIDString;
        NSString *message = textField.text;
        textField.text = nil;
        
        NSMutableURLRequest *request = [[NSMutableURLRequest alloc] initWithURL:[NSURL URLWithString:[variables objectForKey:@"srvSendNotifications"]]];
        [request setHTTPMethod:@"POST"];
        NSString *request_body = [NSString stringWithFormat:@"uniqueIdentifier=%@&message=%@&type=%@", uniqueIdentifier, message, selected];
        [request setHTTPBody:[request_body dataUsingEncoding:NSUTF8StringEncoding]];
        NSError *error = nil;
        NSURLResponse *response;
        NSData *theData = [NSURLConnection sendSynchronousRequest:request returningResponse:&response error:&error];
        
        
        NSMutableURLRequest *request_user = [[NSMutableURLRequest alloc] initWithURL:[NSURL URLWithString:[variables objectForKey:@"srvUserInfos"]]];
        [request_user setHTTPMethod:@"POST"];
        NSString *request_body_user = [NSString stringWithFormat:@"uniqueIdentifier=%@", uniqueIdentifier ];
        [request_user setHTTPBody:[request_body_user dataUsingEncoding:NSASCIIStringEncoding]];
        NSError *error_user = nil;
        NSURLResponse *response_user;
        NSData *data_user = [NSURLConnection sendSynchronousRequest:request_user returningResponse:&response_user error:&error_user ];
        
        [settings setObject:data_user forKey:@"GetUserInfos"];
        
        
        
        JsonReader *jr = [[JsonReader alloc] initWithData:theData];
        
        //if ([[jr getResultCode] isEqualToString:@"0"]) {
            [[[UIAlertView alloc] initWithTitle:[jr getResultTitle] message:[jr getResultString] delegate:self cancelButtonTitle:@"ok" otherButtonTitles:nil, nil] show];
        //}
    
    }
    
}


@end
