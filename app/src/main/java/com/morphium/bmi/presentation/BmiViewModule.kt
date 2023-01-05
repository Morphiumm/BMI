package com.morphium.bmi.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.morphium.bmi.R
import com.morphium.bmi.data.IndexResult
import com.morphium.bmi.domain.BmiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BmiViewModule @Inject constructor(
    private val bmiUseCase: BmiUseCase
) : ViewModel() {

    private val _weights = MutableLiveData<List<Int>>()
    val weights: LiveData<List<Int>> get() = _weights

    private val _heights = MutableLiveData<List<Int>>()
    val heights: LiveData<List<Int>> get() = _heights

    private val _genders = MutableLiveData<List<String>>()
    val genders: LiveData<List<String>> get() = _genders

    private val _bmi = MutableLiveData<IndexResult>()
    val bmi: LiveData<IndexResult> get() = _bmi

    private val _ponderal = MutableLiveData<Double>()
    val ponderal: LiveData<Double> get() = _ponderal



    init {
        fetchWeights()
        fetchHeights()
        fetchGenders()
    }


    fun fetchWeights() {
        _weights.postValue(bmiUseCase.getWeights())
    }

    fun fetchHeights() {
        _heights.postValue(bmiUseCase.getHeights())
    }

    fun fetchGenders() {
        _genders.postValue(bmiUseCase.getGenders())
    }

    fun     calculateBmi(weight: Int, height: Int){
        _bmi.postValue(bmiUseCase.calculateBmi(weight, height))
    }

    fun calculatePonderal(weight: Int, height: Int){
        _ponderal.postValue(bmiUseCase.calculatePonderal(weight, height))
    }

    fun calculate(navController: NavController, height: Int, weight: Int, name: String){
        val bundle = Bundle()
        bundle.putString("name", name)
        bundle.putInt("weight", weight)
        bundle.putInt("height", height)

        navController.navigate(
            R.id.action_bmiDetailsFragment_to_bmiResultFragment,
            bundle
        )
    }
    fun takeScreenshot(fragment: Fragment){
        bmiUseCase.takeScreenshot(fragment)
    }

}