package com.coderobust.handcraftsshop.ui.handcraft

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coderobust.handcraftsshop.model.repositories.AuthRepository
import com.coderobust.handcraftsshop.model.repositories.HandCraftRepository
import com.coderobust.handcraftsshop.ui.HandCraft
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AddHandcraftViewModel:ViewModel() {
    val handCraftsRepository = HandCraftRepository()

    val isSuccessfullySaved = MutableStateFlow<Boolean?>(null)
    val failureMessage = MutableStateFlow<String?>(null)

    fun saveHandCraft(handCraft: HandCraft) {
        viewModelScope.launch {
            val result = handCraftsRepository.saveHandCraft(handCraft)
            if (result.isSuccess) {
                isSuccessfullySaved.value = true
            } else {
                failureMessage.value = result.exceptionOrNull()?.message
            }
        }
    }
}