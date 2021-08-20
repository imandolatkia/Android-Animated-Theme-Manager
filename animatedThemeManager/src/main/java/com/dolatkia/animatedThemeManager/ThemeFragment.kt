package com.dolatkia.animatedThemeManager

import androidx.fragment.app.Fragment

class ThemeFragment : Fragment() {

    override fun onResume() {
        ThemeManager.instance.getCurrentTheme()?.let { (activity as ThemeActivity).syncTheme(it) }
        super.onResume()
    }
}