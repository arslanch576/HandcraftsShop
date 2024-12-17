package com.coderobust.handcraftsshop.ui.order

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.coderobust.handcraftsshop.databinding.ActivityCreateOrderBinding
import com.coderobust.handcraftsshop.ui.HandCraft
import com.coderobust.handcraftsshop.ui.Order
import com.google.firebase.auth.FirebaseUser
import com.google.gson.Gson
import kotlinx.coroutines.launch
import ui.main.MainActivity
import java.text.SimpleDateFormat

class CreateOrderActivity : AppCompatActivity() {
    lateinit var binding: ActivityCreateOrderBinding;
    lateinit var handCraft:HandCraft
    lateinit var viewModel: CreateOrderViewModel
    lateinit var progressDialog:ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCreateOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel=CreateOrderViewModel()
        handCraft = Gson().fromJson(intent.getStringExtra("data"),HandCraft::class.java)

        binding.title.text = handCraft.title
        binding.price.text = "${handCraft.price} Rs."

        progressDialog=ProgressDialog(this)
        progressDialog.setMessage("Saving your order...")
        progressDialog.setCancelable(false)

        lifecycleScope.launch {
            viewModel.isSaving.collect{
                if (it==true)
                    progressDialog.show()
                else
                    progressDialog.dismiss()
            }
        }
        lifecycleScope.launch {
            viewModel.failureMessage.collect{
                if (it!=null)
                    Toast.makeText(this@CreateOrderActivity,it,Toast.LENGTH_LONG).show()
            }
        }
        lifecycleScope.launch {
            viewModel.isSaved.collect{
                if (it==true) {
                    Toast.makeText(
                        this@CreateOrderActivity,
                        "Order saved successfully",
                        Toast.LENGTH_LONG
                    ).show()
                    finish()
                }
            }
        }

        binding.placeOrderButton.setOnClickListener {
            val quantity = binding.quantity.text.toString().toIntOrNull()
            val specialRequirements = binding.specialRequirements.text.toString()
            val postalAddress = binding.postalAddress.text.toString()
            val userContact = binding.userContact.text.toString()

            if (quantity == null || postalAddress.isEmpty() || userContact.isEmpty()) {
                Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val order=Order()
            order.item=handCraft
            order.status="Order Placed"
            order.quantity=quantity
            order.postalAddress=postalAddress
            order.specialRequirements=specialRequirements
            order.userContact=userContact
            order.userFCMToken=MainActivity.fcmToken
            order.orderDate=SimpleDateFormat("yyyy-MM-dd HH:mm a").format(System.currentTimeMillis())
            val user=viewModel.getCurrentUser()
            order.userEmail=user?.email!!
            order.userName=user?.displayName!!
            order.userId=user?.uid!!

            viewModel.saveOrder(order)

            Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show()
        }

    }
}