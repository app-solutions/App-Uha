//
//  NotificationViewController.h
//  App Uha
//
//  Created by Morgan Enderlin, Clément Mouline on 11/11/13.
//  Copyright (c) 2013 Morgan Enderlin, Clément Mouline, App-Solutions. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface NotificationViewController : UIViewController{
    
    //elements graphiques
    IBOutlet UITextField *textField;
    IBOutlet UIPickerView *picker;
    
    //elements non graphiques
    NSMutableArray *chooseList;
    NSString *selected;
    
    //variables
    NSDictionary *variables;
    
}
- (IBAction)dismissKeyboard:(id)sender;
- (IBAction)onTouchView:(id)sender;

- (IBAction)send:(id)sender;

@end
