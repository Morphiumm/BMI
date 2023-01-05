package com.morphium.bmi.data

import javax.inject.Inject

internal class BmiRepositoryImpl @Inject constructor(): BmiRepository {
    override fun getWeights(): List<Int> {
        return (40..140).toList()
    }

    override fun getHeight(): List<Int> {
        return (130..230).toList()
    }

    override fun getGender(): List<String> {
        return listOf("Male","Female")
    }
}