//
//  Dans2SemainesViewController.m
//  App Uha
//
//  Created by clement mouline on 18/02/14.
//  Copyright (c) 2014 clement mouline. All rights reserved.
//

#import "Dans2SemainesViewController.h"
#import "PlanningCell.h"
#import "JsonReader.h"
#import "Cours.h"

@interface Dans2SemainesViewController ()

@end

@implementation Dans2SemainesViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


//avant que la vue n'aparaisse ou ne re-Apparaisse :)
-(void)viewWillAppear:(BOOL)animated{
    
    NSUserDefaults *setting = [NSUserDefaults standardUserDefaults];
    JsonReader *jr = [[JsonReader alloc] initWithData:[setting objectForKey:@"GetPlanning"]];
    
    if ([[jr getResultCode] isEqualToString:@"0"]) {
        return;
    }
    
    
    
    NSMutableArray *coursLundi = [jr getCoursForWeek:@"semaine3" andDay:@"lundi"];
    NSMutableArray *coursMardi = [jr getCoursForWeek:@"semaine3" andDay:@"mardi"];
    NSMutableArray *coursMercredi = [jr getCoursForWeek:@"semaine3" andDay:@"mercredi"];
    NSMutableArray *coursJeudi = [jr getCoursForWeek:@"semaine3" andDay:@"jeudi"];
    NSMutableArray *coursVendredi = [jr getCoursForWeek:@"semaine3" andDay:@"vendredi"];
    NSMutableArray *coursSamedi = [jr getCoursForWeek:@"semaine3" andDay:@"samedi"];
    
    
    
    
    
    itemsNameLundi = [NSMutableArray array];
    itemsNameMardi = [NSMutableArray array];
    itemsNameMercredi = [NSMutableArray array];
    itemsNameJeudi = [NSMutableArray array];
    itemsNameVendredi = [NSMutableArray array];
    itemsNameSamedi = [NSMutableArray array];
    itemsTimeLundi = [NSMutableArray array];
    itemsTimeMardi = [NSMutableArray array];
    itemsTimeMercredi = [NSMutableArray array];
    itemsTimeJeudi = [NSMutableArray array];
    itemsTimeVendredi = [NSMutableArray array];
    itemsTimeSamedi = [NSMutableArray array];
    itemsTeacherLundi = [NSMutableArray array];
    itemsTeacherMardi = [NSMutableArray array];
    itemsTeacherMercredi = [NSMutableArray array];
    itemsTeacherJeudi = [NSMutableArray array];
    itemsTeacherVendredi = [NSMutableArray array];
    itemsTeacherSamedi = [NSMutableArray array];
    itemsRoomLundi = [NSMutableArray array];
    itemsRoomMardi = [NSMutableArray array];
    itemsRoomMercredi = [NSMutableArray array];
    itemsRoomJeudi = [NSMutableArray array];
    itemsRoomVendredi = [NSMutableArray array];
    itemsRoomSamedi = [NSMutableArray array];
    
    
    
    lundiName = [jr getDayNameForWeek:@"semaine3" andDay:@"lundi"];
    mardiName = [jr getDayNameForWeek:@"semaine3" andDay:@"mardi"];
    mercrediName = [jr getDayNameForWeek:@"semaine3" andDay:@"mercredi"];
    jeudiName = [jr getDayNameForWeek:@"semaine3" andDay:@"jeudi"];
    vendrediName = [jr getDayNameForWeek:@"semaine3" andDay:@"vendredi"];
    samediName = [jr getDayNameForWeek:@"semaine3" andDay:@"samedi"];
    
    
    
    [self lundiInMutable:coursLundi];
    [self mardiInMutable:coursMardi];
    [self mercrediInMutable:coursMercredi];
    [self jeudiInMutable:coursJeudi];
    [self vendrediInMutable:coursVendredi];
    [self samediInMutable:coursSamedi];
    
    
    [self.tableView reloadData];
}


//ci-dessous, les methodes de la listeView


//cette methode retourn le numéro de sections
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 6;
}

//cettem methode retourne le nombre d'items par section
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    switch (section) {
        case 0:
            return [itemsNameLundi count];
            break;
        case 1:
            return [itemsNameMardi count];
            break;
        case 2:
            return [itemsNameMercredi count];
            break;
        case 3:
            return [itemsNameJeudi count];
            break;
        case 4:
            return [itemsNameVendredi count];
            break;
        case 5:
            return [itemsNameSamedi count];
            break;
            
        default:
            return 0;
            break;
    }
}

