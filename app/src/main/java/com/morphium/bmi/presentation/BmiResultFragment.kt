package com.morphium.bmi.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.morphium.bmi.R
import com.morphium.bmi.databinding.FragmentBmiResultBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BmiResultFragment : Fragment(R.layout.fragment_bmi_result) {

    private lateinit var binding: FragmentBmiResultBinding
    private val viewModule: BmiViewModule by viewModels()
    val args: BmiResultFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBmiResultBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity?)?.supportActionBar?.title = getString(R.string.bmi_details)

        setObservers()
        viewModule.calculateBmi(args.weight, args.height)
        viewModule.calculatePonderal(args.weight, args.height)

        binding.apply {
            flShare.setOnClickListener {
                parentFragment?.let { it1 -> viewModule.takeScreenshot(it1) }
            }
            flRate.setOnClickListener {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/ ")
                    )
                )
            }
        }

    }

    private fun setObservers() {
        viewModule.bmi.observe(viewLifecycleOwner) {
            binding.tvWeightInteger.text = it.index.toString()
            binding.tvNameBmi.text = getString(R.string.name_result, args.name, it.result)
        }
        viewModule.ponderal.observe(viewLifecycleOwner) {
            binding.tvPonderal.text = getString(R.string.ponderal_index, it.toString())
        }
    }

}