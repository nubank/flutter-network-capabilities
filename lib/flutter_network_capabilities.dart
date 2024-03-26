
import 'flutter_network_capabilities_platform_interface.dart';

class FlutterNetworkCapabilities {
  Future<String?> getPlatformVersion() {
    return FlutterNetworkCapabilitiesPlatform.instance.getPlatformVersion();
  }
}
