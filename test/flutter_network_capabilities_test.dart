import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_network_capabilities/flutter_network_capabilities.dart';
import 'package:flutter_network_capabilities/flutter_network_capabilities_platform_interface.dart';
import 'package:flutter_network_capabilities/flutter_network_capabilities_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockFlutterNetworkCapabilitiesPlatform
    with MockPlatformInterfaceMixin
    implements FlutterNetworkCapabilitiesPlatform {

  @override
  Future<Map<String, String>> getNetworkInfo() => Future.value({"testing": "testing"});
}

void main() {
  final FlutterNetworkCapabilitiesPlatform initialPlatform = FlutterNetworkCapabilitiesPlatform.instance;

  test('$MethodChannelFlutterNetworkCapabilities is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelFlutterNetworkCapabilities>());
  });

  test('getNetworkInfo', () async {
    FlutterNetworkCapabilities flutterNetworkCapabilitiesPlugin = FlutterNetworkCapabilities();
    MockFlutterNetworkCapabilitiesPlatform fakePlatform = MockFlutterNetworkCapabilitiesPlatform();
    FlutterNetworkCapabilitiesPlatform.instance = fakePlatform;

    expect(await flutterNetworkCapabilitiesPlugin.getNetworkInfo(), {"testing": "testing"});
  });
}
