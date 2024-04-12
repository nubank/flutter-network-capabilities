package com.example.flutter_network_capabilities

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.telephony.TelephonyManager
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler

/** FlutterNetworkCapabilitiesPlugin */
class FlutterNetworkCapabilitiesPlugin : FlutterPlugin, MethodCallHandler {
    private lateinit var channel: MethodChannel
    private lateinit var connectivityManager: ConnectivityManager

    private val networkMobileSubtypes: Map<Int, String> by lazy {
        mutableMapOf(
            TelephonyManager.NETWORK_TYPE_GPRS to "network_type_gprs",
            TelephonyManager.NETWORK_TYPE_EDGE to "network_type_edge",
            TelephonyManager.NETWORK_TYPE_UMTS to "network_type_umts",
            TelephonyManager.NETWORK_TYPE_CDMA to "network_type_cdma",
            TelephonyManager.NETWORK_TYPE_EVDO_0 to "network_type_evdo_0",
            TelephonyManager.NETWORK_TYPE_EVDO_A to "network_type_evdo_a",
            TelephonyManager.NETWORK_TYPE_1xRTT to "network_type_1xrtt",
            TelephonyManager.NETWORK_TYPE_HSDPA to "network_type_hsdpa",
            TelephonyManager.NETWORK_TYPE_HSUPA to "network_type_hsupa",
            TelephonyManager.NETWORK_TYPE_HSPA to "network_type_hspa",
            TelephonyManager.NETWORK_TYPE_IDEN to "network_type_iden",
            TelephonyManager.NETWORK_TYPE_EVDO_B to "network_type_evdo_b",
            TelephonyManager.NETWORK_TYPE_LTE to "network_type_lte",
            TelephonyManager.NETWORK_TYPE_EHRPD to "network_type_ehrpd",
            TelephonyManager.NETWORK_TYPE_HSPAP to "network_type_hspap",
        ).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                put(TelephonyManager.NETWORK_TYPE_GSM, "network_type_gsm")
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                put(TelephonyManager.NETWORK_TYPE_TD_SCDMA, "network_type_td_scdma")
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                put(TelephonyManager.NETWORK_TYPE_IWLAN, "network_type_iwlan")
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(TelephonyManager.NETWORK_TYPE_NR, "network_type_nr")
            }
        }
    }

    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel =
            MethodChannel(flutterPluginBinding.binaryMessenger, "flutter_network_capabilities")
        channel.setMethodCallHandler(this)

        val context = flutterPluginBinding.applicationContext
        connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        if (call.method != "getNetworkInfo") {
            result.notImplemented()
        }

        val networkInfoResult = mutableMapOf<String, String>()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                networkCapabilities?.let {
                    networkInfoResult["network_capabilities_signal_strength"] = it.signalStrength
                    networkInfoResult["network_capabilities_link_downstream_bandwidth_kbps"] =
                        it.linkDownstreamBandwidthKbps
                    networkInfoResult["network_capabilities_link_upstream_bandwidth_kbps"] = it.linkUpstreamBandwidthKbps
                }
            }

            when {
                networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true ->
                    networkInfoResult["network_capabilities_transport_type"] = "wifi"

                networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true ->
                    networkInfoResult["network_capabilities_transport_type"] = "cellular"
            }
        }

        // taking advantage of older Android API's that has more information
        val networkInfo = connectivityManager.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            when (networkInfo.type) {
                ConnectivityManager.TYPE_MOBILE -> {
                    networkInfoResult["network_info_transport_type"] = "celullar"
                    networkInfoResult["network_info_transport_type_subtype"] =
                        networkMobileSubtypes[networkInfo.subtype] ?: "unknown"
                }

                ConnectivityManager.TYPE_WIFI -> networkInfoResult["network_info_transport_type"] = "wifi"
            }
        }

        result.success(networkInfoResult)
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }
}
