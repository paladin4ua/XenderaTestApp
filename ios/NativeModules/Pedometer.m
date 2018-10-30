//
//  Pedometer.m
//  XenderaTestApp
//
//  Created by Kubiv on 10/30/18.
//  Copyright © 2018 Facebook. All rights reserved.
//

#import "Pedometer.h"

#define ON_ACTIVITY_CHANGED_KEY @"ON_ACTIVITY_CHANGED"
#define ON_ACTIVITY_CHANGED @"OnActivityChanged"

@implementation Pedometer

RCT_EXPORT_MODULE();

+ (BOOL)requiresMainQueueSetup
{
  return YES;
}

- (NSArray<NSString *> *)supportedEvents
{
  return @[ON_ACTIVITY_CHANGED];
}

- (NSDictionary *)constantsToExport
{
  return @{ ON_ACTIVITY_CHANGED_KEY: ON_ACTIVITY_CHANGED };
}

RCT_REMAP_METHOD(isSupported,
                 isSupportedWithResolver:(RCTPromiseResolveBlock)resolve
                 rejecter:(RCTPromiseRejectBlock)reject)
{
    resolve([[NSNumber alloc] initWithBool: [CMPedometer isStepCountingAvailable]]);
}

RCT_REMAP_METHOD(init, initPedometer)
{
  
  if ([CMPedometer isStepCountingAvailable]) {
    self.pedometer = [[CMPedometer alloc] init];
    
    [self.pedometer startPedometerUpdatesFromDate:[[NSDate date] dateByAddingTimeInterval: -10] withHandler:^(CMPedometerData * _Nullable pedometerData, NSError * _Nullable error) {
            
      [self sendEventWithName:ON_ACTIVITY_CHANGED body:@{}];
    }];
  }
  
}



@end
