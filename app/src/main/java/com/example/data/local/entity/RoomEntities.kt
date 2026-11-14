package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "divisions")
data class DivisionEntity(
  @PrimaryKey val id: String,
  val nameBn: String,
  val nameEn: String,
  val statusLabelBn: String,
  val statusLabelEn: String,
  val sortOrder: Int,
)

@Entity(tableName = "saved_bookmarks")
data class SavedBookmarkEntity(
  @PrimaryKey val itemId: String,
  val titleBn: String,
  val titleEn: String,
  val subtitleBn: String,
  val categoryLabelBn: String,
  val savedAtMillis: Long = System.currentTimeMillis(),
)
