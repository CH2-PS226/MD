package capstone.catora.data.pref

data class UserModel(
    val userId: String,
    val token: String,
    val isLogin: Boolean = false
)
