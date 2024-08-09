package com.example.businesslogic.internalplaylist.repository

import com.example.businesslogic.internalplaylist.InternalPlaylist

internal class PlaylistMapper {
    public fun toDomainModel(dbModel: PlaylistDbModel): InternalPlaylist {
        return InternalPlaylist(
            id = dbModel.id,
            name = dbModel.name,
            songs = dbModel.songs,
        )
    }

    public fun toDbModel(domainModel: InternalPlaylist): PlaylistDbModel {
        return PlaylistDbModel(
            id = domainModel.id,
            name = domainModel.name,
            songs = domainModel.songs,
        )
    }
}