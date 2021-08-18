package com.dolatkia.changeThemeAnimation

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

abstract class ThemeActivity : AppCompatActivity() {
    private lateinit var root: View
    private lateinit var fakeThemeImageView: ImageView
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
        fakeThemeImageView = root.findViewById(R.id.fakeThemeImageView)
    }

    fun changeTheme(newTheme: AppTheme, sourceCoordinate: Coordinate, animDuration: Long) {
        if (fakeThemeImageView.visibility == View.VISIBLE || Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return
        }

        if (isRunningChangeThemeAnimation()) {
            return
        }

        val w = decorView.measuredWidth.toFloat()
        val h = decorView.measuredHeight.toFloat()
        val bitmap = Bitmap.createBitmap(w.toInt(), h.toInt(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        decorView.draw(canvas)

        fakeThemeImageView.setImageBitmap(bitmap)
        fakeThemeImageView.visibility = View.VISIBLE

        syncTheme(newTheme)

        val finalRadius = Math.hypot(w.toDouble(), h.toDouble()).toFloat()
        anim = ViewAnimationUtils.createCircularReveal(
            decorView,
            sourceCoordinate.left + sourceCoordinate.width / 2,
            sourceCoordinate.top + sourceCoordinate.height / 2,
            0f,
            finalRadius
        )
        anim?.duration = animDuration
        anim?.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                themeAnimationListener?.onAnimationStart(animation)
            }
            override fun onAnimationEnd(animation: Animator) {
                fakeThemeImageView.setImageDrawable(null)
                fakeThemeImageView.visibility = View.GONE
                themeAnimationListener?.onAnimationEnd(animation)
            }

            override fun onAnimationCancel(animation: Animator) {
                themeAnimationListener?.onAnimationCancel(animation)
            }
            override fun onAnimationRepeat(animation: Animator) {
                themeAnimationListener?.onAnimationRepeat(animation)
            }
        })
        anim?.start()
    }

    fun isRunningChangeThemeAnimation(): Boolean {
        return anim?.isRunning == true
    }

    fun setThemeAnimationListener(listener: ThemeAnimationListener) {
        this.themeAnimationListener = listener
    }

    abstract fun syncTheme(appTheme: AppTheme)
    abstract fun getStartTheme(): AppTheme
}