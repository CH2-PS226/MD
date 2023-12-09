package capstone.catora.data.remote.api.response

import com.google.gson.annotations.SerializedName

data class PostRegisterResponse (
    @field:SerializedName("message")
    val message:String,
)