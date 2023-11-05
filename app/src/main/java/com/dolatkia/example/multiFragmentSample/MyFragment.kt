package com.dolatkia.example.multiFragmentSample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dolatkia.animatedThemeManager.AppTheme
import com.dolatkia.animatedThemeManager.ThemeFragment
import com.dolatkia.animatedThemeManager.ThemeManager
import com.dolatkia.example.R
import com.dolatkia.example.databinding.FragmentBinding
import com.dolatkia.example.themes.LightTheme
import com.dolatkia.example.themes.MyAppTheme
import com.dolatkia.example.themes.NightTheme
import com.dolatkia.example.themes.PinkTheme

class MyFragment : ThemeFragment(R.layout.fragment) {

    private lateinit var binder: FragmentBinding
    private var number: Int = 0

    companion object {
        @JvmStatic
        fun newInstance(number: Int) = MyFragment().apply {
            arguments = Bundle().apply {
                putInt("NUMBER", number)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt("NUMBER")?.let {
            number = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binder = FragmentBinding.bind(view)

        binder.fragmentNumber.text = number.toString()

        //set next fragment click listener
        binder.nextFragmentButton.setOnClickListener {
            (activity as MyFragmentActivity).addNewFragment()
        }

        // set change theme click listeners for buttons
        binder.lightButton.setOnClickListener {
            themeManager.changeTheme(LightTheme(), it)
        }
        binder.nightButton.setOnClickListener {
            themeManager.changeTheme(NightTheme(), it)
        }
        binder.pinkButton.setOnClickListener {
            themeManager.changeTheme(PinkTheme(), it)
        }
    }


    // to sync ui with selected theme
    override fun syncTheme(appTheme: AppTheme) {
        // change ui colors with new appThem here

        val myAppTheme = appTheme as MyAppTheme
        context?.let {
            // set background color
            binder.root.setBackgroundColor(myAppTheme.activityBackgroundColor(it))

            //set top image
            binder.image.setImageResource(myAppTheme.activityImageRes(it))

            // set icons color
            binder.share.setColorFilter(myAppTheme.activityIconColor(it))
            binder.gift.setColorFilter(myAppTheme.activityIconColor(it))

            //set text color
            binder.text.setTextColor(myAppTheme.activityTextColor(it))
            binder.fragmentNumber.setTextColor(myAppTheme.activityTextColor(it))

            //set card view colors
            binder.lightButton.setCardBackgroundColor(appTheme.activityThemeButtonColor(it))
            binder.nightButton.setCardBackgroundColor(appTheme.activityThemeButtonColor(it))
            binder.pinkButton.setCardBackgroundColor(appTheme.activityThemeButtonColor(it))
            binder.nextFragmentButton.setCardBackgroundColor(appTheme.activityThemeButtonColor(it))
        }


        //syncStatusBarIconColors
        syncStatusBarIconColors(appTheme)
    }

    private fun syncStatusBarIconColors(theme: MyAppTheme) {
        activity?.let {
            themeManager.syncStatusBarIconsColorWithBackground(
                it,
                theme.activityBackgroundColor(it)
            )
        }
        activity?.let {
            themeManager.syncNavigationBarButtonsColorWithBackground(
                it,
                theme.activityBackgroundColor(it)
            )
        }
    }
}