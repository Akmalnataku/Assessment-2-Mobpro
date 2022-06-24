package org.d3if2090.hitungnilai.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.d3if2090.hitungnilai.ui.AboutModel
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://raw.githubusercontent.com/" +
        "Akmalnataku/Assessment-2-Mobpro/Assessment3/static-api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface AboutApiService {
    @GET("static-api.json")
    suspend fun getAbout() : List<AboutModel>
}

object About {
    val service: AboutApiService by lazy {
        retrofit.create(AboutApiService::class.java)
    }

    fun getImageUrl(nama: String): String {
        return "$BASE_URL$nama.jpg"
    }
}
