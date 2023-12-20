package capstone.catora.ui.main.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import capstone.catora.data.remote.api.ApiConfig
import capstone.catora.data.remote.api.response.AllArtworkResponse
import capstone.catora.data.remote.api.response.AllArtworkResponseItem
import capstone.catora.ui.main.profile.ProfileViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val loading = MutableLiveData<Boolean>()

    private val _listArtwork = MutableLiveData<List<AllArtworkResponseItem>?>()
    val listArtwork: MutableLiveData<List<AllArtworkResponseItem>?> = _listArtwork

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    init {
        allArtwork()
    }

    fun allArtwork() {
        showLoading(true)
        val user = ApiConfig.getApiService().getAllArtWork()

        user.enqueue(object : Callback<List<AllArtworkResponseItem>> {
            override fun onResponse(
                call: Call<List<AllArtworkResponseItem>>,
                response: Response<List<AllArtworkResponseItem>>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listArtwork.value = response.body()
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}" )
                }
            }

            override fun onFailure(call: Call<List<AllArtworkResponseItem>>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure : ${t.message}")
            }
        })
    }

    fun getArtworkById(title: String) {
        showLoading(true)
        val artworkId = ApiConfig.getApiService().searchArtwork(title)

        artworkId.enqueue(object : Callback<List<AllArtworkResponseItem>> {
            override fun onResponse(
                call: Call<List<AllArtworkResponseItem>>,
                response: Response<List<AllArtworkResponseItem>>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listArtwork.value = response.body()
                    }
                }
            }

            override fun onFailure(call: Call<List<AllArtworkResponseItem>>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure : ${t.message}")
            }
        })
    }

    private fun showLoading(isLoading: Boolean){
        loading.value = isLoading
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}