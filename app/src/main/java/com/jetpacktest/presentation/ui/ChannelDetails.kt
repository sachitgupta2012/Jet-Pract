package com.jetpacktest.presentation.ui

import android.content.Intent
import android.health.connect.datatypes.units.Length
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.jetpacktest.R
import com.jetpacktest.data.DataStore
import com.jetpacktest.data.Models
import com.jetpacktest.data.OkDialog
import com.jetpacktest.data.drawNeonStroke
import com.jetpacktest.viewmodel.VMChannelDetails

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChannelDetails(navController: NavHostController, channelDetails: Models.ChannelDetails) {

    val TAG = "ChannelDetailsTag"
    val context = LocalContext.current
    val dataStore = DataStore(context)
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(channelDetails.Link ?: ""))
            prepare()
        }
    }
    var isPlaying by remember { mutableStateOf(false) }
    val cViewModel: VMChannelDetails = hiltViewModel()
    val showDialog by cViewModel.showDialog.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.img_screen_bg),
            contentDescription = "Screen Bg",
            contentScale = ContentScale.FillBounds
        )
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = dataStore.getUserName().collectAsState(initial = "").value ?: "",
                            maxLines = 1,
                        )
                    },
                    navigationIcon = {
                        Row {
                            IconButton(
                                onClick = {
                                    navController.navigateUp()
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.icon_back_ios),
                                    contentDescription = "",
                                    tint = colorResource(R.color.white)
                                )
                            }
                            AsyncImage(
                                model = dataStore.getUserProfilePic()
                                    .collectAsState(initial = "").value,
                                contentDescription = "User Profile Pic",
                                placeholder = painterResource(R.drawable.icon_app_place),
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape)
                                    .border(
                                        width = 1.dp,
                                        colorResource(R.color.app_color),
                                        CircleShape
                                    )
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            },
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                AsyncImage(
                    model = channelDetails.LogoUrl,
                    contentDescription = "User Profile Pic",
                    modifier = Modifier
                        .height(180.dp)
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(24.dp))
                Box(
                    modifier = Modifier
                        .height(240.dp)
                        .width(240.dp)
                        .clip(CircleShape)
                        .align(Alignment.CenterHorizontally)
                        .background(colorResource(R.color.white))
                        .border(
                            width = 2.dp,
                            color = colorResource(R.color.app_color),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = if (isPlaying) painterResource(R.drawable.icon_pause) else painterResource(
                            R.drawable.icon_play
                        ),
                        contentDescription = "Play Icon",
                        alignment = Alignment.Center,
                        modifier = Modifier
                            .clickable {
                                if (isPlaying) {
                                    exoPlayer.pause()
                                } else {
                                    exoPlayer.play()
                                }
                                isPlaying = !isPlaying
                            }
                    )
                }
                Spacer(modifier = Modifier.height(40.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column {
                        Button(
                            onClick = {

                            },
                            modifier = Modifier
                                .drawWithContent {
                                    drawContent()
                                    drawNeonStroke(12.dp)
                                },
                            border = BorderStroke(
                                width = 0.1.dp,
                                color = colorResource(R.color.white)
                            ),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(R.color.app_color)
                            ),
                        ) {
                            Text(
                                text = "Request For Song",
                                fontSize = 13.sp
                            )
                        }
                    }
                    Column {
                        Button(
                            onClick = {

                            },
                            modifier = Modifier
                                .drawWithContent {
                                    drawContent()
                                    drawNeonStroke(12.dp)
                                },
                            border = BorderStroke(
                                width = 0.1.dp,
                                color = colorResource(R.color.white)
                            ),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(R.color.app_color)
                            ),

                            ) {
                            Text(
                                text = "Requested Song List",
                                fontSize = 13.sp
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Bottom,
                ) {

                    IconButton(
                        onClick = {
                            if (channelDetails.FacebookLink?.isNotEmpty() == true) {
                                val intent =
                                    Intent(Intent.ACTION_VIEW, channelDetails.FacebookLink?.toUri())
                                context.startActivity(intent)
                            } else {
                                Toast.makeText(context, "Url Not Found !", Toast.LENGTH_SHORT).show()

                            }
                        }
                    ) {
                        Image(
                            painterResource(R.drawable.icon_facebook),
                            contentDescription = "Facebook"
                        )
                    }
                    IconButton(
                        onClick = {
                            if (channelDetails.InstagramLink?.isNotEmpty() == true) {
                                val intent =
                                    Intent(Intent.ACTION_VIEW, channelDetails.InstagramLink?.toUri())
                                context.startActivity(intent)
                            } else {
                                Toast.makeText(context, "Url Not Found !", Toast.LENGTH_SHORT).show()

                            }
                        }
                    ) {
                        Image(
                            painterResource(R.drawable.icon_instagram),
                            contentDescription = "Instagram"
                        )
                    }
                    IconButton(
                        onClick = {
                            if (channelDetails.YoutubeLink?.isNotEmpty() == true) {
                                val intent =
                                    Intent(Intent.ACTION_VIEW, channelDetails.YoutubeLink?.toUri())
                                context.startActivity(intent)
                            } else {
                                Toast.makeText(context, "Url Not Found !", Toast.LENGTH_SHORT).show()

                            }
                        }
                    ) {
                        Image(
                            painterResource(R.drawable.icon_youtube),
                            contentDescription = "YouTube"
                        )
                    }
                    IconButton(
                        onClick = {
                            if (channelDetails.TwitterLink?.isNotEmpty() == true) {
                                val intent =
                                    Intent(Intent.ACTION_VIEW, channelDetails.TwitterLink?.toUri())
                                context.startActivity(intent)
                            } else {
                                Toast.makeText(context, "Url Not Found !", Toast.LENGTH_SHORT).show()

                            }
                        }
                    ) {
                        Image(
                            painter = painterResource(R.drawable.icon_x),
                            contentDescription = "X"
                        )
                    }
                    IconButton(
                        onClick = {
                            if (channelDetails.SnapchatLink?.isNotEmpty() == true) {
                                val intent =
                                    Intent(Intent.ACTION_VIEW, channelDetails.SnapchatLink?.toUri())
                                context.startActivity(intent)
                            } else {
                                Toast.makeText(context, "Url Not Found !", Toast.LENGTH_SHORT).show()

                            }
                        }
                    ) {
                        Image(
                            painterResource(R.drawable.icon_snapchat),
                            contentDescription = "Snapchat"
                        )
                    }


                }
            }
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }
}