package com.jetpacktest.data

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jetpacktest.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

fun isValidEmail(input: String): Boolean {
    val emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
    return (input.matches(emailPattern))
}

fun isValidPassword(input: String): Boolean {
    val emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
    return input.isEmpty()
}

private val _isLoading = MutableStateFlow(false)
val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
fun showLoading() {
    _isLoading.value = true
}

fun hideLoading() {
    _isLoading.value = false
}


@Composable
fun Context.GlobalProgressBar() {
    val isLoading by isLoading.collectAsState()

    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.8f)), // Dimmed background
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color.White)
        }
    }
}

@Composable
fun OkDialog(
    showDialog: Boolean,
    cancelable: Boolean,
    dialogTitle: String,
    dialogMessage: String?,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { if (cancelable) onDismiss() },
            title = { Text(dialogTitle) },
            text = { Text(dialogMessage.toString()) },
            confirmButton = {

            },
            dismissButton = {
                Button(onClick = { onDismiss() }) {
                    Text("Ok")
                }
            }
        )
    }
}


@Composable
fun ConfirmDialog(
    dialogTitle: String,
    dialogMessage: String,
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text("Custom Dialog Title") },
            text = { Text("This is a custom message inside the alert dialog.") },
            confirmButton = {
                Button(onClick = { onConfirm() }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(onClick = { onDismiss() }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun Context.LoadListImage(imgUrl: String, cornerDp: Int) {
    AsyncImage(
        model = ImageRequest.Builder(this)
            .data(imgUrl)
            .error(R.drawable.icon_app_place) // Fallback image on error
            .build(),
        contentDescription = "",
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(cornerDp.dp))
            .alpha(.6f),
        contentScale = ContentScale.Crop,
        placeholder = painterResource(R.drawable.icon_app_place)
    )
}

@Composable
fun PageHeader(title: String, dataStore: DataStore, viewProfilePic: Boolean = false, onBackPress: ()-> Unit) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            //back btn
            IconButton(
                onClick = {
                    onBackPress()
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_back_ios),
                    contentDescription = "",
                    tint = colorResource(R.color.white)
                )
            }
            //profile picture
            if (viewProfilePic) {
                AsyncImage(
                    model = dataStore.getUserProfilePic().collectAsState(initial = "").value,
                    contentDescription = "User Profile Pic",
                    placeholder = painterResource(R.drawable.icon_app_place),
                    modifier = Modifier
                        .size(35.dp)
                        .clip(CircleShape)
                        .border(1.dp, colorResource(R.color.app_color), CircleShape)
                )
            }
            //toolbar title
            Text(
                text = title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.app_color),
                textAlign = TextAlign.Center
            )
            //wave icon
            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_music_wave),
                    contentDescription = "Notifications",
                    modifier =  Modifier.height(50.dp).width(56.dp),
                    tint = Color.Unspecified
                )
            }
        }

}

fun ContentDrawScope.drawNeonStroke(radius: Dp) {
    this.drawIntoCanvas {
        val paint =
            Paint().apply {
                style = PaintingStyle.Stroke
                strokeWidth = 10f
            }

        val frameworkPaint =
            paint.asFrameworkPaint()

        val color = Color(0xFFFFFFFF)

        this.drawIntoCanvas {
            frameworkPaint.color = color.copy(alpha = 0f).toArgb()
            frameworkPaint.setShadowLayer(
                radius.toPx(), 0f, 0f, color.copy(alpha = .5f).toArgb()
            )
            it.drawRoundRect(
                left = 0f,
                right = size.width,
                bottom = size.height,
                top = 0f,
                radiusY = radius.toPx(),
                radiusX = radius.toPx(),
                paint = paint
            )
           /* drawRoundRect(
                color = Color(0xFFFFFFFF),
                size = size,
                cornerRadius = CornerRadius(radius.toPx(), radius.toPx()),
                style = Stroke(width = 1.dp.toPx())
            )*/
        }
    }
}
