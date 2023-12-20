package capstone.catora.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import capstone.catora.data.CatoraRepository
import capstone.catora.data.pref.UserModel

class WelcomeViewModel(private val repository: CatoraRepository): ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}