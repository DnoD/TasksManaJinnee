package com.dnod.tasksmanajinnee.data.remote

import com.dnod.tasksmanajinnee.data.remote.request.AuthRequest
import com.dnod.tasksmanajinnee.data.remote.response.AuthResponse
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import okhttp3.OkHttpClient
import retrofit2.Response

/**
 * This is a concrete ClientApi using Retrofit2 library for the connection with server
 */
class RetrofitClientApi @Inject constructor(
    @Endpoint val endpointUrl: String,
    @ConnectionTimeout val connectionTimeout: Long,
    @ConnectionRetryAttempts val connectionRetryAttempts: Int
) : ClientApi {

    private val manaJinnee: ManaJinneeService
    private var authorizationString: String = ""

    init {
        val okHttpBuilder = OkHttpClient.Builder()
            .cache(null)
            .addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): okhttp3.Response? {
                    var request: okhttp3.Request = chain.request()
                    if (!authorizationString.isEmpty()) {
                        request = request.newBuilder().addHeader("Authorization", authorizationString).build()
                    }
                    return proceed(chain, request, 0) ?: return null
                }

                @Throws(IOException::class)
                private fun proceed(
                    chain: Interceptor.Chain,
                    request: okhttp3.Request,
                    retryCount: Int
                ): okhttp3.Response? {
                    var count = retryCount
                    val response: okhttp3.Response?
                    try {
                        response = chain.proceed(request)
                    } catch (e: IOException) {
                        return if (retryCount < connectionRetryAttempts) {
                            proceed(chain, request, ++count)
                        } else {
                            throw e
                        }
                    }

                    return response
                }
            })
            .connectTimeout(connectionTimeout, TimeUnit.MILLISECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        val retrofitClient = Retrofit.Builder()
            .baseUrl(endpointUrl)
            .client(okHttpBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        manaJinnee = retrofitClient.create(ManaJinneeService::class.java)
    }

    override fun register(userName: String, password: String): Observable<Response<AuthResponse>> {
        return manaJinnee.register(AuthRequest(userName, password))
            .subscribeOn(Schedulers.io())
            .map { response ->
                if (response.errorBody() == null) {
                    val body = response.body() ?: return@map null
                    setSessionToken(body.token ?: "")
                }
                response
            }
    }

    override fun login(userName: String, password: String): Observable<Response<AuthResponse>> {
        return manaJinnee.login(AuthRequest(userName, password))
            .subscribeOn(Schedulers.io())
            .map { response ->
                if (response.errorBody() == null) {
                    val body = response.body() ?: return@map null
                    setSessionToken(body.token ?: "")
                }
                response
            }
    }

    override fun setSessionToken(token: String) {
        if (token.isNotEmpty()) {
            authorizationString = "Bearer $token"
        }
    }
}
