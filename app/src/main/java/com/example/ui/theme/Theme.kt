package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme =
  lightColorScheme(
    primary = ForestGreen,
    onPrimary = Color.White,
    primaryContainer = ForestGreenContainer,
    onPrimaryContainer = OnForestGreenContainer,
    secondary = BangladeshRed,
    onSecondary = Color.White,
    secondaryContainer = BangladeshRedContainer,
    onSecondaryContainer = OnBangladeshRedContainer,
    tertiary = WarmGold,
    onTertiary = Color.White,
    tertiaryContainer = WarmGoldContainer,
    onTertiaryContainer = OnWarmGoldContainer,
    background = OffWhite,
    onBackground = TextPrimaryLight,
    surface = CardBackground,
    onSurface = TextPrimaryLight,
    surfaceVariant = SoftSageSurface,
    onSurfaceVariant = TextSecondaryLight,
    surfaceContainerLowest = CardBackground,
    surfaceContainerLow = OffWhite,
    surfaceContainer = SoftSageSurface,
    surfaceContainerHigh = Color(0xFFE5EEE7),
    surfaceContainerHighest = Color(0xFFDCE7DF),
    outline = Color(0xFF9EB0A4),
    outlineVariant = SoftSageBorder,
    error = BangladeshRed,
    onError = Color.White,
  )

private val DarkColorScheme =
  darkColorScheme(
    primary = EmeraldAccentDark,
    onPrimary = Color(0xFF00391F),
    primaryContainer = EmeraldContainerDark,
    onPrimaryContainer = ForestGreenContainer,
    secondary = RedAccentDark,
    onSecondary = Color(0xFF68000F),
    secondaryContainer = RedContainerDark,
    onSecondaryContainer = BangladeshRedContainer,
    tertiary = Color(0xFFF3C35F),
    onTertiary = Color(0xFF3F2B00),
    tertiaryContainer = Color(0xFF5B3F00),
    onTertiaryContainer = WarmGoldContainer,
    background = DarkBackground,
    onBackground = TextPrimaryDark,
    surface = DarkCardBackground,
    onSurface = TextPrimaryDark,
    surfaceVariant = DarkSurfaceElevated,
    onSurfaceVariant = TextSecondaryDark,
    surfaceContainerLowest = DarkBackground,
    surfaceContainerLow = DarkSurface,
    surfaceContainer = DarkCardBackground,
    surfaceContainerHigh = DarkSurfaceElevated,
    surfaceContainerHighest = Color(0xFF2B3F34),
    outline = Color(0xFF556C5F),
    outlineVariant = DarkOutline,
    error = RedAccentDark,
    onError = Color(0xFF68000F),
  )

@Composable
fun AmarBangladeshTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }
      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = AmarBangladeshTypography,
    shapes = AmarBangladeshShapes,
    content = content,
  )
}
