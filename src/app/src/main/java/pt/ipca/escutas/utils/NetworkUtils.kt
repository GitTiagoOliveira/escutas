package pt.ipca.escutas.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.telephony.TelephonyManager

object NetworkUtils {

    /**
     * Validates if application has network connection
     *
     * @param context
     * @return
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    /**
     * Validates if application has mobile data enabled
     *
     * @param context
     * @return
     */
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

    /**
     * Validates if application has wifi connection enabled
     *
     * @param context
     * @return
     */
    fun isWifiOn(context: Context): Boolean {
        val wifi = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        return wifi.isWifiEnabled
    }
}
