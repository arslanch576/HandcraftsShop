package com.coderobust.handcraftsshop.ui.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coderobust.handcraftsshop.model.repositories.OrderRepository
import com.coderobust.handcraftsshop.ui.Order
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class OrderDetailsViewModel:ViewModel() {
    val orderRepository=OrderRepository()

    val isUpdated= MutableStateFlow<Boolean?>(null)
    val failureMessage= MutableStateFlow<String?>(null)

    public fun updateOrder(order:Order){
        viewModelScope.launch {
            val result=orderRepository.updateOrder(order)
            if (result.isSuccess)
                isUpdated.value=true
            else
                failureMessage.value=result.exceptionOrNull()?.message
        }
    }
}