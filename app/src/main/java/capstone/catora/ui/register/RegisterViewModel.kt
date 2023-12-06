package capstone.catora.ui.register

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import capstone.catora.data.remote.api.ApiConfig
import capstone.catora.data.remote.api.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RegisterViewModel : ViewModel() {
//    private val loading = MutableLiveData<Boolean>()
//
//    fun postUserSignUp(username: String, email: String, password: String) {
//        showLoading(true)
//        viewModelScope.launch {
//            try {
//                val response = ApiConfig.getApiService().postRegister(username, password)
//
//                if (!response.message) {
//                    showLoading(false)
////                    successCallback.postValue(Unit)
//                }
//            } catch (e: HttpException) {
////                val jsonInString = e.response()?.errorBody()?.string()
////                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
////                val errorMessageString = errorBody.message
////                errorMessage.postValue(errorMessageString)
//                showLoading(false)
//            }
//        }
//    }
//
//    private fun showLoading(isLoading: Boolean) {
//        loading.value = isLoading
//    }
}