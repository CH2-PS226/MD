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

    fun userLogin(email: String, password: String) = liveData  {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.postLogin(email, password)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, PostLoginResponse::class.java)
            emit(errorResponse.message?.let { ResultState.Error(it) })
        }
    }

//    fun userRegister(name: String, password: String) = liveData {
//        try {
//            val successResponse = apiService.postRegister(name, password)
//            emit(ResultState.Success(successResponse))
//        } catch (e: HttpException) {
//            val errorBody = e.response()?.errorBody()?.string()
//            val errorResponse = Gson().fromJson(errorBody, PostRegisterResponse::class.java)
//            emit(errorResponse.message?.let { ResultState.Error(it) })
//        }
//    }

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