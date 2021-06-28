package com.github.marcherdiego.swipecompare.core.extensions

import android.content.res.TypedArray
import androidx.annotation.StyleableRes

fun TypedArray.getDimen(@StyleableRes index: Int, defValue: Int = 0): Int {
    return getDimensionPixelSize(index, defValue)
}

fun TypedArray.getResId(@StyleableRes index: Int, defValue: Int = 0): Int {
    return getResourceId(index, defValue)
}

fun TypedArray.getColor(@StyleableRes index: Int, defValue: Int = 0): Int {
    return getColor(index, defValue)
}

fun TypedArray.getBoolean(@StyleableRes index: Int, defValue: Boolean = false): Boolean {
    return getBoolean(index, defValue)
}
