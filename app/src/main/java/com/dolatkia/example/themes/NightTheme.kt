package com.dolatkia.example.themes

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.dolatkia.example.R

class NightTheme : MyAppTheme {

    override fun id(): Int {
        return 1
    }

    override fun activityBackgroundColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.background_night)
    }

    override fun activityActionbarColor(context: Context): Drawable {
        return ColorDrawable(ContextCompat.getColor(context, R.color.actionbar_night))
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