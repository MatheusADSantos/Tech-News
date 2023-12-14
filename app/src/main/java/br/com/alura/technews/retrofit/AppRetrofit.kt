package br.com.alura.technews.retrofit

import br.com.alura.technews.retrofit.service.NoticiaService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val LOCAL_HOST = "172.19.14.237"//"192.168.86.2"
private const val BASE_URL = "http://$LOCAL_HOST:8080/"

class AppRetrofit {

    private val client by lazy {
        val interceptador = HttpLoggingInterceptor()
        interceptador.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .addInterceptor(interceptador)
            .build()
    }
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
    val noticiaService: NoticiaService by lazy {
        retrofit.create(NoticiaService::class.java)
    }

}