package com.dolatkia.example.themes

import android.content.Context
import android.graphics.Color
import com.dolatkia.changeThemeAnimation.AppTheme

class GrayTheme : MyAppTheme {
    override fun backgrondColor(): Int {
        return Color.GREEN
    }

    override fun id(): Int {
        return 2
    }

    override fun navigationBarColor(context: Context?): Int {
        return Color.RED
    }

    override fun statusBarColor(context: Context?): Int {
        return Color.RED
    }
}