[![Nerd's Corner](https://circleci.com/gh/marcherdiego/swipe-compare.svg?style=svg)](https://app.circleci.com/pipelines/github/marcherdiego/swipe-compare)

# Swipe Compare Layout
Create your own swipe-to-compare layouts with this easy-to-use library.
Natively built with no additional libraries to process drawing, other than standard Android ones.
 
## Download
[![Download](https://img.shields.io/maven-central/v/com.github.marcherdiego.swipecompare/core)](https://search.maven.org/artifact/com.github.marcherdiego.swipecompare/core)

## Usage
Add `implementation` or `api` (library projects) dependency

```groovy
implementation "com.github.marcherdiego.swipecompare:core:LATEST_VERSION"
```

### Horizontal & Vertical layouts
| Horizontal                                                            	| Vertical                                                          	|
|-----------------------------------------------------------------------	|-------------------------------------------------------------------	|
| <img alt='Horizontal layout' src='gifs/horizontal.gif' height='400'/> 	| <img alt='Vertical layout' src='gifs/vertical.gif' height='400'/> 	|

#### XML
```xml
<com.github.marcherdiego.swipecompare.core.VerticalSwipeCompareLayout
        app:vertical_top_view="@drawable/img1"
        app:vertical_bottom_view="@drawable/img2"
        app:vertical_slider_bar_color="@color/white"
        app:vertical_slider_bar_height="2dp"
        app:vertical_slider_icon_width="10dp"
        app:vertical_slider_icon_height="10dp"
        app:vertical_slider_icon_image="@drawable/circle_background"
        app:vertical_slider_icon_padding_bottom="4dp"
        app:vertical_slider_icon_padding_left="4dp"
        app:vertical_slider_icon_padding_right="4dp"
        app:vertical_slider_icon_padding_top="4dp"/>
```

#### Setup with Fragments (Programmatically)
```kotlin
private val horizontalSwipeCompareLayout: HorizontalSwipeCompareLayout = findViewById(R.id.horizontal_swipe_compare)
// ...
horizontalSwipeCompareLayout
    .setSliderBarColorRes(R.color.white)
    .setSliderIconBackground(R.drawable.circle_background)
    .setSliderIconPadding(5, 5, 5, 5)
    .setFragments(fragmentManager = supportFragmentManager, leftFragment = Fragment(), rightFragment = Fragment())
    .setSliderBarWidth(3)
    .setSliderIconSize(10, 10)
    .setSliderPositionChangedListener {
        // Do something with `it`
    }
```

#### Setup with Views (Programmatically)
replace 
```kotlin
horizontalSwipeCompareLayout
    //...
    .setFragments(fragmentManager = supportFragmentManager, leftFragment = Fragment(), rightFragment = Fragment())
```
with
```kotlin
horizontalSwipeCompareLayout
    //...
    .setViews(leftView = View(context), rightView = View(context))
```

### Crosshair layout
| Crosshair                                                           	|
|---------------------------------------------------------------------	|
| <img alt='Crosshair layout' src='gifs/crosshair.gif' height='400'/> 	|

#### XML
```xml
<com.github.marcherdiego.swipecompare.core.CrosshairSwipeCompareLayout
        app:crosshair_top_left_view="@drawable/img1"
        app:crosshair_top_right_view="@drawable/img2"
        app:crosshair_bottom_left_view="@drawable/ss1"
        app:crosshair_bottom_right_view="@drawable/ss2"
        app:crosshair_horizontal_slider_bar_color="@color/white"
        app:crosshair_horizontal_slider_bar_width="2dp"
        app:crosshair_horizontal_slider_icon_height="12dp"
        app:crosshair_horizontal_slider_icon_width="12dp"
        app:crosshair_horizontal_slider_icon_image="@drawable/circle_background"
        app:crosshair_horizontal_slider_icon_padding_bottom="4dp"
        app:crosshair_horizontal_slider_icon_padding_left="4dp"
        app:crosshair_horizontal_slider_icon_padding_right="4dp"
        app:crosshair_horizontal_slider_icon_padding_top="4dp"
        app:crosshair_vertical_slider_bar_color="@color/white"
        app:crosshair_vertical_slider_bar_height="2dp"
        app:crosshair_vertical_slider_icon_image="@drawable/circle_background"
        app:crosshair_vertical_slider_icon_width="12dp"
        app:crosshair_vertical_slider_icon_height="12dp"
        app:crosshair_vertical_slider_icon_padding_bottom="4dp"
        app:crosshair_vertical_slider_icon_padding_left="4dp"
        app:crosshair_vertical_slider_icon_padding_right="4dp"
        app:crosshair_vertical_slider_icon_padding_top="4dp"
        app:crosshair_touch_enabled="false"/>
```

#### Setup with Fragments (Programmatically)
```kotlin
private val crosshairSwipeCompareLayout: CrosshairSwipeCompareLayout = findViewById(R.id.crosshair_swipe_compare)
// ...
crosshairSwipeCompareLayout
    .setSliderBarColorRes(R.color.white)
    .setSliderIconBackground(R.drawable.circle_background)
    .setSliderIconPadding(5, 5, 5, 5)
    .setFragments(
        fragmentManager = supportFragmentManager,
        topRightFragment = Fragment(),
        topLeftFragment = Fragment(),
        bottomLeftFragment = Fragment(),
        bottomRightFragment = Fragment()
    )
    .setSliderBarHeight(3)
    .setSliderBarWidth(3)
    .setSliderIconSize(10, 10)
    .setSliderPositionChangedListener { x, y ->
        // Do something with `x` and `y`
    }
```
#### Setup with Views (Programmatically)
replace 
```kotlin
crosshairSwipeCompareLayout
    //...
    .setFragments(
        fragmentManager = supportFragmentManager,
        topRightFragment = Fragment(),
        topLeftFragment = Fragment(),
        bottomLeftFragment = Fragment(),
        bottomRightFragment = Fragment()
    )
```
with
```kotlin
crosshairSwipeCompareLayout
    //...
    .setViews(
        topLeftView = View(context), 
        topRightView = View(context), 
        bottomLeftView = View(context), 
        bottomRightView = View(context)
    )
```
