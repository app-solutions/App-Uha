//
//  CreditsViewController.m
//  App Uha
//
//  Created by Morgan Enderlin, Clément Mouline on 11/11/13.
//  Copyright (c) 2013 Morgan Enderlin, Clément Mouline, App-Solutions. All rights reserved.
//

#import "CreditsViewController.h"
#import "Social/Social.h"

@interface CreditsViewController ()

@end

@implementation CreditsViewController

- (void)viewDidLoad
{
    
    //definition du NSDictionary contenant les variables de Variables.plist
    variables = [NSDictionary dictionaryWithContentsOfFile:[[NSBundle mainBundle] pathForResource:@"Variables" ofType:@"plist"]];
    
    version.text = [variables objectForKey:@"version"];
    
    //definition de la taille du scoll view
    [scroller setScrollEnabled:NO];
    [scroller setContentSize:CGSizeMake(300, 400)];
    
    [super viewDidLoad];
	// Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)easterEgg:(id)sender {
    
    if ([scroller isScrollEnabled]) {
        return;
    }
    
    [scroller setScrollEnabled:YES];
    UIAlertView *alerte = [[UIAlertView alloc] initWithTitle:@"bravo !" message:@"vous avez trouvé l'easterEgg" delegate:self cancelButtonTitle:@"cool !" otherButtonTitles:nil, nil];
    [alerte show];
    
    dev1.text = @"Dark Vador";
    dev2.text = @"Albert Einstein";
    dev4.text = @"Bambi";
    
    rem1.text = @"Au Père Noël";
    rem2.text = @"Aux Extraterrestres";
    
}
@end
