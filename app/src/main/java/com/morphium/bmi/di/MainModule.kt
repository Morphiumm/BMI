package com.morphium.bmi.di

import com.morphium.bmi.data.BmiRepository
import com.morphium.bmi.data.BmiRepositoryImpl
import com.morphium.bmi.domain.AppBmiUseCase
import com.morphium.bmi.domain.BmiUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class MainModule {


    @Binds
    abstract fun bindBmiRepository(impl: BmiRepositoryImpl): BmiRepository

    @Binds
    abstract fun bindBmiUseCase(app: AppBmiUseCase): BmiUseCase

}