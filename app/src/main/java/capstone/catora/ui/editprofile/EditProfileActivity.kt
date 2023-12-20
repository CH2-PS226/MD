package capstone.catora.ui.editprofile

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import capstone.catora.R
import capstone.catora.data.remote.api.ApiConfig
import capstone.catora.data.remote.api.response.PostUploadArtWorkResponse
import capstone.catora.databinding.ActivityEditProfileBinding
import capstone.catora.ui.main.profile.ProfileFragment
import capstone.catora.ui.main.upload.UploadFragment
import capstone.catora.ui.utils.reduceFileImage
import capstone.catora.ui.utils.uriToFile
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding

    private var errorMessage: String? = null

    private var currentImageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = intent.getStringExtra(USERID)

        binding.llChooseImage.setOnClickListener {
            startGalerry()
        }
        binding.tvChooseOtherImage.setOnClickListener {
            startGalerry()
        }

        binding.btnUpload.setOnClickListener {
            if (userId != null) {
                updateProfile(userId)
            } else {
              showToast("Error: gagal upload gambar")
            }
        }
    }

    private fun startGalerry(){
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ){uri: Uri? ->
        if (uri != null){
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image Uri", "showImage: $it")
            binding.ivArtWork.setImageURI(it)
            binding.ivArtWork.visibility = View.VISIBLE
            binding.llChooseImage.visibility = View.GONE
            binding.tvChooseOtherImage.visibility = View.VISIBLE
        }
    }

    private fun showSuccessDialog(success: String) {
        AlertDialog.Builder(this).apply {
            setTitle("Yeah!")
            setMessage(
                success
            )
            setPositiveButton("continue") { _, _ ->
                val intent = Intent(this@EditProfileActivity, ProfileFragment::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
            create()
            show()
        }
    }

    private fun updateProfile(userId: String) {
        showLoading(true)
        if (currentImageUri != null) {

            currentImageUri?.let { uri ->
                val imageFile = uriToFile(uri, this).reduceFileImage()
                Log.d("Image File", "showImage: ${imageFile.path}")

                val description = binding.edDescription.text.toString()
                val title = binding.edTitle.text.toString()

                val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
                val descriptionBody = description.toRequestBody("text/plain".toMediaType())
                val titleBody = title.toRequestBody("text/plain".toMediaType())

                val multipartBody = MultipartBody.Part.createFormData(
                    "profile_image",
                    imageFile.name,
                    requestImageFile
                )

                lifecycleScope.launch {
                    try {
                        val successResponse: PostUploadArtWorkResponse
                        val apiService = ApiConfig.getApiService()

                        successResponse = apiService.editProfile(userId, multipartBody, titleBody, descriptionBody)
                        showSuccessDialog(successResponse.message)
                        showLoading(false)
                    } catch (e: HttpException) {
                        val jsonInString = e.response()?.errorBody()?.string()
                        errorMessage = jsonInString
                        showToast(errorMessage.toString())
                        Log.d(UploadFragment.TAG, errorMessage.toString())
                        showLoading(false)
                    } finally {
                        showLoading(false)
                    }
                }

            } ?: showToast(getString(R.string.empty_image_warning))
        } else {
            showLoading(false)
            showToast(getString(R.string.empty_image_warning))
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val USERID = "userid"
    }
}