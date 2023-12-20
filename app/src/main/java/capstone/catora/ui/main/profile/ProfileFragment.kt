package capstone.catora.ui.main.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import capstone.catora.data.pref.UserPreferences
import capstone.catora.data.pref.dataStore
import capstone.catora.data.remote.api.response.AllArtworkResponseItem
import capstone.catora.databinding.FragmentProfileBinding
import capstone.catora.ui.adapter.AllArtAdapter
import capstone.catora.ui.editprofile.EditProfileActivity
import capstone.catora.ui.orderprocess.OrderProcessActivity
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

//    private val list = ArrayList<ArtWorkProfile>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel =
            ViewModelProvider(this)[ProfileViewModel::class.java]

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val pref = UserPreferences.getInstance(requireActivity().dataStore)
        val user = runBlocking { pref.getSession().first() }

        val userId = user.userId

        Log.d("userid", userId)

        profileViewModel.getUserByID(userId)
        profileViewModel.userId.observe(requireActivity()) {
            if (it != null) {
                binding.tvUsername.text = it.artistName
                binding.tvProfileDescription.text = it.description

                Glide.with(requireActivity())
                    .load(it.profileImageUrl)
                    .circleCrop()
                    .into(binding.ivProfileImage)
            }
        }

        profileViewModel.getArtworkById(userId)
        profileViewModel.listArtwork.observe(requireActivity()) {
            if (it != null ) {
                setAllArtwork(it)
            }
        }

//        binding.rvArtworkProfile.setHasFixedSize(true)
        binding.rvArtworkProfile.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        binding.btnMyOrder.setOnClickListener {
            startActivity(Intent(requireContext(), OrderProcessActivity::class.java))
        }

        binding.btnEditProfile.setOnClickListener {
            val moveData = Intent(requireActivity(), EditProfileActivity::class.java)
            moveData.putExtra("userid", userId)
            requireActivity().startActivity(moveData)
        }

        return root
    }

    private fun setAllArtwork(artwork: List<AllArtworkResponseItem>?) {
        val adapter = AllArtAdapter()
        adapter.submitList(artwork)
        binding.rvArtworkProfile.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}