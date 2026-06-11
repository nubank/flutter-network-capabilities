## 0.0.3

* Fix iOS AppHang: dispatch `SCNetworkReachabilityGetFlags` and `CTTelephonyNetworkInfo()`
  to a background queue to avoid blocking the main thread during XPC calls.
* Fix typo: `transport_subtype` for WiFi was `"unknwon"`, now `"unknown"`.

## 0.0.1

* Initial release supporting the fields:
- `transport_type` (wifi or celluar)
- `transport_subtype`
- `bandwidth` and `signal_strength` information for specific APIs of Android (not supported on iOS)
