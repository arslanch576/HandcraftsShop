package com.coderobust.handcraftsshop.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.coderobust.handcraftsshop.R
import com.coderobust.handcraftsshop.ui.HandCraft
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = MainViewModel()

        lifecycleScope.launch {
            viewModel.isSuccessfullySaved.collect {
                it?.let {
                    if (it == true)
                        Toast.makeText(this@MainActivity, "Successfully saved", Toast.LENGTH_SHORT)
                            .show()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.failureMessage.collect {
                it?.let {
                    Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.data.collect {
                it?.let {
                    Toast.makeText(this@MainActivity, it.size.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }

        val handcraft = HandCraft()
        handcraft.title = "Acrylic Painting"
        handcraft.description = "Customized acrylic painting with beautiful artistic effects"
        handcraft.price = 5000

        viewModel.saveHandCraft(handcraft)
    }
}