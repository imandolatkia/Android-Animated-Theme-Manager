package com.dolatkia.example

import android.animation.Animator
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import com.dolatkia.changeThemeAnimation.AppTheme
import com.dolatkia.changeThemeAnimation.ThemeActivity
import com.dolatkia.changeThemeAnimation.ThemeAnimationListener
import com.dolatkia.changeThemeAnimation.ThemeManager
import com.dolatkia.example.databinding.ActivityMainBinding
import com.dolatkia.example.themes.LightTheme
import com.dolatkia.example.themes.MyAppTheme
import com.dolatkia.example.themes.NightTheme
import com.dolatkia.example.themes.PinkTheme


class MainActivity : ThemeActivity() {

    lateinit var binder: ActivityMainBinding

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
        binder = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binder.root)

        // set change theme click listener
        binder.lightButton.setOnClickListener {
            ThemeManager.instance.changeTheme(LightTheme(), it)
        }

        binder.nightButton.setOnClickListener {
            ThemeManager.instance.changeTheme(NightTheme(), it)
        }

        binder.pinkButton.setOnClickListener {
            ThemeManager.instance.changeTheme(PinkTheme(), it)
        }
    }

    override fun syncTheme(appTheme: AppTheme) {
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
        binder.lightButton.setCardBackgroundColor(appTheme.activityThemeButtonColor(this))
        binder.nightButton.setCardBackgroundColor(appTheme.activityThemeButtonColor(this))
        binder.pinkButton.setCardBackgroundColor(appTheme.activityThemeButtonColor(this))

        //syncStatusBarIconColors
        syncStatusBarIconColors(appTheme)
    }

    override fun getStartTheme(): AppTheme {
        return LightTheme()
    }

    fun syncStatusBarIconColors(theme: MyAppTheme) {
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