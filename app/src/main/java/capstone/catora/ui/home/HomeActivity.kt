package capstone.catora.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import capstone.catora.R
import capstone.catora.databinding.ActivityHomeBinding
import capstone.catora.ui.profile.ProfileActivity
import capstone.catora.ui.upload.UploadActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide() //this line for remove action bar

        binding.btnToUploadTest.setOnClickListener {
            startActivity(Intent(this, UploadActivity::class.java))
        }
        binding.btnToProfileTest.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }


    }



}