package com.jetpacktest.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jetpacktest.R
import com.jetpacktest.data.AppConstant

@Composable
fun OnBoardScreen(navController: NavHostController) {
    val context = LocalContext.current
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.img_bg_onboard),
            contentDescription = "On Board Screen",
            contentScale = ContentScale.FillBounds
        )

        // Use Column to layout content
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.size(80.dp))  // Space at the top

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f)
                    .align(Alignment.CenterHorizontally),  // Align image to the top
                painter = painterResource(id = R.drawable.img_girl_music),
                contentDescription = "On Board Screen"
            )
            OutlinedButton(
                onClick = {
                    navController.navigate( AppConstant.KeyName.login_screen)
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .align(Alignment.CenterHorizontally)
                    .offset(y = -(80).dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = colorResource(id = R.color.white)
                )
            ) {
                Text(
                    text = stringResource(id = R.string.get_started),
                    color = colorResource(id = R.color.black),
                    fontSize = 14.sp
                )
            }
            Text(
                text = stringResource(id = R.string.onboarding_welcome_msg),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .offset(y = -(24).dp)
                    .align(Alignment.CenterHorizontally),
                color = colorResource(id = R.color.white),
                textAlign = TextAlign.Center
            )
            Image(
                painter = painterResource(id = R.drawable.icon_music_wave),
                contentDescription = "Music Icon",
                modifier = Modifier
                    .fillMaxWidth()
                    .width(72.dp)
                    .height(48.dp)
            )
        }
    }
}


@Composable
@Preview
fun PreviewScreen() {
    OnBoardScreen(navController = rememberNavController())
}