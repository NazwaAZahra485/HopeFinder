package com.nazwa.hopefinder.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController
import com.nazwa.hopefinder.ui.pages.HomeSosScreen
//import com.nazwa.hopefinder.viewmodel.AuthViewModel
import com.nazwa.hopefinder.viewmodel.SosViewModel

// TODO: buka booklet, help, map, profile, harus login using authViewModel

@Composable
fun AppNavigation(sosViewModel: SosViewModel){
    val navController = rememberNavController()
    val sosViewModel: SosViewModel = viewModel()

    NavHost(navController = navController, startDestination = Route.HOME){
        composable(Route.HOME){
            HomeSosScreen(sosViewModel, navController)
        }
        composable(Route.HELP){
            HelpScreen(navController)
        }
        composable(Route.BOOKLET){
            BookletScreen(navController)
        }
        composable(Route.MAP){
            MapScreen(navController)
        }
        composable(Route.PROFILE){
            ProfileScreen(navController)
        }
    }
}