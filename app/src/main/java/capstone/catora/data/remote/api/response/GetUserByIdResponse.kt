package capstone.catora.data.remote.api.response

import com.google.gson.annotations.SerializedName

data class GetUserByIdResponse(

	@field:SerializedName("background_image_url")
	val backgroundImageUrl: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("artist_name")
	val artistName: String? = null,

	@field:SerializedName("profile_id")
	val profileId: Int? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("profile_image_url")
	val profileImageUrl: String? = null
)
