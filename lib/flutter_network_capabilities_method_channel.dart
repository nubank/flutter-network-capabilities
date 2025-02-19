import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'flutter_network_capabilities_platform_interface.dart';

/// An implementation of [FlutterNetworkCapabilitiesPlatform] that uses method channels.
class MethodChannelFlutterNetworkCapabilities
    extends FlutterNetworkCapabilitiesPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('flutter_network_capabilities');

  @override
  Future<Map<String, String>> getNetworkInfo() async {
    final networkInfo = await methodChannel
        .invokeMapMethod<String, String>('getNetworkInfo');

    if (networkInfo == null) {
      return {};
    }

    return networkInfo;
  }
}
