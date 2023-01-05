package com.morphium.bmi.data

interface BmiRepository {

    fun getWeights(): List<Int>

    fun getHeight(): List<Int>

    fun getGender(): List<String>
}