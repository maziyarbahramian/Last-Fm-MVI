package com.maziyarbahramian.lastfm.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.maziyarbahramian.lastfm.model.Album
import com.maziyarbahramian.lastfm.model.Artist

@Database(entities = [Artist::class, Album::class], version = 1)
public abstract class LastFmDatabase : RoomDatabase() {
    abstract fun getDao(): LastFmDao

    companion object {

        @Volatile
        private var INSTANCE: LastFmDatabase? = null

        fun getDatabase(context: Context): LastFmDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LastFmDatabase::class.java,
                    "lastfm_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}