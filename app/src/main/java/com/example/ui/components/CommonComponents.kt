package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TempleBuddhist
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.domain.model.DisplayLanguage
import com.example.domain.model.DivisionSummary
import com.example.domain.model.ExploreFilter
import com.example.domain.model.ShowcaseCategory
import com.example.domain.model.ShowcaseItem
import com.example.ui.theme.AmarBangladeshTheme
import com.example.ui.theme.BangladeshRed
import com.example.ui.theme.ForestGreen
import com.example.ui.theme.ForestGreenDark
import com.example.ui.theme.WarmGold

@Composable
fun BrandEmblemBadge(modifier: Modifier = Modifier) {
  Surface(
    modifier = modifier.size(44.dp),
    shape = RoundedCornerShape(14.dp),
    color = ForestGreen,
    shadowElevation = 2.dp,
  ) {
    Canvas(modifier = Modifier.fillMaxSize().padding(8.dp)) {
      val radius = size.minDimension * 0.36f
      drawCircle(
        color = BangladeshRed,
        radius = radius,
        center = Offset(size.width * 0.46f, size.height * 0.5f),
      )
    }
  }
}

@Composable
fun BrandTopHeader(
  displayLanguage: DisplayLanguage,
  modifier: Modifier = Modifier,
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween,
  ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      BrandEmblemBadge()
      Spacer(modifier = Modifier.width(12.dp))
      Column {
        Text(
          text = stringResource(R.string.app_name),
          style = MaterialTheme.typography.headlineSmall,
          color = MaterialTheme.colorScheme.onBackground,
        )
        if (displayLanguage == DisplayLanguage.BILINGUAL) {
          Text(
            text = stringResource(R.string.app_tagline),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary,
          )
        }
      }
    }

    Surface(
      shape = RoundedCornerShape(50),
      color = MaterialTheme.colorScheme.primaryContainer,
    ) {
      Text(
        text = stringResource(R.string.phase_badge),
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
      )
    }
  }
}

@Composable
fun DiscoverySearchBar(
  query: String,
  onQueryChange: (String) -> Unit,
  placeholder: String,
  testTag: String,
  modifier: Modifier = Modifier,
) {
  val focusManager = LocalFocusManager.current
  OutlinedTextField(
    value = query,
    onValueChange = onQueryChange,
    modifier = modifier.fillMaxWidth().testTag(testTag),
    placeholder = {
      Text(
        text = placeholder,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
      )
    },
    leadingIcon = {
      Icon(
        imageVector = Icons.Filled.Search,
        contentDescription = placeholder,
        tint = MaterialTheme.colorScheme.primary,
      )
    },
    trailingIcon = {
      if (query.isNotEmpty()) {
        IconButton(
          onClick = { onQueryChange("") },
          modifier = Modifier.minimumInteractiveComponentSize().testTag("${testTag}_clear"),
        ) {
          Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = stringResource(R.string.home_search_clear),
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
          )
        }
      }
    },
    singleLine = true,
    shape = RoundedCornerShape(18.dp),
    colors =
      OutlinedTextFieldDefaults.colors(
        focusedContainerColor = MaterialTheme.colorScheme.surface,
        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
        focusedBorderColor = MaterialTheme.colorScheme.primary,
        unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
      ),
    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
    keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() }),
  )
}

