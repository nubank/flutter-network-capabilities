## Introduction
--------
This package allows monitoring of Network Capabilities from Android devices.

## Features
--------
Right now, the following information are available for build versions greater or equal `Build.VERSION_CODES.Q`:
- `link_upstream_bandwidth_kbps`
- `link_downstream_bandwidth_kbps`
- `signal_strength`
- `transport_type` (`wifi` or `cellular`)

For versions older than Build.VERSION_CODES.Q, it also provides the following information:
- `legacy_transport_type` (`wifi` or `cellular`)
- `legacy_transport_subtype` (possible values can be found [here](https://github.com/nubank/flutter-network-capabilities/blob/2db3c889499ac0e2ca14a56387292b513f7ce377/android/src/main/kotlin/com/example/flutter_network_capabilities/FlutterNetworkCapabilitiesPlugin.kt#L18-L49))

## Usage
--------
```dart
 FlutterNetworkCapabilities flutterNetworkCapabilitiesPlugin = FlutterNetworkCapabilities();
 final info = await flutterNetworkCapabilitiesPlugin.getNetworkInfo();
 debugPrint(info);
```

## Contributing
--------
The next step for contributing would be to implement the plugin for iOS as well. You can open a Pull Request and it will be reviewed by one of the mainteiners.

## References
--------
- [Android Network Capabilities](https://developer.android.com/reference/android/net/NetworkCapabilities)