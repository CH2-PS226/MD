package capstone.catora.data.remote.api

import capstone.catora.data.remote.api.response.AllArtworkResponse
import capstone.catora.data.remote.api.response.AllArtworkResponseItem
import capstone.catora.data.remote.api.response.PostLoginResponse
import capstone.catora.data.remote.api.response.PostRegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("auth/register")
    suspend fun postRegister(@Body requestBody: Map<String, String>) : PostRegisterResponse

    @POST("auth/login")
    suspend fun postLogin(@Body requestBody: Map<String, String>):PostLoginResponse

    @GET("upload/artworks")
    fun getAllArtWork(): Call<List<AllArtworkResponseItem>>
}