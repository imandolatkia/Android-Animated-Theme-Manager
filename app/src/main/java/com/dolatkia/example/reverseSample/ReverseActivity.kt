package com.dolatkia.example.reverseSample

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import com.dolatkia.animatedThemeManager.AppTheme
import com.dolatkia.animatedThemeManager.ThemeActivity
import com.dolatkia.animatedThemeManager.ThemeManager
import com.dolatkia.example.databinding.ActivityReverseBinding
import com.dolatkia.example.themes.LightTheme
import com.dolatkia.example.themes.MyAppTheme
import com.dolatkia.example.themes.NightTheme


class ReverseActivity : ThemeActivity() {

    private lateinit var binder: ActivityReverseBinding
    private var isNight = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // full screen app
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        // create and bind views
        binder = ActivityReverseBinding.inflate(LayoutInflater.from(this))
        setContentView(binder.root)

        // set change theme click listeners for buttons
        updateButtonText()
        binder.button.setOnClickListener {
            isNight = if(isNight){
                ThemeManager.instance.reverseChangeTheme(LightTheme(), it)
                false
            }else{
                ThemeManager.instance.changeTheme(NightTheme(), it)
                true
            }
            updateButtonText()
        }
    }

    // to sync ui with selected theme
    override fun syncTheme(appTheme: AppTheme) {
        // change ui colors with new appThem here

        val myAppTheme = appTheme as MyAppTheme

        // set background color
        binder.root.setBackgroundColor(myAppTheme.activityBackgroundColor(this))

        //set top image
        binder.image.setImageResource(myAppTheme.activityImageRes(this))

        // set icons color
        binder.share.setColorFilter(myAppTheme.activityIconColor(this))
        binder.gift.setColorFilter(myAppTheme.activityIconColor(this))

        //set text color
        binder.text.setTextColor(myAppTheme.activityTextColor(this))

        //set card view colors
        binder.button.setCardBackgroundColor(appTheme.activityThemeButtonColor(this))

        //syncStatusBarIconColors
        syncStatusBarIconColors(appTheme)
    }

    fun updateButtonText() {
        if (isNight) {
            binder.buttonTextView.text = "Light"
        } else {
            binder.buttonTextView.text = "Night"
        }
    }

    // to get stat theme
    override fun getStartTheme(): AppTheme {
        return LightTheme()
    }

    private fun syncStatusBarIconColors(theme: MyAppTheme) {
        ThemeManager.instance.syncStatusBarIconsColorWithBackground(
            this,
            theme.activityBackgroundColor(this)
        )
        ThemeManager.instance.syncNavigationBarButtonsColorWithBackground(
            this,
            theme.activityBackgroundColor(this)
        )
    }
}