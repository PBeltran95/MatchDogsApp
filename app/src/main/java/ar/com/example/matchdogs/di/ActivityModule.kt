package ar.com.example.matchdogs.di

import ar.com.example.matchdogs.domain.local.LocalDogRepo
import ar.com.example.matchdogs.domain.local.LocalDogRepoImpl
import ar.com.example.matchdogs.domain.local.sharedPreferences.SharedPrefsRepoImpl
import ar.com.example.matchdogs.domain.local.sharedPreferences.SharedRepo
import ar.com.example.matchdogs.domain.remote.DogRepositoryImplements
import ar.com.example.matchdogs.domain.remote.RepositoryOfDogs
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityModule {


    @Binds
    abstract fun bindLocalRepoImpl(repoImpl: LocalDogRepoImpl): LocalDogRepo


    @Binds
    abstract fun bindDogRepositoryImplements(remoteDogRepo: DogRepositoryImplements): RepositoryOfDogs


    @Binds
    abstract fun bindSharedPrefsRepoImpl(sharedRepo: SharedPrefsRepoImpl): SharedRepo
}