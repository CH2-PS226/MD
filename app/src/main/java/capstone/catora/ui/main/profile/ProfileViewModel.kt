package capstone.catora.ui.main.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import capstone.catora.data.remote.api.ApiConfig
import capstone.catora.data.remote.api.response.AllArtworkResponseItem
import capstone.catora.data.remote.api.response.GetUserByIdResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : ViewModel() {
    private val loading = MutableLiveData<Boolean>()

    private val _listArtwork = MutableLiveData<List<AllArtworkResponseItem>?>()
    val listArtwork: MutableLiveData<List<AllArtworkResponseItem>?> = _listArtwork

    private val _userId = MutableLiveData<GetUserByIdResponse>()
    val userId: LiveData<GetUserByIdResponse> = _userId

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    fun getArtworkById(id: String) {
        showLoading(true)
        val artworkId = ApiConfig.getApiService().getArtworkById(id)

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

    fun getUserByID(id: String) {
        showLoading(true)
        val user = ApiConfig.getApiService().getUserById(id)

        user.enqueue(object : Callback<GetUserByIdResponse> {
            override fun onResponse(
                call: Call<GetUserByIdResponse>,
                response: Response<GetUserByIdResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        showLoading(false)
                        _userId.value = response.body()
                    }
                }
            }

            override fun onFailure(call: Call<GetUserByIdResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure : ${t.message}")
            }
        })
    }

    private fun showLoading(isLoading: Boolean){
        loading.value = isLoading
    }

    companion object {
        private const val TAG = "ProfileFragment"
    }
}