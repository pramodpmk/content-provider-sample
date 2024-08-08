package com.hifx.syncadapterexample.ui.components

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
import com.hifx.syncadapterexample.ui.navigation.Screen

@Composable
fun HomeScreen(
    navHostController: NavHostController,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            item {
                Text(
                    text = "Image",
                    modifier = Modifier.clickable {
                        navHostController.navigate(Screen.Photo.route)
                    }.padding(50.dp),
                )
            }
            item {
                Text(
                    text = "Contact",
                    modifier = Modifier.clickable {
                        navHostController.navigate(Screen.Contact.route)
                    }.padding(50.dp),
                )
            }
            item {
                Text(
                    text = "Own Content",
                    modifier = Modifier.clickable {
                        navHostController.navigate(Screen.OwnDb.route)
                    }.padding(50.dp),
                )
            }
            item {
                Text(
                    text = "Sync Adapter",
                    modifier = Modifier.clickable {
                        navHostController.navigate(Screen.SyncingDbAdapter.route)
                    }.padding(50.dp),
                )
            }
        }
    }
}
