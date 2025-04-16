package kazmi.dev.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

object NetworkUtil {

    private fun getNetworkCapabilities(context: Context): NetworkCapabilities?{
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork
        return cm.getNetworkCapabilities(network)

    }

    fun isNetworkConnected(context: Context): Boolean{
        val networkCapabilities = getNetworkCapabilities(context)?: return false
        return when{
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> true
            else -> false
        }
    }

    fun isWifiConnected(context: Context): Boolean{
        return getNetworkCapabilities(context)?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true
    }

    fun isCellularConnected(context: Context): Boolean{
        return getNetworkCapabilities(context)?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true
    }

    fun internetObserver(context: Context) : Flow<Boolean> = callbackFlow {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCallback = object : NetworkCallback(){
            override fun onAvailable(network: Network) {
                trySend(true).isSuccess
            }

            override fun onLost(network: Network) {
                trySend(false).isSuccess
            }

            override fun onUnavailable() {
                trySend(false).isSuccess
            }
        }
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        cm.registerNetworkCallback(networkRequest, networkCallback)

        trySend(isNetworkConnected(context))

        awaitClose {
            cm.unregisterNetworkCallback(networkCallback)
        }

    }


}