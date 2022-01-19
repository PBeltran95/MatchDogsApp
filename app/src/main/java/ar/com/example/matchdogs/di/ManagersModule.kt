package ar.com.example.matchdogs.di

import ar.com.example.matchdogs.managers.NewUserValidator
import ar.com.example.matchdogs.managers.NewUserValidatorImpl
import ar.com.example.matchdogs.managers.Validator
import ar.com.example.matchdogs.managers.ValidatorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class ManagersModule {

    @Provides
    fun providesValidatorImpl() : Validator { return ValidatorImpl() }

    @Provides
    fun providesNewUserValidatorImpl() : NewUserValidator { return NewUserValidatorImpl() }
}