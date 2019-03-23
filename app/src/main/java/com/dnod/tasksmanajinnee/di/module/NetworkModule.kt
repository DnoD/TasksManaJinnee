package com.dnod.tasksmanajinnee.di.module

import android.content.Context
import com.dnod.tasksmanajinnee.R
import com.dnod.tasksmanajinnee.data.source.remote.*
import com.dnod.tasksmanajinnee.manager.AuthManager
import com.dnod.tasksmanajinnee.manager.AuthManagerImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class NetworkModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        @Singleton
        internal fun provideClientApi(clientApi: RetrofitClientApi): ClientApi {
            return clientApi
        }

        @JvmStatic
        @Provides
        @Singleton
        internal fun provideAuthManager(authManager: AuthManagerImpl): AuthManager {
            return authManager
        }

        @JvmStatic
        @Provides
        @Singleton
        internal fun provideGson(): Gson {
            return GsonBuilder()
                .create()
        }

        @JvmStatic
        @Provides
        @Endpoint
        internal fun provideServerUrl(context: Context): String {
            return context.getString(R.string.server_url)
        }

        @JvmStatic
        @Provides
        @ConnectionTimeout
        internal fun provideConnectionTimeout(context: Context): Long {
            return context.resources.getInteger(R.integer.connection_timeout_millis).toLong()
        }

        @JvmStatic
        @Provides
        @ConnectionRetryAttempts
        internal fun provideConnectionRetryAttempts(context: Context): Int {
            return context.resources.getInteger(R.integer.connection_retry_attempts)
        }
    }

}
