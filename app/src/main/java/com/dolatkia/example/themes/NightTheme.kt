package com.dolatkia.example.themes

import android.content.Context
import android.graphics.Color
import com.dolatkia.changeThemeAnimation.AppTheme

class NightTheme : MyAppTheme {
    override fun backgrondColor(): Int {
        return Color.RED
    }

    override fun id(): Int {
        return 1
    }

    override fun navigationBarColor(context: Context?): Int {
        return Color.GREEN
    }

    override fun statusBarColor(context: Context?): Int {
        return Color.GREEN
    }
}