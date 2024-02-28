package com.evilstan.mornhousetest.ui.navigation

interface Destination {
    val route: String
    val title: Int
    val showNavigation: Boolean
}