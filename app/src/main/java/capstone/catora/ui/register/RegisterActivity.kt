package capstone.catora.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import capstone.catora.data.ResultState
import capstone.catora.databinding.ActivityRegisterBinding
import capstone.catora.ui.ViewModelFactory
import capstone.catora.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide() //this line for remove action bar

        binding.tvToLogin.setOnClickListener {
            finish()
            //this will route back to login by destroying current activity
        }

        setupAction()

        viewModel.errorLiveData.observe(this){errorMessage ->
            binding.tvErrorMessage.text = errorMessage
            binding.tvErrorMessage.visibility = View.VISIBLE
            showLoading(false)
        }

        viewModel.loadingLiveData.observe(this){
            showLoading(it)
        }

        viewModel.successLiveData.observe(this){
            showSuccessDialog()
        }
    }

    private fun setupAction() {
        val password = binding.tiPassword.text.toString()
        val name = binding.tiUsername.text.toString()
        binding.btnRegister.setOnClickListener {

            viewModel.userRegister(name, password)

//            viewModel.userRegister(name, password).observe(this) { result ->
//                if (result != null) {
//                    when(result) {
//                        is ResultState.Loading -> {
//                            showLoading(true)
//                        }
//                        is ResultState.Success -> {
//                            val message = result.data.message
//                            showSuccessDialog(message)
//                            showLoading(false)
//                        }
//                        is ResultState.Error -> {
//                            val error = result.error
//                            showToast(error)
//                            showLoading(false)
//                        }
//                    }
//                }
//            }
        }
    }

    private fun showSuccessDialog() {
        AlertDialog.Builder(this).apply {
            setTitle("Yeah!")
            setMessage(
                "welcome to our app ${binding.tiUsername.text.toString()}"
            )
            setPositiveButton("continue") { _, _ ->
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
            create()
            show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

//    private fun showToast(message: String) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
//    }

    companion object{
        val TAG = "Register Activity"
    }
}