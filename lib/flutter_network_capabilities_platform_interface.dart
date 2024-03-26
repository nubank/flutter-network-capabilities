import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'flutter_network_capabilities_method_channel.dart';

abstract class FlutterNetworkCapabilitiesPlatform extends PlatformInterface {
  /// Constructs a FlutterNetworkCapabilitiesPlatform.
  FlutterNetworkCapabilitiesPlatform() : super(token: _token);

  static final Object _token = Object();

  static FlutterNetworkCapabilitiesPlatform _instance = MethodChannelFlutterNetworkCapabilities();

  /// The default instance of [FlutterNetworkCapabilitiesPlatform] to use.
  ///
  /// Defaults to [MethodChannelFlutterNetworkCapabilities].
  static FlutterNetworkCapabilitiesPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [FlutterNetworkCapabilitiesPlatform] when
  /// they register themselves.
  static set instance(FlutterNetworkCapabilitiesPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
