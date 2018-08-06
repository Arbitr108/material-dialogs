package com.afollestad.materialdialogs.internal

import android.content.Context
import android.graphics.Paint
import android.graphics.Paint.Style.STROKE
import android.support.annotation.ColorInt
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.ViewGroup
import com.afollestad.materialdialogs.R
import com.afollestad.materialdialogs.Theme
import com.afollestad.materialdialogs.Theme.LIGHT
import com.afollestad.materialdialogs.extensions.dimenPx

internal abstract class BaseSubLayout(
  context: Context,
  attrs: AttributeSet? = null
) : ViewGroup(context, attrs) {

  private val dividerPaint = Paint()
  private var debugPaint: Paint? = null

  protected val dividerHeight = dimenPx(R.dimen.md_divider_height)

  var drawDivider: Boolean = false
    set(value) {
      field = value
      invalidate()
    }
  var theme: Theme = LIGHT
    get() = dialogParent().theme

  init {
    setWillNotDraw(false)
    dividerPaint.style = STROKE
    dividerPaint.strokeWidth = context.resources.getDimension(R.dimen.md_divider_height)
    dividerPaint.isAntiAlias = true
  }

  protected fun dialogParent(): DialogLayout {
    return parent as DialogLayout
  }

  protected fun dividerPaint(): Paint {
    dividerPaint.color = getDividerColor()
    return dividerPaint
  }

  protected fun debugPaint(@ColorInt color: Int): Paint {
    if (debugPaint == null) {
      debugPaint = Paint()
      debugPaint!!.style = STROKE
    }
    debugPaint!!.color = color
    return debugPaint!!
  }

  private fun getDividerColor(): Int {
    val colorRes =
      if (theme == LIGHT) R.color.md_divider_black else R.color.md_divider_white
    return ContextCompat.getColor(context, colorRes)
  }
}