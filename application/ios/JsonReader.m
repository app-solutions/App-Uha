//
//  JsonReader.m
//  App Uha
//
//  Created by Clément Mouline on 05/11/13.
//  Copyright (c) 2013 Clément Mouline, App-Solutions. All rights reserved.
//

#import "JsonReader.h"
#import "Cours.h"

@implementation JsonReader

@synthesize json;


-(id)initWithData:(NSData *)theData{
    
    if (self = [super init])
    {
        NSError *error = nil;
        self.json = [NSJSONSerialization JSONObjectWithData:theData  options:kNilOptions error:&error];
    }
    return self;
    
}

//methodes génériques
-(NSString *)getResultCode{
    return [[json objectForKey:@"AppUha"] objectForKey:@"resultCode" ];
}

-(NSString *)getResultHeader{
    return [[json objectForKey:@"AppUha"] objectForKey:@"resultHeader" ];
}

-(NSString *)getResultTitle{
    return [[json objectForKey:@"AppUha"] objectForKey:@"resultTitle" ];
}

-(NSString *)getResultString{
    return [[json objectForKey:@"AppUha"] objectForKey:@"resultString" ];
}




//methodes du web service UserInfos
-(NSString *)getUserFirstName{
    return [[[json objectForKey:@"AppUha"] objectForKey:@"infos"] objectForKey:@"prenom"];
}

-(NSString *)getUserName{
    return [[[json objectForKey:@"AppUha"] objectForKey:@"infos"] objectForKey:@"nom"];
}

-(NSString *)getUserEmail{
    return [[[json objectForKey:@"AppUha"] objectForKey:@"infos"] objectForKey:@"email"];
}

-(NSString *)getUserGroupe{
    return [[[json objectForKey:@"AppUha"] objectForKey:@"infos"] objectForKey:@"groupe"];
}

-(NSString *)getUserNotifs{
    return [[[json objectForKey:@"AppUha"] objectForKey:@"infos"] objectForKey:@"notifs"];
}


//methodes du web service GetPlanning
-(NSString *)getWeekName:(NSString *)name{
    return [[[[json objectForKey:@"AppUha"] objectForKey:@"semaines"] objectForKey:name] objectForKey:@"nom"];
}

-(NSString *)getDayNameForWeek:(NSString *)week andDay:(NSString *)day{
    return [[[[[[json objectForKey:@"AppUha"] objectForKey:@"semaines"] objectForKey:week] objectForKey:@"jours" ] objectForKey:day] objectForKey:@"nom"];
}

-(NSMutableArray *)getCoursForWeek:(NSString *)week andDay:(NSString *)day{
    
    NSArray * jsonCours = [[[[[[json objectForKey:@"AppUha"] objectForKey:@"semaines"] objectForKey:week] objectForKey:@"jours"] objectForKey:day] objectForKey:@"cours"];
    
    NSMutableArray *cours = [[NSMutableArray alloc] init];
    for (int i = 0; i<[jsonCours count]; i = i + 1) {
        
        [cours addObject:[[Cours alloc] initWithId:[[jsonCours objectAtIndex:i] objectForKey:@"id"] andWithStart:[[jsonCours objectAtIndex:i] objectForKey:@"start"] andWithStop:[[jsonCours objectAtIndex:i] objectForKey:@"stop"] andWithDuration:[[jsonCours objectAtIndex:i] objectForKey:@"durée"] andWithName:[[jsonCours objectAtIndex:i] objectForKey:@"nom"] andWithRoom:[[jsonCours objectAtIndex:i] objectForKey:@"salle"] andWithTeacher:[[jsonCours objectAtIndex:i] objectForKey:@"enseignant"]]];
        
    }
    return cours;
}


@end
