package com.ghitai.petfinder.common

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import com.ghitai.petfinder.R
import com.ghitai.petfinder.databinding.DialogLoadingBinding
import com.ghitai.petfinder.core.base.Dialog


private const val MIN_SHOW_TIME_MS = 300
private const val MIN_DELAY_MS = 300L

/**
 * LoadingDialog implements a Dialog that waits a minimum time to be
 * dismissed before showing. Once visible, the loading dialog will be visible for
 * a minimum amount of time to avoid "flashes" in the UI when an event could take
 * a largely variable time to complete (from none, to a user perceivable amount)
 */
class LoadingDialog(context: Context) :
    Dialog<DialogLoadingBinding>(context, R.layout.dialog_loading) {

    private var startTime: Long = -1
    private var postedHide = false
    private var postedShow = false
    private var dismissed = false

    private val handler: Handler by lazy { Handler(Looper.getMainLooper()) }

    private val delayedHide = Runnable {
        postedHide = false
        startTime = -1
        super.cancel()
    }

    private val delayedShow = Runnable {
        postedShow = false
        if (!dismissed) {
            startTime = System.currentTimeMillis()
            try {
                super.show()
            } catch (_: WindowManager.BadTokenException) {
            }
        }
    }

    override fun show() {
        // Reset the start time.
        startTime = -1
        dismissed = false
        removeCallbacks(delayedHide)
        postedHide = false
        if (!postedShow) {
            handler.postDelayed(delayedShow, MIN_DELAY_MS)
            postedShow = true
        }
    }

    override fun cancel() {
        performDismissAction { super.cancel() }
    }

    override fun dismiss() {
        performDismissAction { super.dismiss() }
    }

    override fun hide() {
        performDismissAction { super.hide() }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        removeCallbacks()
    }

    private fun removeCallbacks() {
        removeCallbacks(delayedHide)
        removeCallbacks(delayedShow)
    }

    private fun removeCallbacks(runnable: Runnable) {
        handler.removeCallbacks(runnable)
    }

    private fun performDismissAction(action: () -> Unit) {
        dismissed = true
        removeCallbacks(delayedShow)
        postedShow = false
        val diff: Long = System.currentTimeMillis() - startTime
        if (diff >= MIN_SHOW_TIME_MS || startTime == -1L) {
            // The loading dialog has been shown long enough
            // OR was not shown yet. If it wasn't shown yet,
            // it will just never be shown.
            action()
        } else {
            // The loading dialog is shown, but not long enough,
            // so put a delayed message in to hide it when its been
            // shown long enough.
            if (!postedHide) {
                handler.postDelayed(delayedHide, MIN_SHOW_TIME_MS - diff)
                postedHide = true
            }
        }
    }
}