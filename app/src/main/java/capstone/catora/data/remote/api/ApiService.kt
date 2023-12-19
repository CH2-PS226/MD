package capstone.catora.data.remote.api

import capstone.catora.data.remote.api.response.AllArtworkResponse
import capstone.catora.data.remote.api.response.AllArtworkResponseItem
import capstone.catora.data.remote.api.response.PostLoginResponse
import capstone.catora.data.remote.api.response.PostRegisterResponse
import capstone.catora.data.remote.api.response.PostUploadArtWorkResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded

import retrofit2.http.GET

import retrofit2.http.Multipart

import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @POST("auth/register")
    suspend fun postRegister(@Body requestBody: Map<String, String>) : PostRegisterResponse

    @POST("auth/login")
    suspend fun postLogin(@Body requestBody: Map<String, String>):PostLoginResponse


    @GET("upload/artworks")
    fun getAllArtWork(): Call<List<AllArtworkResponseItem>>


//    @Multipart
//    @POST("upload/artworks")
//    suspend fun uploadImage(
//        @Part image: MultipartBody.Part,
//        @Part("user_id") user_id: RequestBody,
//        @Part("title") title: RequestBody,
//        @Part("description") description: RequestBody,
//        @Part("tags") tags: RequestBody,
//    ): PostUploadArtWorkResponse

}