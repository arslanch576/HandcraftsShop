package com.coderobust.handcraftsshop.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coderobust.handcraftsshop.model.repositories.HandCraftRepository
import com.coderobust.handcraftsshop.ui.HandCraft
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val handCraftsRepository = HandCraftRepository()

    val isSuccessfullySaved = MutableStateFlow<Boolean?>(null)
    val failureMessage = MutableStateFlow<String?>(null)
    val data = MutableStateFlow<List<HandCraft>?>(null)

    init {
        readHandcrafts()
    }

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

    fun readHandcrafts() {
        viewModelScope.launch {
            handCraftsRepository.getHandCrafts().catch {
                failureMessage.value = it.message
            }
                .collect {
                    data.value = it
                }
        }
    }
}