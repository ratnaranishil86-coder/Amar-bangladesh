package com.example.ui.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.domain.model.DisplayLanguage
import com.example.domain.model.ExploreFilter
import com.example.domain.model.ShowcaseCategory
import com.example.domain.model.ShowcaseItem
import com.example.ui.components.DiscoverTileCard
import com.example.ui.components.ShowcaseCard
import com.example.ui.theme.AmarBangladeshTheme
import com.example.ui.viewmodel.ExploreUiState

@Composable
fun ExploreScreen(
  uiState: ExploreUiState,
  onSelectFilter: (ExploreFilter) -> Unit,
  onToggleBookmark: (ShowcaseItem) -> Unit,
  modifier: Modifier = Modifier,
) {
  BoxWithConstraints(modifier = modifier.fillMaxSize()) {
    val twoColumns = maxWidth >= 600.dp

    LazyColumn(
      modifier = Modifier.fillMaxSize().testTag("explore_screen"),
      contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
      item(key = "title") {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
          Text(
            text = stringResource(R.string.explore_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
          )
          if (uiState.displayLanguage == DisplayLanguage.BILINGUAL) {
            Text(
              text = stringResource(R.string.explore_subtitle),
              style = MaterialTheme.typography.labelLarge,
              color = MaterialTheme.colorScheme.primary,
            )
          }
        }
      }

      item(key = "filters") {
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          items(ExploreFilter.entries, key = { it.name }) { filter ->
            FilterChip(
              selected = uiState.selectedFilter == filter,
              onClick = { onSelectFilter(filter) },
              label = {
                Text(
                  if (uiState.displayLanguage == DisplayLanguage.BILINGUAL) {
                    "${filter.titleBn} • ${filter.titleEn}"
                  } else {
                    filter.titleBn
                  }
                )
              },
              modifier = Modifier.testTag("explore_chip_${filter.name.lowercase()}"),
              colors =
                FilterChipDefaults.filterChipColors(
                  selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                  selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
            )
          }
        }
      }

      if (twoColumns) {
        val rows = uiState.items.chunked(2)
        items(rows, key = { row -> row.joinToString("_") { it.id } }) { rowItems ->
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
          ) {
            rowItems.forEach { item ->
              ExploreCardItem(
                item = item,
                isSaved = uiState.savedIds.contains(item.id),
                displayLanguage = uiState.displayLanguage,
                onToggleBookmark = { onToggleBookmark(item) },
                modifier = Modifier.weight(1f),
              )
            }
            if (rowItems.size == 1) Spacer(modifier = Modifier.weight(1f))
          }
        }
      } else {
        items(uiState.items, key = { it.id }) { item ->
          ExploreCardItem(
            item = item,
            isSaved = uiState.savedIds.contains(item.id),
            displayLanguage = uiState.displayLanguage,
            onToggleBookmark = { onToggleBookmark(item) },
            modifier = Modifier.fillMaxWidth(),
          )
        }
      }

      item(key = "spacer") { Spacer(modifier = Modifier.height(12.dp)) }
    }
  }
}

@Composable
private fun ExploreCardItem(
  item: ShowcaseItem,
  isSaved: Boolean,
  displayLanguage: DisplayLanguage,
  onToggleBookmark: () -> Unit,
  modifier: Modifier = Modifier,
) {
  if (item.imageRes != null) {
    ShowcaseCard(
      item = item,
      isSaved = isSaved,
      displayLanguage = displayLanguage,
      onToggleSave = onToggleBookmark,
      modifier = modifier,
    )
  } else {
    DiscoverTileCard(
      item = item,
      isSaved = isSaved,
      displayLanguage = displayLanguage,
      onToggleSave = onToggleBookmark,
      modifier = modifier,
    )
  }
}

@Preview(showBackground = true)
@Composable
private fun ExploreScreenPreview() {
  AmarBangladeshTheme {
    ExploreScreen(
      uiState =
        ExploreUiState(
          items =
            listOf(
              ShowcaseItem(
                id = "e1",
                titleBn = "প্রাকৃতিক গন্তব্যের নমুনা ১",
                titleEn = "Destination Placeholder 1",
                subtitleBn = "নদী, পাহাড় ও চা-বাগান গন্তব্যের নমুনা প্রদর্শনী কার্ড",
                subtitleEn = "Placeholder",
                badgeBn = "নমুনা স্থান • Placeholder",
                badgeEn = "Placeholder",
                category = ShowcaseCategory.TOURIST_DESTINATION,
                exploreFilter = ExploreFilter.NATURE,
                imageRes = R.drawable.img_nature_bengal,
              )
            )
        ),
      onSelectFilter = {},
      onToggleBookmark = {},
      modifier = Modifier.background(MaterialTheme.colorScheme.background),
    )
  }
}
