package com.example.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.R
import kotlinx.serialization.Serializable

@Serializable object HomeRoute

@Serializable object DistrictsRoute

@Serializable data class ExploreRoute(val initialFilter: String? = null)

@Serializable object SavedRoute

@Serializable object ProfileRoute

enum class TopLevelDestination(
  val routeTag: String,
  @StringRes val labelRes: Int,
  @StringRes val labelEnRes: Int,
  val selectedIcon: ImageVector,
  val unselectedIcon: ImageVector,
  val testTag: String,
) {
  HOME(
    routeTag = "home",
    labelRes = R.string.nav_home,
    labelEnRes = R.string.nav_home_en,
    selectedIcon = Icons.Filled.Home,
    unselectedIcon = Icons.Outlined.Home,
    testTag = "nav_home",
  ),
  DISTRICTS(
    routeTag = "districts",
    labelRes = R.string.nav_districts,
    labelEnRes = R.string.nav_districts_en,
    selectedIcon = Icons.Filled.Map,
    unselectedIcon = Icons.Outlined.Map,
    testTag = "nav_districts",
  ),
  EXPLORE(
    routeTag = "explore",
    labelRes = R.string.nav_explore,
    labelEnRes = R.string.nav_explore_en,
    selectedIcon = Icons.Filled.Explore,
    unselectedIcon = Icons.Outlined.Explore,
    testTag = "nav_explore",
  ),
  SAVED(
    routeTag = "saved",
    labelRes = R.string.nav_saved,
    labelEnRes = R.string.nav_saved_en,
    selectedIcon = Icons.Filled.Bookmark,
    unselectedIcon = Icons.Outlined.BookmarkBorder,
    testTag = "nav_saved",
  ),
  PROFILE(
    routeTag = "profile",
    labelRes = R.string.nav_profile,
    labelEnRes = R.string.nav_profile_en,
    selectedIcon = Icons.Filled.Person,
    unselectedIcon = Icons.Outlined.Person,
    testTag = "nav_profile",
  ),
}
