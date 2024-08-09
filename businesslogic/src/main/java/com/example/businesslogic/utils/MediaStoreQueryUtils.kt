package com.example.businesslogic.utils

import android.content.ContentResolver
import android.content.ContentUris
import android.net.Uri
import java.io.InputStream

internal fun ContentResolver.albumArtExists(id: Long): Boolean {
    val coverArtUri: Uri = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), id)

    val inputStream: InputStream
    try {
        inputStream = this.openInputStream(coverArtUri)!!
        inputStream.close()
    }
    catch(exception: Exception) {
        return false
    }

    return true
}

internal fun ContentResolver.getAlbumCoverArt(id: Long): Uri? {
    val coverArtUri: Uri = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), id)

    return if (albumArtExists(id)) coverArtUri else null
}