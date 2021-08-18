package com.dolatkia.changeThemeAnimation

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.MutableLiveData

class ThemeManager {

    private var liveTheme: MutableLiveData<AppTheme> = MutableLiveData()
    private lateinit var activity: ThemeActivity

    companion object {
        val instance = ThemeManager()
    }


    fun init(activity: ThemeActivity, defaultTheme: AppTheme) {
        this.activity = activity
        liveTheme.value = defaultTheme
    }

    fun getCurrentTheme(): AppTheme? {
        return getCurrentLiveTheme().value
    }

    fun getCurrentLiveTheme(): MutableLiveData<AppTheme> {
        return liveTheme
    }


    fun changeTheme(newTheme: AppTheme, view: View, duration: Long = 600) {
        changeTheme(newTheme, getViewCoordinates(view), duration)
    }

    fun changeTheme(newTheme: AppTheme, sourceCoordinate: Coordinate, duration: Long = 600) {

        if (getCurrentTheme()?.id() == newTheme.id() || activity.isRunningChangeThemeAnimation()) {
            return
        }

        //start animation
        activity.changeTheme(newTheme, sourceCoordinate, duration)

        //set StatusBar BackgroundColor
        setStatusBarBackgroundColor(activity, newTheme.statusBarColor(activity))

        //set NavigationBar BackgroundColor
        setNavigationBarBackgroundColor(activity, newTheme)

        //set LiveData
        getCurrentLiveTheme().value = newTheme
    }


    fun setStatusBarBackgroundColor(activity: Activity, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val window = activity.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = color
            val decorView = window.decorView
            var flags = decorView.systemUiVisibility
            if (isColorLight(color)) {
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

    private fun setNavigationBarBackgroundColor(activity: Activity, appTheme: AppTheme) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val color: Int = appTheme.navigationBarColor(activity)
            activity.window.navigationBarColor = color
            setNavigationBarButtonsColor(activity, color)
        }
    }

    private fun setNavigationBarButtonsColor(activity: Activity, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val decorView = activity.window.decorView
            var flags = decorView.systemUiVisibility
            flags = if (isColorLight(color)) {
                flags or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            } else {
                flags and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()
            }
            decorView.systemUiVisibility = flags
        }
    }

    private fun isColorLight(color: Int): Boolean {
        val darkness =
            1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(
                color
            )) / 255
        return darkness < 0.5
    }

    private fun getViewCoordinates(view: View): Coordinate {
        val coordinate = Coordinate()
        coordinate.left = getRelativeLeft(view)
        coordinate.top = getRelativeTop(view)
        coordinate.width = view.width
        coordinate.height = view.height
        return coordinate
    }

    private fun getRelativeLeft(myView: View): Int {
        return if ((myView.parent as View).id == R.id.mainContainer) myView.left else myView.left + getRelativeLeft(
            myView.parent as View
        )
    }

    private fun getRelativeTop(myView: View): Int {
        return if ((myView.parent as View).id == R.id.mainContainer) myView.top else myView.top + getRelativeTop(
            myView.parent as View
        )
    }
}