package com.ghitai.petfinder.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.ghitai.petfinder.R
import com.ghitai.petfinder.databinding.ActivityPetBinding
import com.ghitai.petfinder.ui.detail.PetDetailFragment
import com.ghitai.petfinder.ui.list.PetListFragment

class PetActivity : FragmentActivity() {

       val navController: NavController by lazy {
            (supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment).navController
        }

        private lateinit var binding: ActivityPetBinding

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = DataBindingUtil
                .inflate(
                    layoutInflater,
                    R.layout.activity_pet,
                    null,
                    false
                )
            setContentView(binding.root)
            setupToolbar()
        }


        private fun setupToolbar() {
            navController.addOnDestinationChangedListener { _, destination, arguments ->
                when (destination.label) {
                    PetListFragment::class.java.simpleName -> {
                        binding.toolbar.title = ""
                    }
                   PetDetailFragment::class.java.simpleName -> {
                       binding.toolbar.title =
                            arguments?.getString("pet_name") ?: getString(R.string.pet_detail_title)
                    }
                }
            }
        }
    }