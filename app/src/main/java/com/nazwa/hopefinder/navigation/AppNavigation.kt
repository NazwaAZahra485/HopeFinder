package com.nazwa.hopefinder.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController
import com.nazwa.hopefinder.ui.pages.HomeScreen
import com.nazwa.hopefinder.ui.pages.BookletScreen
import com.nazwa.hopefinder.ui.pages.NotificationScreen
import com.nazwa.hopefinder.viewmodel.BookletViewModel
import com.nazwa.hopefinder.viewmodel.HomeViewModel
import com.nazwa.hopefinder.viewmodel.NotificationViewModel

// TODO: buka booklet, home, notification

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    val homeViewModel: HomeViewModel = viewModel()
    val bookletViewModel: BookletViewModel = viewModel()
    val notificationViewModel: NotificationViewModel = viewModel()

    NavHost(navController = navController, startDestination = Route.HOME){
        composable(Route.HOME){
            HomeScreen(homeViewModel, navController)
        }
        composable(Route.BOOKLET){
            BookletScreen(bookletViewModel, navController)
        }
        composable(Route.NOTIFICATION){
            NotificationScreen(notificationViewModel, navController)
        }
    }
}