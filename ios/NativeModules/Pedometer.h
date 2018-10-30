//
//  Pedometer.h
//  XenderaTestApp
//
//  Created by Kubiv on 10/30/18.
//  Copyright © 2018 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>
#import <CoreMotion/CMPedometer.h>

@interface Pedometer : RCTEventEmitter  <RCTBridgeModule>

@property (nonatomic, strong) CMPedometer * pedometer;


@end

