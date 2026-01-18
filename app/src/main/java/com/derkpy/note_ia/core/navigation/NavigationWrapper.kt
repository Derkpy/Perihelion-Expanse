package com.derkpy.note_ia.core.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.derkpy.note_ia.core.navigation.authStates.AuthState
import com.derkpy.note_ia.core.navigation.vm.NavigationViewModel
import com.derkpy.note_ia.ui.detail.ui.DetailScreen
import com.derkpy.note_ia.ui.home.ui.HomeScreen
import com.derkpy.note_ia.ui.login.ui.LoginScreen
import com.derkpy.note_ia.ui.presentation.ui.PresentationScreen
import com.derkpy.note_ia.ui.signup.ui.SingUpScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavigationWrapper(viewModel: NavigationViewModel = koinViewModel() ) {
    val authState by viewModel.authState.collectAsStateWithLifecycle()
    
    when (authState) {
        AuthState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        AuthState.LoggedIn -> NavHostController(startDestination = Home)
        AuthState.LoggedOut -> NavHostController(startDestination = Presentation)
    }
}

@Composable
fun NavHostController(startDestination: Any){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable<Presentation>{
            PresentationScreen(
                navigateToLogin = { navController.navigate(Login) },
                navigateToSignUp = { navController.navigate(SignUp) },
                navigateToHome = {
                    navController.navigate(Home) {
                        popUpTo(Presentation) { inclusive = true }
                    }
                })
        }
        composable<Login>{
            LoginScreen(
                navigateToHome = {
                    navController.navigate(Home) {
                        popUpTo(Login) { inclusive = true }
                    }
                }
            )
        }
        composable<SignUp>{
            SingUpScreen(
                navigateToHome = {
                    navController.navigate(Home) {
                        popUpTo(SignUp) { inclusive = true }
                    }
                }
            )
        }
        composable<Home> {
            HomeScreen(navigateToDetail = {
                navController.navigate(Detail(id = it)) {
                    popUpTo(Home) { inclusive = false }
                }
            })
        }

        composable<Detail> {
            DetailScreen()
        }

    }
}