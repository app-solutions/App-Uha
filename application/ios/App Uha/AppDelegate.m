//
//  AppDelegate.m
//  App Uha
//
//  Created by Clément Mouline on 11/11/13.
//  Copyright (c) 2013 Clément Mouline, App-Solutions. All rights reserved.
//

#import "AppDelegate.h"
#import "Reachability.h"


@implementation AppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    
    //INITIALISATION DE L'APPLICATION
    
    //cette methode enleve le badge qui pourrai y avoir sur l'icon de l'application
    [UIApplication sharedApplication].applicationIconBadgeNumber = 0 ;
    
    //definition du NSDictionary contenant les variables de Variables.plist
    variables = [NSDictionary dictionaryWithContentsOfFile:[[NSBundle mainBundle] pathForResource:@"Variables" ofType:@"plist"]];
    
    //test de la connectivitée internet
    Reachability *reachability = [Reachability reachabilityForInternetConnection];
    NetworkStatus networkStatus = [reachability currentReachabilityStatus];
    if (networkStatus == NotReachable) {
        return YES;
    }
    
    
    
    //LES METHODES CI-DESSOUS SONT DEPENDANTES D'INTERNET NE PAS ECRIRE DE METHODES UTILISANT INTERNET AVANT CE POINT
    
    //definition de l'uniqueIdentifier
    uniqueIdentifier = [UIDevice currentDevice].identifierForVendor.UUIDString;
    
    //gestion des notification, on autorise le telephone à recevoir des notifications et on l'enregistre sur le serveur APNS
    [[UIApplication sharedApplication] registerForRemoteNotificationTypes:(UIRemoteNotificationTypeAlert | UIRemoteNotificationTypeBadge | UIRemoteNotificationTypeSound)];
    
    //service de stockage
    NSUserDefaults *settings = [NSUserDefaults standardUserDefaults];
    
    //telechargement des informations utilisateur;
    NSMutableURLRequest *request_user = [[NSMutableURLRequest alloc] initWithURL:[NSURL URLWithString:[variables objectForKey:@"srvUserInfos"]]];
    [request_user setHTTPMethod:@"POST"];
    NSString *request_body_user = [NSString stringWithFormat:@"uniqueIdentifier=%@", uniqueIdentifier ];
    [request_user setHTTPBody:[request_body_user dataUsingEncoding:NSASCIIStringEncoding]];
    NSError *error_user = nil;
    NSURLResponse *response_user;
    NSData *data_user = [NSURLConnection sendSynchronousRequest:request_user returningResponse:&response_user error:&error_user ];
    
    [settings setObject:data_user forKey:@"GetUserInfos"];
    
    
    //téléchargement des plannings
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
    
    NSDictionary *toto = [launchOptions objectForKey:UIApplicationLaunchOptionsRemoteNotificationKey];
    if (toto != nil) {
        [[[UIAlertView alloc] initWithTitle:@"test" message:[[[toto valueForKey:@"aps"] valueForKey:@"alert"] valueForKey:@"body"] delegate:self cancelButtonTitle:@"ok" otherButtonTitles:nil, nil] show];
    }
    
    return TRUE;
}
							
- (void)applicationWillResignActive:(UIApplication *)application
{
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later. 
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
    // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
}

- (void)applicationWillTerminate:(UIApplication *)application
{
    // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
}


-(void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo{
    
    UIApplicationState state = [application applicationState];
    NSDictionary *notificationhistory = [NSDictionary dictionaryWithObjectsAndKeys:
                                         @"test",@"Message",@"Tokyo",@"Japan",@"London",@"UnitedKingdom", nil];
    NSLog(@"%@", notificationhistory);
    if (state == UIApplicationStateActive) {
        
        NSString *cancelTitle = @"OK";
        NSString *showTitle = @"Afficher";
        NSString *message = [[[userInfo valueForKey:@"aps"] valueForKey:@"alert"] valueForKey:@"body"];
        
        UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"Nouveau message !"
                                                            message:message
                                                           delegate:self
                                                  cancelButtonTitle:cancelTitle
                                                  otherButtonTitles:showTitle, nil];
        [alertView show];
        
        NSLog(@"AppDelegate didReceiveRemoteNotification %@", userInfo);
        
        
    }
    
}




//methode delege apres un succes d'enregistrement APNS
-(void)application:(UIApplication *)application didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken{
    
    NSString *token = [NSString stringWithFormat:@"%@", deviceToken];
    token = [token stringByReplacingOccurrencesOfString:@"<" withString:@""];
    token = [token stringByReplacingOccurrencesOfString:@">" withString:@""];
    
    NSLog(@"token: %@", token);
    
    
    NSMutableURLRequest *request = [[NSMutableURLRequest alloc] initWithURL:[NSURL URLWithString:[variables objectForKey:@"srvDeviceRegister"]]];
    [request setHTTPMethod:@"POST"];
    NSString *request_body = [NSString stringWithFormat:@"uniqueIdentifier=%@&typeOfDevice=ios&token=%@", uniqueIdentifier, token];
    [request setHTTPBody:[request_body dataUsingEncoding:NSASCIIStringEncoding]];
    
    NSError *error = nil;
    NSURLResponse *response;
    
    [NSURLConnection sendSynchronousRequest:request returningResponse:&response error:&error];
    
}

//methode delegé apres un fail d'enregistrement APNS
-(void)application:(UIApplication *)application didFailToRegisterForRemoteNotificationsWithError:(NSError *)error{
    NSLog(@"%@", error);
    
    NSMutableURLRequest *request = [[NSMutableURLRequest alloc] initWithURL:[NSURL URLWithString:[variables objectForKey:@"srvDeviceRegister"]]];
    [request setHTTPMethod:@"POST"];
    NSString *request_body = [NSString stringWithFormat:@"uniqueIdentifier=%@&typeOfDevice=ios&token=%@", uniqueIdentifier, @"null"];
    [request setHTTPBody:[request_body dataUsingEncoding:NSASCIIStringEncoding]];
    
    NSError *errord = nil;
    NSURLResponse *response;
    
    [NSURLConnection sendSynchronousRequest:request returningResponse:&response error:&errord];
}

@end
