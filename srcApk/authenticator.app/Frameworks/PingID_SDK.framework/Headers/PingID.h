//
//  PingID SDK version 1.5.0
//
//  Copyright (c) 2017 Ping Identity. All rights reserved.
//
// See LICENSE_SDK.txt for the PingID SDK library licensing information.
//

#import <Foundation/Foundation.h>

@class PIDUserSelectionObject;


#pragma mark - PIDSupportedMfaType
/**
 Supported MFA types
 
 - PIDSupportedMfaTypeAutomatic:            		MFA Supports Remote Notifications with automatic fallback to One Time Passcode
 - PIDSupportedMfaTypeEnforceRemoteNotifications:   MFA Supports Remote Notifications only
 - PIDSupportedMfaTypeDisableRemoteNotifications:   MFA Will not support Remote notifications and will support only One Time Passcode
 */
typedef NS_ENUM(NSUInteger, PIDSupportedMfaType)
{
    PIDSupportedMfaTypeAutomatic            		=   0,
    PIDSupportedMfaTypeEnforceRemoteNotifications   =   1,
    PIDSupportedMfaTypeDisableRemoteNotifications  	=   2
};

#pragma mark - PIDActionType
/**
 Actions types

 - PIDActionTypeNone:       None
 - PIDActionTypeApprove:    Approve the device
 - PIDActionTypeDeny:       Deny the device
 - PIDActionTypeBlock:      Block the device
 */
typedef NS_ENUM(NSUInteger, PIDActionType)
{
    PIDActionTypeNone       =  -1,
    PIDActionTypeApprove    =   0,
    PIDActionTypeDeny       =   1,
    PIDActionTypeBlock      =   2
};

/**
 Trust Levels

 - PIDTrustLevelNone:       None
 - PIDTrustLevelPrimary:    Sets the device as primary
 - PIDTrustLevelTrusted:    Sets the device as trusted
 - PIDTrustLevelIgnored:    Sets the device as ignored
 */
typedef NS_ENUM(NSUInteger, PIDTrustLevel)
{
    PIDTrustLevelNone       =  -1,
    PIDTrustLevelPrimary    =   0,
    PIDTrustLevelTrusted    =   1,
    PIDTrustLevelIgnored    =   2
};
#pragma mark - PIDFlowState

/**
 Describes the flow state

 - PIDFlowStateDone:            No action is needed
 - PIDFlowStateOneTimePasscode: The device can't be reached. Display available trust levels when device is offline.
 - PIDFlowStateTrustLevels:     Present trust levels to select from
 */
typedef NS_ENUM(NSInteger, PIDFlowState)
{
    PIDFlowStateDone                = 0,
    PIDFlowStateOneTimePasscode     = 1,
    PIDFlowStateTrustLevels         = 2,
    PIDFlowStateDeviceIsTrusted     = 3
};


#pragma mark - PIDIgnoreIntervalUnit

/**
 Ignore interval type

 - PIDIgnoreIntervalUnitMinutes:    Minutes
 - PIDIgnoreIntervalUnitHours:      Hours
 - PIDIgnoreIntervalUnitDays:       Days
 - PIDIgnoreIntervalUnitWeeks:      Weeks
 - PIDIgnoreIntervalUnitMonths:     Months
 - PIDIgnoreIntervalUnitYears:      Years
 - PIDIgnoreIntervalUnitAlways:     Always
 */
typedef NS_ENUM(NSUInteger, PIDIgnoreIntervalUnit)
{
    PIDIgnoreIntervalUnitMinutes    =   0,
    PIDIgnoreIntervalUnitHours      =   1,
    PIDIgnoreIntervalUnitDays       =   2,
    PIDIgnoreIntervalUnitWeeks      =   3,
    PIDIgnoreIntervalUnitMonths     =   4,
    PIDIgnoreIntervalUnitYears      =   5,
    PIDIgnoreIntervalUnitAlways     =   6
};

#pragma mark - PIDRemoteNotificationType

/**
 Remote Notification type
 
 - PIDRemoteNotificationTypeDone:     Authentication completed successfully
 - PIDRemoteNotificationTypeAuth:     Authentication currently in progress
 - PIDRemoteNotificationTypeCancel:   Authentication request was canceled
 */
