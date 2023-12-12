package capstone.catora.ui.main.upload

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import capstone.catora.databinding.FragmentUploadBinding
import kotlin.random.Random

class UploadFragment : Fragment() {

    private var _binding: FragmentUploadBinding? = null

    private var currentImageUri: Uri? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val uploadViewModel =
            ViewModelProvider(this).get(UploadViewModel::class.java)

        _binding = FragmentUploadBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textDashboard
//        dashboardViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }


//        var actionBar = supportActionBar
//
//        if (actionBar != null){
//            actionBar.setDisplayHomeAsUpEnabled(true)
//        }

        binding.llChooseImage.setOnClickListener {
            startGalerry()
        }
        binding.tvChooseOtherImage.setOnClickListener {
            startGalerry()
        }
        binding.btnUpload.setOnClickListener {
            //Random.nextBoolean() just for giving dummy boolean, remove this when system has response from server
            uploadAction(Random.nextBoolean())
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun uploadAction(isHumanArt: Boolean) {

        if (isHumanArt){
            AlertDialog.Builder(requireContext()).apply {
                setTitle("Success!")
                setMessage("Your art likely human art")
                setPositiveButton("Continue"){_,_ ->
//                    finish()
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
            binding.ivArtWork.setImageURI(it)
            binding.ivArtWork.visibility = View.VISIBLE
            binding.llChooseImage.visibility = View.GONE
            binding.tvChooseOtherImage.visibility = View.VISIBLE
        }
    }
}