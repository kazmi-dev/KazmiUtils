package kazmi.dev.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import kazmi.dev.utils.databinding.DialogCutomBasicBinding

object DialogUtil {

    private fun Context.getAlertDialog(): AlertDialog{
        val dialog = AlertDialog.Builder(this).create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    fun showBasicDialog(
        context: Context,
        title: String,
        message: String,
        positiveBtnText: String = context.getString(R.string.ok),
        negativeBtnText: String = context.getString(R.string.cancel),
        positiveBtnClick: () -> Unit,
        negativeBtnClick: () -> Unit
    ){
        val layoutInflater = LayoutInflater.from(context)
        val binding = DialogCutomBasicBinding.inflate(layoutInflater)
        binding.titleText.text = title
        binding.messageText.text = message
        binding.positiveBtn.text = positiveBtnText
        binding.negativeBtn.text = negativeBtnText

        val dialog = context.getAlertDialog()

        binding.positiveBtn.setOnClickListener {
            dialog.dismiss()
            positiveBtnClick()
        }
        binding.negativeBtn.setOnClickListener {
            dialog.dismiss()
            negativeBtnClick()
        }

        dialog.setView(binding.root)
        dialog.show()

    }

}