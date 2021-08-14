package com.dolatkia.example

import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import com.dolatkia.changeThemeAnimation.AppTheme
import com.dolatkia.changeThemeAnimation.ChangeThemeActivity
import com.dolatkia.changeThemeAnimation.ThemeManager
import com.dolatkia.example.databinding.ActivityMainBinding
import com.dolatkia.example.themes.GrayTheme
import com.dolatkia.example.themes.LightTheme
import com.dolatkia.example.themes.MyAppTheme
import com.dolatkia.example.themes.NightTheme

class MainActivity : ChangeThemeActivity() {

    lateinit var binder: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binder.root)

        binder.lightButton.setOnClickListener {
            ThemeManager.instance.changeTheme(LightTheme(), it)
        }

        binder.nightButton.setOnClickListener {
            ThemeManager.instance.changeTheme(NightTheme(), it, 800)
        }

        binder.grayButton.setOnClickListener {
            ThemeManager.instance.changeTheme(GrayTheme(), it)
        }
    }

    override fun syncTheme(appTheme: AppTheme) {
        val myAppTheme = appTheme as MyAppTheme
        binder.background.setBackgroundColor(myAppTheme.backgrondColor())
    }

    override fun getStartTheme(): AppTheme {
        return LightTheme()
    }
}