@Composable
fun HeroBangladeshCard(
  displayLanguage: DisplayLanguage,
  onExploreClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Card(
    modifier = modifier.fillMaxWidth().testTag("home_hero_card"),
    shape = MaterialTheme.shapes.large,
    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
  ) {
    Box(modifier = Modifier.fillMaxWidth().height(236.dp)) {
      Image(
        painter = painterResource(id = R.drawable.img_hero_bangladesh),
        contentDescription = stringResource(R.string.home_hero_title),
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize(),
      )

      Box(
        modifier =
          Modifier.fillMaxSize()
            .background(
              Brush.verticalGradient(
                colors =
                  listOf(
                    Color(0x33082919),
                    Color(0xB80A3621),
                    Color(0xEE082919),
                  )
              )
            )
      )

      Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.SpaceBetween,
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
          Surface(
            color = BangladeshRed,
            shape = CircleShape,
            modifier = Modifier.size(10.dp),
          ) {}
          Surface(
            color = Color.White.copy(alpha = 0.2f),
            shape = RoundedCornerShape(50),
          ) {
            Text(
              text = stringResource(R.string.home_hero_badge),
              style = MaterialTheme.typography.labelMedium,
              color = Color.White,
              modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
            )
          }
        }

        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
          Text(
            text = stringResource(R.string.home_hero_title),
            style = MaterialTheme.typography.headlineLarge,
            color = Color.White,
          )
          Text(
            text =
              if (displayLanguage == DisplayLanguage.BILINGUAL) {
                stringResource(R.string.home_hero_subtitle)
              } else {
                "৮টি বিভাগ ও ৬৪ জেলার ঐতিহ্য, স্বাদ ও প্রাকৃতিক সৌন্দর্যের ডিজিটাল গাইড"
              },
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White.copy(alpha = 0.9f),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
          )
          Spacer(modifier = Modifier.height(4.dp))
          Button(
            onClick = onExploreClick,
            modifier = Modifier.minimumInteractiveComponentSize().testTag("hero_explore_cta"),
            shape = RoundedCornerShape(14.dp),
            colors =
              ButtonDefaults.buttonColors(
                containerColor = BangladeshRed,
                contentColor = Color.White,
              ),
            contentPadding = PaddingValues(horizontal = 18.dp, vertical = 10.dp),
          ) {
            Text(
              text = stringResource(R.string.home_hero_cta),
              style = MaterialTheme.typography.labelLarge,
            )
            Spacer(modifier = Modifier.width(6.dp))
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowForward,
              contentDescription = null,
              modifier = Modifier.size(18.dp),
            )
          }
        }
      }
    }
  }
}

@Composable
fun SectionHeader(
  titleBn: String,
  titleEn: String,
  displayLanguage: DisplayLanguage,
  modifier: Modifier = Modifier,
  onSeeAllClick: (() -> Unit)? = null,
  seeAllTestTag: String? = null,
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Column(modifier = Modifier.weight(1f)) {
      Text(
        text = titleBn,
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onBackground,
      )
      if (displayLanguage == DisplayLanguage.BILINGUAL) {
        Text(
          text = titleEn,
          style = MaterialTheme.typography.labelMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
      }
    }

    if (onSeeAllClick != null) {
      TextButton(
        onClick = onSeeAllClick,
        modifier =
          Modifier.minimumInteractiveComponentSize().let {
            if (seeAllTestTag != null) it.testTag(seeAllTestTag) else it
          },
      ) {
        Text(
          text = stringResource(R.string.see_all),
          style = MaterialTheme.typography.labelLarge,
          color = MaterialTheme.colorScheme.primary,
        )
        Spacer(modifier = Modifier.width(4.dp))
        Icon(
          imageVector = Icons.AutoMirrored.Filled.ArrowForward,
          contentDescription = stringResource(R.string.see_all),
          modifier = Modifier.size(16.dp),
          tint = MaterialTheme.colorScheme.primary,
        )
      }
    }
  }
}

