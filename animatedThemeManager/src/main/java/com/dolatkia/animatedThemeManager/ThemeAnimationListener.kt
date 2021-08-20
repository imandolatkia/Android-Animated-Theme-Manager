package com.dolatkia.animatedThemeManager

import android.animation.Animator

interface ThemeAnimationListener {
    fun onAnimationStart(animation: Animator)
    fun onAnimationEnd(animation: Animator)
    fun onAnimationCancel(animation: Animator)
    fun onAnimationRepeat(animation: Animator)
}