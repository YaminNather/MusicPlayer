package com.example.businesslogic.internalsong.songfetcher

import android.content.ContentResolver
import android.database.Cursor
import android.provider.MediaStore
import com.example.businesslogic.internalsong.InternalSong
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class SongRepository(
    private val contentResolver: ContentResolver,
    private val mapper: SongMapper,
    private val songDao: SongDao,
) {
    public suspend fun save(song: InternalSong): Unit {
        val dbModel: SongDbModel = mapper.mapToDbModel(song)
        withContext(Dispatchers.IO) { songDao.save(dbModel) }
    }

    public suspend fun fetchWithId(id: String): InternalSong? {
        val cursor: Cursor? = withContext(Dispatchers.IO) {
            return@withContext contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                "${MediaStore.Audio.AudioColumns._ID} = ?",
                arrayOf<String>(id),
                null,
            )
        }

        if (cursor == null || !cursor.moveToFirst()) {
            return null
        }

        val dbModel: SongDbModel? = songDao.getWithId(id)

        val song: InternalSong = mapper.toDomainModel(cursor, dbModel)

        cursor.close()

        return song
    }

    public suspend fun fetchAll(): List<InternalSong> {
        val cursor: Cursor? = withContext(Dispatchers.IO) {
            return@withContext contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null,
            )
        }

        if (cursor == null || !cursor.moveToFirst()) {
            return listOf<InternalSong>()
        }

        val dbModels: List<SongDbModel> = songDao.getMultiple(getAllIdsFromCursor(cursor))

        val songs: List<InternalSong> = combineNativeAndNonNativeSongQuery(cursor, dbModels)

        cursor.close()

        return songs
    }

    public suspend fun fetchMultiple(ids: List<String>): List<InternalSong> {
        var querySelection: String = ""
        for (i in 0..ids.size) {
            if (i != 0) querySelection += " OR "
            querySelection += "${MediaStore.Audio.AudioColumns._ID} = ?"
        }

        val cursor: Cursor? = withContext(Dispatchers.IO) {
            return@withContext contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                querySelection,
                ids.toTypedArray(),
                null,
            )
        }

        if (cursor == null || !cursor.moveToFirst()) {
            return listOf<InternalSong>()
        }

        val dbModels: List<SongDbModel> = songDao.getMultiple(getAllIdsFromCursor(cursor))

        val songs: List<InternalSong> = combineNativeAndNonNativeSongQuery(cursor, dbModels)

        cursor.close()

        return songs
    }

    private fun getAllIdsFromCursor(cursor: Cursor): List<String> {
        val ids: MutableList<String> = mutableListOf<String>()
        do {
            cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns._ID))
        }
        while(cursor.moveToNext())

        cursor.moveToFirst()

        return ids
    }

    private fun combineNativeAndNonNativeSongQuery(
        cursor: Cursor,
        songDbModels: List<SongDbModel>
    ): List<InternalSong> {
        val songs: MutableList<InternalSong> = mutableListOf<InternalSong>()
        do {
            val id: String = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns._ID))
            val songDbModel: SongDbModel? = songDbModels.find { element -> element.id == id }
            val song: InternalSong = mapper.toDomainModel(cursor, songDbModel)

            songs.add(song)
        }
        while(cursor.moveToNext())

        return songs
    }


    private val projection: Array<String> = arrayOf<String>(
        MediaStore.Audio.AudioColumns._ID,
        MediaStore.Audio.AudioColumns.DISPLAY_NAME,
        MediaStore.Audio.AudioColumns.DATA,
    )
}
