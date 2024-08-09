package com.example.businesslogic.internalplaylist.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
internal interface PlaylistDao {
    @Upsert
    public suspend fun save(playlist: PlaylistDbModel): Unit

    @Query("SELECT * FROM playlists WHERE id = :id")
    public suspend fun getWithId(id: String): PlaylistDbModel?

    @Query("SELECT * FROM playlists")
    public suspend fun getAll(): List<PlaylistDbModel>

    @Query("DELETE FROM playlists WHERE id = :id")
    public suspend fun deleteWithId(id: String): Unit
}