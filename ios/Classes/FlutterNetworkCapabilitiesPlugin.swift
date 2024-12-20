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
                            
                        networkInfoResult["transport_subtype"] = radioAccessTypeName.lowercased().replacingOccurrences(of: "ctradioaccesstechnology", with: "network_type_")
                    
                    } else {
                        networkInfoResult["transport_subtype"] = "unknown"
                    }
                    
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