typedef NS_ENUM(NSUInteger, PIDRemoteNotificationType)
{
    PIDRemoteNotificationTypeDone     =  0,
    PIDRemoteNotificationTypeAuth     =  1,
    PIDRemoteNotificationTypeCancel   =  2
};

#pragma mark - PIDDataCenterType

/**
Data Center type
 
 - PIDDataCenterTypeDefault:    Default Data Center
 - PIDDataCenterTypeNA:         North America Data Center
 - PIDDataCenterTypeAU:         Australia Data Center
 - PIDDataCenterTypeEU:         Europe Data Center
 */
typedef NS_ENUM(NSUInteger, PIDDataCenterType)
{
    PIDDataCenterTypeDefault    =   0,
    PIDDataCenterTypeNA         =   1,
    PIDDataCenterTypeAU         =   2,
    PIDDataCenterTypeEU         =   3
};

/**
 OneTimePasscodeStatus state
 
 - PIDOneTimePasscodeOK:                                   The one time passcode approved.
 - PIDOneTimePasscodeDeviceRooted:                         The one time passcode denied, device is rooted.
 - PIDOneTimePasscodeCanNotCheckRootDetectionAtOffline:    Can not check device for root detection at offline mode.
 - PIDOneTimePasscodeUnsuccessesful:                       Can not return one time passcode.
 */


#pragma mark - PIDRooDetection
typedef NS_ENUM(NSUInteger, PIDOneTimePasscodeStatus)
{
    PIDOneTimePasscodeOK                                = 0,
    PIDOneTimePasscodeDeviceRooted                      = 1,
    PIDOneTimePasscodeCanNotCheckRootDetectionAtOffline = 2,
    PIDOneTimePasscodeUnsuccessesful                    = 3
};


/**
 PingIDDelegate
 */
@protocol PingIDDelegate <NSObject>

@optional
/**
 Get a new one time passcode once it's created. A new one time passcode will be created on each PingID SDK server request.
 If the server is configured for root detection and the device is rooted or offline, an empty oneTimePasscode string is returned.
 @param oneTimePasscode Current one time passcode
 */
- (void)didRefreshOneTimePasscode:(nonnull NSString *)oneTimePasscode;

/**
 Notifies that the server set the device status to "Untrusted".

 */
- (void)didUntrustDevice;

/**

 Get the token status once it changes.
 
 @param sessionInfo sessionInfo will include the parameter "authentication_token_status" with the possible statuses:
 
    CLAIMED - The authentication token was successfully claimed.
    CLAIMED_AND_PUSH_FAILED - The authentication token was successfully claimed. The push verification failed.
    CLAIMED_AND_PUSHLESS - The authentication token was successfully claimed. The push verification was not executed since the device is pushless.
    PENDING_USER_APPROVAL - The mobile application should ask the user for approval.
    MOBILE_USER_SELECTION - The mobile application prompts for user selection from the user list. An additional "users" parameter will be returned in the response.
    MOBILE_USER_SELECTION_AND_APPROVAL - The mobile application prompts for user selection from the user list. AND asks the user for approval. An additional "users" parameter will be returned  in the response.
    WEB_USER_SELECTION - The web UI prompts for user selection from the user list.
    PENDING_PUSH_VERIFICATION - A silent push was sent to the mobile for extra verification.
    CANCELED - The authentication with the given authentication token was canceled.
    DENIED - The user denied the authentication with the given authentication token.
 
    sessionInfo may also include "client_context" and "users" array. Each element in the array is a JSON object with the following properties:
    username - The username. This value is never empty.
    firstName - The user's first name. May be empty.
    lastName - The user's first name. May be empty.
    status - The user's status. Expected to be one of the following: "ACTIVE", "SUSPENDED".
 @param error If there is a problem with the method, an error code will be returned. (Optional)
 */
-(void)authenticationTokenStatus:(NSDictionary *_Nonnull)sessionInfo error:(NSError * _Nullable)error;

@end

@interface PingID : NSObject

#pragma mark - Delegate

/**
 PingID delegate
 */
@property (nonatomic, weak) _Nullable id<PingIDDelegate> delegate;

/**
 Set PingID Delegate object

 @param aDelegate The object you'd like to use. A UIViewController object for example
 */
+ (void)setPingIDDelegate:(_Nonnull id<PingIDDelegate>)aDelegate;

#pragma mark - Initializing PingID SDK

