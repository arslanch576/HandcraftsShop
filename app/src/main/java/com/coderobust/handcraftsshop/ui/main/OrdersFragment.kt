package com.coderobust.handcraftsshop.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.coderobust.constructioncosttracker.adapters.HandCraftAdapter
import com.coderobust.handcraftsshop.R
import com.coderobust.handcraftsshop.databinding.FragmentHomeBinding
import com.coderobust.handcraftsshop.databinding.FragmentOrdersBinding
import com.coderobust.handcraftsshop.ui.HandCraft
import com.coderobust.handcraftsshop.ui.Order
import com.coderobust.handcraftsshop.ui.handcraft.AddHandcraftActivity
import com.coderobust.handcraftsshop.ui.main.home.HomeFragmentViewModel
import kotlinx.coroutines.launch

class OrdersFragment : Fragment() {

    lateinit var adapter: HandCraftAdapter
    lateinit var binding: FragmentOrdersBinding
    lateinit var viewModel: OrdersFragmentViewModel
    val items=ArrayList<Order>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentOrdersBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter= HandCraftAdapter(items)
        binding.recyclerview.adapter=adapter
        binding.recyclerview.layoutManager= LinearLayoutManager(context)

        viewModel= OrdersFragmentViewModel()
        lifecycleScope.launch {
            viewModel.failureMessage.collect {
                it?.let {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.data.collect {
                it?.let {
                    items.clear()
                    items.addAll(it)
                    adapter.notifyDataSetChanged()
                }
            }
        }


    }

}