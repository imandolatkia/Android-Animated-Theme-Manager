package com.dolatkia.animatedThemeManager

import android.os.Bundle
import androidx.fragment.app.Fragment

abstract class ThemeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        ThemeManager.instance.getCurrentLiveTheme().observe(this) {
            syncTheme(it)
        }
        super.onResume()
    }

    // to sync ui with selected theme
    abstract fun syncTheme(appTheme: AppTheme)

}