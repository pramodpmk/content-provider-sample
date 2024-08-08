package com.hifx.syncadapterexample.ui.presentation.data

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun OwnDatabaseScreen(
    navHostController: NavHostController,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            item {
                Text(
                    text = "Own Database",
                    modifier = Modifier.clickable {
                    }.padding(50.dp),
                )
            }
        }
    }
}
