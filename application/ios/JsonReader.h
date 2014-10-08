//
//  JsonReader.h
//  App Uha
//
//  Created by Clément Mouline on 05/11/13.
//  Copyright (c) 2013 Clément Mouline, App-Solutions. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface JsonReader : NSObject{
    NSDictionary *json;
}

@property (nonatomic, retain)NSDictionary *json;

-(id)initWithData:(NSData *)theData;

//mehodes permetant de recuperer les éléments génerique à toutes les requettes sur le web
-(NSString *)getResultCode;
-(NSString *)getResultHeader;
-(NSString *)getResultTitle;
-(NSString *)getResultString;


//methodes permetant de recuperer les données du service UserInfo
-(NSString *)getUserFirstName;
-(NSString *)getUserName;
-(NSString *)getUserEmail;
-(NSString *)getUserGroupe;
-(NSString *)getUserNotifs;


//methodes permetant de recuperer les données du service GetPlannning

-(NSString *)getWeekName:(NSString *)name;
-(NSString *)getDayNameForWeek:(NSString *)week andDay:(NSString *)day;
-(NSMutableArray *)getCoursForWeek:(NSString *)week andDay:(NSString *)day;

@end