/**
 Initializes the PingID singleton instance with supported MFA of type PIDSupportedMfaTypeAutomatic.
 This method will be deprecated in future releases. Please use +[initWithAppID:supportedMfa:] instead.
 
 @param appID An ID string from PingID portal.
 */
+ (void)initWithAppID:(nonnull NSString *)appID;

/**
 Initializes the PingID singleton instance.
 
 @param appID An ID string from PingID portal.
 @param supportedMfaType The supported MFA type.
 */
+ (void)initWithAppID:(nonnull NSString *)appID supportedMfa:(PIDSupportedMfaType)supportedMfaType;

#pragma mark - RootDetection PingID SDK
/**
 *Call this function to activate mobile root detection.
 If root detection is enabled on the server this function must be called.
 If root detection is not enabled on the server this function is optional.
 @param toActivate mobile root detection, default is false.
 @param dataCenter
 - PIDDataCenterTypeDefault:    Default Data Center
 - PIDDataCenterTypeNA:         North America Data Center
 - PIDDataCenterTypeAU:         Australia Data Center
 - PIDDataCenterTypeEU:         Europe Data Center
 @param completionBlock Used to notify the Customer mobile app about errors. NSError contains a more specific error in case of failure.
 */
+(void)setRootDetection:(BOOL)toActivate dataCenter:(PIDDataCenterType)dataCenter withCompletionBlock:(nullable void(^)(NSError * _Nullable error))completionBlock;

/**
 * Call this function in order to get an offline one time passcode.
 @param completionBlock will return a 6-digit one time passcode string, oneTimePasscodeStatus, error in case of failure.
 */
+(void)getNextRestrictiveOneTimePasscode:(nullable void (^)(NSString  * _Nonnull oneTimePasscode, PIDOneTimePasscodeStatus oneTimePasscodeStatus, NSError * _Nullable  error)) completionBlock;
#pragma mark - Authentications

/**
Send reply to SDK and pair user

 @param selection Should contain PIDActionType or PIDTrustLevel or both. In case of ignore, ignoreIntervalUnit and ignoreInterval may be added. (Both are optional)
 @param completionBlock Used to notify the Customer mobile app about pairing process. NSError contains a more specific error in case of failure.
 */
+ (void)setUserSelection:(nonnull PIDUserSelectionObject *)selection
         completionBlock:(nullable void (^)(NSError * _Nullable error))completionBlock;

/**
 Authenticate user
 
 @param selection Should contain PIDActionType or PIDTrustLevel or both. In case of ignore, ignoreIntervalUnit and ignoreInterval may be added. (Both are optional)
 @param completionBlock Will return NSError in case of an error.
 */
+ (void)setAuthenticationUserSelection:(nonnull PIDUserSelectionObject *)selection
                       completionBlock:(nullable void(^)(NSError * _Nullable error))completionBlock;

/**
 Deprecated!
 Call this function to get an offline one time passcode.
 
 @return  One time passcode 6-digit OTP string.
 
 *********************************************************************************************************************
 * Instead of this deprecated function, it is recommended to use
 * +(void)getNextRestrictiveOneTimePasscode:(nullable void (^)(NSString  * _Nonnull oneTimePasscode, PIDOneTimePasscodeStatus oneTimePasscodeStatus, NSError * _Nullable  error)) completionBlock
 * When the PingID SDK server is configured for root detection, this function returns an empty string.
 **********************************************************************************************************************
 */
+ (nullable NSString *)getNextOneTimePasscode DEPRECATED_MSG_ATTRIBUTE("getNextOneTimePasscode is deprecated. Use getNextRestrictiveOneTimePasscode: with a completionBlock instead");
/**
 Validates the authentication token that was extracted from the QR Code
 
 @param token Should contain the token that was extracted from the QR Code.
 */
+ (void)validateAuthenticationToken:(NSString *_Nonnull)token;

#pragma mark - SDK-to-Server Payload

/**
 Returns an encrypted payload string for any communication needed between Customer mobile app and the PingID SDK server, through the Customer server.
 
 @param error If there is a problem with the method, an error code will be returned. (Optional)
 @return If method is successful, a string will be returned in order to pass to the Customer server. This payload must be sent to the PingID SDK server from the Customer server.
 */
+ (nullable NSString *)generatePayload:(NSError * _Nullable * _Nullable)error;

