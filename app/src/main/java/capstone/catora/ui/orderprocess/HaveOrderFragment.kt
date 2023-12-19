package capstone.catora.ui.orderprocess

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import capstone.catora.R
import capstone.catora.adapter.ListHaveOrderAdapter
import capstone.catora.data.SendOrder
import capstone.catora.databinding.FragmentHaveOrderBinding
import capstone.catora.databinding.FragmentSendOrderBinding


class HaveOrderFragment : Fragment() {
    private var _binding : FragmentHaveOrderBinding? = null
    private val binding get() = _binding!!
    private lateinit var rvHaveOrder: RecyclerView
    private val listHaveOrder = ArrayList<SendOrder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHaveOrderBinding.inflate(inflater, container, false)
        val root: View = binding.root

        listHaveOrder.add(SendOrder("Produk 1", "Deskripsi 1", "Send"))
        listHaveOrder.add(SendOrder("Produk 2", "Deskripsi 2", "Process"))
        listHaveOrder.add(SendOrder("Produk 3", "Deskripsi 3", "Done"))

        rvHaveOrder = binding.rvHaveOrder
        rvHaveOrder.setHasFixedSize(true)

        rvHaveOrder.layoutManager = LinearLayoutManager(requireContext())
        rvHaveOrder.adapter = ListHaveOrderAdapter(listHaveOrder)

        return root
    }

    companion object {

    }


}