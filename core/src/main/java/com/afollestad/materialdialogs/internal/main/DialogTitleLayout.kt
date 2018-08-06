package com.afollestad.materialdialogs.internal.main

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.View.MeasureSpec.AT_MOST
import android.view.View.MeasureSpec.EXACTLY
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
  private val titleMarginBottom = dimenPx(R.dimen.md_dialog_title_layout_margin_bottom)
  private val frameMarginHorizontal = dimenPx(R.dimen.md_dialog_frame_margin_horizontal)

  private val iconMargin = dimenPx(R.dimen.md_icon_margin)
  private val iconSize = dimenPx(R.dimen.md_icon_size)

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
          MeasureSpec.makeMeasureSpec(iconSize, EXACTLY),
          MeasureSpec.makeMeasureSpec(iconSize, EXACTLY)
      )
    }

    val titleMaxWidth =
      parentWidth - (frameMarginHorizontal * 2)
    titleView.measure(
        MeasureSpec.makeMeasureSpec(titleMaxWidth, AT_MOST),
        MeasureSpec.makeMeasureSpec(0, UNSPECIFIED)
    )

    val iconViewHeight =
      if (iconView.isVisible()) iconView.measuredHeight else 0
    val requiredHeight = max(
        iconViewHeight, titleView.measuredHeight
    )
    val actualHeight = requiredHeight + frameMarginVertical + titleMarginBottom

    setMeasuredDimension(
        parentWidth,
        actualHeight
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
    val titleBottom = measuredHeight - titleMarginBottom
    val titleTop = titleBottom - titleView.measuredHeight
    val titleRight = titleLeft + titleView.measuredWidth

    if (iconView.isVisible()) {
      val titleHalfHeight = (titleBottom - titleTop) / 2
      val titleMidPoint = titleBottom - titleHalfHeight
      val iconHalfHeight = iconView.measuredHeight / 2
      val iconLeft = titleLeft
      val iconTop = titleMidPoint - iconHalfHeight
      val iconRight = iconLeft + iconView.measuredWidth
      val iconBottom = iconTop + iconView.measuredHeight
      iconView.layout(iconLeft, iconTop, iconRight, iconBottom)
      titleLeft = iconRight + iconMargin
    }

    titleView.layout(titleLeft, titleTop, titleRight, titleBottom)
  }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)

    if (dialogParent().debugMode) {
      canvas.drawColor(Color.parseColor("#4000cc00"))
    }

    if (drawDivider) {
      canvas.drawLine(
          0f,
          measuredHeight.toFloat() - dividerHeight.toFloat(),
          measuredWidth.toFloat(),
          measuredHeight.toFloat(),
          dividerPaint()
      )
    }
  }
}