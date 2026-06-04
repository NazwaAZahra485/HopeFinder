package com.nazwa.hopefinder.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController
import com.nazwa.hopefinder.ui.components.BurgerMenuDrawer
import com.nazwa.hopefinder.ui.pages.HomeScreen
import com.nazwa.hopefinder.ui.pages.BookletScreen
import com.nazwa.hopefinder.ui.pages.NotificationScreen
import com.nazwa.hopefinder.viewmodel.BookletViewModel
import com.nazwa.hopefinder.viewmodel.HomeViewModel
import com.nazwa.hopefinder.viewmodel.NotificationViewModel
import kotlinx.coroutines.launch

// TODO: buka booklet, home, notification

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    var gpsEnabled by remember { mutableStateOf(true) }

    // ViewModel variable
    val homeViewModel: HomeViewModel = viewModel()
    val bookletViewModel: BookletViewModel = viewModel()
    val notificationViewModel: NotificationViewModel = viewModel()

    // Drawer control variable
    val openDrawer = { coroutineScope.launch { drawerState.open() } }
    val closeDrawer = { coroutineScope.launch { drawerState.close() } }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            BurgerMenuDrawer(
                navController = navController,
                isGpsEnabled = gpsEnabled,
                onGpsToggle = { gpsEnabled = it },
                onCloseDrawer = { closeDrawer() }
            )
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Route.HOME,
            modifier = Modifier.fillMaxSize()
        ){
            composable(Route.HOME){
                HomeScreen(homeViewModel, navController, onOpenDrawer = { openDrawer() })
            }
            composable(Route.BOOKLET){
                BookletScreen(bookletViewModel, navController, onOpenDrawer = { openDrawer() })
            }
            composable(Route.NOTIFICATION){
                NotificationScreen(notificationViewModel, navController, onOpenDrawer = { openDrawer() })
            }

            // Booklet pages
            composable(Route.BANJIR){ //TODO: add the pages screen here }
            composable(Route.BANJIR_BANDANG){ //TODO: add the pages screen here }
            composable(Route.GUNUNG_MELETUS){ //TODO: add the pages screen here }
            composable(Route.GEMPA_BUMI){ //TODO: add the pages screen here }
            composable(Route.LONGSOR){ //TODO: add the pages screen here }
            composable(Route.TSUNAMI){ //TODO: add the pages screen here }
        }
    }

}