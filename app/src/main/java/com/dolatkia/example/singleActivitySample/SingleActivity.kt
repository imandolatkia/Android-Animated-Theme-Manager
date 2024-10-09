package com.dolatkia.example.singleActivitySample

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope
import com.dolatkia.animatedThemeManager.AppTheme
import com.dolatkia.animatedThemeManager.ThemeActivity
import com.dolatkia.animatedThemeManager.ThemeManager
import com.dolatkia.example.databinding.ActivitySingleBinding
import com.dolatkia.example.themes.LightTheme
import com.dolatkia.example.themes.MyAppTheme
import com.dolatkia.example.themes.NightTheme
import com.dolatkia.example.themes.PinkTheme
import kotlinx.coroutines.flow.filterNotNull


class SingleActivity : ThemeActivity() {

    private lateinit var binder: ActivitySingleBinding
    private var number: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //get number
        number = intent.getIntExtra("number", 1)

        // full screen app
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        // create and bind views
        binder = ActivitySingleBinding.inflate(LayoutInflater.from(this))
        setContentView(binder.root)

        // set activity number text
        binder.ActivityNumber.text = number.toString()

        // set change theme click listeners for buttons
        binder.lightButton.setOnClickListener {
            themeManager.changeTheme(LightTheme(), it)
        }
        binder.nightButton.setOnClickListener {
            themeManager.changeTheme(NightTheme(), it)
        }
        binder.pinkButton.setOnClickListener {
            themeManager.changeTheme(PinkTheme(), it)
        }
        binder.nextActivityBtn.setOnClickListener {
            val myIntent = Intent(this, SingleActivity::class.java)
            myIntent.putExtra("number", number + 1)
            this.startActivity(myIntent)
        }
        lifecycleScope.launchWhenStarted {
            themeManager.theme.filterNotNull().collect {
                Log.i("Changed Theme", "Using theme with id: ${it.id()}")
            }
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
        binder.lightButton.setCardBackgroundColor(appTheme.activityThemeButtonColor(this))
        binder.nightButton.setCardBackgroundColor(appTheme.activityThemeButtonColor(this))
        binder.pinkButton.setCardBackgroundColor(appTheme.activityThemeButtonColor(this))
        binder.nextActivityBtn.setCardBackgroundColor(appTheme.activityThemeButtonColor(this))

        //syncStatusBarIconColors
        syncStatusBarIconColors(appTheme)
    }

    // to get stat theme
    override fun getStartTheme(): AppTheme {
        return LightTheme()
    }

    private fun syncStatusBarIconColors(theme: MyAppTheme) {
        themeManager.syncStatusBarIconsColorWithBackground(
            this,
            theme.activityBackgroundColor(this)
        )
        themeManager.syncNavigationBarButtonsColorWithBackground(
            this,
            theme.activityBackgroundColor(this)
        )
    }
}