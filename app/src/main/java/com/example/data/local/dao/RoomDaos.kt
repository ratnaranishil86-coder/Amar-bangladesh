package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.entity.DivisionEntity
import com.example.data.local.entity.SavedBookmarkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DivisionDao {
  @Query("SELECT * FROM divisions ORDER BY sortOrder ASC")
  fun observeDivisions(): Flow<List<DivisionEntity>>

  @Query("SELECT COUNT(*) FROM divisions")
  suspend fun countDivisions(): Int

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertDivisions(divisions: List<DivisionEntity>)
}

@Dao
interface BookmarkDao {
  @Query("SELECT * FROM saved_bookmarks ORDER BY savedAtMillis DESC")
  fun observeBookmarks(): Flow<List<SavedBookmarkEntity>>

  @Query("SELECT itemId FROM saved_bookmarks")
  fun observeBookmarkIds(): Flow<List<String>>

  @Query("SELECT EXISTS(SELECT 1 FROM saved_bookmarks WHERE itemId = :itemId)")
  suspend fun isBookmarked(itemId: String): Boolean

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun upsertBookmark(bookmark: SavedBookmarkEntity)

  @Query("DELETE FROM saved_bookmarks WHERE itemId = :itemId")
  suspend fun deleteBookmarkById(itemId: String)
}
