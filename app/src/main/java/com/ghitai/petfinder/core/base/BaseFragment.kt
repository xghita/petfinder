package com.ghitai.petfinder.core.base

import androidx.fragment.app.Fragment
import com.ghitai.petfinder.R
import com.ghitai.petfinder.ui.PetViewModel
import com.ghitai.petfinder.ui.event.PetActions
import com.ghitai.petfinder.extensions.createLoadingDialog
import com.ghitai.petfinder.extensions.getMessageDialog

abstract class BaseFragment : Fragment() {

    private val loadingDialog by lazy { createLoadingDialog() }

    protected fun observePetEvents(viewModel: PetViewModel) {
        viewModel.eventsState().observe(viewLifecycleOwner) {
            it.petLoadingUI?.let {
                loadingDialog.show()
            }
            it.petErrorUI?.let { errorUI ->
                viewModel.onEventAcknowledged(PetActions.PET_DISPLAY)
                loadingDialog.dismiss()

                getMessageDialog(
                    title = getString(R.string.generic_error_title),
                    message = errorUI.description ?: getString(R.string.generic_error_description),
                    action = getString(R.string.generic_error_action)
                ).show()
            }
            (it.petIdleUI ?: it.petSuccessUI)?.let {
                viewModel.onEventAcknowledged(PetActions.PET_DISPLAY)
                loadingDialog.dismiss()
            }
        }
    }
}