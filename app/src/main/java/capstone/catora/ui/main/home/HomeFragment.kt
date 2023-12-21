package capstone.catora.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import capstone.catora.ui.adapter.AllArtAdapter
import capstone.catora.databinding.FragmentHomeBinding
import capstone.catora.data.remote.api.response.AllArtworkResponseItem

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

//    private val list = ArrayList<ArtWorkProfile>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ConstraintLayout? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

            with(binding) {
                this?.searchView?.setupWithSearchBar(this?.searchBar)
                this?.searchView
                    ?.editText
                    ?.setOnEditorActionListener { textView, actionId, event ->
                        searchBar.text = searchView.text
                        searchView.hide()
                        val search = searchView.text.toString()
                        if (search == "") {
                            homeViewModel.allArtwork()
                        }
                        homeViewModel.getArtworkById( search )
                        false
                    }
            }

            binding?.rvArtwork?.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

            homeViewModel.listArtwork.observe(viewLifecycleOwner) {
                if (it != null ){
                    setAllArtwork(it)
                }

            }
        }
    }

    private fun setAllArtwork(artwork: List<AllArtworkResponseItem>?) {
        val adapter = AllArtAdapter()
        adapter.submitList(artwork)
        binding?.rvArtwork?.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}