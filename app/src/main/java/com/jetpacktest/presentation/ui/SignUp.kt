package com.jetpacktest.presentation.ui

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.jetpacktest.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUp(navController: NavHostController) {

    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    var enterUserName by remember { mutableStateOf("") }
    var enterUserEmail by remember { mutableStateOf("") }
    var enterUserPassword by remember { mutableStateOf("") }
    var enterCPassword by remember { mutableStateOf("") }
    val isChecked = remember { mutableStateOf(false) }


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
                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    stringResource(R.string.sign_up),
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 24.sp,
                    color = colorResource(R.color.app_color),
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(4.dp))

                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = stringResource(id = R.string.welcome_please_enter_your_details),
                    color = colorResource(id = R.color.white),
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .align(Alignment.CenterHorizontally),
                    value = enterUserName,
                    onValueChange = { enterUserName = it },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    shape = RoundedCornerShape(8.dp),
                    label = {
                        Text(
                            text = stringResource(id = R.string.username),
                            color = colorResource(id = R.color.white)
                        )
                    },
                    leadingIcon = {
                        Icon(painter = painterResource(id = R.drawable.icon_email), contentDescription = "")
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
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
                        Icon(painter = painterResource(id = R.drawable.icon_email), contentDescription = "")
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .align(Alignment.CenterHorizontally),
                    value = enterUserPassword,
                    onValueChange = { enterUserPassword = it },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    shape = RoundedCornerShape(8.dp),
                    label = {
                        Text(
                            text = stringResource(id = R.string.password),
                            color = colorResource(id = R.color.white)
                        )
                    },
                    leadingIcon = {
                        Icon(painter = painterResource(id = R.drawable.icon_password_lock), contentDescription = "")
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .align(Alignment.CenterHorizontally),
                    value = enterCPassword,
                    onValueChange = { enterCPassword = it },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    shape = RoundedCornerShape(8.dp),
                    label = {
                        Text(
                            text = stringResource(id = R.string.confirm_password),
                            color = colorResource(id = R.color.white)
                        )
                    },
                    leadingIcon = {
                        Icon(painter = painterResource(id = R.drawable.icon_password_lock), contentDescription = "")
                    }
                )

                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Checkbox(
                        checked = isChecked.value,
                        onCheckedChange = { isChecked.value = it }
                    )
                    Text(
                        text = stringResource(id = R.string.i_agree_with_privacy_and_policy),
                        modifier = Modifier.fillMaxWidth(1f),
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.white)
                    )
                }
                OutlinedButton(
                    onClick = {

                    },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .align(Alignment.CenterHorizontally)
                        .offset(y = 24.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = colorResource(id = R.color.app_color)
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.sign_up),
                        color = colorResource(id = R.color.black),
                        fontSize = 14.sp
                    )
                }

            }
        }
    }

}

@Composable
fun MyPreview(){

}