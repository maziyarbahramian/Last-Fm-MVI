package com.maziyarbahramian.lastfm.persistence

import androidx.lifecycle.LiveData
import androidx.room.*
import com.maziyarbahramian.lastfm.model.Album
import com.maziyarbahramian.lastfm.model.Artist
import com.maziyarbahramian.lastfm.model.ArtistAndAlbums

@Dao
interface LastFmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtist(artist: Artist):Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbum(album: Album)

    @Query("DELETE FROM artists")
    suspend fun clearArtist()

    @Query("DELETE FROM albums")
    suspend fun clearAlbum()

//    @Query("SELECT * FROM artists")
//    suspend fun getAllArtists(): LiveData<List<Artist>>
//
//    @Query("SELECT * FROM albums WHERE artistId = :artistId")
//    suspend fun getAlbumsOfArtist(artistId: Int): LiveData<List<Album>>
//
//    @Query("SELECT * FROM artists")
//    @Transaction
//    suspend fun getArtistAndAllAlbums(): LiveData<List<ArtistAndAlbums>>
}