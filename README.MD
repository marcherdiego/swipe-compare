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

#### Setup with Fragments
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

#### Setup with Views
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

#### Setup with Fragments
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
#### Setup with Views
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
