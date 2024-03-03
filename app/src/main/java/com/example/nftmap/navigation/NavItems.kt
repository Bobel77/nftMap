package com.example.nftmap.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

val listOfNavItems = listOf(
    NavItem(
        label = "Map",
        icon = Icons.Default.Home,
        route = Screens.MapScreen.name
    ),
    NavItem(
        label = "Nfts",
        icon = Icons.Default.Home,
        route = Screens.MyNfts.name
    ),
            NavItem(
            label = "QrScanner",
    icon = Icons.Default.Build,
    route = Screens.QrScanner.name
)
)
