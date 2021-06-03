package com.swenson.basemodule.data.helpers.network

import android.content.Context
import android.os.Build
import com.swenson.basemodule.BuildConfig
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitFactory {

    const val BASE_URL = BuildConfig.BASE_URL


    fun getHeaders(
        request: Request,
        context: Context
    ): Headers {

        return request.headers.newBuilder()
            .build()
    }

    inline fun <reified T> getService(context: Context): T {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.HEADERS
        logging.level = HttpLoggingInterceptor.Level.BODY

        val headerAuthorizationInterceptor = Interceptor { chain ->
            var request: Request = chain.request()
            request = request.newBuilder().headers(
                getHeaders(
                    request, context
                )
            ).build()
            chain.proceed(request)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(headerAuthorizationInterceptor)
            .addInterceptor(logging)
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(T::class.java)
    }


}