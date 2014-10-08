//
//  Cours.m
//  App Uha
//
//  Created by Clément Mouline on 21/10/13.
//  Copyright (c) 2013 Clément Mouline, App-Solutions. All rights reserved.
//

#import "Cours.h"

@implementation Cours

-(id)initWithId:(NSString *)idt andWithStart:(NSString *)startt andWithStop:(NSString *)stopt andWithDuration:(NSString *)durationt andWithName:(NSString *)namet andWithRoom:(NSString *)roomt andWithTeacher:(NSString *)teachert
{
    if (self = [super init])
    {
        self.coursId = idt;
        self.coursStart = startt;
        self.coursStop = stopt;
        self.coursDuration = durationt;
        self.coursName = namet;
        self.coursRoom = roomt;
        self.coursTeacher = teachert;
    }
    return self;
}

@synthesize coursId;
@synthesize coursStart;
@synthesize coursStop;
@synthesize coursDuration;
@synthesize coursName;
@synthesize coursRoom;
@synthesize coursTeacher;

@end
