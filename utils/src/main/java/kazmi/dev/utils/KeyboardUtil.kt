package kazmi.dev.utils

import android.app.Activity
import android.content.Context
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES
import android.os.IBinder
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

object KeyboardUtil {

    private fun getInputMethodManager(context: Context): InputMethodManager?{
        return context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    }

    private fun hideMyKeyboard(context: Context, windowToken: IBinder?) {
        getInputMethodManager(context)?.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun showMyKeyboard(context: Context, view: View) {
        view.requestFocus()
        getInputMethodManager(context)?.let {
            if (SDK_INT >= VERSION_CODES.P)
                it.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
            else
                it.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }
    }

    fun View.showKeyboard(){
        showMyKeyboard(context, this)
    }

    fun View.hideKeyboard(){
        hideMyKeyboard(context, windowToken)
    }

    fun Fragment.hideKeyboard(){
        getInputMethodManager(requireContext())?.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    fun Activity.hideKeyboard(){
        val view = currentFocus ?: View(this)
        getInputMethodManager(this)?.hideSoftInputFromWindow(view.windowToken, 0)
    }

}