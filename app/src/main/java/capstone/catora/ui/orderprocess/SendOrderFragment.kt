package capstone.catora.ui.orderprocess

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import capstone.catora.R
import capstone.catora.adapter.ListSendOrderAdapter
import capstone.catora.data.SendOrder
import capstone.catora.databinding.FragmentSendOrderBinding

class SendOrderFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private var _binding : FragmentSendOrderBinding? = null
    private val binding get() = _binding!!

    private lateinit var rvSendOrder: RecyclerView
    private val listSendOrder = ArrayList<SendOrder>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSendOrderBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val spinner = binding.spinnerSortSendOrder
        ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.sort_send_order,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        listSendOrder.add(SendOrder("Produk 1", "Deskripsi 1", "Send"))
        listSendOrder.add(SendOrder("Produk 2", "Deskripsi 2", "Process"))
        listSendOrder.add(SendOrder("Produk 3", "Deskripsi 3", "Done"))

        spinner.onItemSelectedListener = this

        rvSendOrder = binding.rvSendOrder
        rvSendOrder.setHasFixedSize(true)

        rvSendOrder.layoutManager = LinearLayoutManager(requireContext())
        rvSendOrder.adapter = ListSendOrderAdapter(listSendOrder)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        val selectedItem = parent?.getItemAtPosition(pos).toString()
        Toast.makeText(requireContext(), selectedItem, Toast.LENGTH_SHORT).show()
//        when(selectedItem){
//            "Send" -> Toast.makeText(requireContext(), selectedItem, Toast.LENGTH_SHORT).show()
//            "Process" -> Toast.makeText(requireContext(), selectedItem, Toast.LENGTH_SHORT).show()
//            "Done" -> Toast.makeText(requireContext(), selectedItem, Toast.LENGTH_SHORT).show()
//        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}