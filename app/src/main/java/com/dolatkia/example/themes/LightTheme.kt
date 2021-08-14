package com.dolatkia.example.themes

import android.content.Context
import android.graphics.Color
import com.dolatkia.changeThemeAnimation.AppTheme

class LightTheme : MyAppTheme {
    override fun backgrondColor(): Int {
        return Color.BLUE
    }

    override fun id(): Int {
        return 0
    }

    override fun navigationBarColor(context: Context?): Int {
        return Color.BLUE
    }

    override fun statusBarColor(context: Context?): Int {
        return Color.BLUE
    }
}