/**
 Returns an encrypted updated payload string for any communication needed between Customer mobile app and the PingID SDK server, through the Customer server.
 
 @param selection Should contain PIDActionType or PIDTrustLevel or both. In case of ignore, ignoreIntervalUnit and ignoreInterval may be added. (Both are optional)
 @param error If there is a problem with the method, an error code will be returned. (Optional)
 @return If method is successful, a string will be returned in order to pass to the Customer server. This payload must be sent to the PingID SDK server from the Customer server.
 */
+ (nullable NSString *)updateExistingPayloadWithUserSelection:(nonnull PIDUserSelectionObject *)selection error:(NSError * _Nullable * _Nullable)error;

/**
 Every payload received from the Customer server should be sent to the PingID SDK
 
 @param serverPayload Payload from the Customer server
 @param completionBlock May include availableTrustLevels to present to the user. availableTrustLevels is an array of PIDTrustLevel wrapped with NSNumber, for example [@(PIDTrustLevelPrimary),@(PIDTrustLevelTrusted)]
 */
+ (void)setServerPayload:(nonnull NSString *)serverPayload
         completionBlock:(nullable void(^)(PIDFlowState flowState, NSArray * _Nullable availableTrustLevels, NSError * _Nullable error))completionBlock;

/**
 This is a mandatory post action method which should be executed once, after receiving an access token from Ping Federate.
 @param dataCenter The data center that should be used.
 @param completionBlock May include availableTrustLevels to present to the user. availableTrustLevels is an array of PIDTrustLevel wrapped with NSNumber, for example [@(PIDTrustLevelPrimary),@(PIDTrustLevelTrusted)]
 */
+ (void)postIDPAuthenticationStepWithDataCenter:(PIDDataCenterType)dataCenter
                                completionBlock:(nullable void(^)(PIDFlowState flowState, NSArray * _Nullable availableTrustLevels, NSError * _Nullable error))completionBlock;
#pragma mark - Remote Notifications

/**
 Checks the payload received in remote Notification in order to check its validity against PingID SDK server.
 
 @param userInfo The userInfo received in the AppDelegate application:didReceiveRemoteNotification:fetchCompletionHandler: method.
 @return Returns YES if the remote notification data is PingID related.
 */
+ (BOOL)isRemoteNotificationFromPingID:(nonnull NSDictionary *)userInfo;

/**
 Set device remote notification token. Should be within application:didRegisterForRemoteNotificationsWithDeviceToken:

 @param deviceToken The deviceToken received in the AppDelegate application:didRegisterForRemoteNotificationsWithDeviceToken: method.
 */
+ (void)setRemoteNotificationsDeviceToken:(nullable NSData *)deviceToken;

/**
 Get PingID remote notification categories for iOS 8 & 9. Registering UIUserNotificationSettings more than once results in previous settings being overwritten. PingID provides the needed categories. The developer may add categories.
 @return Returns PingID remote notification categories.
 */
+ (nonnull NSMutableSet *)getPingIDDeprecatedRemoteNotificationsCategories;

/**
 Get PingID remote notification categories for iOS 10. Setting UNNotificationCategory more than once results in previous settings being overwritten. PingID provides the needed categories. The developer may add categories.
 @return Returns PingID remote notification categories.
 */
+ (nonnull NSMutableSet *)getPingIDRemoteNotificationsCategories;

/**
 Handles the remote notification received from PingID. The developer should call isRemoteNotificationFromPingID: before this method in order to check the payload validity.

 @param userInfo The userInfo received in the AppDelegate application:didReceiveRemoteNotification:fetchCompletionHandler: method.
 @param completionBlock Will return NSError in case of an error. sessionInfo may include device details and timeout details. availableTrustLevels is an array of PIDActionType wrapped with NSNumber, for example [@(PIDTrustLevelPrimary),@(PIDTrustLevelTrusted)] (Optional)
 */
+ (void)handleRemoteNotification:(nonnull NSDictionary *)userInfo
                   completion:(nullable void(^)(PIDRemoteNotificationType remoteNotificationType, NSArray * _Nullable availableTrustLevels, NSDictionary * _Nullable sessionInfo, NSError * _Nullable error))completionBlock;

