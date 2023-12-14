package capstone.catora.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import capstone.catora.data.CatoraRepository
import capstone.catora.data.remote.api.ApiConfig
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RegisterViewModel(private val repository: CatoraRepository) : ViewModel() {
//    fun userRegister(name: String, password: String) = repository.userRegister(name, password)

    private val loading = MutableLiveData<Boolean>()
    private val errorMessage = MutableLiveData<String?>()
    private val successCallback = MutableLiveData<Unit>()

    val loadingLiveData: LiveData<Boolean>
        get() = loading

    val errorLiveData: MutableLiveData<String?>
        get() = errorMessage

    val successLiveData: LiveData<Unit>
        get () = successCallback

    fun userRegister(name: String, password: String){
        showLoading(true)
        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService().postRegister(name, password)

                if (response.message == "Registration successful"){
                    showLoading(false)
                    successCallback.postValue(Unit)
                }
            } catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
//                val errorBody = Gson().fromJson(jsonInString, Error)
//                error
                errorMessage.postValue(jsonInString)
                showLoading(false)
            }
        }
    }

    private fun showLoading(isLoading: Boolean){
        loading.value = isLoading
    }
}