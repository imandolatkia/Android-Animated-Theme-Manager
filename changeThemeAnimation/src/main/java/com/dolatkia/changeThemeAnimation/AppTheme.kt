package com.dolatkia.changeThemeAnimation

import android.content.Context

interface AppTheme {
    fun id(): Int
    fun navigationBarColor(context: Context?): Int
    fun statusBarColor(context: Context?): Int
}