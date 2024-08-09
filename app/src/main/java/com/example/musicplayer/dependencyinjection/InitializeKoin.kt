package com.example.musicplayer.dependencyinjection

import android.content.Context
import android.app.Activity
import android.content.ContentResolver
import com.example.businesslogic.albumqueryapi.AlbumQueryApi
import com.example.businesslogic.dependencyinjection.CompositionRoot
import com.example.businesslogic.playlistapi.PlaylistApi
import com.example.businesslogic.songplayersystem.songplayerapi.SongPlayerApi
import com.example.businesslogic.songplayersystem.songqueueapi.SongQueueApi
import com.example.businesslogic.songqueryapi.SongQueryApi
import com.example.musicplayer.components.songplayeroverlay.minimizedsongplayer.viewmodel.MinimizedSongPlayerOverlayViewModel
import com.example.musicplayer.components.songplayeroverlay.minimizedsongplayer.viewmodel.MinimizedSongPlayerOverlayViewModelImpl
import com.example.musicplayer.components.songplayeroverlay.viewmodel.SongPlayerOverlayViewModel
import com.example.musicplayer.screens.addsongstoplaylist.AddSongsToPlaylistPage
import com.example.musicplayer.screens.addsongstoplaylist.viewmodel.AddSongsToPlaylistPageViewModelImplFactory
import com.example.musicplayer.screens.album.viewmodel.AlbumPageViewModelFactory
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import com.example.musicplayer.screens.albums.viewmodel.AlbumsPageViewModel
import com.example.musicplayer.screens.albums.viewmodel.AlbumsPageViewModelImpl
import com.example.musicplayer.screens.playlist.viewmodel.PlaylistPageViewModelImplFactory
import com.example.musicplayer.screens.playlists.components.createplaylistdialog.viewmodel.CreatePlaylistDialogViewModel
import com.example.musicplayer.screens.playlists.components.createplaylistdialog.viewmodel.CreatePlaylistDialogViewModelImpl
import com.example.musicplayer.screens.playlists.viewmodel.PlaylistsPageViewModel
import com.example.musicplayer.screens.playlists.viewmodel.PlaylistsPageViewModelImpl
import com.example.musicplayer.screens.songplayer.viewmodel.SongPlayerPageViewModel
import com.example.musicplayer.screens.songplayer.viewmodel.SongPlayerPageViewModelImpl
import com.example.musicplayer.screens.selectsong.viewmodel.SelectSongPageViewModel
import com.example.musicplayer.screens.selectsong.viewmodel.SelectSongPageViewModelImpl
import com.example.musicplayer.screens.songqueue.viewmodel.SongQueuePageViewModel
import com.example.musicplayer.screens.songqueue.viewmodel.SongQueuePageViewModelImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf

private typealias BusinessLogicCompositionRoot = CompositionRoot

public fun initializeKoin(activity: Activity) {
    val applicationModule: Module = module {
        single<Context> { activity}
        single<ContentResolver> { activity.contentResolver }

        BusinessLogicCompositionRoot.compose(this)

        factory<SelectSongPageViewModel> {
            SelectSongPageViewModelImpl(get<SongQueryApi>(), get<SongQueueApi>())
        }

        factory<AlbumsPageViewModel> {
            AlbumsPageViewModelImpl(get<AlbumQueryApi>(), get<SongQueueApi>())
        }

        singleOf(::AlbumPageViewModelFactory)

        factory<SongQueuePageViewModel> {
            SongQueuePageViewModelImpl(songQueueApi = get<SongQueueApi>())
        }

        factoryOf(::SongPlayerOverlayViewModel)

        factory<MinimizedSongPlayerOverlayViewModel> {
            MinimizedSongPlayerOverlayViewModelImpl(
                songQueueApi = get<SongQueueApi>(),
                songPlayerApi = get<SongPlayerApi>()
            )
        }

        factory<SongPlayerPageViewModel> {
            SongPlayerPageViewModelImpl(
                songPlayerApi = get<SongPlayerApi>(),
                songQueueApi = get<SongQueueApi>(),
            )
        }

        factory<PlaylistsPageViewModel> {
            PlaylistsPageViewModelImpl(playlistApi = get<PlaylistApi>())
        }

        factory<CreatePlaylistDialogViewModel> {
            CreatePlaylistDialogViewModelImpl(get<PlaylistApi>())
        }

        singleOf(::PlaylistPageViewModelImplFactory)

        singleOf(::AddSongsToPlaylistPageViewModelImplFactory)
    }

    startKoin {
        modules(applicationModule)
    }
}