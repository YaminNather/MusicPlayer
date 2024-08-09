package com.example.businesslogic.querysongfetcher

import android.content.ContentResolver
import android.content.ContentUris
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.text.TextUtils
import com.example.businesslogic.apimodels.Song
import com.example.businesslogic.internalsong.songfetcher.SongDao
import com.example.businesslogic.internalsong.songfetcher.SongDbModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import kotlin.time.Duration.Companion.milliseconds

internal class QuerySongFetcher(
    private val contentResolver: ContentResolver,
    private val songDao: SongDao,
) {
    public suspend fun fetchWithId(id: String): Song? {
        val cursor: Cursor = withContext(Dispatchers.IO) {
            return@withContext contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                "${MediaStore.Audio.AudioColumns._ID} = ?",
                arrayOf(id),
                null
            )
        } ?: return null


        if (!cursor.moveToFirst()) {
            cursor.close()
            return null
        }

        return mapQueryToSong(cursor)
    }

    public suspend fun getAll(): List<Song> {
        return withContext(Dispatchers.IO) {
            val cursor: Cursor = contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null
            ) ?: return@withContext listOf<Song>()

            if (!cursor.moveToFirst()) return@withContext listOf<Song>()

            val songs: MutableList<Song> = mutableListOf<Song>()

            do {
                songs.add(mapQueryToSong(cursor))
            }
            while(cursor.moveToNext())

            cursor.close()

            return@withContext songs
        }
    }

    public suspend fun getMultiple(ids: List<String>): List<Song> {
        if (ids.isEmpty()) return listOf<Song>()

        var selection: String = ""
        for (i in ids.indices) {
            if (i != 0) selection += " OR "
            selection += "${MediaStore.Audio.AudioColumns._ID} = ?"
        }

        val cursor: Cursor? = withContext(Dispatchers.IO) {
            return@withContext contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                ids.toTypedArray(),
                null,
            )
        }

        if (cursor == null || !cursor.moveToFirst()) return listOf<Song>()

        val songs: MutableList<Song> = mutableListOf<Song>()
        do {
            songs.add(mapQueryToSong(cursor))
        }
        while (cursor.moveToNext())

        cursor.close()

        return songs
    }

    public suspend fun getInAlbum(albumId: String): List<Song> {
        val cursor: Cursor? = withContext(Dispatchers.IO) {
            return@withContext contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                "${MediaStore.Audio.AudioColumns.ALBUM_ID} = ?",
                arrayOf<String>(albumId),
                null,
            )
        }

        if (cursor == null || !cursor.moveToFirst()) return listOf<Song>()

        val songs: MutableList<Song> = mutableListOf<Song>()
        do {
            songs.add(mapQueryToSong(cursor))
        }
        while(cursor.moveToNext())

        cursor.close()

        return songs
    }

    public suspend fun getRecentlyPlayed(): List<Song> {
        val dbModels: List<SongDbModel> = songDao.getRecentlyPlayed()

        if (dbModels.isEmpty()) return listOf<Song>()

        var selectionParameters: String = ""
        for (i in dbModels.indices) {
            if (i != 0) selectionParameters += ", "
            selectionParameters += "?"
        }

        val cursor: Cursor? = withContext(Dispatchers.IO) {
            return@withContext contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                "${MediaStore.Audio.AudioColumns._ID} in ($selectionParameters)",
                dbModels.map { element -> element.id }.toTypedArray(),
                null
            )
        }

        if (cursor == null || !cursor.moveToNext()) return listOf<Song>()

        val songs: MutableList<Song> = mutableListOf<Song>()
        do {
            songs.add(mapQueryToSong(cursor))
        }
        while(cursor.moveToNext())

        cursor.close()

        return songs
    }

    private fun mapQueryToSong(cursor: Cursor): Song {
        val albumId: Long = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ALBUM_ID))

        val potentialCoverArt: Uri = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), albumId)
        var coverArt: Uri?
        try {
            val inputStream: InputStream = contentResolver.openInputStream(potentialCoverArt)!!
            inputStream.close()

            coverArt = potentialCoverArt
        }
        catch (exception: Exception) {
            coverArt = null
        }


        return Song(
            id = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns._ID)),
            name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DISPLAY_NAME)),
            artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ARTIST)),
            coverArt = coverArt,
            duration = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DURATION)).milliseconds,
            albumId = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ALBUM_ID))
        )
    }



    private val projection: Array<String> = arrayOf<String>(
        MediaStore.Audio.AudioColumns._ID,
        MediaStore.Audio.AudioColumns.DISPLAY_NAME,
        MediaStore.Audio.AudioColumns.ARTIST,
        MediaStore.Audio.AudioColumns.DATA,
        MediaStore.Audio.AudioColumns.ALBUM_ID,
        MediaStore.Audio.AudioColumns.DURATION,
    )
}