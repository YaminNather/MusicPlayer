package com.example.businesslogic.playlistapi.interactors

import android.content.Context
import android.net.Uri
import com.example.businesslogic.internalplaylist.repository.InternalPlaylistRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

internal class SetPlaylistImageInteractor(private val context: Context) {
    suspend fun setImage(id: String, image: Uri) = withContext(Dispatchers.IO) {
        val existingImageFile = File(context.filesDir, "playlist/images/$id.jpg")
        if (existingImageFile.exists()) {
            existingImageFile.delete()
        }

        val selectedImageInputStream = context.contentResolver.openInputStream(image)!!
        val creatingImageOutputStream = existingImageFile.outputStream()

        selectedImageInputStream.copyTo(creatingImageOutputStream)

        selectedImageInputStream.close()
        creatingImageOutputStream.close()
    }

}