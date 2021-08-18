package com.dolatkia.changeThemeAnimation

import android.animation.Animator
import android.view.View

interface ThemeAnimationListener {
    fun onAnimationStart(animation: Animator)
    fun onAnimationEnd(animation: Animator)
    fun onAnimationCancel(animation: Animator)
    fun onAnimationRepeat(animation: Animator)
}