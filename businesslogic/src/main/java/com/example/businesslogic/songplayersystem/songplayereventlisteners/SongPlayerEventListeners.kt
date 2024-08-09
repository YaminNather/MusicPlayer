package com.example.businesslogic.songplayersystem.songplayereventlisteners

public class SongPlayerEventListeners {
    public val songCompletedListeners: MutableList<() -> Unit> = mutableListOf()
}