package com.coderobust.handcraftsshop.model.repositories

import com.coderobust.handcraftsshop.ui.Order
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class OrderRepository {
    val orderCollection = FirebaseFirestore.getInstance().collection("orders")

    suspend fun saveOrder(order: Order): Result<Boolean> {
        try {
            val document = orderCollection.document()
            order.id = document.id
            document.set(order).await()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
    suspend fun updateOrder(order: Order): Result<Boolean> {
        try {
            val document = orderCollection.document(order.id)
            document.set(order).await()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    fun getOrders() =
        orderCollection.snapshots().map { it.toObjects(Order::class.java) }

}