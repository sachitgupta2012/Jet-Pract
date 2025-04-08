package com.jetpacktest.presentation.ui

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.jetpacktest.R
import com.jetpacktest.data.AppConstant
import com.jetpacktest.data.isValidEmail
import com.jetpacktest.viewmodel.LoginViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.jetpacktest.data.GlobalProgressBar
import com.jetpacktest.data.OkDialog
import com.jetpacktest.data.hideLoading
import com.jetpacktest.data.showLoading

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavHostController) {

    val context = LocalContext.current
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    val viewModel: LoginViewModel = hiltViewModel()
    val loginState = viewModel.loginState.collectAsState()
    var enterUserEmail by remember { mutableStateOf("") }
    var enterUserPassword by remember { mutableStateOf("") }
    var checkedRememberState by remember { mutableStateOf(false) }
    var errorEmailMessage by remember { mutableStateOf<String?>(null) }
    var errorPasswordMessage by remember { mutableStateOf<String?>(null) }
    val showDialog by viewModel.showDialog.collectAsState()
    val callbackManager = remember { CallbackManager.Factory.create() }
    val TAG = "LoginScreenTag"


    rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        callbackManager.onActivityResult(
            result.resultCode,
            result.resultCode,
            result.data
        )
    }

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
                Spacer(modifier = Modifier.height(50.dp))
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = stringResource(id = R.string.login_to_your_ac),
                    color = colorResource(id = R.color.white),
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .align(Alignment.CenterHorizontally),
                    value = enterUserEmail,
                    onValueChange = {
                        enterUserEmail = it
                        errorEmailMessage =
                            if (isValidEmail(it)) null else if (it.isEmpty()) "Email can't be empty" else "Invalid Email"
                    },
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
                    },
                    isError = errorEmailMessage != null,
                    supportingText = {
                        if (errorEmailMessage != null) {
                            Text(
                                text = errorEmailMessage!!,
                                color = Color.Red,
                                fontSize = 12.sp
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .align(Alignment.CenterHorizontally),
                    value = enterUserPassword,
                    onValueChange = {
                        enterUserPassword = it
                        errorPasswordMessage =
                            if (it.isEmpty()) "Please enter password" else if (it.length < 6 || it.length > 8) "Password should between 6 to 8 characters" else ""
                    },
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
                        Icon(
                            painter = painterResource(id = R.drawable.icon_password_lock),
                            contentDescription = ""
                        )
                    },
                    isError = errorPasswordMessage != null,
                    supportingText = {
                        errorPasswordMessage?.let {
                            Text(text = it, color = Color.Red, fontSize = 12.sp)
                        }
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
                        checked = checkedRememberState,
                        onCheckedChange = { checkedRememberState = it }
                    )
                    Text(
                        text = stringResource(id = R.string.remember_password),
                        modifier = Modifier.fillMaxWidth(1f),
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.white)
                    )
                }
                OutlinedButton(
                    onClick = {

                        if (enterUserEmail.isEmpty()) {
                            Toast.makeText(context, "Please Enter user email", Toast.LENGTH_SHORT)
                                .show()
                            return@OutlinedButton
                        } else if (!isValidEmail(enterUserEmail)) {
                            Toast.makeText(context, "Please Enter valid email", Toast.LENGTH_SHORT)
                                .show()
                            return@OutlinedButton
                        } else if (enterUserPassword.isEmpty()) {
                            Toast.makeText(context, "Please Enter password", Toast.LENGTH_SHORT)
                                .show()
                            return@OutlinedButton
                        } else if (enterUserPassword.length < 6 || enterUserPassword.length > 8) {
                            Toast.makeText(
                                context,
                                "Password should between 6 to 8 characters",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@OutlinedButton
                        } else {
                            showLoading()
                            viewModel.login(enterUserEmail, enterUserPassword, "")
                        }

                    },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .align(Alignment.CenterHorizontally)
                        .offset(y = 16.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = colorResource(id = R.color.app_color)
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.login),
                        color = colorResource(id = R.color.black),
                        fontSize = 14.sp
                    )
                }

                loginState.let {
                    if (it.value?.StatusCode == 200) {
                        hideLoading()
                        navController.navigate(AppConstant.KeyName.dashboard)
                    } else {
                        hideLoading()
                        if (showDialog) {
                            OkDialog(
                                showDialog,
                                false,
                                stringResource(R.string.app_name),
                                it.value?.Message
                            ) {
                                viewModel.setShowDialog(false)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(28.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                            navController.navigate(AppConstant.KeyName.forgetPassword)
                        },
                    text = stringResource(id = R.string.forget_the_password),
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.white),
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(20.dp))

                AndroidView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    factory = { context ->
                        LoginButton(context).apply {
                            setPublishPermissions(listOf("email", "public_profile"))
                            registerCallback(
                                callbackManager,
                                object : FacebookCallback<LoginResult> {

                                    override fun onCancel() {
                                        Toast.makeText(
                                            context,
                                            "Login canceled",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                    override fun onError(error: FacebookException) {
                                        error.printStackTrace()
                                    }

                                    override fun onSuccess(result: LoginResult) {
                                        Log.e(TAG, "onSuccess: ${result.accessToken}")
                                        result.accessToken
                                    }

                                })
                        }
                    }
                )
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .align(Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.dont_have_an_ac),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        color = colorResource(id = R.color.white)
                    )
                    Text(
                        text = stringResource(id = R.string.sign_up_q),
                        modifier = Modifier.clickable {
                            navController.navigate(AppConstant.KeyName.sign_up)
                        },
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.app_color)
                    )
                }
            }
        }
    }
    context.GlobalProgressBar()
}

