package com.jetpacktest.data

object AppConstant {
    const val BASE_URL = "https://trukastapps.augursapps.com/"
    const val headerJSON = "Accept: application/json"

    object EndUrl {
        const val loginUser = "api/Account/VerifyLogin"
        const val getChannelList = "api/ChannelDetails/GetChannelDetailsAuth"
        const val getSettingContent = "api/User/GetContentPages"
        const val logout = "Logout"
    }

    object KeyName {
        const val onboard = "OnBoardScreen"
        const val splashscreen = "SplashScreen"
        const val login_screen = "LoginScreen"
        const val sign_up = "SignUp"
        const val dashboard = "Dashboard"
        const val forgetPassword = "ForgetPassword"
        const val settings = "Settings"
        const val channelDetails = "ChannelDetails"
        const val requestForSong = "RequestForSong"
        const val requestedSongList = "RequestedSongList"
    }
    object ErrorMsg{
        const val notFound = "Not Found."
        const val unauthorisedUser = "Unauthorised User."
    }
}