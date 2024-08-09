package com.example.businesslogic.apimodels

import android.net.Uri
import kotlin.time.Duration

public data class Song(
    public val id: String,
    public val name: String,
    public val artist: String,
    public val coverArt: Uri?,
    public val duration: Duration,
    public val albumId: String,
)