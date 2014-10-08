//
//  Dans3SemainesViewController.h
//  App Uha
//
//  Created by clement mouline on 18/02/14.
//  Copyright (c) 2014 clement mouline. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface Dans3SemainesViewController : UITableViewController{
    
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
    
}

@end
