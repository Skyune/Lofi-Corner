package com.skyune.loficorner.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.room.Room
import com.skyune.loficorner.WeatherApplication
import com.skyune.loficorner.network.WeatherApi
import com.skyune.loficorner.utils.Constants
import com.example.cleannote.data.NoteDatabase
import com.example.cleannote.data.NoteDatabaseDao
import com.skyune.loficorner.exoplayer.MusicServiceConnection
import com.skyune.loficorner.exoplayer.MusicSource
import com.yeocak.parallaximage.GravitySensorDefaulted
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
class AppModule {


    @Singleton
    @Provides
    fun provideMusicServiceConnection(
        @ApplicationContext context: Context,
        musicSource: MusicSource
    ) = MusicServiceConnection(musicSource, context)

    @Singleton
    @Provides
    fun provideMusicSource(
    ) = MusicSource()



    @Provides
    @Singleton
    fun provideOpenWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): WeatherApplication {
        return app as WeatherApplication
    }

    @Singleton
    @Provides
    fun provideNotesDao(noteDatabase: NoteDatabase): NoteDatabaseDao
            = noteDatabase.noteDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): NoteDatabase
            = Room.databaseBuilder(
        context,
        NoteDatabase::class.java,
        "notes_db"
    ).fallbackToDestructiveMigration().build()
}