package capstone.catora.data.remote.api.response

import com.google.gson.annotations.SerializedName

data class PostLoginResponse(
    @field:SerializedName("message")
    val message:String,

    @field:SerializedName("user_id")
    val userId: Int,

    @field:SerializedName("token")
    val token: String,
)
