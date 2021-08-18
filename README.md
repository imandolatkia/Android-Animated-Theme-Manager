# Android-Animated-Theme-Manager
create your custom themes and change them dynamically with animation

![animation-ripple-android-theme](https://user-images.githubusercontent.com/6734608/129915453-b57a1618-2d20-42a3-85a7-57bd1c425522.gif)

# How to install?
//we will upload it to maven soon

# How to use?

1- for each theme that you want in your app, create a class that extends from AppTheme:
```kotlin
class LightTheme : MyAppTheme {

    override fun id(): Int {
        return 0
    }

    override fun activityBackgroundColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.background_light)
    }

    override fun activityImageRes(context: Context): Int {
        return R.drawable.image_light
    }

    override fun activityIconColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.icon_light)
    }

    override fun activityTextColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.text_light)
    }

    override fun activityThemeButtonColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.button_light)
    }
}
```

2- extends your activity from ThemeActivity:
```kotlin
MainActivity : ThemeActivity() {
...
}
```

3- implement ThemeActivity abstract method:

```kotlin


    // to sync ui with selected theme
    override fun syncTheme(appTheme: AppTheme) {
        val myAppTheme = appTheme as MyAppTheme
        // set background color
        binder.root.setBackgroundColor(myAppTheme.activityBackgroundColor(this))

        //set top image
        binder.image.setImageResource(myAppTheme.activityImageRes(this))

        // set icons color
        binder.share.setColorFilter(myAppTheme.activityIconColor(this))
        binder.gift.setColorFilter(myAppTheme.activityIconColor(this))

        //set text color
        binder.text.setTextColor(myAppTheme.activityTextColor(this))

        //set card view colors
        binder.lightButton.setCardBackgroundColor(appTheme.activityThemeButtonColor(this))
        binder.nightButton.setCardBackgroundColor(appTheme.activityThemeButtonColor(this))
        binder.pinkButton.setCardBackgroundColor(appTheme.activityThemeButtonColor(this))

        //syncStatusBarIconColors
        syncStatusBarIconColors(appTheme)
    }

    // to get stat theme
    override fun getStartTheme(): AppTheme {
        return LightTheme()
    }

```

4- change theme from user click with ```ThemeManager.instance.changeTheme()``` method

```kotlin
// set change theme click listener
binder.lightButton.setOnClickListener {
  ThemeManager.instance.changeTheme(LightTheme(), it)
}

```

5- Done

# how to use in fragments?
//We are working on it
