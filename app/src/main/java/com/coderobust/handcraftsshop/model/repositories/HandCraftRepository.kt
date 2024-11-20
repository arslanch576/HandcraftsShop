package com.coderobust.handcraftsshop.model.repositories

import com.coderobust.handcraftsshop.ui.HandCraft
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class HandCraftRepository {
    val handCraftCollection = FirebaseFirestore.getInstance().collection("handcrafts")


    suspend fun saveHandCraft(handCraft: HandCraft): Result<Boolean> {
        try {
            val document = handCraftCollection.document()
            handCraft.id = document.id
            document.set(handCraft).await()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    fun getHandCrafts() =
        handCraftCollection.snapshots().map { it.toObjects(HandCraft::class.java) }

}