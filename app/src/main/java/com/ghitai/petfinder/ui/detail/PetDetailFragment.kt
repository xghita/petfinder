package com.ghitai.petfinder.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.transition.TransitionInflater
import com.ghitai.petfinder.R
import com.ghitai.petfinder.core.AppGlide
import com.ghitai.petfinder.core.base.BaseFragment
import com.ghitai.petfinder.databinding.FragmentPetDetailBinding
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf

class PetDetailFragment : BaseFragment() {

    private lateinit var petDetailViewModel: PetDetailViewModel
    private lateinit var binding: FragmentPetDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.fade)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val petId = requireArguments().getInt("pet_id")

        petDetailViewModel = get { parametersOf(petId) }

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_pet_detail,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observePetEvents(petDetailViewModel)

        petDetailViewModel.petDetailViewState().observe(viewLifecycleOwner) { viewState ->
            binding.apply {

                this.data = viewState
                AppGlide.load(viewState.photoUrl, avatarIv)
            }
        }
    }
}