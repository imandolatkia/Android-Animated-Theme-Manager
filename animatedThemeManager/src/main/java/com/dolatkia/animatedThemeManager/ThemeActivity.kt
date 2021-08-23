package com.dolatkia.animatedThemeManager

import android.animation.Animator
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.hypot

abstract class ThemeActivity : AppCompatActivity() {
    private lateinit var root: View
    private lateinit var frontFakeThemeImageView: ImageView
    private lateinit var behindFakeThemeImageView: ImageView
    private lateinit var decorView: FrameLayout
    private var anim: Animator? = null
    private var themeAnimationListener: ThemeAnimationListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeManager.instance.init(this, getStartTheme())
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        ThemeManager.instance.init(this, getStartTheme())
    }

    override fun onStart() {
        super.onStart()
        ThemeManager.instance.getCurrentTheme()?.let { syncTheme(it) }
    }

    override fun setContentView(@LayoutRes layoutResID: Int) {
        createViews()
        decorView.addView(LayoutInflater.from(this).inflate(layoutResID, null))
        super.setContentView(root)
    }

    override fun setContentView(view: View?) {
        createViews()
        decorView.addView(view)
        super.setContentView(root)
    }

    override fun setContentView(view: View?, params: ViewGroup.LayoutParams?) {
        createViews()
        decorView.addView(view, params)
        super.setContentView(root)
    }

    private fun createViews() {
        root = LayoutInflater.from(this).inflate(R.layout.change_theme_activity, null)
        decorView = root.findViewById(R.id.mainContainer)
        frontFakeThemeImageView = root.findViewById(R.id.frontFakeThemeImageView)
        behindFakeThemeImageView = root.findViewById(R.id.behindFakeThemeImageView)
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
        val w = decorView.measuredWidth.toFloat()
        val h = decorView.measuredHeight.toFloat()
        val bitmap = Bitmap.createBitmap(w.toInt(), h.toInt(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        decorView.draw(canvas)

        // update theme
        syncTheme(newTheme)

        //create anim
        val finalRadius = hypot(w.toDouble(), h.toDouble()).toFloat()
        if (isReverse) {
            frontFakeThemeImageView.setImageBitmap(bitmap)
            frontFakeThemeImageView.visibility = View.VISIBLE
            anim = ViewAnimationUtils.createCircularReveal(
                frontFakeThemeImageView,
                sourceCoordinate.x,
                sourceCoordinate.y,
                finalRadius,
                0f
            )
        } else {
            behindFakeThemeImageView.setImageBitmap(bitmap)
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
                behindFakeThemeImageView.setImageDrawable(null)
                frontFakeThemeImageView.setImageDrawable(null)
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

    // to get stat theme
    abstract fun getStartTheme(): AppTheme
}