@Composable
fun DivisionCard(
  division: DivisionSummary,
  displayLanguage: DisplayLanguage,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val accentColor =
    when (division.accentIndex % 3) {
      0 -> MaterialTheme.colorScheme.primary
      1 -> MaterialTheme.colorScheme.secondary
      else -> WarmGold
    }

  Card(
    modifier =
      modifier
        .fillMaxWidth()
        .minimumInteractiveComponentSize()
        .clip(MaterialTheme.shapes.medium)
        .clickable(onClick = onClick)
        .testTag("division_card_${division.id}"),
    shape = MaterialTheme.shapes.medium,
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
  ) {
    Column(
      modifier = Modifier.padding(14.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Box(
          modifier =
            Modifier.size(34.dp)
              .clip(RoundedCornerShape(10.dp))
              .background(accentColor.copy(alpha = 0.14f)),
          contentAlignment = Alignment.Center,
        ) {
          Icon(
            imageVector = Icons.Filled.LocationOn,
            contentDescription = division.nameBn,
            tint = accentColor,
            modifier = Modifier.size(18.dp),
          )
        }
        Surface(
          shape = RoundedCornerShape(50),
          color = MaterialTheme.colorScheme.surfaceVariant,
        ) {
          Text(
            text = "0${division.accentIndex + 1}",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
          )
        }
      }

      Column {
        Text(
          text = division.nameBn,
          style = MaterialTheme.typography.titleMedium,
          color = MaterialTheme.colorScheme.onSurface,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis,
        )
        if (displayLanguage == DisplayLanguage.BILINGUAL) {
          Text(
            text = division.nameEn,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
          )
        }
      }

      Text(
        text =
          if (displayLanguage == DisplayLanguage.BILINGUAL) division.statusLabelBn
          else division.statusLabelBn,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.primary,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
      )
    }
  }
}

@Composable
fun ShowcaseCard(
  item: ShowcaseItem,
  isSaved: Boolean,
  displayLanguage: DisplayLanguage,
  onToggleSave: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val bookmarkTint by
    animateColorAsState(
      targetValue =
        if (isSaved) BangladeshRed else MaterialTheme.colorScheme.onSurfaceVariant,
      label = "bookmark_tint",
    )

  Card(
    modifier = modifier.testTag("showcase_card_${item.id}"),
    shape = MaterialTheme.shapes.medium,
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
  ) {
    Column {
      if (item.imageRes != null) {
        Box(modifier = Modifier.fillMaxWidth().height(148.dp)) {
          Image(
            painter = painterResource(id = item.imageRes),
            contentDescription = item.titleBn,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
          )
          Surface(
            color = Color(0xCC101814),
            shape = RoundedCornerShape(50),
            modifier = Modifier.align(Alignment.TopStart).padding(10.dp),
          ) {
            Text(
              text = item.badgeBn,
              style = MaterialTheme.typography.labelSmall,
              color = Color.White,
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            )
          }
        }
      }

      Column(
        modifier = Modifier.padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.Top,
        ) {
          Column(modifier = Modifier.weight(1f)) {
            if (item.imageRes == null) {
              Surface(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(50),
                modifier = Modifier.padding(bottom = 6.dp),
              ) {
                Text(
                  text = item.badgeBn,
                  style = MaterialTheme.typography.labelSmall,
                  color = MaterialTheme.colorScheme.onPrimaryContainer,
                  modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                )
              }
            }
            Text(
              text = item.titleBn,
              style = MaterialTheme.typography.titleMedium,
              color = MaterialTheme.colorScheme.onSurface,
              maxLines = 2,
              overflow = TextOverflow.Ellipsis,
            )
            if (displayLanguage == DisplayLanguage.BILINGUAL) {
              Text(
                text = item.titleEn,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
              )
            }
          }

          IconButton(
            onClick = onToggleSave,
            modifier =
              Modifier.minimumInteractiveComponentSize().testTag("bookmark_btn_${item.id}"),
          ) {
            Icon(
              imageVector = if (isSaved) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
              contentDescription =
                if (isSaved) stringResource(R.string.saved_remove)
                else stringResource(R.string.saved_add),
              tint = bookmarkTint,
            )
          }
        }

        Text(
          text =
            if (displayLanguage == DisplayLanguage.BILINGUAL) item.subtitleBn
            else item.subtitleBn,
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          maxLines = 2,
          overflow = TextOverflow.Ellipsis,
        )
      }
    }
  }
}

