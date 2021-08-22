# Android Animated Theme Manager
create your custom themes and change them dynamically with animation

![animation-ripple-android-theme](https://user-images.githubusercontent.com/6734608/129915453-b57a1618-2d20-42a3-85a7-57bd1c425522.gif)


# Features
⭐change theme without recreating activities and fragments.

⭐support muli fragments apps.

⭐ripple animation.

⭐reverse animation.

⭐changeable animation duration.

⭐changeable animation position.

⭐animation listener.

⭐observe changes of themes for custom actions with Livedata.

⭐easy to use.

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

    override fun id(): Int { // set unique iD for each theme 
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

5- change theme from user click with ```ThemeManager.instance.changeTheme()``` method:
```kotlin
// set change theme click listener
binder.lightButton.setOnClickListener {
  ThemeManager.instance.changeTheme(LightTheme(), it)
}
```
the first argument is the selected theme.

the second argument is the view that animation starts from the center of it.


# how to use in multi fragments app?
repeat all previous 5 steps, and then:


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

# Some Other settings:
###  ✔️ reverse animation
if you want to use reverse animation, call reverseChangeTheme() instead of changeTheme():

```kotline
   binder.lightButton.setOnClickListener {
        ThemeManager.instance.reverseChangeTheme(LightTheme(), it)
   }
```
![reverse ripple theme animation](https://user-images.githubusercontent.com/6734608/130370446-1218d53d-9c25-4dc9-8d6b-f7e8a1056731.gif)

###  ✔️ change animation duration
if you want to change animation duration, add your desire duration in millisecond as the third argument of ThemeManager.instance.changeTheme(). the default value is 600:

```kotline
   binder.lightButton.setOnClickListener {
        ThemeManager.instance.changeTheme(LightTheme(), it, 800)
   }
```

###  ✔️ change animation center position
if you want to start animation somewhere other than the view that clicked, send a Coordinate object instead of View in ThemeManager.instance.changeTheme()
```kotline
   binder.lightButton.setOnClickListener {
          binder.nightButton.setOnClickListener {
            ThemeManager.instance.changeTheme(NightTheme(), Coordinate(10, 20)
        }
   }
```

witch the Coordinate is:
```kotlin
 Coordinate(var x: Int, var y: Int) 
```

### ✔️ observe changes of themes yourself
if you want to observe changes of themes and do some custom action, you can use theme Livedata in any fragment or activity:

```kotlin
    ThemeManager.instance.getCurrentLiveTheme().observe(this) {
        Toast.makeText(this, "on Theme changed to ${it.id()}", Toast.LENGTH_SHORT).show()
    }
```


### ✔️ set animation listener
if you want to set animation listener, use setThemeAnimationListener() method in your activity

```kotlin
     setThemeAnimationListener(MyThemeAnimationListener(this))
```
witch the MyThemeAnimationListener is:

```kotlin
    class MyThemeAnimationListener(var context: Context) : ThemeAnimationListener{
        override fun onAnimationStart(animation: Animator) {
           Toast.makeText(context, "onAnimationStart", Toast.LENGTH_SHORT).show()
        }

        override fun onAnimationEnd(animation: Animator) {
            Toast.makeText(context, "onAnimationEnd", Toast.LENGTH_SHORT).show()
        }

        override fun onAnimationCancel(animation: Animator) {
            Toast.makeText(context, "onAnimationCancel", Toast.LENGTH_SHORT).show()
        }

        override fun onAnimationRepeat(animation: Animator) {
            Toast.makeText(context, "onAnimationRepeat", Toast.LENGTH_SHORT).show()
        }
    }

```






