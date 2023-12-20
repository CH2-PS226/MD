package capstone.catora.ui.detailartwork

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import capstone.catora.R
import capstone.catora.ui.adapter.AllArtAdapter
import capstone.catora.data.ArtWorkProfile
import capstone.catora.databinding.ActivityDetailArtworkBinding
import capstone.catora.ui.adapter.ListArtWorkProfileAdapter
import capstone.catora.data.remote.api.response.AllArtworkResponseItem
import capstone.catora.databinding.FragmentHomeBinding
import capstone.catora.ui.ViewModelFactory
import capstone.catora.ui.detailartist.DetailArtist
import capstone.catora.ui.login.LoginViewModel
import capstone.catora.ui.main.home.HomeViewModel
import com.bumptech.glide.Glide

class DetailArtworkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailArtworkBinding
    private lateinit var viewModel: DetailArtworkViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArtworkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailArtworkViewModel::class.java]

        val title = intent.getStringExtra(TITLE)
        val description = intent.getStringExtra(DESCRIPTION)
        val tags = intent.getStringExtra(TAGS)
        val createdat = intent.getStringExtra(CREATEDAT)
        val imageurl = intent.getStringExtra(IMAGEURL)
        val userid = intent.getIntExtra(USERID, 0)

        val imagenull = AppCompatResources.getDrawable( applicationContext, R.drawable.cat_profile)

//        if (userid != null) {
//            val factory = DetailArtworkViewModelFactory.createFactory(userid)
//            viewModel = ViewModelProvider(this, factory)[DetailArtworkViewModel::class.java]
//        }

        binding.tags.text = tags
        binding.title.text = title
        binding.artworkDescription.text = description
        binding.createdat.text = createdat

        Glide.with(this)
            .load(imageurl)
            .into(binding.imageurl)

        viewModel.allArtwork()
        viewModel.getUserByID(userid.toString())

        viewModel.userId.observe(this) {
            binding.artistName.text = it.artistName

            if (it.profileImageUrl != null) {
                Glide.with(this)
                    .load(it.profileImageUrl)
                    .circleCrop()
                    .into(binding.artistPhoto)
            } else {
                Glide.with(this)
                    .load(imagenull)
                    .circleCrop()
                    .into(binding.artistPhoto)
            }


        }

//        binding.rvArtwork.setHasFixedSize(true)
        binding.rvArtwork.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        viewModel.listArtwork.observe(this) {
            setAllArtwork(it)
        }

        binding.artistName.setOnClickListener {
            val moveData = Intent(this, DetailArtist::class.java)
            moveData.putExtra("userid", userid)
            startActivity(moveData)
        }

        binding.artistPhoto.setOnClickListener {
            val moveData = Intent(this, DetailArtist::class.java)
            moveData.putExtra("userid", userid)
            this.startActivity(moveData)
        }
    }

    private fun setAllArtwork(artwork: List<AllArtworkResponseItem>?) {
        val adapter = AllArtAdapter()
        adapter.submitList(artwork)
        binding.rvArtwork.adapter = adapter
    }

    companion object {
        private const val TITLE = "title"
        private const val DESCRIPTION = "description"
        private const val TAGS = "tags"
        private const val USERID = "userid"
        private const val IMAGEURL = "imageurl"
        private const val CREATEDAT = "createdat"
    }
}