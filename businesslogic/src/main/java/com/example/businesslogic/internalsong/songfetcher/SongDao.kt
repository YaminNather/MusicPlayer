package com.example.businesslogic.internalsong.songfetcher

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
internal interface SongDao {
    @Upsert
    public suspend fun save(song: SongDbModel): Unit

    @Query("SELECT * FROM songs WHERE id = :id")
    public suspend fun getWithId(id: String): SongDbModel?

    @Query("SELECT * FROM songs WHERE id in (:ids)")
    public suspend fun getMultiple(ids: List<String>): List<SongDbModel>

    @Query("SELECT * FROM songs ORDER BY last_played_at DESC LIMIT 10")
    public suspend fun getRecentlyPlayed(): List<SongDbModel>
}