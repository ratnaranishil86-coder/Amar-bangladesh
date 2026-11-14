package com.example.ui

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.di.AppContainer
import com.example.di.viewModelFactory
import com.example.domain.model.ThemeMode
import com.example.navigation.DistrictsRoute
import com.example.navigation.ExploreRoute
import com.example.navigation.HomeRoute
import com.example.navigation.ProfileRoute
import com.example.navigation.SavedRoute
import com.example.navigation.TopLevelDestination
import com.example.ui.screens.DistrictsScreen
import com.example.ui.screens.ExploreScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.SavedScreen
import com.example.ui.theme.AmarBangladeshTheme
import com.example.ui.viewmodel.AppStateViewModel
import com.example.ui.viewmodel.DistrictsViewModel
import com.example.ui.viewmodel.ExploreViewModel
import com.example.ui.viewmodel.HomeViewModel
import com.example.ui.viewmodel.ProfileViewModel
import com.example.ui.viewmodel.SavedViewModel

@Composable
fun AmarBangladeshApp(
  appContainer: AppContainer,
  navController: NavHostController = rememberNavController(),
) {
  val repository = appContainer.discoveryRepository
  val appStateViewModel: AppStateViewModel =
    viewModel(factory = viewModelFactory { AppStateViewModel(repository) })
  val homeViewModel: HomeViewModel =
    viewModel(factory = viewModelFactory { HomeViewModel(repository) })
  val districtsViewModel: DistrictsViewModel =
    viewModel(factory = viewModelFactory { DistrictsViewModel(repository) })
  val exploreViewModel: ExploreViewModel =
    viewModel(factory = viewModelFactory { ExploreViewModel(repository) })
  val savedViewModel: SavedViewModel =
    viewModel(factory = viewModelFactory { SavedViewModel(repository) })
  val profileViewModel: ProfileViewModel =
    viewModel(factory = viewModelFactory { ProfileViewModel(repository) })

  val themeMode by appStateViewModel.themeMode.collectAsStateWithLifecycle()
  val isDark =
    when (themeMode) {
      ThemeMode.SYSTEM -> isSystemInDarkTheme()
      ThemeMode.LIGHT -> false
      ThemeMode.DARK -> true
    }

  AmarBangladeshTheme(darkTheme = isDark) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val navigateToTopLevel: (TopLevelDestination) -> Unit = { dest ->
      val route: Any =
        when (dest) {
          TopLevelDestination.HOME -> HomeRoute
          TopLevelDestination.DISTRICTS -> DistrictsRoute
          TopLevelDestination.EXPLORE -> ExploreRoute()
          TopLevelDestination.SAVED -> SavedRoute
          TopLevelDestination.PROFILE -> ProfileRoute
        }
      navController.navigate(route) {
        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
      }
    }

    val isSelected: (TopLevelDestination) -> Boolean = { dest ->
      when (dest) {
        TopLevelDestination.HOME -> currentDestination?.hasRoute<HomeRoute>() == true
        TopLevelDestination.DISTRICTS -> currentDestination?.hasRoute<DistrictsRoute>() == true
        TopLevelDestination.EXPLORE -> currentDestination?.hasRoute<ExploreRoute>() == true
        TopLevelDestination.SAVED -> currentDestination?.hasRoute<SavedRoute>() == true
        TopLevelDestination.PROFILE -> currentDestination?.hasRoute<ProfileRoute>() == true
      }
    }

    BoxWithConstraints(
      modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
    ) {
      val useNavRail = maxWidth >= 600.dp

      Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets.safeDrawing,
        bottomBar = {
          if (!useNavRail) {
            NavigationBar(
              modifier = Modifier.testTag("bottom_navigation_bar"),
              containerColor = MaterialTheme.colorScheme.surface,
              tonalElevation = 4.dp,
            ) {
              TopLevelDestination.entries.forEach { dest ->
                val selected = isSelected(dest)
                val label = stringResource(dest.labelRes)
                NavigationBarItem(
                  selected = selected,
                  onClick = { navigateToTopLevel(dest) },
                  modifier = Modifier.testTag(dest.testTag),
                  icon = {
                    Icon(
                      imageVector = if (selected) dest.selectedIcon else dest.unselectedIcon,
                      contentDescription = label,
                    )
                  },
                  label = {
                    Text(
                      text = label,
                      style = MaterialTheme.typography.labelSmall,
                      maxLines = 1,
                      overflow = TextOverflow.Ellipsis,
                    )
                  },
                  colors =
                    NavigationBarItemDefaults.colors(
                      selectedIconColor = MaterialTheme.colorScheme.primary,
                      selectedTextColor = MaterialTheme.colorScheme.primary,
                      indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                      unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                      unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                )
              }
            }
          }
        },
      ) { innerPadding ->
        Row(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
          if (useNavRail) {
            NavigationRail(
              modifier = Modifier.fillMaxHeight().testTag("navigation_rail"),
              containerColor = MaterialTheme.colorScheme.surface,
            ) {
              TopLevelDestination.entries.forEach { dest ->
                val selected = isSelected(dest)
                val label = stringResource(dest.labelRes)
                NavigationRailItem(
                  selected = selected,
                  onClick = { navigateToTopLevel(dest) },
                  modifier = Modifier.testTag(dest.testTag),
                  icon = {
                    Icon(
                      imageVector = if (selected) dest.selectedIcon else dest.unselectedIcon,
                      contentDescription = label,
                    )
                  },
                  label = {
                    Text(
                      text = label,
                      style = MaterialTheme.typography.labelSmall,
                      maxLines = 1,
                      overflow = TextOverflow.Ellipsis,
                    )
                  },
                  colors =
                    NavigationRailItemDefaults.colors(
                      selectedIconColor = MaterialTheme.colorScheme.primary,
                      selectedTextColor = MaterialTheme.colorScheme.primary,
                      indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                    ),
                )
              }
            }
          }

          Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
            NavHost(
              navController = navController,
              startDestination = HomeRoute,
              modifier = Modifier.fillMaxSize().widthIn(max = 900.dp),
              enterTransition = { fadeIn(animationSpec = tween(220)) },
              exitTransition = { fadeOut(animationSpec = tween(180)) },
              popEnterTransition = { EnterTransition.None },
              popExitTransition = { ExitTransition.None },
            ) {
              composable<HomeRoute> {
                val homeState by homeViewModel.uiState.collectAsStateWithLifecycle()
                HomeScreen(
                  uiState = homeState,
                  onSearchQueryChange = homeViewModel::onSearchQueryChange,
                  onDivisionClick = { division ->
                    districtsViewModel.onSelectDivision(division.id)
                    navigateToTopLevel(TopLevelDestination.DISTRICTS)
                  },
                  onNavigateToDistricts = { navigateToTopLevel(TopLevelDestination.DISTRICTS) },
                  onNavigateToExplore = { navigateToTopLevel(TopLevelDestination.EXPLORE) },
                  onToggleBookmark = homeViewModel::onToggleBookmark,
                )
              }

              composable<DistrictsRoute> {
                val homeState by homeViewModel.uiState.collectAsStateWithLifecycle()
                val districtsState by districtsViewModel.uiState.collectAsStateWithLifecycle()
                DistrictsScreen(
                  uiState = districtsState,
                  allDivisions = homeState.divisions,
                  onSearchQueryChange = districtsViewModel::onSearchQueryChange,
                  onSelectDivision = districtsViewModel::onSelectDivision,
                )
              }

              composable<ExploreRoute> {
                val exploreState by exploreViewModel.uiState.collectAsStateWithLifecycle()
                ExploreScreen(
                  uiState = exploreState,
                  onSelectFilter = exploreViewModel::onSelectFilter,
                  onToggleBookmark = exploreViewModel::onToggleBookmark,
                )
              }

              composable<SavedRoute> {
                val savedState by savedViewModel.uiState.collectAsStateWithLifecycle()
                SavedScreen(
                  uiState = savedState,
                  onRemoveBookmark = savedViewModel::onRemoveBookmark,
                  onNavigateHome = { navigateToTopLevel(TopLevelDestination.HOME) },
                )
              }

              composable<ProfileRoute> {
                val profileState by profileViewModel.uiState.collectAsStateWithLifecycle()
                ProfileScreen(
                  uiState = profileState,
                  onThemeModeChange = profileViewModel::onThemeModeChange,
                  onLanguageChange = profileViewModel::onLanguageChange,
                )
              }
            }
          }
        }
      }
    }
  }
}
