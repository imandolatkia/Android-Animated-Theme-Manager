package com.dolatkia.example.themes

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.dolatkia.example.R

class PinkTheme : MyAppTheme {

    override fun id(): Int {
        return 2
    }

    override fun activityBackgroundColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.background_pink)
    }

    override fun activityActionbarColor(context: Context): Drawable {
        return ColorDrawable(ContextCompat.getColor(context, R.color.actionbar_pink))
    }

    override fun activityImageRes(context: Context): Int {
        return R.drawable.image_pink
    }

    override fun activityIconColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.icon_pink)
    }

    override fun activityTextColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.text_pink)
    }

    override fun activityThemeButtonColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.button_pink)
    }
}