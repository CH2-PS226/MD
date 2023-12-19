package capstone.catora.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import capstone.catora.R
import capstone.catora.data.SendOrder

class ListSendOrderAdapter(private val listSendOrder: ArrayList<SendOrder>) : RecyclerView.Adapter<ListSendOrderAdapter.ListViewHolder>() {
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle : TextView = itemView.findViewById(R.id.item_tv_title)
        val tvDescription: TextView = itemView.findViewById(R.id.item_tv_description)
        val ivCategory: ImageView = itemView.findViewById(R.id.item_iv_category)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListSendOrderAdapter.ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_send_order, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val title = listSendOrder[position].title
        val description = listSendOrder[position].description
        val category = listSendOrder[position].category
        holder.tvTitle.text = title
        holder.tvDescription.text = description
        holder.ivCategory.setImageResource(
            when(category){
                "Send" -> R.drawable.ic_send_order_category_send
                "Process" -> R.drawable.ic_send_order_category_process
//                "Done" -> R.drawable.ic_send_order_category_done
                else -> R.drawable.ic_send_order_category_done
            }
        )
    }

    override fun getItemCount(): Int {
        return listSendOrder.size
    }
}