package com.nazwa.hopefinder

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.nazwa.hopefinder.navigation.AppNavigation
import com.nazwa.hopefinder.viewmodel.SosViewModel

@Composable
fun App(){
    val sosViewModel: SosViewModel = viewModel()
    AppNavigation(sosViewModel)
}