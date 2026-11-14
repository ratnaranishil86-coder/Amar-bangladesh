package com.example.domain.model

import androidx.annotation.DrawableRes

enum class ThemeMode {
  SYSTEM,
  LIGHT,
  DARK,
}

enum class DisplayLanguage {
  BENGALI_PRIMARY,
  BILINGUAL,
}

enum class ShowcaseCategory(val titleBn: String, val titleEn: String) {
  FEATURED_DISTRICT("নির্বাচিত জেলা", "Featured District"),
  POPULAR_FOOD("জনপ্রিয় খাবার", "Popular Food"),
  TOURIST_DESTINATION("দর্শনীয় স্থান", "Tourist Destination"),
  DISCOVER_MORE("আরও জানুন", "Discover More"),
}

enum class ExploreFilter(val titleBn: String, val titleEn: String) {
  ALL("সব", "All"),
  NATURE("প্রকৃতি", "Nature"),
  FOOD("খাবার", "Cuisine"),
  HERITAGE("ঐতিহ্য", "Heritage"),
  CULTURE("সংস্কৃতি", "Culture"),
}

data class DivisionSummary(
  val id: String,
  val nameBn: String,
  val nameEn: String,
  val statusLabelBn: String,
  val statusLabelEn: String,
  val accentIndex: Int,
)

data class ShowcaseItem(
  val id: String,
  val titleBn: String,
  val titleEn: String,
  val subtitleBn: String,
  val subtitleEn: String,
  val badgeBn: String,
  val badgeEn: String,
  val category: ShowcaseCategory,
  val exploreFilter: ExploreFilter,
  @DrawableRes val imageRes: Int? = null,
)

data class SavedBookmark(
  val itemId: String,
  val titleBn: String,
  val titleEn: String,
  val subtitleBn: String,
  val categoryLabelBn: String,
  val savedAtMillis: Long,
)
