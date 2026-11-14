package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.DiscoveryRepository
import com.example.domain.model.DisplayLanguage
import com.example.domain.model.DivisionSummary
import com.example.domain.model.ExploreFilter
import com.example.domain.model.SavedBookmark
import com.example.domain.model.ShowcaseCategory
import com.example.domain.model.ShowcaseItem
import com.example.domain.model.ThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AppStateViewModel(repository: DiscoveryRepository) : ViewModel() {
  val themeMode: StateFlow<ThemeMode> =
    repository
      .observeThemeMode()
      .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ThemeMode.SYSTEM)

  val displayLanguage: StateFlow<DisplayLanguage> =
    repository
      .observeDisplayLanguage()
      .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DisplayLanguage.BILINGUAL)
}

data class HomeUiState(
  val searchQuery: String = "",
  val divisions: List<DivisionSummary> = emptyList(),
  val featuredDistricts: List<ShowcaseItem> = emptyList(),
  val popularFoods: List<ShowcaseItem> = emptyList(),
  val touristDestinations: List<ShowcaseItem> = emptyList(),
  val discoverMoreItems: List<ShowcaseItem> = emptyList(),
  val savedIds: Set<String> = emptySet(),
  val displayLanguage: DisplayLanguage = DisplayLanguage.BILINGUAL,
  val isLoading: Boolean = true,
)

class HomeViewModel(private val repository: DiscoveryRepository) : ViewModel() {
  private val searchQuery = MutableStateFlow("")

  val uiState: StateFlow<HomeUiState> =
    combine(
        searchQuery,
        repository.observeDivisions(),
        repository.observeShowcaseItems(),
        repository.observeSavedItemIds(),
        repository.observeDisplayLanguage(),
      ) { query, divisions, items, savedIds, language ->
        val normalized = query.trim().lowercase()
        val filteredDivisions =
          if (normalized.isEmpty()) divisions
          else
            divisions.filter {
              it.nameBn.lowercase().contains(normalized) ||
                it.nameEn.lowercase().contains(normalized)
            }
        val filteredItems =
          if (normalized.isEmpty()) items
          else
            items.filter {
              it.titleBn.lowercase().contains(normalized) ||
                it.titleEn.lowercase().contains(normalized) ||
                it.subtitleBn.lowercase().contains(normalized) ||
                it.subtitleEn.lowercase().contains(normalized)
            }

        HomeUiState(
          searchQuery = query,
          divisions = filteredDivisions,
          featuredDistricts =
            filteredItems.filter { it.category == ShowcaseCategory.FEATURED_DISTRICT },
          popularFoods = filteredItems.filter { it.category == ShowcaseCategory.POPULAR_FOOD },
          touristDestinations =
            filteredItems.filter { it.category == ShowcaseCategory.TOURIST_DESTINATION },
          discoverMoreItems =
            filteredItems.filter { it.category == ShowcaseCategory.DISCOVER_MORE },
          savedIds = savedIds,
          displayLanguage = language,
          isLoading = false,
        )
      }
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeUiState(),
      )

  fun onSearchQueryChange(query: String) {
    searchQuery.value = query
  }

  fun onToggleBookmark(item: ShowcaseItem) {
    viewModelScope.launch { repository.toggleBookmark(item) }
  }
}

data class DistrictsUiState(
  val searchQuery: String = "",
  val selectedDivisionId: String? = null,
  val divisions: List<DivisionSummary> = emptyList(),
  val displayLanguage: DisplayLanguage = DisplayLanguage.BILINGUAL,
)

class DistrictsViewModel(repository: DiscoveryRepository) : ViewModel() {
  private val searchQuery = MutableStateFlow("")
  private val selectedDivisionId = MutableStateFlow<String?>(null)

