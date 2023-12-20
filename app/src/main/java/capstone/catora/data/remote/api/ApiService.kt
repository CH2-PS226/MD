package capstone.catora.data.remote.api

import capstone.catora.data.remote.api.response.AllArtworkResponse
import capstone.catora.data.remote.api.response.AllArtworkResponseItem
import capstone.catora.data.remote.api.response.GetUserByIdResponse
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
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Part
import retrofit2.http.Query


interface ApiService {

    @POST("auth/register")
    suspend fun postRegister(@Body requestBody: Map<String, String>) : PostRegisterResponse

    @POST("auth/login")
    suspend fun postLogin(@Body requestBody: Map<String, String>):PostLoginResponse

    @GET("upload/artworks")
    fun getAllArtWork(): Call<List<AllArtworkResponseItem>>

    @GET("profile/profile/{id}")
    fun getUserById(
        @Path("id") id: String,
    ): Call<GetUserByIdResponse>
    @GET("upload/artworks/{id}")
    fun getArtworkById(
        @Path("id") id: String,
    ): Call<List<AllArtworkResponseItem>>
    @GET("upload/search")
    fun searchArtwork(
        @Query("title") title: String,
//        @Query("description") description: String,
//        @Query("tags") tags: String,
    ): Call<List<AllArtworkResponseItem>>
    @Multipart
    @PUT("profile/profile/{id}")
    suspend fun editProfile(
        @Path("id") id: String,
        @Part image: MultipartBody.Part,
        @Part("artist_name") artistName: RequestBody,
        @Part("description") description: RequestBody
    ): PostUploadArtWorkResponse

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