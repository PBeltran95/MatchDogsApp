package ar.com.example.matchdogs.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import ar.com.example.matchdogs.application.AppConstants
import ar.com.example.matchdogs.core.WebService
import ar.com.example.matchdogs.data.local.AppDataBase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    @Provides
    fun providesSharedPreferences(@ApplicationContext appContext:Context): SharedPreferences =
        appContext.getSharedPreferences(AppConstants.KEY_MODE, Context.MODE_PRIVATE)


    @Singleton
    @Provides
    fun provideFirebaseAuthInstance():FirebaseAuth = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun providesFirebaseStorageInstance(): FirebaseFirestore = FirebaseFirestore.getInstance()


}