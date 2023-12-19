package capstone.catora.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import capstone.catora.R
import capstone.catora.databinding.ActivityWelcomeBinding
import capstone.catora.ui.login.LoginActivity
import capstone.catora.ui.register.RegisterActivity

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnWelcomeLogin.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.btnWelcomeRegister.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}