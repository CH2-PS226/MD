package capstone.catora.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import capstone.catora.databinding.ActivityLoginBinding
import capstone.catora.ui.ViewModelFactory
import capstone.catora.ui.main.MainActivity
import capstone.catora.ui.register.RegisterActivity
import capstone.catora.ui.register.RegisterViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide() //this line for remove action bar

        setupAction()

        viewModel.errorLiveData.observe(this){ errorMessage ->
            binding.tvErrorMessage.text = errorMessage
            binding.tvErrorMessage.visibility = View.VISIBLE
            showLoading(false)
        }

        viewModel.loadingLiveData.observe(this){
            showLoading(it)
        }

        viewModel.successLiveData.observe(this){
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun setupAction() {
        binding.tvToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {

            val name = binding.edUsername.text.toString()
            val password = binding.edPassword.text.toString()

            viewModel.userLogin(name, password)
            //for bypass the login view
//            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}