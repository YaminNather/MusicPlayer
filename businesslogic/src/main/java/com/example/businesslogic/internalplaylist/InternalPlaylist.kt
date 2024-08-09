package com.example.businesslogic.internalplaylist

internal class InternalPlaylist(
    public val id: String,
    name: String,
    songs: List<String>,
) {
    public fun changeName(newName: String): Unit {
        _name = newName
    }

    public fun addSong(id: String): Unit {
        _songs.add(id)
    }

    public fun addSongs(id: List<String>): Unit {
        _songs.addAll(id)
    }

    public fun removeSong(id: String): Unit {
        _songs.remove(id)
    }

    public val name get() = _name

    public val songs get() = _songs.toList()



    private var _name: String = name
    private val _songs: MutableList<String> = songs.toMutableList()
}