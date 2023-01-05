package com.morphium.bmi.domain

import androidx.fragment.app.Fragment
import com.morphium.bmi.data.IndexResult

interface BmiUseCase {

    fun getWeights(): List<Int>

    fun getHeights(): List<Int>

    fun getGenders(): List<String>

    fun calculateBmi(weight: Int, height: Int): IndexResult

    fun calculatePonderal(weight: Int, height: Int): Double

    fun takeScreenshot(context: Fragment)
}