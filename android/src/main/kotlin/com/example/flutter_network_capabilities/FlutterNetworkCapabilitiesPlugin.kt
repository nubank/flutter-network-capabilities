package com.example.flutter_network_capabilities

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.telephony.PhoneStateListener
import android.telephony.SignalStrength
import android.telephony.TelephonyCallback
import android.telephony.TelephonyManager
import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import java.util.concurrent.Executor


/** FlutterNetworkCapabilitiesPlugin */
class FlutterNetworkCapabilitiesPlugin : FlutterPlugin, MethodCallHandler {
    private lateinit var channel: MethodChannel
    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var telephonyManager: TelephonyManager
    private var mainExecutor: Executor? = null

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel =
            MethodChannel(flutterPluginBinding.binaryMessenger, "flutter_network_capabilities")
        channel.setMethodCallHandler(this)

        val context = flutterPluginBinding.applicationContext
        connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            mainExecutor = context.mainExecutor
        }
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: MethodChannel.Result) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && mainExecutor != null) {
            telephonyManager.registerTelephonyCallback(
                mainExecutor!!,
                object : TelephonyCallback(), TelephonyCallback.SignalStrengthsListener {
                    override fun onSignalStrengthsChanged(signalStrength: SignalStrength) {
                        println("[HUE] Signal Strength ${signalStrength.gsmSignalStrength}")
                        println("[HUE] Signal Strength ${signalStrength.level}")
                        println("[HUE] Signal Strength ${signalStrength.cellSignalStrengths}")
                        println("[HUE] Signal Strength ${signalStrength.describeContents()}")
                    }
                })
        }

        telephonyManager.listen(object : PhoneStateListener() {
            override fun onSignalStrengthsChanged(signalStrength: SignalStrength) {
                super.onSignalStrengthsChanged(signalStrength)
                println("[HUE] Signal Strength ${signalStrength.gsmSignalStrength}")
                println("[HUE] Signal Strength ${signalStrength.describeContents()}")
            }
        }, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val signalStrength = networkCapabilities?.signalStrength
                println("[HUE-new]: $signalStrength")
            }

//            when {
//                networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true -> {/* WIFI */}
//                networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true -> {
//                    // Cellular data, we can check the generation of the network
//                    when {
//                        networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
//                                !networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_RESTRICTED) &&
//                                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) -> {/* 2G */}
//                        networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_IA) -> {/* 3G */}
//                        networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_LTE) -> {/* 4G */}
//                        networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NR)
//                                || networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NR_MMWAVE) -> {/* 5G*/ }
//                    }
//                }

            if (networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    println("[HUE-new] ${networkCapabilities.capabilities}")
                }
                println("[HUE-new] has2g: ${networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)}")
                println("[HUE-new] has3g: ${networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_IA)}")
            }
        }

        val networkInfo = connectivityManager.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            println(networkInfo.detailedState.name)
            when (networkInfo.type) {
                ConnectivityManager.TYPE_MOBILE -> {
                    val subtype: Int = networkInfo.subtype
                    println(subtype)
                    when (subtype) {
                        TelephonyManager.NETWORK_TYPE_LTE -> println("[HUE-old] - 4g")
                        TelephonyManager.NETWORK_TYPE_HSDPA, TelephonyManager.NETWORK_TYPE_HSPA, TelephonyManager.NETWORK_TYPE_HSUPA -> println(
                            "[HUE-old] - 3g"
                        )

                        else -> println("[HUE-old] - 2g or unknown")
                    }
                }

                ConnectivityManager.TYPE_WIFI -> println("[HUE] - wifi")
            }
        }
        if (call.method == "getPlatformVersion") {
            result.success("Android ${android.os.Build.VERSION.RELEASE}")
        } else {
            result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }
}
