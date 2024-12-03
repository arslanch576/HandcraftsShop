package com.coderobust.handcraftsshop.ui.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coderobust.handcraftsshop.model.repositories.AuthRepository
import com.coderobust.handcraftsshop.model.repositories.OrderRepository
import com.coderobust.handcraftsshop.ui.Order
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CreateOrderViewModel:ViewModel() {
    val orderRepository= OrderRepository()
    val authRepository=AuthRepository()

    val isSaving= MutableStateFlow<Boolean>(false)
    val isSaved= MutableStateFlow<Boolean?>(null)
    val failureMessage= MutableStateFlow<String?>(null)

    fun getCurrentUser() = authRepository.getCurrentUser()

    fun saveOrder(order:Order){
        viewModelScope.launch {
            val result=orderRepository.saveOrder(order)
            isSaving.value=false
            if (result.isSuccess){
                isSaved.value=true
            }else{
                failureMessage.value=result.exceptionOrNull()?.message
            }
        }
    }


}