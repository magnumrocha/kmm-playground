package com.magnumrocha.kmm.playground

import android.content.Context
import com.magnumrocha.kmm.playground.connection.ConnectionState
import com.magnumrocha.kmm.playground.picsum.PicsumImagesApi
import com.magnumrocha.kmm.playground.utils.HttpClientFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideConnectionStateService(@ApplicationContext context: Context): ConnectionState =
        ConnectionState(com.magnumrocha.kmm.playground.connection.KonnectionFactory(context = context, enableDebugLog = BuildConfig.DEBUG))

    @Provides
    fun provideHttpClientFactory(): HttpClientFactory =
        HttpClientFactory(enableNetworkLogs = BuildConfig.DEBUG)

    @Provides
    fun providePicsumImagesApi(httpClientFactory: HttpClientFactory): PicsumImagesApi =
        PicsumImagesApi(httpClientFactory)
}
