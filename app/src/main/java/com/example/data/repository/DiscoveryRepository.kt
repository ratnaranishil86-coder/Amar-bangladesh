package com.example.data.repository

import com.example.R
import com.example.data.local.dao.BookmarkDao
import com.example.data.local.dao.DivisionDao
import com.example.data.local.entity.DivisionEntity
import com.example.data.local.entity.SavedBookmarkEntity
import com.example.data.preferences.UserPreferencesStore
import com.example.domain.model.DisplayLanguage
import com.example.domain.model.DivisionSummary
import com.example.domain.model.ExploreFilter
import com.example.domain.model.SavedBookmark
import com.example.domain.model.ShowcaseCategory
import com.example.domain.model.ShowcaseItem
import com.example.domain.model.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

interface DiscoveryRepository {
  fun observeDivisions(): Flow<List<DivisionSummary>>

  fun observeShowcaseItems(): Flow<List<ShowcaseItem>>

  fun observeSavedBookmarks(): Flow<List<SavedBookmark>>

  fun observeSavedItemIds(): Flow<Set<String>>

  fun observeThemeMode(): Flow<ThemeMode>

  fun observeDisplayLanguage(): Flow<DisplayLanguage>

  suspend fun toggleBookmark(item: ShowcaseItem)

  suspend fun removeBookmark(itemId: String)

  suspend fun setThemeMode(mode: ThemeMode)

  suspend fun setDisplayLanguage(language: DisplayLanguage)
}

class LocalDiscoveryRepository(
  private val divisionDao: DivisionDao,
  private val bookmarkDao: BookmarkDao,
  private val preferencesStore: UserPreferencesStore,
) : DiscoveryRepository {

  override fun observeDivisions(): Flow<List<DivisionSummary>> =
    divisionDao
      .observeDivisions()
      .onStart { ensureDivisionsSeeded() }
      .map { entities ->
        entities.map { entity ->
          DivisionSummary(
            id = entity.id,
            nameBn = entity.nameBn,
            nameEn = entity.nameEn,
            statusLabelBn = entity.statusLabelBn,
            statusLabelEn = entity.statusLabelEn,
            accentIndex = entity.sortOrder,
          )
        }
      }

  override fun observeShowcaseItems(): Flow<List<ShowcaseItem>> =
    kotlinx.coroutines.flow.flowOf(Phase01PlaceholderCatalog.items)

  override fun observeSavedBookmarks(): Flow<List<SavedBookmark>> =
    bookmarkDao.observeBookmarks().map { entities ->
      entities.map { entity ->
        SavedBookmark(
          itemId = entity.itemId,
          titleBn = entity.titleBn,
          titleEn = entity.titleEn,
          subtitleBn = entity.subtitleBn,
          categoryLabelBn = entity.categoryLabelBn,
          savedAtMillis = entity.savedAtMillis,
        )
      }
    }

  override fun observeSavedItemIds(): Flow<Set<String>> =
    bookmarkDao.observeBookmarkIds().map { it.toSet() }

  override fun observeThemeMode(): Flow<ThemeMode> = preferencesStore.themeMode

  override fun observeDisplayLanguage(): Flow<DisplayLanguage> = preferencesStore.displayLanguage

  override suspend fun toggleBookmark(item: ShowcaseItem) {
    if (bookmarkDao.isBookmarked(item.id)) {
      bookmarkDao.deleteBookmarkById(item.id)
    } else {
      bookmarkDao.upsertBookmark(
        SavedBookmarkEntity(
          itemId = item.id,
          titleBn = item.titleBn,
          titleEn = item.titleEn,
          subtitleBn = item.subtitleBn,
          categoryLabelBn = item.category.titleBn,
        )
      )
    }
  }

  override suspend fun removeBookmark(itemId: String) {
    bookmarkDao.deleteBookmarkById(itemId)
  }

  override suspend fun setThemeMode(mode: ThemeMode) {
    preferencesStore.setThemeMode(mode)
  }

  override suspend fun setDisplayLanguage(language: DisplayLanguage) {
    preferencesStore.setDisplayLanguage(language)
  }

  private suspend fun ensureDivisionsSeeded() {
    if (divisionDao.countDivisions() == 0) {
      divisionDao.insertDivisions(Phase01PlaceholderCatalog.eightDivisions)
    }
  }
}

private object Phase01PlaceholderCatalog {
  val eightDivisions =
    listOf(
      DivisionEntity("dhaka", "ঢাকা", "Dhaka", "নমুনা • জেলা তথ্য Phase 02", "Placeholder • Phase 02", 0),
      DivisionEntity("chattogram", "চট্টগ্রাম", "Chattogram", "নমুনা • জেলা তথ্য Phase 02", "Placeholder • Phase 02", 1),
      DivisionEntity("rajshahi", "রাজশাহী", "Rajshahi", "নমুনা • জেলা তথ্য Phase 02", "Placeholder • Phase 02", 2),
      DivisionEntity("khulna", "খুলনা", "Khulna", "নমুনা • জেলা তথ্য Phase 02", "Placeholder • Phase 02", 3),
      DivisionEntity("barishal", "বরিশাল", "Barishal", "নমুনা • জেলা তথ্য Phase 02", "Placeholder • Phase 02", 4),
      DivisionEntity("sylhet", "সিলেট", "Sylhet", "নমুনা • জেলা তথ্য Phase 02", "Placeholder • Phase 02", 5),
      DivisionEntity("rangpur", "রংপুর", "Rangpur", "নমুনা • জেলা তথ্য Phase 02", "Placeholder • Phase 02", 6),
      DivisionEntity("mymensingh", "ময়মনসিংহ", "Mymensingh", "নমুনা • জেলা তথ্য Phase 02", "Placeholder • Phase 02", 7),
    )