/**
 Tells PingID to perform the custom action specified by a remote notification. Should be within application:handleActionWithIdentifier:forRemoteNotification:

 @param identifier The identifier received in the AppDelegate application:handleActionWithIdentifier:forRemoteNotification: method.
 @param userInfo The userInfo received in the AppDelegate application:handleActionWithIdentifier:forRemoteNotification: method.
 @param completionBlock Will return NSError in case of an error.
 */
+ (void)handleActionWithIdentifier:(nonnull NSString *)identifier forRemoteNotification:(nonnull NSDictionary *)userInfo
                  completionBlock:(nullable void(^)(NSError * _Nullable error))completionBlock;

/**
 The developer can decide if PingID uses sandbox or production APNS

 @param debugMode YES to use sandbox APNS. Defaults to NO
 */
+ (void)setDebugMode:(BOOL)debugMode;


/**
 The developer can decide if the device will stay trusted after app reinstallation. This method should only be called once per SDK initialization. 
 
 @param keep YES to save state. Defaults to NO
 */
+ (void)setKeepTrustedDeviceAfterReinstall:(BOOL)keep;

/**
 Checks if the device is trusted
 
 @return Returns YES if the device is trusted.
 */
+ (BOOL)isDeviceTrusted;

/**
 Send logs to PingID SDK server

 @param completionBlock will return NSError in case of an error.
 */
+ (void)sendLogs:(nullable void (^)(NSString * _Nullable supportId, NSError * _Nullable error))completionBlock;

/**
 **************** Warning  ****************
 Using this method will remove the trusted connection between the iOS SDK and the PingID SDK 
 server in a one sided manner, where only the iOS side will be removed.
 This method should not be used when logging out of your account.
 */
+(void)removePingIDSDKLocalData;






typedef NS_ENUM(NSUInteger, PIDError)
{
    PIDErrorInternal                    =   -10000,
    PIDErrorWithPairing                 =   -10001,
    PIDErrorAppDisabled                 =   -10002,
    PIDErrorWrongAppID                  =   -10003,
    PIDErrorRemoteNotificationTimeout   =   -10004,
    PIDErrorNoInternetConnection        =   -10005,
    PIDErrorWrongSignature              =   -10006,
    PIDErrorProblemWithPublicKey        =   -10007,
    PIDErrorMissingPermissions          =   -10008,
    PIDErrorReachability                =   -10009,
    PIDErrorServerResponse              =   -10010,
    PIDErrorServerInternal              =   -10011,
    PIDErrorWithServerPayload           =   -10012,
    PIDErrorSelectionIsMissing          =   -10013,
    PIDErrorDeviceTokenIsMissing        =   -10014,
    PIDErrorExpiredOrWrongAuthToken     =   -10015,
    PIDErrorWrongAppIDFromAuthToken     =   -10016,
    PIDErrorWrongAccountIDFromAuthToken =   -10017,
    PIDErrorUserIsSuspended             =   -10018,
    PIDErrorUsernameNotPairedForToken   =   -10019,
    PIDErrorAuthTokenAlreadyUsed        =   -10020,
    PIDErrorRootedDevice                =   -10021,
    PIDErrorMissingAppId                =   -10022

};



@end

@interface PIDUserSelectionObject : NSObject

/**
 @param action Can be any of the PIDActionType.
 @param trustLevel Can be any of the PIDTrustLevel.
 @param userName selected username.
 @return Returns the PIDUserSelectionObject.
 */
-(nonnull PIDUserSelectionObject *)initWithAction:(PIDActionType)action trustLevel:(PIDTrustLevel)trustLevel userName:(NSString *_Nullable)userName;

@property (nonatomic, assign)   PIDIgnoreIntervalUnit ignoreIntervalUnit; //Can be any of the PIDIgnoreIntervalUnit types   (Optional)
@property (nonatomic, assign)   NSInteger ignoreInterval;                 //Units of time                                   (Optional)
@property (readonly)            PIDActionType action;
@property (readonly)            PIDTrustLevel trustLevel;
@property (readonly)            NSString * _Nullable userName;


- (nonnull PIDUserSelectionObject *)initWithAction:(PIDActionType)action trustLevel:(PIDTrustLevel)trustLevel DEPRECATED_MSG_ATTRIBUTE("Use -initWithAction:trustLevel:username: instead");
- (nonnull instancetype)init NS_UNAVAILABLE;

@end

