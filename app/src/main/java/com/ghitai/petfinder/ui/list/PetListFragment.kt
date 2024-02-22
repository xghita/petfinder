package com.ghitai.petfinder.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.ghitai.petfinder.R
import com.ghitai.petfinder.core.base.BaseFragment
import com.ghitai.petfinder.databinding.FragmentPetListBinding
import com.ghitai.petfinder.extensions.getMessageDialog
import com.ghitai.petfinder.ui.PetActivity
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class PetListFragment : BaseFragment() {

    private lateinit var binding: FragmentPetListBinding
    private lateinit var petAdapter: PetAdapter
    private val petListViewModel: PetListViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_pet_list,
            container,
            false
        )

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        observePetEvents(petListViewModel)

        petListViewModel.petListViewState().observe(viewLifecycleOwner) { viewState ->
            if (viewState.animals.isNullOrEmpty()) {
                getMessageDialog(
                    title = getString(R.string.pet_list_nodata_title),
                    message = getString(R.string.pet_list_nodata_title_description),
                    action = getString(R.string.pet_list_nodata_title_action),
                )
                return@observe
            }

            petAdapter.updateList(viewState.animals)
        }
    }

    private fun initViews() {
        petAdapter = PetAdapter { petId, name, cardView ->
            val extras =
                FragmentNavigatorExtras(cardView to getString(R.string.card_view_transition))

            (requireActivity() as PetActivity).navController.navigate(
                PetListFragmentDirections.actionPetListToPetDetail(
                    name,
                    petId,
                ),
                extras
            )
        }

        binding.petsRv.adapter = petAdapter
    }
}