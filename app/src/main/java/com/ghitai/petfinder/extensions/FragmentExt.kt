package com.ghitai.petfinder.extensions

import androidx.fragment.app.Fragment
import com.ghitai.petfinder.common.LoadingDialog
import com.ghitai.petfinder.common.MessageDialog

fun Fragment.createLoadingDialog(
    cancelable: Boolean = false,
    cancelCallback: (() -> Unit)? = null
) = LoadingDialog(requireContext()).also {
    it.setCancelable(cancelable)
    it.cancelCallback = cancelCallback
}

fun Fragment.getMessageDialog(
    title: String,
    message: String,
    action: String
): MessageDialog {
    return MessageDialog(
        requireContext(),
        title,
        message,
        action
    )
}