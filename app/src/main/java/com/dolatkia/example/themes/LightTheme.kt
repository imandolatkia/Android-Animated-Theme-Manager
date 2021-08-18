package com.dolatkia.example.themes

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.dolatkia.example.R

class LightTheme : MyAppTheme {

    override fun id(): Int {
        return 0
    }

    override fun navigationBarColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.background_light)
    }

    override fun statusBarColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.background_light)
    }

    override fun activityBackgroundColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.background_light)
    }

    override fun activityActionbarColor(context: Context): Drawable {
        return ColorDrawable(ContextCompat.getColor(context, R.color.actionbar_light))
    }

    override fun activityImageRes(context: Context): Int {
        return R.drawable.image_light
    }

    override fun activityIconColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.icon_light)
    }

    override fun activityTextColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.text_light)
    }

    override fun activityThemeButtonColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.button_light)
    }
}