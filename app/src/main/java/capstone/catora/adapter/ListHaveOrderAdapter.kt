package capstone.catora.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import capstone.catora.R
import capstone.catora.data.SendOrder

class ListHaveOrderAdapter(private val listHaveOrder: ArrayList<SendOrder>) : RecyclerView.Adapter<ListHaveOrderAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListHaveOrderAdapter.ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_have_order, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListHaveOrderAdapter.ListViewHolder, position: Int) {
        holder.tvTitle.text = listHaveOrder[position].title
        holder.tvDescription.text = listHaveOrder[position].description

    }

    override fun getItemCount(): Int {
        return listHaveOrder.size
    }
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle : TextView = itemView.findViewById(R.id.item_tv_title)
        val tvDescription: TextView = itemView.findViewById(R.id.item_tv_description)
        val btnAccept: Button = itemView.findViewById(R.id.item_btn_accept)
        val btnDecline: Button = itemView.findViewById(R.id.item_btn_decline)
    }
}
