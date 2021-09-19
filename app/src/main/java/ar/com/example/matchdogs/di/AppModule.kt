package ar.com.example.matchdogs.di

import android.content.Context
import androidx.room.Room
import ar.com.example.matchdogs.application.AppConstants
import ar.com.example.matchdogs.core.WebService
import ar.com.example.matchdogs.data.local.AppDataBase
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //Room
    @Singleton
    @Provides
    fun provideRoomInstance(
        @ApplicationContext context:Context
    )=
        Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            "dog_table"
        ).build()


    @Singleton
    @Provides
    fun provideDogDao(db: AppDataBase) = db.dogDao()


    //Retrofit2
    @Singleton
    @Provides
    fun providesRetrofit() = Retrofit.Builder()
        .baseUrl(AppConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

    @Singleton
    @Provides
    fun providesWebService(retrofit: Retrofit) = retrofit.create(WebService::class.java)

    //Shared Preferences




}