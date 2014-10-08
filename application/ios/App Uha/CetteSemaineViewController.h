//
//  CetteSemaineViewController.h
//  App Uha
//
//  Created by Morgan Enderlin, Clément Mouline on 11/11/13.
//  Copyright (c) 2013 Morgan Enderlin, Clément Mouline, App-Solutions. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface CetteSemaineViewController : UITableViewController{
    
    //items le la listeView
    NSMutableArray *itemsNameLundi;
    NSMutableArray *itemsNameMardi;
    NSMutableArray *itemsNameMercredi;
    NSMutableArray *itemsNameJeudi;
    NSMutableArray *itemsNameVendredi;
    NSMutableArray *itemsNameSamedi;
    
    
    NSMutableArray *itemsTimeLundi;
    NSMutableArray *itemsTimeMardi;
    NSMutableArray *itemsTimeMercredi;
    NSMutableArray *itemsTimeJeudi;
    NSMutableArray *itemsTimeVendredi;
    NSMutableArray *itemsTimeSamedi;
    
    NSMutableArray *itemsTeacherLundi;
    NSMutableArray *itemsTeacherMardi;
    NSMutableArray *itemsTeacherMercredi;
    NSMutableArray *itemsTeacherJeudi;
    NSMutableArray *itemsTeacherVendredi;
    NSMutableArray *itemsTeacherSamedi;
    
    NSMutableArray *itemsRoomLundi;
    NSMutableArray *itemsRoomMardi;
    NSMutableArray *itemsRoomMercredi;
    NSMutableArray *itemsRoomJeudi;
    NSMutableArray *itemsRoomVendredi;
    NSMutableArray *itemsRoomSamedi;
    
    
    
    //nom des jours
    NSString *lundiName;
    NSString *mardiName;
    NSString *mercrediName;
    NSString *jeudiName;
    NSString *vendrediName;
    NSString *samediName;
    
    
    //variables
    NSDictionary *variables;
    
}

- (IBAction)onRefresh:(id)sender;


@end
