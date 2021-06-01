package pt.ipca.escutas.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.telephony.TelephonyManager
import com.facebook.FacebookSdk.getApplicationContext

object NetworkUtils {

    fun isNetworkAvailable(): Boolean {
        val connectivityManager = getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun checkMobileDataIsEnabled(context: Context): Boolean {
        var mobileYN = false
        val tm =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        if (tm.simState == TelephonyManager.SIM_STATE_READY) {
            val tel =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val dataState = tel.dataState
            if (dataState != TelephonyManager.DATA_DISCONNECTED) {
                mobileYN = true
            }
        }
        return mobileYN
    }

    fun isWifiOn(): Boolean {
        val wifi = getApplicationContext().getSystemService(Context.WIFI_SERVICE) as WifiManager
        return wifi.isWifiEnabled
    }

}