/*
 * Licensed under Apache-2.0
 *
 * Designed an developed by Aidan Follestad (afollestad)
 */

package com.afollestad.materialdialogs.extensions

import android.graphics.Color
import android.support.annotation.ColorInt

//const val DARKENED_COLOR_ALPHA = 0.8f

fun Int.isColorDark(): Boolean {
  val darkness =
    1 - (0.299 * Color.red(this) + 0.587 * Color.green(this) + 0.114 * Color.blue(this)) / 255
  return darkness >= 0.5
}

@ColorInt
internal fun Int.adjustAlpha(factor: Float): Int {
  val alpha = Math.round(Color.alpha(this) * factor)
  val red = Color.red(this)
  val green = Color.green(this)
  val blue = Color.blue(this)
  return Color.argb(alpha, red, green, blue)
}

//internal fun getColorSelector(@ColorInt colorDefault: Int): ColorStateList {
//  return ColorStateList(
//      arrayOf(
//          intArrayOf(
//              -attr.state_checked,
//              attr.state_checked
//          )
//      ),
//      intArrayOf(colorDefault, colorDefault.adjustAlpha(DARKENED_COLOR_ALPHA))
//  )
//}