@Composable
fun DiscoverTileCard(
  item: ShowcaseItem,
  isSaved: Boolean,
  displayLanguage: DisplayLanguage,
  onToggleSave: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val icon =
    when (item.exploreFilter) {
      ExploreFilter.FOOD -> Icons.Filled.Restaurant
      ExploreFilter.HERITAGE -> Icons.Filled.TempleBuddhist
      ExploreFilter.NATURE -> Icons.Outlined.Explore
      else -> Icons.Filled.Info
    }

  Card(
    modifier = modifier.fillMaxWidth().testTag("discover_card_${item.id}"),
    shape = MaterialTheme.shapes.medium,
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
  ) {
    Row(
      modifier = Modifier.fillMaxWidth().padding(14.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
      Box(
        modifier =
          Modifier.size(48.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(
              Brush.linearGradient(
                listOf(ForestGreen.copy(alpha = 0.16f), ForestGreenDark.copy(alpha = 0.08f))
              )
            ),
        contentAlignment = Alignment.Center,
      ) {
        Icon(
          imageVector = icon,
          contentDescription = item.titleBn,
          tint = MaterialTheme.colorScheme.primary,
          modifier = Modifier.size(24.dp),
        )
      }

      Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
        Text(
          text = item.titleBn,
          style = MaterialTheme.typography.titleSmall,
          color = MaterialTheme.colorScheme.onSurface,
        )
        if (displayLanguage == DisplayLanguage.BILINGUAL) {
          Text(
            text = item.titleEn,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
          )
        }
        Text(
          text = item.subtitleBn,
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          maxLines = 2,
          overflow = TextOverflow.Ellipsis,
        )
      }

      IconButton(
        onClick = onToggleSave,
        modifier = Modifier.minimumInteractiveComponentSize().testTag("discover_save_${item.id}"),
      ) {
        Icon(
          imageVector = if (isSaved) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
          contentDescription =
            if (isSaved) stringResource(R.string.saved_remove)
            else stringResource(R.string.saved_add),
          tint = if (isSaved) BangladeshRed else MaterialTheme.colorScheme.onSurfaceVariant,
        )
      }
    }
  }
}

@Composable
fun PhaseNoticeBanner(
  title: String,
  body: String,
  modifier: Modifier = Modifier,
) {
  Surface(
    modifier = modifier.fillMaxWidth(),
    shape = MaterialTheme.shapes.medium,
    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.65f),
    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)),
  ) {
    Row(
      modifier = Modifier.padding(14.dp),
      horizontalArrangement = Arrangement.spacedBy(12.dp),
      verticalAlignment = Alignment.Top,
    ) {
      Icon(
        imageVector = Icons.Outlined.Info,
        contentDescription = title,
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(top = 2.dp),
      )
      Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
          text = title,
          style = MaterialTheme.typography.titleSmall,
          color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
        Text(
          text = body,
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.9f),
        )
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun HeroAndDivisionPreview() {
  AmarBangladeshTheme(darkTheme = false) {
    Column(
      modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
      BrandTopHeader(displayLanguage = DisplayLanguage.BILINGUAL)
      HeroBangladeshCard(displayLanguage = DisplayLanguage.BILINGUAL, onExploreClick = {})
      DivisionCard(
        division =
          DivisionSummary(
            id = "dhaka",
            nameBn = "ঢাকা",
            nameEn = "Dhaka",
            statusLabelBn = "নমুনা • জেলা তথ্য Phase 02",
            statusLabelEn = "Placeholder • Phase 02",
            accentIndex = 0,
          ),
        displayLanguage = DisplayLanguage.BILINGUAL,
        onClick = {},
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun ShowcaseCardDarkPreview() {
  AmarBangladeshTheme(darkTheme = true) {
    Column(
      modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
      ShowcaseCard(
        item =
          ShowcaseItem(
            id = "preview_food",
            titleBn = "ঐতিহ্যবাহী খাবারের নমুনা ১",
            titleEn = "Traditional Cuisine Placeholder 1",
            subtitleBn = "আঞ্চলিক খাবারের যাচাইকৃত তালিকা Phase 02-তে যুক্ত হবে",
            subtitleEn = "Reserved for Phase 02",
            badgeBn = "নমুনা খাবার • Placeholder",
            badgeEn = "Food Placeholder",
            category = ShowcaseCategory.POPULAR_FOOD,
            exploreFilter = ExploreFilter.FOOD,
            imageRes = R.drawable.img_cuisine_bengal,
          ),
        isSaved = true,
        displayLanguage = DisplayLanguage.BILINGUAL,
        onToggleSave = {},
      )
    }
  }
}
