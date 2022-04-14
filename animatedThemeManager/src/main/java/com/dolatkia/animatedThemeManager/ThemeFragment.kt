package com.dolatkia.animatedThemeManager

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.filterNotNull

abstract class ThemeFragment : Fragment {

    constructor() : super() { }

    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    override fun onCreate(savedInstanceState: Bundle?) {
        this.lifecycleScope.launchWhenResumed {
            themeManager.theme.filterNotNull().collect {
                syncTheme(it)
            }
        }

        super.onCreate(savedInstanceState)
    }

    protected val themeManager: ThemeManager
        get() = ThemeManager

    // to sync ui with selected theme
    abstract fun syncTheme(appTheme: AppTheme)
}