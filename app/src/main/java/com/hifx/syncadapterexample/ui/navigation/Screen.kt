package com.hifx.syncadapterexample.ui.navigation

const val HOME_ROUTE = "home_page"
const val PHOTO_ROUTE = "photo_page"
const val CONTACT_ROUTE = "contact_page"
const val ADD_CONTACT_ROUTE = "add_contact_page"
const val OWN_ROUTE = "own_page"
const val SYNC_ADAPTER_ROUTE = "sync_adapter_page"

sealed class Screen(val route: String) {
    object Home : Screen(route = HOME_ROUTE)
    object Photo : Screen(route = PHOTO_ROUTE)
    object Contact : Screen(route = CONTACT_ROUTE)
    object AddContact : Screen(route = ADD_CONTACT_ROUTE)
    object OwnDb : Screen(route = OWN_ROUTE)
    object SyncingDbAdapter : Screen(route = SYNC_ADAPTER_ROUTE)
}
