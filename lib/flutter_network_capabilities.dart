
import 'flutter_network_capabilities_platform_interface.dart';

class FlutterNetworkCapabilities {
  Future<Map<String, String>> getNetworkInfo() {
    return FlutterNetworkCapabilitiesPlatform.instance.getNetworkInfo();
  }
}
