package com.example.businesslogic.internalsong.songfetcher

import android.database.Cursor
import android.provider.MediaStore
import com.example.businesslogic.internalsong.InternalSong
import java.util.Date

internal class SongMapper {
    public fun toDomainModel(queryCursor: Cursor, dbModel: SongDbModel?): InternalSong {
        val idColumnIndex: Int = queryCursor
            .getColumnIndexOrThrow(MediaStore.Audio.AudioColumns._ID)

        val uriColumnIndex: Int = queryCursor
            .getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATA)

        return InternalSong(
            id = queryCursor.getString(idColumnIndex),
            uri = queryCursor.getString(uriColumnIndex),
            isLiked = dbModel?.isLiked ?: false,
            lastPlayed = dbModel?.lastPlayedAt ?: Date(0L),
        )
    }

    public fun mapToDbModel(domainModel: InternalSong): SongDbModel {
        return SongDbModel(
            id = domainModel.id,
            isLiked = domainModel.isLiked,
            lastPlayedAt = domainModel.lastPlayedAt,
        )
    }
}