package com.jetpacktest.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jetpacktest.R
import com.jetpacktest.data.AppConstant
import com.jetpacktest.data.DataStore
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    val context = LocalContext.current
    val dataStore = DataStore(context = context)
    val userId = dataStore.getUserId().collectAsState(initial = "")
    LaunchedEffect(key1 = Unit) {
        delay(3000) // Simulate loading time

        if (userId.value?.isNotEmpty() == true) {
            navController.navigate(AppConstant.KeyName.dashboard) {
                popUpTo(AppConstant.KeyName.splashscreen) { inclusive = true }
            }
        } else {
            navController.navigate(AppConstant.KeyName.onboard) {
                popUpTo(AppConstant.KeyName.splashscreen) { inclusive = true }
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            contentDescription = "SplashScreen",
            painter = painterResource(id = R.drawable.img_splash_screen),
            contentScale = ContentScale.FillBounds
        )
    }
}

@Composable
@Preview
fun MyScreen() {
    SplashScreen(navController = rememberNavController())
}
