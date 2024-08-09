package com.example.businesslogic.dependencyinjection

import android.content.ContentResolver
import android.content.Context
import com.example.businesslogic.Initializer
import com.example.businesslogic.albumqueryapi.AlbumQueryApi

import org.koin.core.module.Module

import com.example.businesslogic.internalsong.songfetcher.SongRepository
import com.example.businesslogic.internalsong.songfetcher.SongRepositoryFactory
import com.example.businesslogic.songplayersystem.songplayer.SongPlayer
import com.example.businesslogic.songplayersystem.songplayer.mediaplayer.MediaPlayerSongPlayerFactory

import com.example.businesslogic.eventsystem.EventPublisher
import com.example.businesslogic.internalplaylist.repository.InternalPlaylistRepository
import com.example.businesslogic.internalplaylist.repository.InternalPlaylistRepositoryFactory
import com.example.businesslogic.internalplaylist.repository.PlaylistDao
import com.example.businesslogic.internalsong.songfetcher.SongDao
import com.example.businesslogic.playlistapi.PlaylistApi
import com.example.businesslogic.playlistapi.interactors.SetPlaylistImageInteractor
import com.example.businesslogic.querysongfetcher.QuerySongFetcher
import com.example.businesslogic.room.AppDatabase
import com.example.businesslogic.songapi.SongApi
import com.example.businesslogic.songplayersystem.eventhandlers.completedsong.NotifyExternalListenersEventHandler
import com.example.businesslogic.songplayersystem.eventhandlers.completedsong.ToNextSongInQueueEventHandler
import com.example.businesslogic.songplayersystem.eventhandlers.completedsong.UpdateLastPlayedAtEventHandler
import com.example.businesslogic.songplayersystem.eventhandlers.currentsongupdated.PlayNewSongOnCurrentSongUpdatedEventHandler
import com.example.businesslogic.songplayersystem.eventhandlers.currentsongupdated.NotifyExternalListenersOnCurrentSongUpdatedEventHandler

import com.example.businesslogic.songplayersystem.songplayerapi.SongPlayerApi
import com.example.businesslogic.songplayersystem.songplayerapi.interactors.AddProgressListenerInteractor
import com.example.businesslogic.songplayersystem.songplayerapi.interactors.RemoveProgressListenerInteractor
import com.example.businesslogic.songplayersystem.songplayerapi.interactors.SeekToPositionInteractor
import com.example.businesslogic.songplayersystem.songplayerapi.interactors.TogglePauseStateInteractor
import com.example.businesslogic.songplayersystem.songplayereventlisteners.SongPlayerEventListeners

import com.example.businesslogic.songplayersystem.songqueue.SongQueue
import com.example.businesslogic.songplayersystem.songqueueapi.ClearInteractor
import com.example.businesslogic.songplayersystem.songqueueapi.SongQueueApi
import com.example.businesslogic.songplayersystem.songqueueeventlisteners.SongQueueEventListeners
import com.example.businesslogic.songplayersystem.songqueueapi.interactors.AddToEndInteractor
import com.example.businesslogic.songplayersystem.songqueueapi.interactors.AddAsNextInteractor
import com.example.businesslogic.songplayersystem.songqueueapi.interactors.GetSongsInteractor
import com.example.businesslogic.songplayersystem.songqueueapi.interactors.GetCurrentSongInteractor
import com.example.businesslogic.songqueryapi.SongQueryApi


import org.koin.core.module.dsl.singleOf

public class CompositionRoot {
    public companion object {
        public fun compose(module: Module) {
            module.single<AppDatabase> { AppDatabase.create(get<Context>()) }
            module.single<SongDao> { get<AppDatabase>().songs }

            composeEventPublisher(module)

            module.single<SongRepository> {
                SongRepositoryFactory(get<ContentResolver>(), get<SongDao>()).build()
            }

            module.singleOf(::SongApi)

            module.single<SongQueue> { SongQueue() }

            module.single<SongPlayer> {
                MediaPlayerSongPlayerFactory(get<Context>(), get<EventPublisher>()).build()
            }

            composeSongPlayerApi(module)
            module.singleOf(::SongPlayerEventListeners)

            composeSongQueueApi(module)
            module.singleOf(::SongQueueEventListeners)

            module.singleOf(::QuerySongFetcher)
            module.singleOf(::SongQueryApi)

            module.singleOf(::AlbumQueryApi)

            module.single<PlaylistDao> { get<AppDatabase>().playlists }
            module.single<InternalPlaylistRepository> {
                InternalPlaylistRepositoryFactory(get<PlaylistDao>()).build()
            }
            module.singleOf(::SetPlaylistImageInteractor)
            module.singleOf(::PlaylistApi)

            module.singleOf(::Initializer)
        }

        private fun composeSongPlayerApi(module: Module): Unit {
            module.singleOf(::TogglePauseStateInteractor)
            module.singleOf(::SeekToPositionInteractor)
            module.singleOf(::AddProgressListenerInteractor)
            module.singleOf(::RemoveProgressListenerInteractor)

            module.singleOf(::SongPlayerApi)
        }

        private fun composeSongQueueApi(module: Module): Unit {
            module.singleOf(::AddAsNextInteractor)
            module.singleOf(::AddToEndInteractor)
            module.singleOf(::ClearInteractor)
            module.singleOf(::GetSongsInteractor)
            module.singleOf(::GetCurrentSongInteractor)

            module.singleOf(::SongQueueApi)
        }

        private fun composeEventPublisher(module: Module): Unit {
            module.singleOf(::PlayNewSongOnCurrentSongUpdatedEventHandler)
            module.singleOf(::NotifyExternalListenersOnCurrentSongUpdatedEventHandler)

            module.singleOf(::UpdateLastPlayedAtEventHandler)
            module.singleOf(::ToNextSongInQueueEventHandler)
            module.singleOf(::NotifyExternalListenersEventHandler)

            module.singleOf(::EventPublisher)
        }
    }
}