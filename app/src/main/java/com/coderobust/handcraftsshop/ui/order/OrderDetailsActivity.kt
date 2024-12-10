package com.coderobust.handcraftsshop.ui.order

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.coderobust.handcraftsshop.R
import com.coderobust.handcraftsshop.databinding.ActivityMainBinding
import com.coderobust.handcraftsshop.databinding.ActivityOrderDetailsBinding
import com.coderobust.handcraftsshop.model.repositories.AuthRepository
import com.coderobust.handcraftsshop.ui.Order
import com.google.firebase.auth.FirebaseUser
import com.google.gson.Gson

class OrderDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityOrderDetailsBinding;
    lateinit var order:Order
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityOrderDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        order= Gson().fromJson(intent.getStringExtra("data"),Order::class.java)

        binding.orderId.text = order.id
        binding.orderDate.text = order.orderDate
        binding.item.text = order.item?.title ?: "N/A"
        binding.quantity.text = order.quantity.toString()
        binding.specialRequirements.text = order.specialRequirements
        binding.postalAddress.text = order.postalAddress
        binding.userName.text = order.userName
        binding.userEmail.text = order.userEmail
        binding.userContact.text = order.userContact
        binding.status.text = order.status

        val user:FirebaseUser = AuthRepository().getCurrentUser()!!
        var isAdmin=false
        if (user.email.equals("arslanch576@gmail.com"))
            isAdmin=true

        if (!order.status.equals("Order Placed")||!isAdmin)
            binding.confirmOrder.visibility= View.GONE

        if (!order.status.equals("Order Confirmed")||!isAdmin)
            binding.deliverOrder.visibility= View.GONE

        if (!order.status.equals("Delivered")||isAdmin)
            binding.confirmOrderReceived.visibility= View.GONE

        binding.confirmOrder.setOnClickListener {
            order.status="Order Confirmed"
            //TODO: update in firestore
        }
        binding.deliverOrder.setOnClickListener {
            order.status="Delivered"
            //TODO: update in firestore
        }
        binding.confirmOrderReceived.setOnClickListener {
            order.status="Order Received"
            //TODO: update in firestore
        }

    }
}