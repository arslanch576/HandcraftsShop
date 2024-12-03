package com.coderobust.handcraftsshop.ui.handcraft

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.coderobust.handcraftsshop.R
import com.coderobust.handcraftsshop.databinding.ActivityHandCraftDetailsBinding
import com.coderobust.handcraftsshop.databinding.ActivityMainBinding
import com.coderobust.handcraftsshop.ui.HandCraft
import com.coderobust.handcraftsshop.ui.order.CreateOrderActivity
import com.google.gson.Gson

class HandCraftDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityHandCraftDetailsBinding;
    lateinit var handCraft:HandCraft

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityHandCraftDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handCraft = Gson().fromJson(intent.getStringExtra("data"),HandCraft::class.java)

        binding.title.text = handCraft.title
        binding.description.text = handCraft.description
        binding.price.text = "${handCraft.price} Rs."
        binding.ratingBar.text = String.format("⭐ %.1f", handCraft.rating)

        binding.orderButton.setOnClickListener {
            startActivity(Intent(this,CreateOrderActivity::class.java).putExtra("data", Gson().toJson(handCraft)))
            finish()
        }
    }
}