package capstone.catora.data.remote.api

import capstone.catora.data.remote.api.response.PostRegisterResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("auth/register")
    fun postRegister(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<PostRegisterResponse>

    @FormUrlEncoded
    @POST("auth/login")
    fun postLogin(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<PostRegisterResponse>
}