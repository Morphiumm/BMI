package com.morphium.bmi.domain

import android.graphics.Bitmap
import android.text.format.DateFormat
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.morphium.bmi.R
import com.morphium.bmi.data.BmiRepository
import com.morphium.bmi.data.IndexResult
import java.io.File
import java.io.FileOutputStream
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*
import javax.inject.Inject
import kotlin.math.pow


class AppBmiUseCase @Inject constructor(
    private val repository: BmiRepository
) : BmiUseCase {
    override fun getWeights(): List<Int> {
        return repository.getWeights()
    }

    override fun getHeights(): List<Int> {
        return repository.getHeight()
    }

    override fun getGenders(): List<String> {
        return repository.getGender()
    }

    override fun calculateBmi(weight: Int, height: Int): IndexResult {
        val bmi = round(weight / ((height.toDouble() / 100).pow(2.0)))
        val result = when (bmi) {
            in 0.0..18.5 -> "Underweight"
            in 18.5..24.9 -> "Normal Weight"
            in 25.0..29.9 -> "Overweight"
            in 30.0..34.9 -> "Obesity class |"
            in 35.0..39.9 -> "Obesity class ||"

            else -> "Obesity class |||"
        }
        return IndexResult(bmi, result)

    }

    override fun calculatePonderal(weight: Int, height: Int): Double {
        return round(weight / (height.toDouble() / 100).pow(3.0))
    }

    override fun takeScreenshot(context: Fragment) {
        val now = Date()
        DateFormat.format("yyyy-MM-dd_hh:mm:ss", now)

        try {
            val mPath = context.context?.cacheDir?.path + "/" + now + ".jpg"

            val v1: View? = context.activity?.window?.decorView?.rootView
            v1?.setDrawingCacheEnabled(true)
            val bitmap: Bitmap? = v1?.getDrawingCache()?.let { Bitmap.createBitmap(it) }
            v1?.setDrawingCacheEnabled(false)
            val imageFile = File(mPath)
            val outputStream = FileOutputStream(imageFile)
            val quality = 100
            bitmap?.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            outputStream.flush()
            outputStream.close()
            context.view?.let {
                Snackbar.make(
                    it,
                    context.getString(R.string.screenshot_saved),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    private fun round(value: Double): Double {
        var bd: BigDecimal = BigDecimal.valueOf(value)
        bd = bd.setScale(2, RoundingMode.HALF_UP)
        return bd.toDouble()
    }

}