package com.jetpacktest.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.jetpacktest.data.AppConstant
import com.jetpacktest.data.Models
import com.jetpacktest.presentation.ui.ChannelDetails
import com.jetpacktest.presentation.ui.Dashboard
import com.jetpacktest.presentation.ui.ForgetPassword
import com.jetpacktest.presentation.ui.LoginScreen
import com.jetpacktest.presentation.ui.OnBoardScreen
import com.jetpacktest.presentation.ui.RequestForSong
import com.jetpacktest.presentation.ui.RequestedSongList
import com.jetpacktest.presentation.ui.Settings
import com.jetpacktest.presentation.ui.SignUp
import com.jetpacktest.presentation.ui.SplashScreen
import com.jetpacktest.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                // Set up the NavController
                val navHostController = rememberNavController()
                // Navigation host
                NavHost(
                    navController = navHostController,
                    startDestination = AppConstant.KeyName.splashscreen
                ) {
                    composable(AppConstant.KeyName.splashscreen) {
                        SplashScreen(navController = navHostController)
                    }
                    composable(AppConstant.KeyName.onboard) {
                        OnBoardScreen(navController = navHostController)
                    }
                    composable(AppConstant.KeyName.login_screen) {
                        LoginScreen(navController = navHostController)
                    }
                    composable(AppConstant.KeyName.sign_up) {
                        SignUp(navController = navHostController)
                    }
                    composable(AppConstant.KeyName.dashboard) {
                        Dashboard(navController = navHostController)
                    }

                    composable(
                        route = "${AppConstant.KeyName.channelDetails}/{channelDetails}",
                        arguments = listOf(
                            navArgument("channelDetails") { type = NavType.StringType },
                        )
                    ) { backStackEntry ->

                        val jsonChannel = backStackEntry.arguments?.getString("channelDetails")
                        val channelDetails = Gson().fromJson(jsonChannel, Models.ChannelDetails::class.java)

                        ChannelDetails(
                            navController = navHostController,
                            channelDetails = channelDetails
                        )
                    }
                    composable(AppConstant.KeyName.forgetPassword) {
                        ForgetPassword(navController = navHostController)
                    }
                    composable(AppConstant.KeyName.settings) {
                        Settings(navController = navHostController)
                    }
                    composable(AppConstant.KeyName.requestForSong) {
                        RequestForSong(navController = navHostController)
                    }
                    composable(AppConstant.KeyName.requestedSongList) {
                        RequestedSongList(navController = navHostController)
                    }


                }
            }
        }
    }
}
