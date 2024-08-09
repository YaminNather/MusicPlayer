package com.example.businesslogic.internalplaylist.repository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlists")
internal data class PlaylistDbModel(
    @PrimaryKey
    @ColumnInfo("id")
    public val id: String,

    @ColumnInfo("name")
    public val name: String,

    @ColumnInfo("songs")
    public val songs: List<String>,
)