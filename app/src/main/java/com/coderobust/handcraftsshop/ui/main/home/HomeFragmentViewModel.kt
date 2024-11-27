package com.coderobust.handcraftsshop.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coderobust.handcraftsshop.model.repositories.HandCraftRepository
import com.coderobust.handcraftsshop.ui.HandCraft
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeFragmentViewModel : ViewModel() {
    val handCraftsRepository = HandCraftRepository()

    val failureMessage = MutableStateFlow<String?>(null)
    val data = MutableStateFlow<List<HandCraft>?>(null)

    init {
        readHandcrafts()
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