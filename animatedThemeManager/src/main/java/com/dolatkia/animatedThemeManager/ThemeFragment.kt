package com.dolatkia.animatedThemeManager

import androidx.fragment.app.Fragment

abstract class ThemeFragment : Fragment() {

    override fun onResume() {
        getThemeManager()?.getCurrentLiveTheme()?.observe(this) {
            syncTheme(it)
        }

        super.onResume()
    }

    protected fun getThemeManager() : ThemeManager? {
        return ThemeManager.instance
    }

    // to sync ui with selected theme
    abstract fun syncTheme(appTheme: AppTheme)
}