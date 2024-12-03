package com.coderobust.handcraftsshop.ui.main.home

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
import com.coderobust.handcraftsshop.ui.HandCraft
import com.coderobust.handcraftsshop.ui.handcraft.AddHandcraftActivity
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    lateinit var adapter: HandCraftAdapter
    lateinit var binding:FragmentHomeBinding
    lateinit var viewModel: HomeFragmentViewModel
    val items=ArrayList<HandCraft>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter= HandCraftAdapter(items)
        binding.recyclerview.adapter=adapter
        binding.recyclerview.layoutManager= LinearLayoutManager(context)

        viewModel= HomeFragmentViewModel()
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

        binding.floatingActionButton.setOnClickListener {
            startActivity(Intent(context,AddHandcraftActivity::class.java))
        }
    }

}