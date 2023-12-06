package capstone.catora.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import capstone.catora.R
import capstone.catora.data.remote.api.ApiConfig
import capstone.catora.data.remote.api.response.PostRegisterResponse
import capstone.catora.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide() //this line for remove action bar

        binding.tvToLogin.setOnClickListener {
            finish()
            //this will route back to login by destroying current activity
        }

        binding.btnRegister.setOnClickListener {
            binding.tvErrorMessage.visibility = View.INVISIBLE
            postRegister(binding.tiUsername.text.toString(), binding.tiPassword.text.toString())

        }

    }

    private fun postRegister(username: String, password: String) {
        Log.d(TAG, "${username} - ${password}")
        showLoading(true)
        val client = ApiConfig.getApiService().postRegister(username, password)
        client.enqueue(object : Callback<PostRegisterResponse> {
            override fun onResponse(
                call: Call<PostRegisterResponse>,
                response: Response<PostRegisterResponse>
            ) {
                showLoading(false)
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    showSuccessDialog()
                } else {
                    binding.tvErrorMessage.text = response.message().toString()
                    binding.tvErrorMessage.visibility = View.VISIBLE
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<PostRegisterResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun showSuccessDialog() {
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

    companion object{
        val TAG = "Register Activity"
    }
}