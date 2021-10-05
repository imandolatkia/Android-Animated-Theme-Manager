package com.dolatkia.animatedThemeManager

import androidx.fragment.app.Fragment

abstract class ThemeFragment : Fragment() {

    private val fragmentThemeManager: ThemeManager? by lazy {

        if(requireActivity() is ThemeActivity) {
            (requireActivity() as ThemeActivity).getThemeManager()
        } else {
            null
        }
    }

    override fun onResume() {
        fragmentThemeManager?.getCurrentLiveTheme()?.observe(this) {
            syncTheme(it)
        }

        super.onResume()
    }

    protected fun getThemeManager() : ThemeManager? {
        return fragmentThemeManager
    }

    // to sync ui with selected theme
    abstract fun syncTheme(appTheme: AppTheme)
}