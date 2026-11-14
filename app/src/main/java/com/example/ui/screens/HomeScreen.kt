package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SearchOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.domain.model.DisplayLanguage
import com.example.domain.model.DivisionSummary
import com.example.domain.model.ExploreFilter
import com.example.domain.model.ShowcaseCategory
import com.example.domain.model.ShowcaseItem
import com.example.ui.components.BrandTopHeader
import com.example.ui.components.DiscoverTileCard
import com.example.ui.components.DiscoverySearchBar
import com.example.ui.components.DivisionCard
import com.example.ui.components.HeroBangladeshCard
import com.example.ui.components.SectionHeader
import com.example.ui.components.ShowcaseCard
import com.example.ui.theme.AmarBangladeshTheme
import com.example.ui.viewmodel.HomeUiState

@Composable
fun HomeScreen(
  uiState: HomeUiState,
  onSearchQueryChange: (String) -> Unit,
  onDivisionClick: (DivisionSummary) -> Unit,
  onNavigateToDistricts: () -> Unit,
  onNavigateToExplore: () -> Unit,
  onToggleBookmark: (ShowcaseItem) -> Unit,
  modifier: Modifier = Modifier,
) {
  val noMatches =
    uiState.searchQuery.isNotBlank() &&
      uiState.divisions.isEmpty() &&
      uiState.featuredDistricts.isEmpty() &&
      uiState.popularFoods.isEmpty() &&
      uiState.touristDestinations.isEmpty() &&
      uiState.discoverMoreItems.isEmpty()

  BoxWithConstraints(modifier = modifier.fillMaxSize()) {
    val divisionColumns = if (maxWidth >= 600.dp) 4 else 2
    val cardWidth = if (maxWidth >= 600.dp) 300.dp else 264.dp

    LazyColumn(
      modifier = Modifier.fillMaxSize().testTag("home_screen_list"),
      contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
      verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
      item(key = "header") { BrandTopHeader(displayLanguage = uiState.displayLanguage) }

      item(key = "welcome") {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
          Text(
            text = stringResource(R.string.home_welcome_greeting),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
          )
          Text(
            text = stringResource(R.string.home_welcome_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
          )
        }
      }

      item(key = "search") {
        DiscoverySearchBar(
          query = uiState.searchQuery,
          onQueryChange = onSearchQueryChange,
          placeholder = stringResource(R.string.home_search_placeholder),
          testTag = "home_search_bar",
        )
      }

      if (uiState.searchQuery.isBlank()) {
        item(key = "hero") {
          HeroBangladeshCard(
            displayLanguage = uiState.displayLanguage,
            onExploreClick = onNavigateToExplore,
          )
        }
      }

      if (noMatches) {
        item(key = "empty_search") {
          Surface(
            modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp),
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.surface,
          ) {
            Column(
              modifier = Modifier.fillMaxWidth().padding(28.dp),
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
              Icon(
                imageVector = Icons.Outlined.SearchOff,
                contentDescription = stringResource(R.string.no_search_results_title),
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(40.dp),
              )
              Text(
                text = stringResource(R.string.no_search_results_title),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
              )
              Text(
                text = stringResource(R.string.no_search_results_desc),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
              )
            }
          }
        }
      }

      if (uiState.divisions.isNotEmpty()) {
        item(key = "divisions_header") {
          SectionHeader(
            titleBn = stringResource(R.string.home_section_divisions),
            titleEn = stringResource(R.string.home_section_divisions_sub),
            displayLanguage = uiState.displayLanguage,
            onSeeAllClick = onNavigateToDistricts,
            seeAllTestTag = "see_all_divisions_btn",
          )
        }

        item(key = "divisions_grid") {
          val rows = uiState.divisions.chunked(divisionColumns)
          Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            rows.forEach { rowItems ->
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
              ) {
                rowItems.forEach { division ->
                  DivisionCard(
                    division = division,
                    displayLanguage = uiState.displayLanguage,
                    onClick = { onDivisionClick(division) },
                    modifier = Modifier.weight(1f),
                  )
                }
                repeat(divisionColumns - rowItems.size) {
                  Spacer(modifier = Modifier.weight(1f))
                }
              }
            }
          }
        }
      }

      if (uiState.featuredDistricts.isNotEmpty()) {
        item(key = "featured_header") {
          SectionHeader(
            titleBn = stringResource(R.string.home_section_featured_district),
            titleEn = stringResource(R.string.home_section_featured_district_sub),
            displayLanguage = uiState.displayLanguage,
            onSeeAllClick = onNavigateToDistricts,
            seeAllTestTag = "see_all_featured_btn",
          )
        }

        items(uiState.featuredDistricts, key = { it.id }) { featured ->
          ShowcaseCard(
            item = featured,
            isSaved = uiState.savedIds.contains(featured.id),
            displayLanguage = uiState.displayLanguage,
            onToggleSave = { onToggleBookmark(featured) },
            modifier = Modifier.fillMaxWidth(),
          )
        }
      }

      if (uiState.popularFoods.isNotEmpty()) {
        item(key = "foods_header") {
          SectionHeader(
            titleBn = stringResource(R.string.home_section_popular_foods),
            titleEn = stringResource(R.string.home_section_popular_foods_sub),
            displayLanguage = uiState.displayLanguage,
            onSeeAllClick = onNavigateToExplore,
            seeAllTestTag = "see_all_foods_btn",
          )
        }

        item(key = "foods_row") {
          LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(end = 4.dp),
          ) {
            items(uiState.popularFoods, key = { it.id }) { food ->
              ShowcaseCard(
                item = food,
                isSaved = uiState.savedIds.contains(food.id),
                displayLanguage = uiState.displayLanguage,
                onToggleSave = { onToggleBookmark(food) },
                modifier = Modifier.width(cardWidth),
              )
            }
          }
        }
      }

      if (uiState.touristDestinations.isNotEmpty()) {
        item(key = "destinations_header") {
          SectionHeader(
            titleBn = stringResource(R.string.home_section_destinations),
            titleEn = stringResource(R.string.home_section_destinations_sub),
            displayLanguage = uiState.displayLanguage,
            onSeeAllClick = onNavigateToExplore,
            seeAllTestTag = "see_all_destinations_btn",
          )
        }

        item(key = "destinations_row") {
          LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(end = 4.dp),
          ) {
            items(uiState.touristDestinations, key = { it.id }) { dest ->
              ShowcaseCard(
                item = dest,
                isSaved = uiState.savedIds.contains(dest.id),
                displayLanguage = uiState.displayLanguage,
                onToggleSave = { onToggleBookmark(dest) },
                modifier = Modifier.width(cardWidth),
              )
            }
          }
        }
      }

      if (uiState.discoverMoreItems.isNotEmpty()) {
        item(key = "discover_header") {
          SectionHeader(
            titleBn = stringResource(R.string.home_section_discover_more),
            titleEn = stringResource(R.string.home_section_discover_more_sub),
            displayLanguage = uiState.displayLanguage,
          )
        }

        items(uiState.discoverMoreItems, key = { it.id }) { item ->
          AnimatedVisibility(visible = true, enter = fadeIn(), exit = fadeOut()) {
            DiscoverTileCard(
              item = item,
              isSaved = uiState.savedIds.contains(item.id),
              displayLanguage = uiState.displayLanguage,
              onToggleSave = { onToggleBookmark(item) },
            )
          }
        }
      }

      item(key = "bottom_spacer") { Spacer(modifier = Modifier.height(12.dp)) }
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
  AmarBangladeshTheme {
    HomeScreen(
      uiState =
        HomeUiState(
          divisions =
            listOf(
              DivisionSummary("dhaka", "ঢাকা", "Dhaka", "নমুনা • Phase 02", "Placeholder", 0),
              DivisionSummary(
                "chattogram",
                "চট্টগ্রাম",
                "Chattogram",
                "নমুনা • Phase 02",
                "Placeholder",
                1,
              ),
              DivisionSummary("sylhet", "সিলেট", "Sylhet", "নমুনা • Phase 02", "Placeholder", 2),
              DivisionSummary("khulna", "খুলনা", "Khulna", "নমুনা • Phase 02", "Placeholder", 3),
            ),
          featuredDistricts =
            listOf(
              ShowcaseItem(
                id = "f1",
                titleBn = "নির্বাচিত জেলার নমুনা কার্ড",
                titleEn = "Featured District Placeholder",
                subtitleBn = "Phase 02-তে প্রকৃত তথ্য যুক্ত হবে",
                subtitleEn = "Placeholder",
                badgeBn = "নমুনা • Placeholder",
                badgeEn = "Placeholder",
                category = ShowcaseCategory.FEATURED_DISTRICT,
                exploreFilter = ExploreFilter.HERITAGE,
                imageRes = R.drawable.img_hero_bangladesh,
              )
            ),
          isLoading = false,
        ),
      onSearchQueryChange = {},
      onDivisionClick = {},
      onNavigateToDistricts = {},
      onNavigateToExplore = {},
      onToggleBookmark = {},
      modifier = Modifier.background(MaterialTheme.colorScheme.background),
    )
  }
}
