## Introduction
--------
This package allows monitoring of Network Capabilities from Android and iOS devices.

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
Contributions are made to this repo via Issues and Pull Requests (PRs).

### Issues

Issues should be used to report problems with the library, request a new feature, or to discuss potential changes before a PR is created.

If you find an Issue that addresses the problem you're having, please add your own reproduction information to the existing issue rather than creating a new one. Adding a [reaction](https://github.blog/2016-03-10-add-reactions-to-pull-requests-issues-and-comments/) can also help be indicating to our maintainers that a particular problem is affecting more than just the reporter.

### Pull Requests

PRs to our libraries are always welcome and can be a quick way to get your fix or improvement slated for the next release. In general, PRs should:

- Only fix/add the functionality in question **OR** address wide-spread whitespace/style issues, not both.
- Add unit or integration tests for fixed or changed functionality (if a test suite already exists).
- Address a single concern in the least number of changed lines as possible.

For changes that address core functionality or would require breaking changes (e.g. a major release), it's best to open an Issue to discuss your proposal first. This is not required but can save time creating and reviewing changes.

In general, we follow the ["fork-and-pull" Git workflow](https://github.com/susam/gitpr)

1. Fork the repository to your own Github account
2. Clone the project to your machine
3. Create a branch locally with a succinct but descriptive name
4. Commit changes to the branch
5. Following any formatting and testing guidelines specific to this repo
6. Push changes to your fork
7. Open a PR in our repository

## References
--------
- [Android Network Capabilities](https://developer.android.com/reference/android/net/NetworkCapabilities)
- [iOS Network Info](https://developer.apple.com/documentation/coretelephony/cttelephonynetworkinfo)