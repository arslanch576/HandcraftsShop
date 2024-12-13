package com.coderobust.handcraftsshop.ui.handcraft

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coderobust.handcraftsshop.model.repositories.AuthRepository
import com.coderobust.handcraftsshop.model.repositories.HandCraftRepository
import com.coderobust.handcraftsshop.model.repositories.StorageRepository
import com.coderobust.handcraftsshop.ui.HandCraft
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AddHandcraftViewModel:ViewModel() {
    val handCraftsRepository = HandCraftRepository()
    val storageRepository = StorageRepository()

    val isSuccessfullySaved = MutableStateFlow<Boolean?>(null)
    val failureMessage = MutableStateFlow<String?>(null)

    fun uploadImageAndSaveHandCraft(imagePath: String, handCraft: HandCraft) {
        storageRepository.uploadFile(imagePath, onComplete = { success, result ->
            if (success) {
                handCraft.image=result!!
                saveHandCraft(handCraft)
            }
            else failureMessage.value=result
        })
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

}