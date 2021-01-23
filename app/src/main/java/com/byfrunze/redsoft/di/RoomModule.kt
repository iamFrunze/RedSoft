package com.byfrunze.redsoft.di

import android.content.Context
import androidx.room.Room
import com.byfrunze.redsoft.data.source.local.ProductDao
import com.byfrunze.redsoft.data.source.local.RedsoftDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ActivityComponent::class)
@Module
object RoomModule {
    @Singleton
    @Provides
    fun provideRedSoftDB(@ApplicationContext context: Context): RedsoftDatabase =
        Room.databaseBuilder(
            context,
            RedsoftDatabase::class.java,
            RedsoftDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideProductDao(redsoftDatabase: RedsoftDatabase): ProductDao =
        redsoftDatabase.productDao()
}