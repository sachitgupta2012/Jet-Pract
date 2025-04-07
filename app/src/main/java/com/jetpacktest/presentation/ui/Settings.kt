package com.jetpacktest.presentation.ui

import android.util.Log
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.jetpacktest.R
import com.jetpacktest.data.ApiState
import com.jetpacktest.data.AppConstant
import com.jetpacktest.data.DataStore
import com.jetpacktest.data.GlobalProgressBar
import com.jetpacktest.data.OkDialog
import com.jetpacktest.data.PageHeader
import com.jetpacktest.data.hideLoading
import com.jetpacktest.data.showLoading
import com.jetpacktest.viewmodel.VMSettings
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Settings(navController: NavHostController) {

    val context = LocalContext.current
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    val dataStore = DataStore(context)
    val settingViewModel: VMSettings = hiltViewModel()
    val settingVMState by settingViewModel.contentState.collectAsState()
    val logoutState by settingViewModel.logoutState.collectAsState()
    var showSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var userToken by remember { mutableStateOf("") }
    val showDialog by settingViewModel.showDialog.collectAsState()
    val TAG = "SettingsTag"

    LaunchedEffect(Unit) {
        dataStore.getUserToken().collect { token ->
            if (!token.isNullOrEmpty()) {
                userToken = token
                settingViewModel.getSettingContent(token)
            }
        }
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
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                PageHeader(stringResource(R.string.settings), dataStore, false) {
                    backDispatcher?.onBackPressed()
                }
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row {
                        AsyncImage(
                            model = dataStore.getUserProfilePic()
                                .collectAsState(initial = "").value,
                            contentDescription = "User Profile Pic",
                            placeholder = painterResource(R.drawable.icon_app_place),
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .border(1.dp, colorResource(R.color.app_color), CircleShape)
                        )
                        Spacer(modifier = Modifier.size(16.dp))
                        Text(
                            text = "Shubham Gupta",
                            modifier = Modifier.align(Alignment.CenterVertically),
                            fontSize = 20.sp,
                            color = colorResource(R.color.white)
                        )
                    }
                    Spacer(modifier = Modifier.size(24.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Black)
                            .border(
                                width = 1.dp,
                                Color.White,
                                RoundedCornerShape(12.dp)
                            )
                    ) {
                        Column(
                            Modifier.padding(vertical = 24.dp, horizontal = 8.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(
                                    onClick = { /* TODO: Open notifications */ })
                                {
                                    Icon(
                                        painter = painterResource(id = R.drawable.icon_bell_notification),
                                        contentDescription = "Notifications",
                                        tint = Color.Unspecified,
                                    )
                                }
                                Text(
                                    text = stringResource(R.string.notifications),
                                    fontSize = 18.sp,
                                    color = Color.White,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(onClick = { /* TODO: Open notifications */ }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.icon_privacy_policy),
                                        contentDescription = "Notifications",
                                        tint = Color.Unspecified
                                    )
                                }
                                Text(
                                    text = stringResource(R.string.privacy_and_policy),
                                    fontSize = 18.sp,
                                    color = Color.White,
                                    modifier = Modifier.weight(1f)
                                )
                                IconButton(
                                    onClick = { /* TODO: Open notifications */ },
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.icon_arrow_go_to),
                                        contentDescription = "Notifications",
                                        tint = Color.White
                                    )
                                }
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(onClick = { /* TODO: Open notifications */ }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.icon_reset_password),
                                        contentDescription = "Notifications",
                                        tint = Color.Unspecified
                                    )
                                }
                                Text(
                                    text = stringResource(R.string.reset_password),
                                    fontSize = 18.sp,
                                    color = Color.White,
                                    modifier = Modifier.weight(1f)
                                )
                                IconButton(
                                    onClick = { /* TODO: Open notifications */ },
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.icon_arrow_go_to),
                                        contentDescription = "Notifications",
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                    }
                    Spacer(Modifier.size(20.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                Color.White,
                                RoundedCornerShape(12.dp)
                            )
                            .background(Color.Black)

                    ) {
                        Column(
                            Modifier.padding(vertical = 24.dp, horizontal = 8.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(
                                    onClick = { /* TODO: Open notifications */ })
                                {
                                    Icon(
                                        painter = painterResource(id = R.drawable.icon_term_and_condition_),
                                        contentDescription = "Notifications",
                                        tint = Color.Unspecified,
                                    )
                                }
                                Text(
                                    text = stringResource(R.string.terms_and_conditions),
                                    fontSize = 18.sp,
                                    color = Color.White,
                                    modifier = Modifier.weight(1f)
                                )
                                IconButton(
                                    onClick = { /* TODO: Open notifications */ },
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.icon_arrow_go_to),
                                        contentDescription = "Notifications",
                                        tint = Color.White
                                    )
                                }
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(onClick = { /* TODO: Open notifications */ }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.icon_delete_account),
                                        contentDescription = "Notifications",
                                        tint = Color.Unspecified
                                    )
                                }
                                Text(
                                    text = stringResource(R.string.delete_account),
                                    fontSize = 18.sp,
                                    color = Color.White,
                                    modifier = Modifier.weight(1f)
                                )
                                IconButton(
                                    onClick = { /* TODO: Open notifications */ },
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.icon_arrow_go_to),
                                        contentDescription = "Notifications",
                                        tint = Color.White
                                    )
                                }
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(onClick = { /* TODO: Open notifications */ }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.icon_about_us_info),
                                        contentDescription = "Notifications",
                                        tint = Color.Unspecified
                                    )
                                }
                                Text(
                                    text = stringResource(R.string.about_us),
                                    fontSize = 18.sp,
                                    color = Color.White,
                                    modifier = Modifier.weight(1f)
                                )
                                IconButton(
                                    onClick = { /* TODO: Open notifications */ },
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.icon_arrow_go_to),
                                        contentDescription = "Notifications",
                                        tint = Color.White
                                    )
                                }
                            }

                            OutlinedButton(
                                onClick = {
                                    showSheet = true
                                },
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .offset(y = 32.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = colorResource(id = R.color.app_color)
                                )
                            ) {
                                Text(
                                    text = stringResource(id = R.string.logout),
                                    color = colorResource(id = R.color.black),
                                    fontSize = 16.sp
                                )
                            }
                            Spacer(Modifier.size(32.dp))
                        }
                    }

                }
            }
            when (settingVMState) {
                is ApiState.Loading -> {
                    showLoading()
                }

                is ApiState.Success -> {
                    hideLoading()
                }

                is ApiState.Error -> {
                    hideLoading()
                    val errorMsg = (settingVMState as ApiState.Error).message
                    OkDialog(true, false, stringResource(R.string.app_name), errorMsg) {
                        settingViewModel.setShowDialog(false)
                    }
                }
            }
        }
    }
    context.GlobalProgressBar()

    when (logoutState) {
        is ApiState.Loading -> {
            showLoading()
        }

        is ApiState.Success -> {
            hideLoading()
            navController.navigate(AppConstant.KeyName.login_screen) {
                popUpTo(AppConstant.KeyName.settings) { inclusive = true }
            }
        }

        is ApiState.Error -> {
            hideLoading()
            OkDialog(
                showDialog, false, stringResource(R.string.app_name),
                (logoutState as ApiState.Error).message
            ) {
                settingViewModel.setShowDialog(false)
            }
        }
    }
    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(R.string.logout_q),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    stringResource(R.string.are_you_sure_to_logout),
                    fontSize = 16.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = {
                        Log.e(TAG, "Settings: $userToken")
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            showSheet = false
                        }
                        settingViewModel.userLogout(userToken, dataStore)
                    },
                    modifier = Modifier.fillMaxWidth(0.9f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.app_color)
                    )
                ) {
                    Text(
                        stringResource(R.string.logout),
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(Modifier.size(10.dp))
                OutlinedButton(
                    onClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion { showSheet = false }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .align(Alignment.CenterHorizontally)
                        .clip(RoundedCornerShape(24.dp))
                        .shadow(8.dp, shape = RoundedCornerShape(24.dp)),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = colorResource(id = R.color.black)
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.cancel),
                        color = colorResource(id = R.color.white),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun MyPreviewSettings() {
    Settings(rememberNavController())
}