package com.afollestad.materialdialogs.internal.main

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.View.MeasureSpec.AT_MOST
import android.view.View.MeasureSpec.UNSPECIFIED
import android.widget.ImageView
import android.widget.TextView
import com.afollestad.materialdialogs.R
import com.afollestad.materialdialogs.extensions.dimenPx
import com.afollestad.materialdialogs.extensions.isNotVisible
import com.afollestad.materialdialogs.extensions.isVisible
import com.afollestad.materialdialogs.internal.BaseSubLayout
import java.lang.Math.max

/**
 * Manages the header frame of the dialog, including the optional icon and title.
 *
 * @author Aidan Follestad (afollestad)
 */
internal class DialogTitleLayout(
  context: Context,
  attrs: AttributeSet? = null
) : BaseSubLayout(context, attrs) {

  private val frameMarginVertical = dimenPx(R.dimen.md_dialog_frame_margin_vertical)
  private val frameMarginHorizontal = dimenPx(R.dimen.md_dialog_frame_margin_horizontal)
  private val iconMargin = dimenPx(R.dimen.md_icon_margin)

  internal lateinit var iconView: ImageView
  internal lateinit var titleView: TextView

  override fun onFinishInflate() {
    super.onFinishInflate()
    iconView = findViewById(R.id.md_icon_title)
    titleView = findViewById(R.id.md_text_title)
  }

  fun shouldNotBeVisible() =
    iconView.isNotVisible() && titleView.isNotVisible()

  override fun onMeasure(
    widthMeasureSpec: Int,
    heightMeasureSpec: Int
  ) {
    if (shouldNotBeVisible()) {
      setMeasuredDimension(0, 0)
      return
    }

    val parentWidth = MeasureSpec.getSize(widthMeasureSpec)

    if (iconView.isVisible()) {
      iconView.measure(
          MeasureSpec.makeMeasureSpec(0, UNSPECIFIED),
          MeasureSpec.makeMeasureSpec(0, UNSPECIFIED)
      )
    }

    val titleMaxWidth =
      measuredWidth - (frameMarginHorizontal * 2)
    titleView.measure(
        MeasureSpec.makeMeasureSpec(titleMaxWidth, AT_MOST),
        MeasureSpec.makeMeasureSpec(0, UNSPECIFIED)
    )

    val iconViewHeight =
      if (iconView.isVisible()) iconView.measuredHeight else 0
    val requiredHeight = max(
        iconViewHeight, titleView.measuredHeight
    )

    setMeasuredDimension(
        parentWidth,
        requiredHeight + (frameMarginVertical * 2)
    )
  }

  override fun onLayout(
    changed: Boolean,
    left: Int,
    top: Int,
    right: Int,
    bottom: Int
  ) {
    if (shouldNotBeVisible()) return

    var titleLeft = frameMarginHorizontal
    val midPointY = measuredHeight / 2

    if (iconView.isVisible()) {
      val iconHalfHeight = iconView.measuredHeight / 2
      val iconLeft = titleLeft
      val iconTop = midPointY - iconHalfHeight
      val iconRight = left + iconView.measuredWidth
      val iconBottom = top + iconView.measuredHeight
      iconView.layout(iconLeft, iconTop, iconRight, iconBottom)
      titleLeft = iconRight + iconMargin
    }

    val titleHalfHeight = titleView.measuredHeight / 2
    val titleTop = midPointY - titleHalfHeight
    val titleRight = titleLeft + titleView.measuredWidth
    val titleBottom = titleTop + titleView.measuredHeight
    titleView.layout(titleLeft, titleTop, titleRight, titleBottom)
  }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)

    if (dialogParent().debugMode) {
      canvas.drawColor(Color.parseColor("#4000cc00"))
    }

    if (drawDivider) {
      dividerPaint.color = getDividerColor()
      canvas.drawLine(
          0f,
          measuredHeight.toFloat() - dividerHeight.toFloat(),
          measuredWidth.toFloat(),
          measuredHeight.toFloat(),
          dividerPaint
      )
    }
  }
}