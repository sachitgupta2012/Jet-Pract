package com.jetpacktest.presentation.ui

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jetpacktest.R
import com.jetpacktest.data.AppConstant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgetPassword(navController: NavHostController) {

    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    var enterUserEmail by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.img_screen_bg),
            contentDescription = "Screen Bg",
            contentScale = ContentScale.FillBounds
        )
        Scaffold(
            containerColor = Color.Transparent,
            modifier = Modifier
                .fillMaxSize(),
            topBar = {
                TopAppBar(
                    { },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                backDispatcher?.onBackPressed()
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_back_ios),
                                contentDescription = ""
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent, // Make TopAppBar background transparent
                        navigationIconContentColor = Color.White, // Customize icon color if needed
                        titleContentColor = Color.White
                    )
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp)
                        .width(106.dp),
                    painter = painterResource(id = R.drawable.icon_music_wave),
                    contentDescription = "",
                )
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .width(177.dp)
                        .height(61.dp),
                    painter = painterResource(id = R.drawable.icon_trucast_solution),
                    contentDescription = ""
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    Spacer(modifier = Modifier.height(40.dp))
                    Text(
                        stringResource(R.string.forget_password),
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 24.sp,
                        color = colorResource(R.color.app_color),
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(20.dp))

                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = stringResource(id = R.string.enter_email_address_to_receive_notification_code),
                        color = colorResource(id = R.color.white),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .align(Alignment.CenterHorizontally),
                        value = enterUserEmail,
                        onValueChange = { enterUserEmail = it },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        ),
                        shape = RoundedCornerShape(8.dp),
                        label = {
                            Text(
                                text = stringResource(id = R.string.email),
                                color = colorResource(id = R.color.white)
                            )
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_email),
                                contentDescription = ""
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(50.dp))

                    OutlinedButton(
                        onClick = {

                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .align(Alignment.CenterHorizontally)
                            .offset(y = 24.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .shadow(8.dp, shape = RoundedCornerShape(24.dp)),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = colorResource(id = R.color.app_color)
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.send_code),
                            color = colorResource(id = R.color.black),
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun MyPreviewTwo(){
    ForgetPassword(rememberNavController())
}