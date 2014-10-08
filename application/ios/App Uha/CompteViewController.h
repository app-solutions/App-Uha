//
//  CompteViewController.h
//  App Uha
//
//  Created by Morgan Enderlin, Clément Mouline on 11/11/13.
//  Copyright (c) 2013 Morgan Enderlin, Clément Mouline, App-Solutions. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface CompteViewController : UIViewController{
    
    //le scroller
    IBOutlet UIScrollView *scroller;
    
    
    //les elements de la vue
    IBOutlet UITextField *firstNameField;
    IBOutlet UITextField *nameField;
    IBOutlet UITextField *emailField;
    IBOutlet UITextField *passwordField;
    IBOutlet UIPickerView *picker;
    
    
    //elements non graphiques
    NSMutableArray *groupeList;
    NSString *groupeSelected;
    
    
    //variables
    NSDictionary *variables;
    
}

//les actions
- (IBAction)dismissKeyboard:(id)sender;
- (IBAction)onTouchView:(id)sender;
- (IBAction)send:(id)sender;


@end
