package com.dolatkia.animatedThemeManager

import android.animation.Animator
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import kotlin.math.sqrt

abstract class ThemeActivity : AppCompatActivity() {
    private lateinit var root: View
    private lateinit var frontFakeThemeImageView: SimpleImageView
    private lateinit var behindFakeThemeImageView: SimpleImageView
    private lateinit var decorView: FrameLayout

    private var anim: Animator? = null
    private var themeAnimationListener: ThemeAnimationListener? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        ThemeManager.init(this, getStartTheme())
        initViews()
        super.setContentView(root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeManager.init(this, getStartTheme())
        initViews()
        super.setContentView(root)
    }

    override fun onResume() {
        super.onResume()
        ThemeManager.setActivity(this)
        themeManager.currentTheme?.let { syncTheme(it) }
    }

    protected val themeManager: ThemeManager
        get() = ThemeManager

    override fun setContentView(@LayoutRes layoutResID: Int) {
        setContentView(LayoutInflater.from(this).inflate(layoutResID, decorView, false))
    }

    override fun setContentView(view: View?) {
        decorView.removeAllViews()
        decorView.addView(view)
    }

    override fun setContentView(view: View?, params: ViewGroup.LayoutParams?) {
        decorView.removeAllViews()
        decorView.addView(view, params)
    }

    private fun initViews() {
        // create roo view
        root = FrameLayout(this).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )

            // create and add behindFakeThemeImageView
            addView(SimpleImageView(context).apply {
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
                )
                visibility = View.GONE
                behindFakeThemeImageView = this
            })

            // create and add decorView, ROOT_ID is generated ID
            addView(FrameLayout(context).apply {
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
                )
                decorView = this
                id = ROOT_ID
            })

            // create and add frontFakeThemeImageView
            addView(SimpleImageView(context).apply {
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
                )
                visibility = View.GONE
                frontFakeThemeImageView = this
            })
        }
    }

    fun changeTheme(
        newTheme: AppTheme,
        sourceCoordinate: Coordinate,
        animDuration: Long,
        isReverse: Boolean
    ) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // update theme and return: no animation
            syncTheme(newTheme)
            return
        }

        if (frontFakeThemeImageView.visibility == View.VISIBLE ||
            behindFakeThemeImageView.visibility == View.VISIBLE ||
            isRunningChangeThemeAnimation()
        ) {
            return
        }

        // take screenshot
        val w = decorView.measuredWidth
        val h = decorView.measuredHeight
        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        decorView.draw(canvas)

        // update theme
        syncTheme(newTheme)

        //create anim
        val finalRadius = sqrt((w * w + h * h).toDouble()).toFloat()
        if (isReverse) {
            frontFakeThemeImageView.bitmap = bitmap
            frontFakeThemeImageView.visibility = View.VISIBLE
            anim = ViewAnimationUtils.createCircularReveal(
                frontFakeThemeImageView,
                sourceCoordinate.x,
                sourceCoordinate.y,
                finalRadius,
                0f
            )
        } else {
            behindFakeThemeImageView.bitmap = bitmap
            behindFakeThemeImageView.visibility = View.VISIBLE
            anim = ViewAnimationUtils.createCircularReveal(
                decorView,
                sourceCoordinate.x,
                sourceCoordinate.y,
                0f,
                finalRadius
            )
        }

        // set duration
        anim?.duration = animDuration

        // set listener
        anim?.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                themeAnimationListener?.onAnimationStart(animation)
            }

            override fun onAnimationEnd(animation: Animator) {
                behindFakeThemeImageView.bitmap = null
                frontFakeThemeImageView.bitmap = null
                frontFakeThemeImageView.visibility = View.GONE
                behindFakeThemeImageView.visibility = View.GONE
                themeAnimationListener?.onAnimationEnd(animation)
            }

            override fun onAnimationCancel(animation: Animator) {
                themeAnimationListener?.onAnimationCancel(animation)
            }

            override fun onAnimationRepeat(animation: Animator) {
                themeAnimationListener?.onAnimationRepeat(animation)
            }
        })

        // start it :)
        anim?.start()
    }

    fun isRunningChangeThemeAnimation(): Boolean {
        return anim?.isRunning == true
    }

    fun setThemeAnimationListener(listener: ThemeAnimationListener) {
        this.themeAnimationListener = listener
    }

    // to sync ui with selected theme
    abstract fun syncTheme(appTheme: AppTheme)

    // to save the theme for the next time, save it in onDestroy() (exp: in pref or DB) and return it here.
    // it just used for the first time (first activity).
    abstract fun getStartTheme(): AppTheme

    override fun onDestroy() {
        themeManager.clearActivity()
        super.onDestroy()
    }

    companion object {
        //generated Id for decorView
        internal val ROOT_ID = ViewCompat.generateViewId()
    }
}