  // Clearly labeled Phase 01 temporary placeholders — no invented district statistics or facts.
  val items =
    listOf(
      ShowcaseItem(
        id = "placeholder_featured_district",
        titleBn = "নির্বাচিত জেলার নমুনা কার্ড",
        titleEn = "Featured District Placeholder",
        subtitleBn = "Phase 02-তে প্রকৃত জেলার পরিচিতি, মানচিত্র ও বিবরণ এখানে যুক্ত হবে",
        subtitleEn = "Verified district details and highlights will load here in Phase 02",
        badgeBn = "নমুনা • Placeholder",
        badgeEn = "Placeholder",
        category = ShowcaseCategory.FEATURED_DISTRICT,
        exploreFilter = ExploreFilter.HERITAGE,
        imageRes = R.drawable.img_hero_bangladesh,
      ),
      ShowcaseItem(
        id = "placeholder_food_01",
        titleBn = "ঐতিহ্যবাহী খাবারের নমুনা ১",
        titleEn = "Traditional Cuisine Placeholder 1",
        subtitleBn = "আঞ্চলিক খাবারের যাচাইকৃত তালিকা Phase 02-তে যুক্ত হবে",
        subtitleEn = "Regional dish showcase slot reserved for Phase 02",
        badgeBn = "নমুনা খাবার • Placeholder",
        badgeEn = "Food Placeholder",
        category = ShowcaseCategory.POPULAR_FOOD,
        exploreFilter = ExploreFilter.FOOD,
        imageRes = R.drawable.img_cuisine_bengal,
      ),
      ShowcaseItem(
        id = "placeholder_food_02",
        titleBn = "ঐতিহ্যবাহী খাবারের নমুনা ২",
        titleEn = "Traditional Cuisine Placeholder 2",
        subtitleBn = "জনপ্রিয় মিষ্টান্ন ও লোকজ খাবারের নমুনা কার্ড",
        subtitleEn = "Sweets & culinary heritage slot reserved for Phase 02",
        badgeBn = "নমুনা খাবার • Placeholder",
        badgeEn = "Food Placeholder",
        category = ShowcaseCategory.POPULAR_FOOD,
        exploreFilter = ExploreFilter.FOOD,
        imageRes = R.drawable.img_cuisine_bengal,
      ),
      ShowcaseItem(
        id = "placeholder_dest_01",
        titleBn = "প্রাকৃতিক গন্তব্যের নমুনা ১",
        titleEn = "Destination Placeholder 1",
        subtitleBn = "নদী, পাহাড় ও চা-বাগান গন্তব্যের নমুনা প্রদর্শনী কার্ড",
        subtitleEn = "Nature & landscape destination slot reserved for Phase 02",
        badgeBn = "নমুনা স্থান • Placeholder",
        badgeEn = "Destination Placeholder",
        category = ShowcaseCategory.TOURIST_DESTINATION,
        exploreFilter = ExploreFilter.NATURE,
        imageRes = R.drawable.img_nature_bengal,
      ),
      ShowcaseItem(
        id = "placeholder_dest_02",
        titleBn = "প্রাকৃতিক গন্তব্যের নমুনা ২",
        titleEn = "Destination Placeholder 2",
        subtitleBn = "ভ্রমণ গাইড ও সময়সূচি তথ্য Phase 02-তে যুক্ত হবে",
        subtitleEn = "Verified travel guide details reserved for Phase 02",
        badgeBn = "নমুনা স্থান • Placeholder",
        badgeEn = "Destination Placeholder",
        category = ShowcaseCategory.TOURIST_DESTINATION,
        exploreFilter = ExploreFilter.NATURE,
        imageRes = R.drawable.img_hero_bangladesh,
      ),
      ShowcaseItem(
        id = "placeholder_discover_heritage",
        titleBn = "স্থাপত্য ও ঐতিহ্য (নমুনা)",
        titleEn = "Heritage & Architecture Placeholder",
        subtitleBn = "ঐতিহাসিক নিদর্শন ও সংস্কৃতির বিবরণ Phase 02-তে যুক্ত হবে",
        subtitleEn = "Cultural & heritage stories reserved for Phase 02",
        badgeBn = "নমুনা • Placeholder",
        badgeEn = "Placeholder",
        category = ShowcaseCategory.DISCOVER_MORE,
        exploreFilter = ExploreFilter.HERITAGE,
        imageRes = null,
      ),
      ShowcaseItem(
        id = "placeholder_discover_culture",
        titleBn = "লোকসংস্কৃতি ও উৎসব (নমুনা)",
        titleEn = "Folk Culture & Festivals Placeholder",
        subtitleBn = "বাংলার ঋতু উৎসব, কারুশিল্প ও ঐতিহ্যের নমুনা বিভাগ",
        subtitleEn = "Seasonal festivals & crafts slot reserved for Phase 02",
        badgeBn = "নমুনা • Placeholder",
        badgeEn = "Placeholder",
        category = ShowcaseCategory.DISCOVER_MORE,
        exploreFilter = ExploreFilter.CULTURE,
        imageRes = null,
      ),
    )
}