  val uiState: StateFlow<DistrictsUiState> =
    combine(
        searchQuery,
        selectedDivisionId,
        repository.observeDivisions(),
        repository.observeDisplayLanguage(),
      ) { query, selectedId, divisions, language ->
        val normalized = query.trim().lowercase()
        val filtered =
          divisions.filter { division ->
            val matchesQuery =
              normalized.isEmpty() ||
                division.nameBn.lowercase().contains(normalized) ||
                division.nameEn.lowercase().contains(normalized)
            val matchesChip = selectedId == null || division.id == selectedId
            matchesQuery && matchesChip
          }
        DistrictsUiState(
          searchQuery = query,
          selectedDivisionId = selectedId,
          divisions = filtered,
          displayLanguage = language,
        )
      }
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DistrictsUiState(),
      )

  fun onSearchQueryChange(query: String) {
    searchQuery.value = query
  }

  fun onSelectDivision(divisionId: String?) {
    selectedDivisionId.value = if (selectedDivisionId.value == divisionId) null else divisionId
  }
}

data class ExploreUiState(
  val selectedFilter: ExploreFilter = ExploreFilter.ALL,
  val items: List<ShowcaseItem> = emptyList(),
  val savedIds: Set<String> = emptySet(),
  val displayLanguage: DisplayLanguage = DisplayLanguage.BILINGUAL,
)

class ExploreViewModel(
  private val repository: DiscoveryRepository,
  initialFilter: ExploreFilter = ExploreFilter.ALL,
) : ViewModel() {
  private val selectedFilter = MutableStateFlow(initialFilter)

  val uiState: StateFlow<ExploreUiState> =
    combine(
        selectedFilter,
        repository.observeShowcaseItems(),
        repository.observeSavedItemIds(),
        repository.observeDisplayLanguage(),
      ) { filter, items, savedIds, language ->
        val filtered =
          if (filter == ExploreFilter.ALL) items else items.filter { it.exploreFilter == filter }
        ExploreUiState(
          selectedFilter = filter,
          items = filtered,
          savedIds = savedIds,
          displayLanguage = language,
        )
      }
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ExploreUiState(selectedFilter = initialFilter),
      )

  fun onSelectFilter(filter: ExploreFilter) {
    selectedFilter.value = filter
  }

  fun onToggleBookmark(item: ShowcaseItem) {
    viewModelScope.launch { repository.toggleBookmark(item) }
  }
}

data class SavedUiState(
  val bookmarks: List<SavedBookmark> = emptyList(),
  val displayLanguage: DisplayLanguage = DisplayLanguage.BILINGUAL,
)

class SavedViewModel(private val repository: DiscoveryRepository) : ViewModel() {
  val uiState: StateFlow<SavedUiState> =
    combine(repository.observeSavedBookmarks(), repository.observeDisplayLanguage()) {
        bookmarks,
        language ->
        SavedUiState(bookmarks = bookmarks, displayLanguage = language)
      }
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SavedUiState(),
      )

  fun onRemoveBookmark(itemId: String) {
    viewModelScope.launch { repository.removeBookmark(itemId) }
  }
}

data class ProfileUiState(
  val themeMode: ThemeMode = ThemeMode.SYSTEM,
  val displayLanguage: DisplayLanguage = DisplayLanguage.BILINGUAL,
  val savedCount: Int = 0,
  val divisionCount: Int = 8,
)

class ProfileViewModel(private val repository: DiscoveryRepository) : ViewModel() {
  val uiState: StateFlow<ProfileUiState> =
    combine(
        repository.observeThemeMode(),
        repository.observeDisplayLanguage(),
        repository.observeSavedBookmarks(),
        repository.observeDivisions(),
      ) { theme, language, bookmarks, divisions ->
        ProfileUiState(
          themeMode = theme,
          displayLanguage = language,
          savedCount = bookmarks.size,
          divisionCount = divisions.size,
        )
      }
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ProfileUiState(),
      )

  fun onThemeModeChange(mode: ThemeMode) {
    viewModelScope.launch { repository.setThemeMode(mode) }
  }

  fun onLanguageChange(language: DisplayLanguage) {
    viewModelScope.launch { repository.setDisplayLanguage(language) }
  }
}
