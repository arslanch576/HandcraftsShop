package com.coderobust.handcraftsshop.model.repositories

import com.coderobust.handcraftsshop.model.dataSource.CloudinaryUploadHelper

class StorageRepository {

    fun uploadFile(filePath:String,onComplete: (Boolean,String?) -> Unit){
        CloudinaryUploadHelper().uploadFile(filePath,onComplete)
    }

}