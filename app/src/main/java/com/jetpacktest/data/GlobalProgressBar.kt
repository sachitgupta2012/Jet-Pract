package com.jetpacktest.data

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment

@Composable
fun GlobalProgressBar() {
    val isLoading by LoaderUtils.isLoading.collectAsState()

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(64.dp)
            )
        }
    }
}