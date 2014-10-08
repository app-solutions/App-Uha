//
//  CreditsViewController.h
//  App Uha
//
//  Created by Morgan Enderlin, Clément Mouline on 11/11/13.
//  Copyright (c) 2013 Morgan Enderlin, Clément Mouline, App-Solutions. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface CreditsViewController : UIViewController{
    
    IBOutlet UIScrollView *scroller;
    
    
    IBOutlet UILabel *version;
    
    IBOutlet UILabel *dev1;
    IBOutlet UILabel *dev2;
    IBOutlet UILabel *dev4;
    IBOutlet UILabel *rem1;
    IBOutlet UILabel *rem2;
    
    //variables
    NSDictionary *variables;
    
}

- (IBAction)easterEgg:(id)sender;

@end
