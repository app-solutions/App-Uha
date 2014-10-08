//
//  AppDelegate.h
//  App Uha
//
//  Created by Clément Mouline on 11/11/13.
//  Copyright (c) 2013 Clément Mouline, App-Solutions. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface AppDelegate : UIResponder <UIApplicationDelegate>{
    
    NSString *uniqueIdentifier;
    NSDictionary  *variables;
    
}

@property (strong, nonatomic) UIWindow *window;

@end
