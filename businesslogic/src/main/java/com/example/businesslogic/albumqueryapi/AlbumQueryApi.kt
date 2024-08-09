package com.example.businesslogic.albumqueryapi

import android.content.ContentResolver
import android.content.ContentUris
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import com.example.businesslogic.apimodels.Album
import com.example.businesslogic.utils.albumArtExists
import com.example.businesslogic.utils.getAlbumCoverArt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream

public class AlbumQueryApi internal constructor(private val contentResolver: ContentResolver) {
    @RequiresApi(Build.VERSION_CODES.Q)
    public suspend fun getById(id: String): Album? {
        val cursor: Cursor? = withContext(Dispatchers.IO) {
            return@withContext contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                "${MediaStore.Audio.AlbumColumns.ALBUM_ID} = ?",
                arrayOf<String>(id),
                null,
            )
        }

        if (cursor == null || !cursor.moveToFirst()) return null

        return getAlbumFromCursor(cursor)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    public suspend fun getAll(): List<Album> {
        val cursor: Cursor? = withContext(Dispatchers.IO) {
            return@withContext contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null,
            )
        }

        if (cursor == null || !cursor.moveToFirst()) return listOf<Album>()

        val albums: MutableMap<Long, Album> = mutableMapOf<Long, Album>()
        do {
            val id: Long = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.AlbumColumns.ALBUM_ID))

            if (albums.containsKey(id)) continue

            albums[id] = getAlbumFromCursor(cursor)
        }
        while(cursor.moveToNext())

        cursor.close()

        return albums.values.toList()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun getAlbumFromCursor(cursor: Cursor): Album {
        val id: Long = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.AlbumColumns.ALBUM_ID))

        return Album(
            id = id.toString(),
            name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ALBUM)),
            coverArt = contentResolver.getAlbumCoverArt(id),
            artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AlbumColumns.ARTIST))
        )
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    private val projection: Array<String> = arrayOf<String>(
        MediaStore.Audio.AlbumColumns.ALBUM_ID,
        MediaStore.Audio.AlbumColumns.ALBUM,
        MediaStore.Audio.AlbumColumns.ARTIST,
    )
}