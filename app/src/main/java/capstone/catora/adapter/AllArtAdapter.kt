package capstone.catora.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import capstone.catora.data.remote.api.response.AllArtworkResponseItem
import capstone.catora.databinding.ItemArtworkProfileBinding
import capstone.catora.ui.detailartwork.DetailArtworkActivity
import com.bumptech.glide.Glide

class AllArtAdapter: ListAdapter<AllArtworkResponseItem, AllArtAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemArtworkProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val artworkList = getItem(position)
        holder.bind(artworkList)
    }


    class MyViewHolder(val binding: ItemArtworkProfileBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(allArtwork: AllArtworkResponseItem) {
            Glide.with(itemView)
                .load(allArtwork.imageUrl)
                .into(binding.itemArtwork)

            itemView.setOnClickListener {
                val moveWithData = Intent(itemView.context, DetailArtworkActivity::class.java)
                moveWithData.putExtra("userid", allArtwork.artworkId)
                itemView.context.startActivity(moveWithData)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AllArtworkResponseItem>(){
            override fun areItemsTheSame(
                oldItem: AllArtworkResponseItem,
                newItem: AllArtworkResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: AllArtworkResponseItem,
                newItem: AllArtworkResponseItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}