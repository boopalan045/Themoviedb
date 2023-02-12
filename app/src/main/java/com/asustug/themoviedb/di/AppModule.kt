package com.asustug.themoviedb.di

import android.content.Context
import com.asustug.themoviedb.data.remote.ApiService
import com.asustug.themoviedb.di.interceptors.AuthInterceptor
import com.asustug.themoviedb.repositories.ApiRepository
import com.asustug.themoviedb.repositories.ApiRepositoryImpl
import com.asustug.themoviedb.utils.Utils
import com.asustug.themoviedb.utils.Utils.Companion.BASE_URL
import com.asustug.themoviedb.utils.dataStore.PreferenceDataStoreHelper
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    private var mHttpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    private var client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .addInterceptor(mHttpLoggingInterceptor)
        .build()

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideApiRepository(apiService: ApiService): ApiRepository {
        return ApiRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideAPIpreferernce(@ApplicationContext context: Context): PreferenceDataStoreHelper {
        return PreferenceDataStoreHelper(context)
    }

    @Provides
    @Singleton
    fun provideUtils(@ApplicationContext context: Context): Utils {
        return Utils(context)
    }
}