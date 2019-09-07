/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mjb.android.mvvm.di

import android.app.Application
import android.content.SharedPreferences
import androidx.room.Room
import com.mjb.android.mvvm.database.MvvmDatabase
import com.mjb.android.mvvm.database.PhotosDao
import com.mjb.android.mvvm.network.ApiService
import com.mjb.android.mvvm.network.ConnectivityInterceptor
import com.mjb.android.mvvm.network.LiveDataCallAdapterFactory
import com.mjb.android.mvvm.util.API_TIMEOUT
import com.mjb.android.mvvm.util.DATABASE
import com.mjb.android.mvvm.util.SHARED_PREFERENCE
import com.mjb.android.mvvm.util.URL_BASE
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module(includes = [ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideNetworkService(app: Application): ApiService {

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(API_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(API_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(API_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(ConnectivityInterceptor(app))
//            .addInterceptor {
//                val request = it.request().newBuilder().addHeader("api_key", API_KEY).build()
//                it.proceed(request)
//            }
            .build()



        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .baseUrl(URL_BASE)
            .build()
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): MvvmDatabase {
        return Room
            .databaseBuilder(app, MvvmDatabase::class.java, DATABASE)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideBoardDao(db: MvvmDatabase): PhotosDao {
        return db.photosDao()
    }

    @Singleton
    @Provides
    fun providePreference(app: Application): SharedPreferences = app.getSharedPreferences(SHARED_PREFERENCE, 0)


    @Singleton
    @Provides
    fun provideAppContext(app: Application) = app.applicationContext


}
