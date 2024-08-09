package com.example.businesslogic.internalsong.songfetcher

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "songs")
internal data class SongDbModel(
    @PrimaryKey
    @ColumnInfo("id")
    public val id: String,

    @ColumnInfo(name = "is_liked")
    public val isLiked: Boolean,

    @ColumnInfo(name = "last_played_at")
    public val lastPlayedAt: Date,
)