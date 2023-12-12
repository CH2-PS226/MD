package capstone.catora.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import capstone.catora.data.CatoraRepository
import capstone.catora.data.pref.UserModel
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: CatoraRepository) : ViewModel(){
    fun userLogin(email: String, password: String) = repository.userLogin(email, password)

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
}