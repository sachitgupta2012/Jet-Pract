package com.jetpacktest.presentation.ui

import android.util.Log
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.gson.Gson
import com.jetpacktest.R
import com.jetpacktest.data.ApiState
import com.jetpacktest.data.AppConstant
import com.jetpacktest.data.DataStore
import com.jetpacktest.data.GlobalProgressBar
import com.jetpacktest.data.LoadListImage
import com.jetpacktest.data.Models
import com.jetpacktest.data.OkDialog
import com.jetpacktest.data.hideLoading
import com.jetpacktest.data.showLoading
import com.jetpacktest.viewmodel.VMDashboard
import java.net.URLEncoder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard(navController: NavHostController) {

    val TAG = "DashboardTag"
    val context = LocalContext.current
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    val dashboardViewModel: VMDashboard = hiltViewModel()
    val dashboardState by dashboardViewModel.itemsState.collectAsState()
    val dataStore = DataStore(context)
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val showDialog by dashboardViewModel.showDialog.collectAsState()

    LaunchedEffect(Unit) {
        dataStore.getUserToken().collect { token ->
            if (!token.isNullOrEmpty()) {
                dashboardViewModel.getChannelList(token)
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
            topBar = {
                TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Profile Image
                            AsyncImage(
                                model = dataStore.getUserProfilePic()
                                    .collectAsState(initial = "").value,
                                contentDescription = "User Profile Pic",
                                placeholder = painterResource(R.drawable.icon_app_place),
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape)
                                    .border(1.dp, colorResource(R.color.app_color), CircleShape)
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            // Welcome Text
                            Column() {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Welcome!",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Icon(
                                        painter = painterResource(id = R.drawable.icon_edit), // Edit icon
                                        contentDescription = "Edit",
                                        modifier = Modifier.size(18.dp),
                                        tint = Color.White
                                    )
                                }

                                Text(
                                    text = dataStore.getUserName()
                                        .collectAsState(initial = "").value.toString(),
                                    fontSize = 14.sp,
                                    color = Color.LightGray
                                )
                            }


                            // Edit Icon

                        }
                    },
                    actions = {
                        // Settings Icon
                        IconButton(
                            onClick = {
                                navController.navigate(AppConstant.KeyName.settings)
                            },
                            modifier = Modifier.testTag("SettingsIcon")
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_settings),
                                contentDescription = "Settings",
                                tint = Color.White
                            )
                        }

                        // Notification Icon
                        IconButton(onClick = { }) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_notification),
                                contentDescription = "Notifications",
                                tint = Color.White
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
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(R.string.listen_to_your_favourite_channel),
                    modifier = Modifier.offset(x = 16.dp),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.white)
                )
                Spacer(modifier = Modifier.height(20.dp))
                DockedSearchBar(
                    query = query,
                    onQueryChange = { query = it },
                    onSearch = { /* Handle search action */ },
                    active = false, // Keep it non-expandable
                    onActiveChange = { },
                    placeholder = { Text("Channels") },
                    colors = SearchBarDefaults.colors(
                        containerColor = colorResource(R.color.white)
                    ),
                    leadingIcon = {
                        Icon(
                            painterResource(R.drawable.icon_search),
                            contentDescription = "Search Icon"
                        )
                    },
                    trailingIcon = {
                        if (query.isNotEmpty()) {
                            IconButton(onClick = { query = "" }) {
                                Icon(Icons.Default.Close, contentDescription = "Clear Search")
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .align(Alignment.CenterHorizontally)
                ) {

                }
                Spacer(modifier = Modifier.height(20.dp))
                when (dashboardState) {
                    is ApiState.Loading -> {
                        showLoading()
                    }

                    is ApiState.Success -> {
                        Log.e(TAG, "Dashboard: Success")
                        hideLoading()
                        val channelListResponse =
                            (dashboardState as ApiState.Success<Models.ChannelListResponse>).data
                        ChannelListView(channelListResponse, navController)
                    }

                    is ApiState.Error -> {
                        Log.e(TAG, "Dashboard: Error")
                        hideLoading()
                        if (showDialog) {
                            OkDialog(
                                showDialog,
                                false,
                                stringResource(R.string.app_name),
                                (dashboardState as ApiState.Error).message
                            ) {
                                dashboardViewModel.setShowDialog(false)
                            }
                        }
                    }
                }
            }
        }
    }
    context.GlobalProgressBar()
}

@Composable
fun ChannelListView(channelList: Models.ChannelListResponse, navController: NavHostController) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(channelList.ChannelDetails) { channel ->
            ChannelItem(channel, navController)
        }
    }

}

@Composable
fun ChannelItem(channelDetails: Models.ChannelDetails, navHostController: NavHostController) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .height(100.dp)
                .clickable {
                    val jsonChannel = URLEncoder.encode(Gson().toJson(channelDetails), "UTF-8")
                    navHostController.navigate("${AppConstant.KeyName.channelDetails}/${jsonChannel}")
                }
        ) {
            context.LoadListImage(channelDetails.BannerUrl, 8)
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = channelDetails.ChannelName ?: "No Name",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W600
                )
            }
        }
    }
}