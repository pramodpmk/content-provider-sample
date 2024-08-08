package com.hifx.syncadapterexample.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hifx.syncadapterexample.ui.presentation.contact.ContactScreen
import com.hifx.syncadapterexample.ui.components.HomeScreen
import com.hifx.syncadapterexample.ui.presentation.contact.AddContactScreen
import com.hifx.syncadapterexample.ui.presentation.image.ImageScreen
import com.hifx.syncadapterexample.ui.presentation.data.OwnDatabaseScreen
import com.hifx.syncadapterexample.ui.presentation.notes.NoteScreen
import com.hifx.syncadapterexample.ui.presentation.sync.SyncingDataScreen

@Composable
fun SetupNavGraph(
    navHostController: NavHostController,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Home.route,
    ) {
        composable(
            route = Screen.Home.route,
        ) {
            HomeScreen(navHostController = navHostController)
        }
        composable(
            route = Screen.Contact.route,
        ) {
            ContactScreen(navHostController = navHostController)
        }
        composable(
            route = Screen.AddContact.route,
        ) {
            AddContactScreen(navHostController = navHostController)
        }
        composable(
            route = Screen.Photo.route,
        ) {
            ImageScreen(navHostController = navHostController)
        }
        composable(
            route = Screen.OwnDb.route,
        ) {
            OwnDatabaseScreen(navHostController = navHostController)
        }
        composable(
            route = Screen.SyncingDbAdapter.route,
        ) {
            NoteScreen(navHostController = navHostController)
        }
    }
}
