package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Architecture
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.domain.model.DisplayLanguage
import com.example.domain.model.ThemeMode
import com.example.ui.theme.AmarBangladeshTheme
import com.example.ui.theme.BangladeshRed
import com.example.ui.viewmodel.ProfileUiState

@Composable
fun ProfileScreen(
  uiState: ProfileUiState,
  onThemeModeChange: (ThemeMode) -> Unit,
  onLanguageChange: (DisplayLanguage) -> Unit,
  modifier: Modifier = Modifier,
) {
  LazyColumn(
    modifier = modifier.fillMaxSize().testTag("profile_screen"),
    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
  ) {
    item(key = "header") {
      Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
          text = stringResource(R.string.profile_title),
          style = MaterialTheme.typography.headlineMedium,
          color = MaterialTheme.colorScheme.onBackground,
        )
        if (uiState.displayLanguage == DisplayLanguage.BILINGUAL) {
          Text(
            text = stringResource(R.string.profile_subtitle),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
          )
        }
      }
    }

    item(key = "identity_card") {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
      ) {
        Row(
          modifier = Modifier.fillMaxWidth().padding(18.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(14.dp),
        ) {
          Box(
            modifier =
              Modifier.size(58.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center,
          ) {
            Icon(
              imageVector = Icons.Filled.Person,
              contentDescription = stringResource(R.string.profile_guest_name),
              tint = MaterialTheme.colorScheme.primary,
              modifier = Modifier.size(30.dp),
            )
          }
          Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
            Text(
              text = stringResource(R.string.profile_guest_name),
              style = MaterialTheme.typography.titleLarge,
              color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
              text = stringResource(R.string.profile_guest_subtitle),
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
          }
        }
      }
    }

    item(key = "stats") {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
      ) {
        ProfileStatCard(
          icon = Icons.Filled.Bookmark,
          value = "${uiState.savedCount}",
          label = stringResource(R.string.profile_saved_count_label),
          accentTint = BangladeshRed,
          modifier = Modifier.weight(1f),
        )
        ProfileStatCard(
          icon = Icons.Filled.Map,
          value = "${uiState.divisionCount}",
          label = stringResource(R.string.profile_divisions_count_label),
          accentTint = MaterialTheme.colorScheme.primary,
          modifier = Modifier.weight(1f),
        )
      }
    }

    item(key = "theme_card") {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
          ) {
            Icon(
              imageVector = Icons.Filled.DarkMode,
              contentDescription = null,
              tint = MaterialTheme.colorScheme.primary,
            )
            Text(
              text = stringResource(R.string.profile_theme_title),
              style = MaterialTheme.typography.titleMedium,
            )
          }
          Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ThemeChip(
              selected = uiState.themeMode == ThemeMode.SYSTEM,
              label = stringResource(R.string.profile_theme_system),
              testTag = "theme_chip_system",
              onClick = { onThemeModeChange(ThemeMode.SYSTEM) },
            )
            ThemeChip(
              selected = uiState.themeMode == ThemeMode.LIGHT,
              label = stringResource(R.string.profile_theme_light),
              testTag = "theme_chip_light",
              onClick = { onThemeModeChange(ThemeMode.LIGHT) },
            )
            ThemeChip(
              selected = uiState.themeMode == ThemeMode.DARK,
              label = stringResource(R.string.profile_theme_dark),
              testTag = "theme_chip_dark",
              onClick = { onThemeModeChange(ThemeMode.DARK) },
            )
          }
        }
      }
    }

    item(key = "language_card") {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
          ) {
            Icon(
              imageVector = Icons.Filled.Language,
              contentDescription = null,
              tint = MaterialTheme.colorScheme.primary,
            )
            Text(
              text = stringResource(R.string.profile_language_title),
              style = MaterialTheme.typography.titleMedium,
            )
          }
          Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ThemeChip(
              selected = uiState.displayLanguage == DisplayLanguage.BENGALI_PRIMARY,
              label = stringResource(R.string.profile_language_bn),
              testTag = "lang_chip_bn",
              onClick = { onLanguageChange(DisplayLanguage.BENGALI_PRIMARY) },
            )
            ThemeChip(
              selected = uiState.displayLanguage == DisplayLanguage.BILINGUAL,
              label = stringResource(R.string.profile_language_bilingual),
              testTag = "lang_chip_bilingual",
              onClick = { onLanguageChange(DisplayLanguage.BILINGUAL) },
            )
          }
        }
      }
    }

    item(key = "arch_card") {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
          ) {
            Icon(
              imageVector = Icons.Outlined.Architecture,
              contentDescription = null,
              tint = MaterialTheme.colorScheme.primary,
            )
            Text(
              text = stringResource(R.string.profile_arch_title),
              style = MaterialTheme.typography.titleMedium,
            )
          }
          Text(
            text = stringResource(R.string.profile_arch_desc),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
          )
        }
      }
    }

    item(key = "spacer") { Spacer(modifier = Modifier.height(12.dp)) }
  }
}

@Composable
private fun ProfileStatCard(
  icon: ImageVector,
  value: String,
  label: String,
  accentTint: androidx.compose.ui.graphics.Color,
  modifier: Modifier = Modifier,
) {
  Card(
    modifier = modifier,
    shape = MaterialTheme.shapes.medium,
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
  ) {
    Column(
      modifier = Modifier.padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
      Icon(imageVector = icon, contentDescription = label, tint = accentTint)
      Text(
        text = value,
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.onSurface,
      )
      Text(
        text = label,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
      )
    }
  }
}

@Composable
private fun ThemeChip(
  selected: Boolean,
  label: String,
  testTag: String,
  onClick: () -> Unit,
) {
  FilterChip(
    selected = selected,
    onClick = onClick,
    label = { Text(label) },
    modifier = Modifier.testTag(testTag),
    colors =
      FilterChipDefaults.filterChipColors(
        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
        selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
      ),
  )
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
  AmarBangladeshTheme {
    ProfileScreen(
      uiState = ProfileUiState(savedCount = 2, divisionCount = 8),
      onThemeModeChange = {},
      onLanguageChange = {},
      modifier = Modifier.background(MaterialTheme.colorScheme.background),
    )
  }
}
