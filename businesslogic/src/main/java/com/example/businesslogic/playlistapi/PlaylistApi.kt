package com.example.businesslogic.playlistapi

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import com.example.businesslogic.BuiltInPlaylists
import com.example.businesslogic.apimodels.Playlist
import com.example.businesslogic.internalplaylist.InternalPlaylist
import com.example.businesslogic.internalplaylist.repository.InternalPlaylistRepository
import com.example.businesslogic.internalplaylist.repository.PlaylistDao
import com.example.businesslogic.internalplaylist.repository.PlaylistDbModel
import com.example.businesslogic.playlistapi.interactors.SetPlaylistImageInteractor
import com.example.businesslogic.songqueryapi.SongQueryApi
import java.io.File
import java.security.InvalidParameterException
import java.util.UUID

public class PlaylistApi internal constructor(
    private val playlistRepository: InternalPlaylistRepository,
    private val dao: PlaylistDao,
    private val songQueryApi: SongQueryApi,
    private val context: Context,

    private val setPlaylistImageInteractor: SetPlaylistImageInteractor,
) {
    public suspend fun createPlaylist(name: String, songs: List<String> = listOf<String>()): Unit {
        val playlist: InternalPlaylist = InternalPlaylist(
            id = UUID.randomUUID().toString(),
            name = name,
            songs = listOf<String>(),
        )

        playlist.addSongs(songs)

        playlistRepository.save(playlist)
    }

    public suspend fun changeName(id: String, newName: String) {
        val playlist = playlistRepository.getWithId(id)!!
        playlist.changeName(newName)
        playlistRepository.save(playlist)
    }

    public suspend fun setImage(id: String, image: Uri): Unit {
        setPlaylistImageInteractor.setImage(id, image)
    }

    public suspend fun addSong(playlistId: String, songId: String): Unit {
        val playlist: InternalPlaylist = playlistRepository.getWithId(playlistId)
            ?: throw InvalidParameterException("Playlist with id $playlistId does not exist")

        playlist.addSong(songId)

        playlistRepository.save(playlist)
    }

    public suspend fun addSongs(playlistId: String, songIds: List<String>): Unit {
        val playlist: InternalPlaylist = playlistRepository.getWithId(playlistId)
            ?: throw InvalidParameterException("Playlist with id $playlistId does not exist")

        playlist.addSongs(songIds)

        playlistRepository.save(playlist)
    }

    public suspend fun removeSong(playlistId: String, songId: String): Unit {
        val playlist: InternalPlaylist = playlistRepository.getWithId(playlistId)
            ?: throw InvalidParameterException("Playlist with id $playlistId does not exist")

        playlist.removeSong(songId)
    }

    public suspend fun deletePlaylist(id: String): Unit {
        playlistRepository.delete(id)
    }

    public suspend fun getWithId(id: String): Playlist? {
        val dbModel: PlaylistDbModel = dao.getWithId(id) ?: return null

        return Playlist(
            id = dbModel.id,
            name = dbModel.name,
            image = getPlaylistImageUri(id),
            songs = songQueryApi.getMultiple(dbModel.songs),
        )
    }

    public suspend fun getAll(): List<Playlist> {
        val dbModels: List<PlaylistDbModel> = dao.getAll()

        return dbModels.map { dbModel ->
            Playlist(
                id = dbModel.id,
                name = dbModel.name,
                image = getPlaylistImageUri(dbModel.id),
                songs = songQueryApi.getMultiple(dbModel.songs),
            )
        }
    }

    public suspend fun createBuiltInPlaylists(): Unit {
        for (playlist in BuiltInPlaylists.entries) {
            if (playlistRepository.exists(playlist.id)) continue

            createPlaylist(playlist.id)
        }
    }

    private fun getPlaylistImageUri(id: String): Uri? {
        val imageFile = File(context.filesDir, "playlist/images/$id.jpg")

        return if (imageFile.exists()) imageFile.toUri() else null
    }
}