package com.dolatkia.example.themes

import android.content.Context
import androidx.core.content.ContextCompat
import com.dolatkia.example.R

class NightTheme : MyAppTheme {

    companion object {
        val ThemeId = 1
    }

    override fun id(): Int {
        return ThemeId
    }

    override fun activityBackgroundColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.background_night)
    }

    override fun activityImageRes(context: Context): Int {
        return R.drawable.image_night
    }

    override fun activityIconColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.icon_night)
    }

    override fun activityTextColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.text_night)
    }

    override fun activityThemeButtonColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.button_night)
    }
}