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
import com.example.domain.model.DivisionSummary
import com.example.ui.components.DiscoverySearchBar
import com.example.ui.components.DivisionCard
import com.example.ui.components.PhaseNoticeBanner
import com.example.ui.theme.AmarBangladeshTheme
import com.example.ui.viewmodel.DistrictsUiState

@Composable
fun DistrictsScreen(
  uiState: DistrictsUiState,
  allDivisions: List<DivisionSummary>,
  onSearchQueryChange: (String) -> Unit,
  onSelectDivision: (String?) -> Unit,
  modifier: Modifier = Modifier,
) {
  BoxWithConstraints(modifier = modifier.fillMaxSize()) {
    val columns = if (maxWidth >= 600.dp) 3 else 2

    LazyColumn(
      modifier = Modifier.fillMaxSize().testTag("districts_screen"),
      contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
      item(key = "title") {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
          Text(
            text = stringResource(R.string.districts_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
          )
          if (uiState.displayLanguage == DisplayLanguage.BILINGUAL) {
            Text(
              text = stringResource(R.string.districts_subtitle),
              style = MaterialTheme.typography.labelLarge,
              color = MaterialTheme.colorScheme.primary,
            )
          }
        }
      }

      item(key = "search") {
        DiscoverySearchBar(
          query = uiState.searchQuery,
          onQueryChange = onSearchQueryChange,
          placeholder = stringResource(R.string.districts_search_hint),
          testTag = "districts_search_bar",
        )
      }

      item(key = "filter_chips") {
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          item(key = "all") {
            FilterChip(
              selected = uiState.selectedDivisionId == null,
              onClick = { onSelectDivision(null) },
              label = { Text(stringResource(R.string.districts_all_divisions)) },
              modifier = Modifier.testTag("division_chip_all"),
              colors =
                FilterChipDefaults.filterChipColors(
                  selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                  selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
            )
          }
          items(allDivisions, key = { it.id }) { division ->
            FilterChip(
              selected = uiState.selectedDivisionId == division.id,
              onClick = { onSelectDivision(division.id) },
              label = { Text(division.nameBn) },
              modifier = Modifier.testTag("division_chip_${division.id}"),
              colors =
                FilterChipDefaults.filterChipColors(
                  selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                  selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
            )
          }
        }
      }

      item(key = "phase_notice") {
        PhaseNoticeBanner(
          title = stringResource(R.string.districts_phase_notice_title),
          body = stringResource(R.string.districts_phase_notice_body),
        )
      }

      item(key = "grid") {
        val rows = uiState.divisions.chunked(columns)
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
                  onClick = { onSelectDivision(division.id) },
                  modifier = Modifier.weight(1f),
                )
              }
              repeat(columns - rowItems.size) { Spacer(modifier = Modifier.weight(1f)) }
            }
          }
        }
      }

      item(key = "spacer") { Spacer(modifier = Modifier.height(12.dp)) }
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun DistrictsScreenPreview() {
  val sample =
    listOf(
      DivisionSummary("dhaka", "ঢাকা", "Dhaka", "নমুনা • Phase 02", "Placeholder", 0),
      DivisionSummary("chattogram", "চট্টগ্রাম", "Chattogram", "নমুনা • Phase 02", "Placeholder", 1),
      DivisionSummary("rajshahi", "রাজশাহী", "Rajshahi", "নমুনা • Phase 02", "Placeholder", 2),
      DivisionSummary("khulna", "খুলনা", "Khulna", "নমুনা • Phase 02", "Placeholder", 3),
    )
  AmarBangladeshTheme {
    DistrictsScreen(
      uiState = DistrictsUiState(divisions = sample),
      allDivisions = sample,
      onSearchQueryChange = {},
      onSelectDivision = {},
      modifier = Modifier.background(MaterialTheme.colorScheme.background),
    )
  }
}
