import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_network_capabilities/flutter_network_capabilities_method_channel.dart';

void main() {
  MethodChannelFlutterNetworkCapabilities platform = MethodChannelFlutterNetworkCapabilities();
  const MethodChannel channel = MethodChannel('flutter_network_capabilities');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await platform.getPlatformVersion(), '42');
  });
}
