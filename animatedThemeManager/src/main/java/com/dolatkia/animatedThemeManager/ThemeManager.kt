package com.dolatkia.animatedThemeManager

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.lifecycle.MutableLiveData

class ThemeManager {

    private var liveTheme: MutableLiveData<AppTheme> = MutableLiveData()
    private lateinit var activity: ThemeActivity

    companion object {
        val instance = ThemeManager()
    }

    fun init(activity: ThemeActivity, defaultTheme: AppTheme) {
        if (liveTheme.value == null) {
            this.liveTheme.value = defaultTheme
        }
        this.activity = activity
    }

    fun setActivity(activity: ThemeActivity) {
        this.activity = activity
    }

    fun getCurrentTheme(): AppTheme? {
        return getCurrentLiveTheme().value
    }

    fun getCurrentLiveTheme(): MutableLiveData<AppTheme> {
        return liveTheme
    }

    fun reverseChangeTheme(newTheme: AppTheme, view: View, duration: Long = 600) {
        changeTheme(newTheme, getViewCoordinates(view), duration, true)
    }

    fun reverseChangeTheme(newTheme: AppTheme, sourceCoordinate: Coordinate, duration: Long = 600) {
        changeTheme(newTheme, sourceCoordinate, duration, true)

    }

    fun changeTheme(newTheme: AppTheme, view: View, duration: Long = 600) {
        changeTheme(newTheme, getViewCoordinates(view), duration)
    }

    fun changeTheme(
        newTheme: AppTheme,
        sourceCoordinate: Coordinate,
        duration: Long = 600,
        isRevers: Boolean = false
    ) {

        if (getCurrentTheme()?.id() == newTheme.id() || activity.isRunningChangeThemeAnimation()) {
            return
        }

        //start animation
        activity.changeTheme(newTheme, sourceCoordinate, duration, isRevers)

        //set LiveData
        getCurrentLiveTheme().value = newTheme
    }

    fun setStatusBarBackgroundColor(activity: Activity, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val window = activity.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            syncStatusBarIconsColorWithBackground(activity, color)
        }
    }

    fun syncStatusBarIconsColorWithBackground(activity: Activity, statusBarBackgroundColor: Int) {
        val window = activity.window
        val decorView = window.decorView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.setSystemBarsAppearance(
                if (isColorLight(statusBarBackgroundColor)) WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS else 0,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var flags = decorView.systemUiVisibility
            if (isColorLight(statusBarBackgroundColor)) {
                if (flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR == 0) {
                    flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    decorView.systemUiVisibility = flags
                }
            } else {
                if (flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR != 0) {
                    flags = flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                    decorView.systemUiVisibility = flags
                }
            }
        }
    }

    fun setNavigationBarBackgroundColor(activity: Activity, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.navigationBarColor = color
            syncNavigationBarButtonsColorWithBackground(activity, color)
        }
    }

    fun syncNavigationBarButtonsColorWithBackground(
        activity: Activity,
        navigationbarBackgroundClor: Int
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.window.insetsController?.setSystemBarsAppearance(
                if (isColorLight(navigationbarBackgroundClor)) WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS else 0,
                WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val decorView = activity.window.decorView
            var flags = decorView.systemUiVisibility
            flags = if (isColorLight(navigationbarBackgroundClor)) {
                flags or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            } else {
                flags and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()
            }
            decorView.systemUiVisibility = flags
        }
    }

    private fun isColorLight(color: Int): Boolean {
        val lightness =
            0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)
        return lightness > 127
    }

    private fun getViewCoordinates(view: View): Coordinate {
        return Coordinate(
            getRelativeLeft(view) + view.width / 2,
            getRelativeTop(view) + view.height / 2
        )
    }

    private fun getRelativeLeft(myView: View): Int {
        return if ((myView.parent as View).id == ThemeActivity.ROOT_ID) myView.left else myView.left + getRelativeLeft(
            myView.parent as View
        )
    }

    private fun getRelativeTop(myView: View): Int {
        return if ((myView.parent as View).id == ThemeActivity.ROOT_ID) myView.top else myView.top + getRelativeTop(
            myView.parent as View
        )
    }
}