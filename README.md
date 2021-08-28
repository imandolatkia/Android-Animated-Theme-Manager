# Android Animated Theme Manager   [![Tweet](https://img.shields.io/twitter/url/http/shields.io.svg?style=social)](https://twitter.com/intent/tweet?text=Android%20Animated%20Theme%20Manager%20Library&url=https://github.com/imandolatkia/Android-Animated-Theme-Manager&hashtags=Android,Theme,Ripple,kotlin,java,lbrary)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)   [![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.imandolatkia/animatedThemeManager/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.imandolatkia/animatedThemeManager)
[![Generic badge](https://img.shields.io/badge/Repo_Size-26_kb-orange.svg)](https://search.maven.org/remotecontent?filepath=io/github/imandolatkia/animatedThemeManager/1.1.2/animatedThemeManager-1.1.2.aar)
[![Generic badge](https://img.shields.io/badge/kotlin-100-blue.svg)](https://github.com/imandolatkia/Android-Animated-Theme-Manager/search?l=kotlin)
[![Generic badge](https://img.shields.io/badge/support-java_&_kotlin-green.svg)](https://github.com/imandolatkia/Android-Animated-Theme-Manager/search?l=kotlin)
[![CodeFactor](https://www.codefactor.io/repository/github/imandolatkia/android-animated-theme-manager/badge)](https://www.codefactor.io/repository/github/imandolatkia/android-animated-theme-manager)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=imandolatkia_Android-Animated-Theme-Manager&metric=alert_status)](https://sonarcloud.io/dashboard?id=imandolatkia_Android-Animated-Theme-Manager)

create your custom themes and change them dynamically with ripple animation

![animation-ripple-android-theme](https://user-images.githubusercontent.com/6734608/129915453-b57a1618-2d20-42a3-85a7-57bd1c425522.gif)
</br></br>


# Features
* support **java** and **kotlin** projects.
* change theme **without recreating** activities and fragments.
* support muli fragments apps.
* ripple animation.
* **reverse animation**.
* changeable animation duration.
* changeable animation position.
* animation listener.
* observe changes of themes for custom actions with Livedata.
* easy to use, 5 or 7 tiny steps.
* **support any android APIs** (animation works on API>20).

</br></b>

# How to install? [![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.imandolatkia/animatedThemeManager/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.imandolatkia/animatedThemeManager)


add the following line to **app-level** build.gradle file, in dependencies scope:
```gradle
dependencies {
    ...
    implementation "io.github.imandolatkia:animatedThemeManager:1.1.2"
}
```
</br>

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

<details><summary>java</summary>
<p>

```java
interface MyAppTheme extends AppTheme {
    int firstActivityBackgroundColor(@NotNull Context context);
    int firstActivityTextColor(@NotNull Context context);
    int firstActivityIconColor(@NotNull Context context);
    // any other methods for other elements
}
```
</details>

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

<details><summary>java</summary>
<p>

```java
public class LightTheme implements MyAppTheme {
    public int id() { // set unique iD for each theme 
        return 0;
    }

    public int firstActivityBackgroundColor(@NotNull Context context) {
        return ContextCompat.getColor(context, R.color.background_light);
    }

    public int firstActivityTextColor(@NotNull Context context) {
        return ContextCompat.getColor(context,  R.color.text_light);
    }

    public int firstActivityIconColor(@NotNull Context context) {
        return ContextCompat.getColor(context, R.color.icon_light);
    }
    
    ...
}
    
public class NightTheme implements MyAppTheme {...}
public class PinkTheme implements MyAppTheme {...}

```
</details>



3- extends your activity from **ThemeActivity**:
```kotlin
MainActivity : ThemeActivity() {
...
}
```
<details><summary>java</summary>
<p>

```java
public class MainActivity implements ThemeActivity {
...
}

```
</details>

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

<details><summary>java</summary>
<p>

```java
// to sync ui with selected theme
@Override
public void syncTheme(@NotNull AppTheme appTheme) {
    // change ui colors with new appThem here

    MyAppTheme myAppTheme = (MyAppTheme) appTheme;

    // set background color
    binder.getRoot().setBackgroundColor(myAppTheme.activityBackgroundColor(this));

    //set text color
    binder.text.setTextColor(myAppTheme.activityTextColor(this));

    // set icons color
    binder.share.setColorFilter(myAppTheme.activityBackgroundColor(this));
    binder.gift.setColorFilter(myAppTheme.activityBackgroundColor(this));
}

// to get start theme
@NotNull
@Override
public AppTheme getStartTheme() {
    return new LightTheme();
}
```
</details>

5- change theme from user click with ```ThemeManager.instance.changeTheme()``` method:
```kotlin
// set change theme click listener
binder.lightButton.setOnClickListener {
  ThemeManager.instance.changeTheme(LightTheme(), it)
}
```
<details><summary>java</summary>
<p>

```java
binder.lightButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      ThemeManager.Companion.getInstance().changeTheme(new LightTheme(), v, 600);
    }
});
```
</details>

the first argument is the selected theme.

the second argument is the view that animation starts from the center of it.
</br></br>

# How to use in multi fragments app?
repeat all previous 5 steps, and then:


6- extends your fragments from **ThemeFragment**:
```kotlin
MyFragment : ThemeFragment() {
...
}
```

<details><summary>java</summary>
<p>

```java
public class MyFragment implements ThemeFragment {
...
}
```
</details>

7- implement ThemeFragment **syncTheme abstract methods**:

```kotlin
// to sync ui with selected theme
override fun syncTheme(appTheme: AppTheme) {
    // change ui colors with new appThem here
    ...
}
```

<details><summary>java</summary>
<p>

```java
@Override
public void syncTheme(@NotNull AppTheme appTheme) {
    // change ui colors with new appThem here
    ...
}
```
</details>
</br>

# Some other settings and customization:
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

```kotlin
   binder.lightButton.setOnClickListener {
        ThemeManager.instance.changeTheme(LightTheme(), it, 800)
   }
```

###  ✔️ change animation center position
if you want to start animation somewhere other than the view that clicked, send a Coordinate object instead of View in ThemeManager.instance.changeTheme()
```kotlin
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
# Stargazers
[![Stargazers repo roster for @imandolatkia/Android-Animated-Theme-Manager](https://reporoster.com/stars/imandolatkia/Android-Animated-Theme-Manager)](https://github.com/imandolatkia/Android-Animated-Theme-Manager/stargazers)


# Forkers
[![Forkers repo roster for @imandolatkia/Android-Animated-Theme-Manager](https://reporoster.com/forks/imandolatkia/Android-Animated-Theme-Manager)](https://github.com/imandolatkia/Android-Animated-Theme-Manager/network/members)


[![Quality gate](https://sonarcloud.io/api/project_badges/quality_gate?project=imandolatkia_Android-Animated-Theme-Manager)](https://sonarcloud.io/dashboard?id=imandolatkia_Android-Animated-Theme-Manager)



