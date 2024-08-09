package com.example.businesslogic.apimodels

import android.net.Uri

public data class Album(
    public val id: String,
    public val name: String,
    public val coverArt: Uri?,
    public val artist: String,
)