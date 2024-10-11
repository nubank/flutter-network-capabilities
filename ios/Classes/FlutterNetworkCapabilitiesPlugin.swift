import Flutter
import UIKit
import SystemConfiguration
import CoreTelephony
import Foundation

public class FlutterNetworkCapabilitiesPlugin: NSObject, FlutterPlugin {
    public static func register(with registrar: FlutterPluginRegistrar) {
        let channel = FlutterMethodChannel(name: "flutter_network_capabilities", binaryMessenger: registrar.messenger())
        let instance = FlutterNetworkCapabilitiesPlugin()
        registrar.addMethodCallDelegate(instance, channel: channel)
    }
    
    public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
        if (call.method != "getNetworkInfo") {
            result(FlutterMethodNotImplemented)
            return
        }
        result(getNetworkInfoResult())
    }
    
    //    private func getTelephonyNetworkInfo() {
    //        let networkInfo = CTTelephonyNetworkInfo()
    //        let networkString = networkInfo.currentRadioAccessTechnology
    //
    //        if networkString == CTRadioAccessTechnologyLTE{
    //          // LTE (4G)
    //        }else if networkString == CTRadioAccessTechnologyWCDMA{
    //          // 3G
    //        }else if networkString == CTRadioAccessTechnologyEdge{
    //          // EDGE (2G)
    //        }
    //    }
    
    // TODO: adjust signature to map
    private func getNetworkInfoResult() -> [String : String] {
        guard let reachability = SCNetworkReachabilityCreateWithName(kCFAllocatorDefault, "www.google.com") else {
            return [:]
        }
        
        var flags = SCNetworkReachabilityFlags()
        SCNetworkReachabilityGetFlags(reachability, &flags)
        
        let isReachable = flags.contains(.reachable)
        let isWWAN = flags.contains(.isWWAN)
        
        if isReachable {
            var networkInfoResult = [String: String]()
            if isWWAN {
                let networkInfo = CTTelephonyNetworkInfo()
                networkInfoResult["transport_type"] = "cellular"
                if #available(iOS 12.0, *) {
                    let radioAccessTechnology = networkInfo.serviceCurrentRadioAccessTechnology
                    if let radioAccessTypeName = radioAccessTechnology?.first?.value {
                        let transportSubType: String?
                        // TODO: mapear igual Android
    //                    switch radioAccessTypeName {
    //                        case CTRadioAccessTechnologyGPRS, CTRadioAccessTechnologyEdge, CTRadioAccessTechnologyCDMA1x:
    //                            return "2G"
    //                        case CTRadioAccessTechnologyLTE:
    //                            return "4G"
    //                        default:
    //                            return "3G"
    //                    }
                    }
                    
                    networkInfoResult["transport_subtype"] = "unknwon"
                }
            } else {
                networkInfoResult["transport_type"] = "wifi"
                networkInfoResult["transport_subtype"] = "unknwon"
            }
            
            return networkInfoResult
        }
        
        return [:]
    }
}
    
    /**
     following: CTRadioAccessTechnologyGPRS, CTRadioAccessTechnologyEdge, CTRadioAccessTechnologyCDMA1x -> 2g. CTRadioAccessTechnologyWCDMA, CTRadioAccessTechnologyHSDPA, CTRadioAccessTechnologyHSUPA, CTRadioAccessTechnologyCDMAEVDORev0, CTRadioAccessTechnologyCDMAEVDORevA, CTRadioAccessTechnologyCDMAEVDORevB, CTRadioAccessTechnologyeHRPD -> 3G CTRadioAccessTechnologyLTE -> 4G else -> 5G.
     */
