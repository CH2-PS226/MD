package capstone.catora.data.remote.api.response

import com.google.gson.annotations.SerializedName

data class PostUploadArtWorkResponse (
    @field:SerializedName("confidence")
    val confidence:String,

    @field:SerializedName("message")
    val message:String
)