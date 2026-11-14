package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.local.dao.BookmarkDao
import com.example.data.local.dao.DivisionDao
import com.example.data.local.entity.DivisionEntity
import com.example.data.local.entity.SavedBookmarkEntity

@Database(
  entities = [DivisionEntity::class, SavedBookmarkEntity::class],
  version = 1,
  exportSchema = false,
)
abstract class AmarBangladeshDatabase : RoomDatabase() {
  abstract fun divisionDao(): DivisionDao

  abstract fun bookmarkDao(): BookmarkDao

  companion object {
    @Volatile private var INSTANCE: AmarBangladeshDatabase? = null

    fun getInstance(context: Context): AmarBangladeshDatabase {
      return INSTANCE
        ?: synchronized(this) {
          INSTANCE
            ?: Room.databaseBuilder(
                context.applicationContext,
                AmarBangladeshDatabase::class.java,
                "amar_bangladesh.db",
              )
              .build()
              .also { INSTANCE = it }
        }
    }
  }
}
