package com.example.businesslogic.apimodels

import android.net.Uri

public class Playlist(
    public val id: String,
    public val name: String,
    public val image: Uri?,
    public val songs: List<Song>,
)