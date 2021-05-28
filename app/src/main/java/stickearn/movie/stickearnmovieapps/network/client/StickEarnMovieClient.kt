package stickearn.movie.stickearnmovieapps.network.client

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import stickearn.movie.stickearnmovieapps.BuildConfig
import java.util.concurrent.TimeUnit


class StickEarnMovieClient {

    private fun okHttpClient(): OkHttpClient {

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(BaseInterceptor())
            .addInterceptor(interceptor)
            .connectTimeout(3, TimeUnit.MINUTES)
            .writeTimeout(3, TimeUnit.MINUTES)
            .readTimeout(3, TimeUnit.MINUTES)
            .build()
    }

    fun getMovieDbServices(): Retrofit {

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_SERVER_URL)
            .client(okHttpClient())
            .build()
    }

}