package com.coderobust.handcraftsshop.model.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class AuthRepository {
    suspend fun login(email:String,password:String):Result<FirebaseUser>{
        try {
            val result= FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
            return Result.success(result.user!!)
        }catch (e:Exception){
            return Result.failure(e)
        }
    }

    fun getCurrentUser():FirebaseUser?{
        return FirebaseAuth.getInstance().currentUser
    }
}