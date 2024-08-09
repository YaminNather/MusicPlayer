package com.example.businesslogic.internalplaylist.repository

import com.example.businesslogic.internalplaylist.InternalPlaylist

internal class InternalPlaylistRepository(
    private val dao: PlaylistDao,
    private val mapper: PlaylistMapper,
) {
    public suspend fun save(domainModel: InternalPlaylist): Unit {
        dao.save(mapper.toDbModel(domainModel))
    }

    public suspend fun getWithId(id: String): InternalPlaylist? {
        val dao: PlaylistDbModel = dao.getWithId(id) ?: return null

        return mapper.toDomainModel(dao)
    }

    public suspend fun delete(id: String): Unit {
        return dao.deleteWithId(id)
    }

    public suspend fun exists(id: String): Boolean {
        return dao.getWithId(id) != null
    }
}