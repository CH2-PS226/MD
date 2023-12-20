package capstone.catora.ui.detailartist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import capstone.catora.R
import capstone.catora.data.remote.api.response.AllArtworkResponseItem
import capstone.catora.databinding.ActivityDetailArtistBinding
import capstone.catora.databinding.ActivityDetailArtworkBinding
import capstone.catora.ui.adapter.AllArtAdapter
import capstone.catora.ui.detailartwork.DetailArtworkActivity
import capstone.catora.ui.detailartwork.DetailArtworkViewModel
import capstone.catora.ui.ordercommission.OrderCommissionActivity
import com.bumptech.glide.Glide

class DetailArtist : AppCompatActivity() {
    private lateinit var binding: ActivityDetailArtistBinding
    private lateinit var viewModel: DetailArtistViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArtistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailArtistViewModel::class.java]

        val userid = intent.getIntExtra(USERID, 0)

        val imagenull = AppCompatResources.getDrawable( applicationContext, R.drawable.cat_profile)

        viewModel.allArtwork()
        viewModel.getUserByID(userid.toString())

        viewModel.userId.observe(this) {
            binding.artistName.text = it.artistName
            binding.artistDescriptoin.text = it.description
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

        binding.orderBtn.setOnClickListener {
            val moveData = Intent(this, OrderCommissionActivity::class.java)
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
        private const val USERID = "userid"
    }
}