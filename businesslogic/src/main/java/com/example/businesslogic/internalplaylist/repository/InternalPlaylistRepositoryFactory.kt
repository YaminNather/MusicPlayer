package com.example.businesslogic.internalplaylist.repository

internal class InternalPlaylistRepositoryFactory(private val dao: PlaylistDao) {
    public fun build(): InternalPlaylistRepository {
        return InternalPlaylistRepository(dao = dao, mapper = PlaylistMapper())
    }
}