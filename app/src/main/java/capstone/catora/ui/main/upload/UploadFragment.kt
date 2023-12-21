package capstone.catora.ui.main.upload

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import capstone.catora.R
import capstone.catora.data.remote.api.ApiConfigUploadArt
import capstone.catora.data.remote.api.response.PostUploadArtWorkResponse
import capstone.catora.databinding.FragmentUploadBinding
import capstone.catora.utils.uriToFile
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException

class UploadFragment : Fragment() {

    private var _binding: FragmentUploadBinding? = null
    private var errorMessage: String? = null

    private var currentImageUri: Uri? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ConstraintLayout? {

        _binding = FragmentUploadBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val uploadViewModel = ViewModelProvider(this)[UploadViewModel::class.java]

            //        val textView: TextView = binding.textDashboard
//        dashboardViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }


//        var actionBar = supportActionBar
//
//        if (actionBar != null){
//            actionBar.setDisplayHomeAsUpEnabled(true)
//        }

            binding?.llChooseImage?.setOnClickListener {
                startGalerry()
            }
            binding?.tvChooseOtherImage?.setOnClickListener {
                startGalerry()
            }
            binding?.btnUpload?.setOnClickListener {
                //Random.nextBoolean() just for giving dummy boolean, remove this when system has response from server
        //            uploadAction(Random.nextBoolean())
                uploadArtwork()
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun uploadAction(isHumanArt: String) {

        if (isHumanArt=="Artwork created by Human and uploaded successfully"){
            AlertDialog.Builder(requireContext()).apply {
                setTitle("Success!")
                setMessage("Your art likely human art")
                setPositiveButton("Continue"){_,_ ->
                    activity?.recreate()
                    findNavController().popBackStack()
                    findNavController().navigate(R.id.navigation_notifications)
                }
                create()
                show()
            }
        } else{
            AlertDialog.Builder(requireContext()).apply {
                setTitle("Failed")
                setMessage("Your art likely Ai")
                setPositiveButton("Close"){_,_ ->
//                    finish()
                }
                create()
                show()
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
            binding?.ivArtWork?.setImageURI(it)
            binding?.ivArtWork?.visibility = View.VISIBLE
            binding?.llChooseImage?.visibility = View.GONE
            binding?.tvChooseOtherImage?.visibility = View.VISIBLE
        }
    }

    private fun uploadArtwork() {
        showLoading(true)
        if (currentImageUri != null) {

            currentImageUri?.let { uri ->
//                val imageFile = uriToFile(uri, requireContext()).reduceFileImage()
                val imageFile = uriToFile(uri, requireContext())
                Log.d("Image File", "showImage: ${imageFile.path}")
//                val description = binding.descriptionEditText.text.toString()
//                if (description.isEmpty()) {
//                    showToast("Description ")
//                    showLoading(false)
//                    return
//                }

                val userId = "20"
                val description = binding?.edDescription?.text.toString()
                val tag = binding?.edTag?.text.toString()
                val title = binding?.edTitle?.text.toString()

                val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
                val descriptionBody = description.toRequestBody("text/plain".toMediaType())
                val tagBody = tag.toRequestBody("text/plain".toMediaType())
                val titleBody = title.toRequestBody("text/plain".toMediaType())
                val userIdBody = userId.toRequestBody("text/plain".toMediaType())

                val multipartBody = MultipartBody.Part.createFormData(
                    "image",
                    imageFile.name,
                    requestImageFile
                )

                lifecycleScope.launch {
                    try {
                        val successResponse:PostUploadArtWorkResponse
                        val apiService = ApiConfigUploadArt.getApiService()

                        successResponse = apiService.uploadImage(image = multipartBody, user_id = userIdBody, title = titleBody, tags = tagBody, description = descriptionBody)
                        uploadAction(successResponse.message)
                        showLoading(false)
                    } catch (e: HttpException) {
                        val jsonInString = e.response()?.errorBody()?.string()
                        errorMessage = jsonInString
                        showToast(errorMessage.toString())
                        Log.d(TAG, errorMessage.toString())
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
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object{
        const val TAG = "Upload Fragment"
    }
}