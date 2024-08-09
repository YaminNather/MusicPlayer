package com.example.musicplayer.screens

public sealed class PageRoutes {
    public companion object {
        public const val splashScreen: String = "splash_screen"
        public const val home: String = "home"
        public const val songs: String = "songs"
        public const val albums: String = "albums"
        public const val album: String = "albums/{albumId}"
        public const val playlists: String = "playlists"
        public const val playlist: String = "playlists/{playlistId}"
        public const val addSongsToPlaylist: String = "playlists/{playlistId}/addSongs"
        public const val equalizer: String = "equalizer"
//        public const val songPlayer: String = "song_player_page"
        public const val songQueue: String = "song_queue"
    }
}