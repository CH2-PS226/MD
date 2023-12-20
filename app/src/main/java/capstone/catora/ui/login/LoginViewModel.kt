package capstone.catora.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import capstone.catora.data.CatoraRepository
import capstone.catora.data.pref.UserModel
import capstone.catora.data.remote.api.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(private val repository: CatoraRepository) : ViewModel(){
    private val loading = MutableLiveData<Boolean>()
    private val errorMessage = MutableLiveData<String?>()
    private val successCallback = MutableLiveData<Unit>()

    val loadingLiveData: LiveData<Boolean>
        get() = loading

    val errorLiveData: MutableLiveData<String?>
        get() = errorMessage

    val successLiveData: LiveData<Unit>
        get () = successCallback

    fun userLogin(name: String, password:String){
        showLoading(true)
        viewModelScope.launch {
            try {
                val requestBody = mapOf(
                    "username" to name,
                    "password" to password
                )
                val response = ApiConfig.getApiService().postLogin(requestBody)

                if (response.message == "Login successful"){
                    showLoading(false)
                    successCallback.postValue(Unit)

                    saveSession(UserModel(response.userId.toString(), response.token))
                }
            } catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()

                errorMessage.postValue(jsonInString)
                showLoading(false)
            }
        }
    }

    private fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    private fun showLoading(isLoading: Boolean){
        loading.value = isLoading
    }
}