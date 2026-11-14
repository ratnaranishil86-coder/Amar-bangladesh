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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.domain.model.DisplayLanguage
import com.example.domain.model.SavedBookmark
import com.example.ui.theme.AmarBangladeshTheme
import com.example.ui.viewmodel.SavedUiState

@Composable
fun SavedScreen(
  uiState: SavedUiState,
  onRemoveBookmark: (String) -> Unit,
  onNavigateHome: () -> Unit,
  modifier: Modifier = Modifier,
) {
  LazyColumn(
    modifier = modifier.fillMaxSize().testTag("saved_screen"),
    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp),
  ) {
    item(key = "header") {
      Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
          text = stringResource(R.string.saved_title),
          style = MaterialTheme.typography.headlineMedium,
          color = MaterialTheme.colorScheme.onBackground,
        )
        if (uiState.displayLanguage == DisplayLanguage.BILINGUAL) {
          Text(
            text = stringResource(R.string.saved_subtitle),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
          )
        }
      }
    }

    if (uiState.bookmarks.isEmpty()) {
      item(key = "empty_state") {
        Card(
          modifier = Modifier.fillMaxWidth().padding(top = 12.dp).testTag("saved_empty_state"),
          shape = MaterialTheme.shapes.large,
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        ) {
          Column(
            modifier = Modifier.fillMaxWidth().padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
          ) {
            Box(
              modifier =
                Modifier.size(72.dp)
                  .clip(CircleShape)
                  .background(MaterialTheme.colorScheme.primaryContainer),
              contentAlignment = Alignment.Center,
            ) {
              Icon(
                imageVector = Icons.Outlined.BookmarkBorder,
                contentDescription = stringResource(R.string.saved_empty_title),
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(34.dp),
              )
            }
            Text(
              text = stringResource(R.string.saved_empty_title),
              style = MaterialTheme.typography.titleLarge,
              textAlign = TextAlign.Center,
            )
            Text(
              text = stringResource(R.string.saved_empty_body),
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Button(
              onClick = onNavigateHome,
              modifier = Modifier.minimumInteractiveComponentSize().testTag("saved_empty_cta"),
              shape = RoundedCornerShape(14.dp),
              colors =
                ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            ) {
              Text(text = stringResource(R.string.saved_empty_cta))
            }
          }
        }
      }
    } else {
      items(uiState.bookmarks, key = { it.itemId }) { bookmark ->
        SavedBookmarkCard(
          bookmark = bookmark,
          displayLanguage = uiState.displayLanguage,
          onRemove = { onRemoveBookmark(bookmark.itemId) },
        )
      }
    }
  }
}

@Composable
private fun SavedBookmarkCard(
  bookmark: SavedBookmark,
  displayLanguage: DisplayLanguage,
  onRemove: () -> Unit,
) {
  Card(
    modifier = Modifier.fillMaxWidth().testTag("saved_item_${bookmark.itemId}"),
    shape = MaterialTheme.shapes.medium,
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
  ) {
    Row(
      modifier = Modifier.fillMaxWidth().padding(16.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween,
    ) {
      Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Surface(
          shape = RoundedCornerShape(50),
          color = MaterialTheme.colorScheme.primaryContainer,
        ) {
          Text(
            text = bookmark.categoryLabelBn,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
          )
        }
        Text(
          text = bookmark.titleBn,
          style = MaterialTheme.typography.titleMedium,
          color = MaterialTheme.colorScheme.onSurface,
        )
        if (displayLanguage == DisplayLanguage.BILINGUAL) {
          Text(
            text = bookmark.titleEn,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary,
          )
        }
        Text(
          text = bookmark.subtitleBn,
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
      }

      IconButton(
        onClick = onRemove,
        modifier =
          Modifier.minimumInteractiveComponentSize().testTag("remove_saved_${bookmark.itemId}"),
      ) {
        Icon(
          imageVector = Icons.Filled.DeleteOutline,
          contentDescription = stringResource(R.string.saved_remove),
          tint = MaterialTheme.colorScheme.secondary,
        )
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun SavedEmptyPreview() {
  AmarBangladeshTheme {
    SavedScreen(
      uiState = SavedUiState(),
      onRemoveBookmark = {},
      onNavigateHome = {},
      modifier = Modifier.background(MaterialTheme.colorScheme.background),
    )
  }
}
