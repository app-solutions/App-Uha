//
//  Cours.h
//  App Uha
//
//  Created by Clément Mouline on 21/10/13.
//  Copyright (c) 2013 Clément Mouline, App-Solutions. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Cours : NSObject{
    
    NSString * coursId;
    NSString * coursStart;
    NSString * coursStop;
    NSString * coursDuration;
    NSString * coursName;
    NSString * coursRoom;
    NSString * coursTeacher;
    
}

-(id)initWithId:(NSString *)idt andWithStart:(NSString *)startt andWithStop:(NSString *)stopt andWithDuration:(NSString *)durationt andWithName:(NSString *)namet andWithRoom:(NSString *)roomt andWithTeacher:(NSString *)teachert;

@property (nonatomic, retain) NSString* coursId;
@property (nonatomic, retain) NSString* coursStart;
@property (nonatomic, retain) NSString* coursStop;
@property (nonatomic, retain) NSString* coursDuration;
@property (nonatomic, retain) NSString* coursName;
@property (nonatomic, retain) NSString* coursRoom;
@property (nonatomic, retain) NSString* coursTeacher;

@end
