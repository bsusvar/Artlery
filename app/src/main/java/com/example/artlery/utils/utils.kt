package com.example.artlery.utils

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass


@Composable
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
fun getWindowSizeClass(activity: Activity): WindowWidthSizeClass {
    return calculateWindowSizeClass(activity).widthSizeClass
}