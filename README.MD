# Swipe Compare Layout
 
## Download
[![Download](https://img.shields.io/maven-central/v/com.github.marcherdiego.swipecompare/core)](https://search.maven.org/artifact/com.github.marcherdiego.swipecompare/core)

## Usage
Add `implementation` or `api` (library projects) dependency

```groovy
implementation "com.github.marcherdiego.swipecompare:core:LATEST_VERSION"
```

### Horizontal & Vertical layouts
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

## Features
### Horizontal slider
https://user-images.githubusercontent.com/4492972/119355504-bcd4e400-bc7b-11eb-9dbc-6bc7de3eacaa.mp4

### Vertical slider
https://user-images.githubusercontent.com/4492972/119355700-f4439080-bc7b-11eb-9933-214713567ec2.mp4

### Crosshair slider
https://user-images.githubusercontent.com/4492972/119355716-f9084480-bc7b-11eb-9a3e-9bb19bda28de.mp4
