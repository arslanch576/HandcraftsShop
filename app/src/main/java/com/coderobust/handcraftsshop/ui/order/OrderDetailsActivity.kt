package com.coderobust.handcraftsshop.ui.order

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.coderobust.handcraftsshop.databinding.ActivityOrderDetailsBinding
import com.coderobust.handcraftsshop.model.repositories.AuthRepository
import com.coderobust.handcraftsshop.model.repositories.NotificationsRepository
import com.coderobust.handcraftsshop.ui.Order
import com.google.firebase.auth.FirebaseUser
import com.google.gson.Gson
import kotlinx.coroutines.launch
import ui.main.MainActivity

class OrderDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityOrderDetailsBinding;
    lateinit var order: Order
    lateinit var viewModel: OrderDetailsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = OrderDetailsViewModel()
        order = Gson().fromJson(intent.getStringExtra("data"), Order::class.java)

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

        val user: FirebaseUser = AuthRepository().getCurrentUser()!!
        var isAdmin = false
        if (user.email.equals("arslan@gmail.com"))
            isAdmin = true

        if (!order.status.equals("Order Placed") || !isAdmin)
            binding.confirmOrder.visibility = View.GONE

        if (!order.status.equals("Order Confirmed") || !isAdmin)
            binding.deliverOrder.visibility = View.GONE

        if (!order.status.equals("Delivered") || isAdmin)
            binding.confirmOrderReceived.visibility = View.GONE

        binding.confirmOrder.setOnClickListener {
            order.status = "Order Confirmed"
            viewModel.updateOrder(order)
        }
        binding.deliverOrder.setOnClickListener {
            order.status = "Delivered"
            viewModel.updateOrder(order)
        }
        binding.confirmOrderReceived.setOnClickListener {
            order.status = "Order Received"
            viewModel.updateOrder(order)
        }

        lifecycleScope.launch {
            viewModel.isUpdated.collect {
                it?.let {
                    if (order.status.equals("Order Confirmed")) {
                        NotificationsRepository().sendNotification(order.userId, "Order confirmed", "Your order of ${order.item?.title} has been confirmed", this@OrderDetailsActivity)
                    }
                    if (order.status.equals("Delivered")) {
                        NotificationsRepository().sendNotification(order.userId, "Order dispatched", "Your order of ${order.item?.title} has been dispatched, you will receive it soon at your postal address", this@OrderDetailsActivity)
                    }
                    if (order.status.equals("Order Received")) {
                        NotificationsRepository().sendNotification(MainActivity.adminUid, "Order Received", "${order.userName} has received the order ${order.item?.title}.", this@OrderDetailsActivity)
                    }
                    Toast.makeText(this@OrderDetailsActivity, "Updated", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.failureMessage.collect {
                it?.let {
                    Toast.makeText(this@OrderDetailsActivity, it, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}