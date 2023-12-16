package capstone.catora.data.remote.api.response

import com.google.gson.annotations.SerializedName

data class AllArtworkResponse(

	@field:SerializedName("AllArtworkResponse")
	val allArtworkResponse: List<AllArtworkResponseItem?>? = null
)

data class AllArtworkResponseItem(

	@field:SerializedName("updated_at")
	val updatedAt: Any? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("artwork_id")
	val artworkId: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("tags")
	val tags: String? = null
)
