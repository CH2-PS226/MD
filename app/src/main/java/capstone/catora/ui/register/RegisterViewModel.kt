package capstone.catora.ui.register

import androidx.lifecycle.ViewModel
import capstone.catora.data.CatoraRepository

class RegisterViewModel(private val repository: CatoraRepository) : ViewModel() {
    fun userRegister(name: String, password: String) = repository.userRegister(name, password)

}