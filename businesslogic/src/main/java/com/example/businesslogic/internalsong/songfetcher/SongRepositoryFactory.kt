package com.example.businesslogic.internalsong.songfetcher

import android.content.ContentResolver

internal class SongRepositoryFactory(
    private val contentResolver: ContentResolver,
    private val songDao: SongDao,
) {
    public fun build(): SongRepository {
        return SongRepository(contentResolver, SongMapper(), songDao)
    }
}