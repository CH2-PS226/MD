package capstone.catora.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import capstone.catora.data.ResultState
import capstone.catora.databinding.ActivityRegisterBinding
import capstone.catora.ui.ViewModelFactory

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

    }

    private fun setupAction() {
        binding.btnRegister.setOnClickListener {

            val name = binding.tiUsername.text.toString()
            val password = binding.tiPassword.text.toString()

            viewModel.userRegister(name, password).observe(this) { result ->
                if (result != null) {
                    when(result) {
                        is ResultState.Loading -> {
                            showLoading(true)
                        }
                        is ResultState.Success -> {
                            val message = result.data.message
                            showSuccessDialog(message)
                            showLoading(false)
                        }
                        is ResultState.Error -> {
                            val error = result.error
                            showToast(error)
                            showLoading(false)
                        }
                    }
                }
            }
        }
    }

    private fun showSuccessDialog(message: String) {
        AlertDialog.Builder(this).apply {
            setTitle("Yeah!")
            setMessage(
                "welcome to our app ${binding.tiUsername.text.toString()}"
            )
            setPositiveButton("continue") { _, _ ->
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

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object{
        val TAG = "Register Activity"
    }
}