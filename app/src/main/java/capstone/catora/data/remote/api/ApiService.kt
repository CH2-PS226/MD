package capstone.catora.data.remote.api

import capstone.catora.data.remote.api.response.PostRegisterResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("auth/register")
    suspend fun postRegister(
        @Field("username") username: String,
        @Field("password") password: String
    ): PostRegisterResponse

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun postLogin(
        @Field("username") username: String,
        @Field("password") password: String
    ): PostRegisterResponse
}