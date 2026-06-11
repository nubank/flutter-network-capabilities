import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_network_capabilities/flutter_network_capabilities_method_channel.dart';

void main() {
  MethodChannelFlutterNetworkCapabilities platform = MethodChannelFlutterNetworkCapabilities();
  const MethodChannel channel = MethodChannel('flutter_network_capabilities');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return {"testing": "testing"};
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getNetworkInfo', () async {
    expect(await platform.getNetworkInfo(), {"testing": "testing"});
  });

  test('getNetworkInfo returns empty map when native returns null', () async {
    channel.setMockMethodCallHandler((MethodCall methodCall) async => null);

    expect(await platform.getNetworkInfo(), isEmpty);
  });

  test('getNetworkInfo returns empty map on PlatformException', () async {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      throw PlatformException(code: 'UNAVAILABLE');
    });

    expect(await platform.getNetworkInfo(), isEmpty);
  });
}
