package capstone.catora.data

import androidx.lifecycle.liveData
import capstone.catora.data.pref.UserModel
import capstone.catora.data.pref.UserPreferences
import capstone.catora.data.remote.api.ApiService
import capstone.catora.data.remote.api.response.PostLoginResponse
import capstone.catora.data.remote.api.response.PostRegisterResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class CatoraRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreferences
){
    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: CatoraRepository? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreferences
        ): CatoraRepository =
            instance ?: synchronized(this) {
                instance ?: CatoraRepository(apiService,userPreference)
            }.also { instance = it }
    }
}