-(UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section
{
    UIView *view = [[UIView alloc] initWithFrame:CGRectMake(0, 0, tableView.frame.size.width, 18)];
    [view setBackgroundColor:[UIColor colorWithRed:47/255.0f green:47/255.0f blue:139/255.0f alpha:0.85f]];
    UILabel *label = [[UILabel alloc] initWithFrame:CGRectMake(15, 2, tableView.frame.size.width, 18)];
    [label setFont:[UIFont boldSystemFontOfSize:15]];
    [label setTextColor:[UIColor whiteColor]];
    
    [view addSubview:label];
    
    if (lundiName != nil) {
        switch (section) {
            case 0:
                [label setText:lundiName];
                return view;
                break;
            case 1:
                [label setText:mardiName];
                return view;
                break;
            case 2:
                [label setText:mercrediName];;
                return view;
                break;
            case 3:
                [label setText:jeudiName];;
                return view;
                break;
            case 4:
                [label setText:vendrediName];;
                return view;
                break;
            case 5:
                [label setText:samediName];;
                return view;
                break;
                
            default:
                return nil;
                break;
        }
    }
    return nil;
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *cellIdentifier = @"PlanningCell";
    PlanningCell *cell = (PlanningCell *)[tableView dequeueReusableCellWithIdentifier:cellIdentifier];
    
    
    
    if (cell == nil)
    {
        NSArray *nib = [[NSBundle mainBundle] loadNibNamed:@"PlanningCell" owner:self options:nil];
        cell = [nib objectAtIndex:0];
    }
    
    
    // Configure the cell.
    
    switch (indexPath.section) {
        case 0:
            cell.name.text = [itemsNameLundi objectAtIndex:indexPath.row];
            cell.time.text = [itemsTimeLundi objectAtIndex:indexPath.row];
            cell.teacher.text = [itemsTeacherLundi objectAtIndex:indexPath.row];
            cell.room.text = [itemsRoomLundi objectAtIndex:indexPath.row];
            break;
        case 1:
            cell.name.text = [itemsNameMardi objectAtIndex:indexPath.row];
            cell.time.text = [itemsTimeMardi objectAtIndex:indexPath.row];
            cell.teacher.text = [itemsTeacherMardi objectAtIndex:indexPath.row];
            cell.room.text = [itemsRoomMardi objectAtIndex:indexPath.row];
            break;
        case 2:
            cell.name.text = [itemsNameMercredi objectAtIndex:indexPath.row];
            cell.time.text = [itemsTimeMercredi objectAtIndex:indexPath.row];
            cell.teacher.text = [itemsTeacherMercredi objectAtIndex:indexPath.row];
            cell.room.text = [itemsRoomMercredi objectAtIndex:indexPath.row];
            break;
        case 3:
            cell.name.text = [itemsNameJeudi objectAtIndex:indexPath.row];
            cell.time.text = [itemsTimeJeudi objectAtIndex:indexPath.row];
            cell.teacher.text = [itemsTeacherJeudi objectAtIndex:indexPath.row];
            cell.room.text = [itemsRoomJeudi objectAtIndex:indexPath.row];
            break;
        case 4:
            cell.name.text = [itemsNameVendredi objectAtIndex:indexPath.row];
            cell.time.text = [itemsTimeVendredi objectAtIndex:indexPath.row];
            cell.teacher.text = [itemsTeacherVendredi objectAtIndex:indexPath.row];
            cell.room.text = [itemsRoomVendredi objectAtIndex:indexPath.row];
            break;
        case 5:
            cell.name.text = [itemsNameSamedi objectAtIndex:indexPath.row];
            cell.time.text = [itemsTimeSamedi objectAtIndex:indexPath.row];
            cell.teacher.text = [itemsTeacherSamedi objectAtIndex:indexPath.row];
            cell.room.text = [itemsRoomSamedi objectAtIndex:indexPath.row];
            break;
        default:
            break;
    }
    
    
    return cell;
}



-(void)lundiInMutable:(NSMutableArray *)coursLundi{
    
    for (int i = 0; i<[coursLundi count]; i = i + 1) {
        
        NSString *name = [NSString stringWithFormat:@"%@", [[coursLundi objectAtIndex:i] coursName]];
        NSString *time = [NSString stringWithFormat:@"%@ - %@", [[coursLundi objectAtIndex:i] coursStart], [[coursLundi objectAtIndex:i] coursStop]];
        NSString *teacher = [NSString stringWithFormat:@"%@", [[coursLundi objectAtIndex:i] coursTeacher]];
        NSString *room = [NSString stringWithFormat:@"%@", [[coursLundi objectAtIndex:i] coursRoom]];
        
        [itemsNameLundi addObject:name];
        [itemsTimeLundi addObject:time];
        [itemsTeacherLundi addObject:teacher];
        [itemsRoomLundi addObject:room];
        
    }
    
}

-(void)mardiInMutable:(NSMutableArray *)coursMardi{
    
    for (int i = 0; i<[coursMardi count]; i = i + 1) {
        
        NSString *name = [NSString stringWithFormat:@"%@", [[coursMardi objectAtIndex:i] coursName]];
        NSString *time = [NSString stringWithFormat:@"%@ - %@", [[coursMardi objectAtIndex:i] coursStart], [[coursMardi objectAtIndex:i] coursStop]];
        NSString *teacher = [NSString stringWithFormat:@"%@", [[coursMardi objectAtIndex:i] coursTeacher]];
        NSString *room = [NSString stringWithFormat:@"%@", [[coursMardi objectAtIndex:i] coursRoom]];
        
        [itemsNameMardi addObject:name];
        [itemsTimeMardi addObject:time];
        [itemsTeacherMardi addObject:teacher];
        [itemsRoomMardi addObject:room];
        
    }
    
}

-(void)mercrediInMutable:(NSMutableArray *)coursMercredi{
    
    for (int i = 0; i<[coursMercredi count]; i = i + 1) {
        
        NSString *name = [NSString stringWithFormat:@"%@", [[coursMercredi objectAtIndex:i] coursName]];
        NSString *time = [NSString stringWithFormat:@"%@ - %@", [[coursMercredi objectAtIndex:i] coursStart], [[coursMercredi objectAtIndex:i] coursStop]];
        NSString *teacher = [NSString stringWithFormat:@"%@", [[coursMercredi objectAtIndex:i] coursTeacher]];
        NSString *room = [NSString stringWithFormat:@"%@", [[coursMercredi objectAtIndex:i] coursRoom]];
        
        [itemsNameMercredi addObject:name];
        [itemsTimeMercredi addObject:time];
        [itemsTeacherMercredi addObject:teacher];
        [itemsRoomMercredi addObject:room];
        
    }
    
}


-(void)jeudiInMutable:(NSMutableArray *)coursJeudi{
    
    for (int i = 0; i<[coursJeudi count]; i = i + 1) {
        
        NSString *name = [NSString stringWithFormat:@"%@", [[coursJeudi objectAtIndex:i] coursName]];
        NSString *time = [NSString stringWithFormat:@"%@ - %@", [[coursJeudi objectAtIndex:i] coursStart], [[coursJeudi objectAtIndex:i] coursStop]];
        NSString *teacher = [NSString stringWithFormat:@"%@", [[coursJeudi objectAtIndex:i] coursTeacher]];
        NSString *room = [NSString stringWithFormat:@"%@", [[coursJeudi objectAtIndex:i] coursRoom]];
        
        [itemsNameJeudi addObject:name];
        [itemsTimeJeudi addObject:time];
        [itemsTeacherJeudi addObject:teacher];
        [itemsRoomJeudi addObject:room];
        
    }
    
}


-(void)vendrediInMutable:(NSMutableArray *)coursVendredi{
    
    for (int i = 0; i<[coursVendredi count]; i = i + 1) {
        
        NSString *name = [NSString stringWithFormat:@"%@", [[coursVendredi objectAtIndex:i] coursName]];
        NSString *time = [NSString stringWithFormat:@"%@ - %@", [[coursVendredi objectAtIndex:i] coursStart], [[coursVendredi objectAtIndex:i] coursStop]];
        NSString *teacher = [NSString stringWithFormat:@"%@", [[coursVendredi objectAtIndex:i] coursTeacher]];
        NSString *room = [NSString stringWithFormat:@"%@", [[coursVendredi objectAtIndex:i] coursRoom]];
        
        [itemsNameVendredi addObject:name];
        [itemsTimeVendredi addObject:time];
        [itemsTeacherVendredi addObject:teacher];
        [itemsRoomVendredi addObject:room];
        
    }
    
}


-(void)samediInMutable:(NSMutableArray *)coursSamedi{
    
    for (int i = 0; i<[coursSamedi count]; i = i + 1) {
        
        NSString *name = [NSString stringWithFormat:@"%@", [[coursSamedi objectAtIndex:i] coursName]];
        NSString *time = [NSString stringWithFormat:@"%@ - %@", [[coursSamedi objectAtIndex:i] coursStart], [[coursSamedi objectAtIndex:i] coursStop]];
        NSString *teacher = [NSString stringWithFormat:@"%@", [[coursSamedi objectAtIndex:i] coursTeacher]];
        NSString *room = [NSString stringWithFormat:@"%@", [[coursSamedi objectAtIndex:i] coursRoom]];
        
        [itemsNameSamedi addObject:name];
        [itemsTimeSamedi addObject:time];
        [itemsTeacherSamedi addObject:teacher];
        [itemsRoomSamedi addObject:room];
        
    }
    
}
@end
