# Android-Animated-Theme-Manager
create your custom themes and change them dynamically with animation

![animation-ripple-android-theme](https://user-images.githubusercontent.com/6734608/129915453-b57a1618-2d20-42a3-85a7-57bd1c425522.gif)

# How to install?
add the following line to **app-level** build.gradle file, in dependencies scope:
```gradle
dependencies {
    ...
    implementation "io.github.imandolatkia:animatedThemeManager:1.1.0"
}
```

# How to use?

1- create an abstract class that inherits from **AppTheme**. in this class create abstract methods to return related color for all UI element that you want to change them on each theme. for example, if you want to change the background color, text colors and icon colors in your **firstActivity**, do this:

```kotlin
interface MyAppTheme : AppTheme {
    fun firstActivityBackgroundColor(context: Context): Int
    fun firstActivityTextColor(context: Context): Int
    fun firstActivityIconColor(context: Context): Int
    // any other methods for other elements
}

```


2- for each theme that you want in your app, create a class that extends from  **the class that was created in step 1 (MyAppTheme)**, and implement methods with related colors or resources, for example, if you want to have 3 themes, you should create 3 class and implement methods:

```kotlin
class LightTheme : MyAppTheme {

    override fun id(): Int {
        return 0
    }

    override fun firstActivityBackgroundColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.background_light)
    }

    override fun firstActivityTextColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.text_light)
    }
    
     override fun firstActivityIconColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.icon_light)
    }
    
    ...
}

class NightTheme : MyAppTheme {...}
class PinkTheme : MyAppTheme {...}

```



3- extends your activity from **ThemeActivity**:
```kotlin
MainActivity : ThemeActivity() {
...
}
```

4- implement ThemeActivity **2 abstract methods**:

```kotlin


    // to sync ui with selected theme
    override fun syncTheme(appTheme: AppTheme) {
        // change ui colors with new appThem here

        val myAppTheme = appTheme as MyAppTheme
        // set background color
        binder.root.setBackgroundColor(myAppTheme.firstActivityBackgroundColor(this))

        //set text color
        binder.text.setTextColor(myAppTheme.activityTextColor(this))
        
         // set icons color
        binder.share.setColorFilter(myAppTheme.firstActivityIconColor(this))
        binder.gift.setColorFilter(myAppTheme.firstActivityIconColor(this))
        
        ...
    }

    // to get start theme
    override fun getStartTheme(): AppTheme {
        return LightTheme()
    }

```

5- change theme from user click with ```ThemeManager.instance.changeTheme()``` method

```kotlin
// set change theme click listener
binder.lightButton.setOnClickListener {
  ThemeManager.instance.changeTheme(LightTheme(), it)
}

```

# how to use in multi fragments app?
repeat all previous 4 steps, and then:


6- extends your fragments from **ThemeFragment**:
```kotlin
MyFragment : ThemeFragment() {
...
}
```

7- implement ThemeFragment **syncTheme abstract methods**:

```kotlin
    // to sync ui with selected theme
    override fun syncTheme(appTheme: AppTheme) {
     // change ui colors with new appThem here
     ...
    }
```

