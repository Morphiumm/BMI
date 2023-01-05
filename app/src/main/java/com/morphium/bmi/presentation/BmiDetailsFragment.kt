package com.morphium.bmi.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.morphium.bmi.R
import com.morphium.bmi.databinding.FragmentBmiDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BmiDetailsFragment @Inject constructor() : Fragment(R.layout.fragment_bmi_details) {

    private lateinit var binding: FragmentBmiDetailsBinding
    private val  viewModule: BmiViewModule by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBmiDetailsBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity?)?.supportActionBar?.title = getString(R.string.add_bmi_details)
        binding.apply {
            cvCalculate.setOnClickListener {
                if (etName.text.toString() == "") {
                    Snackbar.make(it, getString(R.string.write_name), Snackbar.LENGTH_LONG).show()

                } else {
                    viewModule.calculate(
                        findNavController(),
                        npHeightPicker.value,
                        npWeightPicker.value,
                        etName.text.toString()
                    )
                }

            }
        }

        setObservers()

    }

    private fun setObservers() {
        viewModule.weights.observe(viewLifecycleOwner){
            binding.npWeightPicker.minValue = it[0]
            binding.npWeightPicker.maxValue = it.last()
        }

        viewModule.heights.observe(viewLifecycleOwner){
            binding.npHeightPicker.minValue = it[0]
            binding.npHeightPicker.maxValue = it.last()
        }

        viewModule.genders.observe(viewLifecycleOwner){
            binding.npGenderPicker.minValue = 0
            binding.npGenderPicker.maxValue = 1
            binding.npGenderPicker.displayedValues = it.toTypedArray()
        }
    }
}