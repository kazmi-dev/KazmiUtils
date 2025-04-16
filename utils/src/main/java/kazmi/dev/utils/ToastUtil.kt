package kazmi.dev.utils

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

object ToastUtil {
    private var toast: Toast? = null

    private fun cancelAndShowToast(context: Context, msg: String, duration: Int) {
        toast?.cancel()
        toast = Toast.makeText(context, msg, duration)
        toast?.show()
    }

    fun shortToast(context: Context, msg: String) {
        kotlin.runCatching {
            cancelAndShowToast(context, msg, Toast.LENGTH_SHORT)
        }.onFailure {
            it.printStackTrace()
        }
    }

    fun longToast(context: Context, msg: String) {
        kotlin.runCatching {
            cancelAndShowToast(context, msg, Toast.LENGTH_LONG)
        }.onFailure {
            it.printStackTrace()
        }
    }

    fun Fragment.shortToast(msg: String) {
        kotlin.runCatching {
            cancelAndShowToast(requireContext(), msg, Toast.LENGTH_SHORT)
        }.onFailure {
            it.printStackTrace()
        }
    }

    fun Fragment.longToast(msg: String) {
        kotlin.runCatching {
            cancelAndShowToast(requireContext(), msg, Toast.LENGTH_LONG)
        }.onFailure {
            it.printStackTrace()
        }
    }

    fun Activity.shortToast(msg: String) {
        kotlin.runCatching {
            cancelAndShowToast(this, msg, Toast.LENGTH_SHORT)
        }.onFailure {
            it.printStackTrace()
        }
    }

    fun Activity.longToast(msg: String) {
        kotlin.runCatching {
            cancelAndShowToast(this, msg, Toast.LENGTH_LONG)
        }.onFailure {
            it.printStackTrace()
        }
    }
}