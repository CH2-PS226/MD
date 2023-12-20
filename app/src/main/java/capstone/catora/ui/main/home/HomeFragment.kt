package capstone.catora.ui.main.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import capstone.catora.R
import capstone.catora.ui.adapter.AllArtAdapter
import capstone.catora.data.ArtWorkProfile
import capstone.catora.databinding.FragmentHomeBinding
import capstone.catora.ui.adapter.ListArtWorkProfileAdapter
import capstone.catora.data.remote.api.response.AllArtworkResponseItem

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

//    private val list = ArrayList<ArtWorkProfile>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.rvArtwork.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        homeViewModel.listArtwork.observe(viewLifecycleOwner) {
            setAllArtwork(it)
        }


//        list.add(ArtWorkProfile(R.drawable.dummy_artwork_1))
//        list.add(ArtWorkProfile(R.drawable.dummy_artwork_9))
//        list.add(ArtWorkProfile(R.drawable.dummy_artwork_1))
//        list.add(ArtWorkProfile(R.drawable.dummy_artwork_1))
//        list.add(ArtWorkProfile(R.drawable.dummy_artwork_1))
//        list.add(ArtWorkProfile(R.drawable.dummy_artwork_11))
//        list.add(ArtWorkProfile(R.drawable.dummy_artwork_1))
//        list.add(ArtWorkProfile(R.drawable.dummy_artwork_1))
//        list.add(ArtWorkProfile(R.drawable.dummy_artwork_2))
//        list.add(ArtWorkProfile(R.drawable.dummy_artwork_10))
//        list.add(ArtWorkProfile(R.drawable.dummy_artwork_3))
//        list.add(ArtWorkProfile(R.drawable.dummy_artwork_9))
//        list.add(ArtWorkProfile(R.drawable.dummy_artwork_9))
//        list.add(ArtWorkProfile(R.drawable.dummy_artwork_10))
//        list.add(ArtWorkProfile(R.drawable.dummy_artwork_11))
//        list.add(ArtWorkProfile(R.drawable.dummy_artwork_4))
//        list.add(ArtWorkProfile(R.drawable.dummy_artwork_11))
//        list.add(ArtWorkProfile(R.drawable.dummy_artwork_4))
//        list.add(ArtWorkProfile(R.drawable.dummy_artwork_10))

//        binding.rvArtwork.adapter = ListArtWorkProfileAdapter(list)

        return root
    }

    private fun setAllArtwork(artwork: List<AllArtworkResponseItem>?) {
        val adapter = AllArtAdapter()
        adapter.submitList(artwork)
        binding.rvArtwork.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}