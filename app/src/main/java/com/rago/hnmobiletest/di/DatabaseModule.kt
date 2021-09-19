package com.rago.hnmobiletest.di

import android.content.Context
import androidx.room.Room
import com.rago.hnmobiletest.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//Modulo para la base de datos que queda como Singleton
//
@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    // Provider de la base de datos
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "HNMobileTest.db"
    ).build()

    // Provider del dao ExcludeArticle
    @Provides
    fun provideExcludeArticleDao(appDatabase: AppDatabase) =
        appDatabase.excludeArticleDao()
}