package com.coderobust.handcraftsshop.ui.handcraft

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.coderobust.handcraftsshop.R
import com.coderobust.handcraftsshop.databinding.ActivityAddHandcraftBinding
import com.coderobust.handcraftsshop.databinding.ActivityMainBinding
import com.coderobust.handcraftsshop.ui.HandCraft
import kotlinx.coroutines.launch

class AddHandcraftActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddHandcraftBinding;
    lateinit var viewModel: AddHandcraftViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddHandcraftBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = AddHandcraftViewModel()

        lifecycleScope.launch {
            viewModel.isSuccessfullySaved.collect {
                it?.let {
                    if (it == true) {
                        Toast.makeText(
                            this@AddHandcraftActivity,
                            "Successfully saved",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        finish()
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewModel.failureMessage.collect {
                it?.let {
                    Toast.makeText(this@AddHandcraftActivity, it, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.submitButton.setOnClickListener {
            val title = binding.titleInput.text.toString().trim()
            val description = binding.descriptionInput.text.toString().trim()
            val priceText = binding.priceInput.text.toString().trim()

            // Validate the input fields
            if (title.isEmpty() || description.isEmpty() || priceText.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val price = priceText.toIntOrNull()

            if (price == null) {
                Toast.makeText(this, "Please enter a valid price", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create a Handcraft object with the entered data
            val handcraft = HandCraft()
            handcraft.title = title
            handcraft.price = price
            handcraft.description = description

            viewModel.saveHandCraft(handcraft)

            // Save the Handcraft object (this would be a database operation, Firestore, etc.)
            // For now, just display the success message
            Toast.makeText(this, "Handcraft Added Successfully!", Toast.LENGTH_SHORT).show()

        }


    }
}