package com.dolatkia.example.themes

import android.content.Context
import android.graphics.drawable.Drawable
import com.dolatkia.changeThemeAnimation.AppTheme

interface MyAppTheme : AppTheme {
    fun activityBackgroundColor(context: Context): Int
    fun activityActionbarColor(context: Context): Drawable
    fun activityImageRes(context: Context): Int
    fun activityIconColor(context: Context): Int
    fun activityTextColor(context: Context): Int
    fun activityThemeButtonColor(context: Context): Int
}