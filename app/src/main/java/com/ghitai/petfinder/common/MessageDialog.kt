package com.ghitai.petfinder.common

import android.content.Context
import android.view.View
import com.ghitai.petfinder.R
import com.ghitai.petfinder.databinding.DialogMessageBinding
import com.ghitai.petfinder.core.base.Dialog

class MessageDialog(
    context: Context,
    private var title: String,
    private var message: String,
    private var primaryButtonLabel: String = "",
) : Dialog<DialogMessageBinding>(context, R.layout.dialog_message), View.OnClickListener {

    private var onPrimaryClicked: ((v: View) -> Unit)? = null

    private var onCloseClicked: ((v: View) -> Unit)? = null

    init {
        binding.apply {
            title = this@MessageDialog.title
            message = this@MessageDialog.message
            buttonText = this@MessageDialog.primaryButtonLabel
            close.setOnClickListener(this@MessageDialog)
            primaryButton.setOnClickListener(this@MessageDialog)

            createDialogAnimation(popupView = dialogContainer, animId = R.anim.center_translate_dialog_in)
        }
    }

    override fun show() {
        super.show()
        binding.dialogContainer.visibility = View.VISIBLE
    }

    override fun onClick(v: View) {
        dismiss()

        when (v.id) {
            R.id.close -> onCloseClicked?.invoke(v)
            R.id.primaryButton -> onPrimaryClicked?.invoke(v)
        }
    }
}
