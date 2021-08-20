package com.dolatkia.example.themes

import android.content.Context
import com.dolatkia.animatedThemeManager.AppTheme

interface MyAppTheme : AppTheme {
    fun activityBackgroundColor(context: Context): Int
    fun activityImageRes(context: Context): Int
    fun activityIconColor(context: Context): Int
    fun activityTextColor(context: Context): Int
    fun activityThemeButtonColor(context: Context): Int
}