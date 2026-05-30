package com.nazwa.hopefinder

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nazwa.hopefinder.navigation.AppNavigation
import com.nazwa.hopefinder.viewmodel.HomeViewModel

@Composable
fun App(){
    val homeViewModel : HomeViewModel = viewModel()
    AppNavigation(homeViewModel)
}