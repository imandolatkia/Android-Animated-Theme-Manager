package com.dolatkia.example

import android.os.Bundle
import android.view.LayoutInflater
import com.dolatkia.changeThemeAnimation.AppTheme
import com.dolatkia.changeThemeAnimation.ThemeActivity
import com.dolatkia.changeThemeAnimation.ThemeManager
import com.dolatkia.example.databinding.ActivityMainBinding
import com.dolatkia.example.themes.PinkTheme
import com.dolatkia.example.themes.LightTheme
import com.dolatkia.example.themes.MyAppTheme
import com.dolatkia.example.themes.NightTheme


class MainActivity : ThemeActivity() {

    lateinit var binder: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
    }

    override fun getStartTheme(): AppTheme {
        return LightTheme()
    }
}