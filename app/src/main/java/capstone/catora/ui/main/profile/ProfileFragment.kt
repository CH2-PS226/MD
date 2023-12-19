package capstone.catora.ui.main.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import capstone.catora.R
import capstone.catora.data.ArtWorkProfile
import capstone.catora.ui.adapter.ListArtWorkProfileAdapter
import capstone.catora.databinding.FragmentProfileBinding
import capstone.catora.ui.orderprocess.OrderProcessActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    private val list = ArrayList<ArtWorkProfile>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.rvArtworkProfile.setHasFixedSize(true)
        binding.rvArtworkProfile.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        list.add(ArtWorkProfile(R.drawable.dummy_artwork_1))
        list.add(ArtWorkProfile(R.drawable.dummy_artwork_9))
        list.add(ArtWorkProfile(R.drawable.dummy_artwork_1))
        list.add(ArtWorkProfile(R.drawable.dummy_artwork_1))
        list.add(ArtWorkProfile(R.drawable.dummy_artwork_1))
        list.add(ArtWorkProfile(R.drawable.dummy_artwork_11))
        list.add(ArtWorkProfile(R.drawable.dummy_artwork_1))
        list.add(ArtWorkProfile(R.drawable.dummy_artwork_1))
        list.add(ArtWorkProfile(R.drawable.dummy_artwork_2))
        list.add(ArtWorkProfile(R.drawable.dummy_artwork_10))
        list.add(ArtWorkProfile(R.drawable.dummy_artwork_3))
        list.add(ArtWorkProfile(R.drawable.dummy_artwork_9))
        list.add(ArtWorkProfile(R.drawable.dummy_artwork_9))
        list.add(ArtWorkProfile(R.drawable.dummy_artwork_10))
        list.add(ArtWorkProfile(R.drawable.dummy_artwork_11))
        list.add(ArtWorkProfile(R.drawable.dummy_artwork_4))
        list.add(ArtWorkProfile(R.drawable.dummy_artwork_11))
        list.add(ArtWorkProfile(R.drawable.dummy_artwork_4))
        list.add(ArtWorkProfile(R.drawable.dummy_artwork_10))

        binding.rvArtworkProfile.adapter = ListArtWorkProfileAdapter(list)
        binding.btnMyOrder.setOnClickListener {
            startActivity(Intent(requireContext(), OrderProcessActivity::class.java))
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}