package com.example.businesslogic.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.businesslogic.internalplaylist.repository.PlaylistDao
import com.example.businesslogic.internalplaylist.repository.PlaylistDbModel
import com.example.businesslogic.internalsong.songfetcher.SongDao
import com.example.businesslogic.internalsong.songfetcher.SongDbModel

@Database(
    entities = [
        SongDbModel::class,
        PlaylistDbModel::class,
    ],
    version = 2,
)
@TypeConverters(DateTypeConverters::class)
internal abstract class AppDatabase : RoomDatabase() {
    public abstract val songs: SongDao
    public abstract val playlists: PlaylistDao

    public companion object {
        public fun create(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "music_player")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}